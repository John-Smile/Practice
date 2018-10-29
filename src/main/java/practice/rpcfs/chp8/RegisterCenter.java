package practice.rpcfs.chp8;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;
import practice.rpcfs.chp4.IPHelper;
import practice.rpcfs.chp4.ProviderService;
import practice.rpcfs.chp5.IRegisterCenter4Invoker;
import practice.rpcfs.chp5.IRegisterCenter4Provider;
import practice.rpcfs.chp5.InvokerService;
import practice.rpcfs.chp5.PropertyConfigeHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class RegisterCenter implements IRegisterCenter4Invoker, IRegisterCenter4Provider, IRegisterCenter4Governance {
    private static RegisterCenter registerCenter = new RegisterCenter();
    private static final Map<String, List<ProviderService>> providerServiceMap = Maps.newConcurrentMap();
    private static final Map<String, List<ProviderService>> serviceMetaDataMap4Consume = Maps.newConcurrentMap();
    private static String ZK_SERVICE = PropertyConfigeHelper.getZkService();
    private static int ZK_SESSION_TIME_OUT = PropertyConfigeHelper.getZkSessionTimeout();
    private static int ZK_CONNECTION_TIME_OUT = PropertyConfigeHelper.getZkConnectTimeout();
    private static String ROOT_PATH = "/config_register";
    public static String PROVIDER_TYPE = "provider";
    public static String INVOKER_TYPE = "consumer";
    private static volatile ZkClient zkClient = null;
    @Override
    public void initProviderMap(String remoteAppKey, String groupName) {
        if (MapUtils.isEmpty(serviceMetaDataMap4Consume)) {
            serviceMetaDataMap4Consume.putAll(fetchOrUpdateServiceMetaData(remoteAppKey, groupName));
        }
    }

    private Map<String, List<ProviderService>> fetchOrUpdateServiceMetaData(String remoteAppKey, String groupName) {
        final Map<String, List<ProviderService>> providerServiceMap = Maps.newConcurrentMap();
        synchronized (RegisterCenter.class) {
            if (zkClient == null) {
                zkClient = new ZkClient(ZK_SERVICE, ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
            }
        }
        String providerPath = ROOT_PATH + "/" + remoteAppKey + "/" + groupName;
        List<String> providerServices = zkClient.getChildren(providerPath);
        for (String serviceName : providerServices) {
            String servicePath = providerPath + "/" + serviceName + "/" + PROVIDER_TYPE;
            List<String> ipPathList = zkClient.getChildren(servicePath);
            for (String ipPath : ipPathList) {
                String serverIp = StringUtils.split(ipPath, "|")[0];
                String serverPort = StringUtils.split(ipPath, "|")[1];
                int weight = Integer.parseInt(StringUtils.split(ipPath, "|")[2]);
                int workerThreads = Integer.parseInt(StringUtils.split(ipPath, "|")[3]);
                String group = StringUtils.split(ipPath, "|")[4];
                List<ProviderService> providerServiceList = providerServiceMap.get(serviceName);
                if (providerServiceList == null) {
                    providerServiceList = Lists.newArrayList();
                }
                ProviderService providerService = new ProviderService();
                try {
                    providerService.setServiceItf(ClassUtils.getClass(serviceName));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                providerService.setServerIp(serverIp);
                providerService.setServerPort(Integer.parseInt(serverPort));
                providerService.setWeight(weight);
                providerService.setWorkerThreads(workerThreads);
                providerService.setGroupName(groupName);
                providerServiceList.add(providerService);
                providerServiceMap.put(serviceName, providerServiceList);
            }
            zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    if (currentChilds == null) {
                        currentChilds = Lists.newArrayList();
                    }
                    currentChilds = Lists.newArrayList(Lists.transform(currentChilds, new Function<String, String>() {
                        @Nullable
                        @Override
                        public String apply(@Nullable String s) {
                            return StringUtils.split(s, "|")[0];
                        }
                    }));
                    refreshServiceMetaDataMap(currentChilds);
                }
            });
        }
        return providerServiceMap;
    }

    private void refreshServiceMetaDataMap(List<String> currentChilds) {
    }

    public static RegisterCenter singleton() {
        return registerCenter;
    }

    @Override
    public Map<String, List<ProviderService>> getServiceMetaDataMap4Consumer() {
        return serviceMetaDataMap4Consume;
    }

    @Override
    public void registerInvoker(InvokerService invoker) {
        if (invoker == null) {
            return;
        }
        synchronized (RegisterCenter.class) {
            if (zkClient == null) {
                zkClient = new ZkClient(ZK_SERVICE, ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
            }
            boolean exist = zkClient.exists(ROOT_PATH);
            if (!exist) {
                zkClient.createPersistent(ROOT_PATH, true);
            }
            String remoteAppKey = invoker.getRemoteAppKey();
            String groupName = invoker.getGroupName();
            String serviceNode = invoker.getServiceItf.getName();
            String servicePath = ROOT_PATH + "/" + remoteAppKey + "/" + groupName + "/" + serviceNode + "/" + INVOKER_TYPE;
            exist = zkClient.exists(servicePath);
            if (!exist) {
                zkClient.createPersistent(servicePath, true);
            }
            String localIp = IPHelper.localIp();
            String currentServiceIpNodes = servicePath + "/" + localIp;
            exist = zkClient.exists(currentServiceIpNodes);
            if (!exist) {
                zkClient.createEphemeral(currentServiceIpNodes);
            }
        }
    }

    @Override
    public void registerProvider(List<ProviderService> serviceMetaData) {
        if (CollectionUtils.isEmpty(serviceMetaData)) {
            return;
        }
        synchronized (RegisterCenter.class) {
            for (ProviderService provider : serviceMetaData) {
                String serviceItfKey = provider.getServiceItf().getName();
                List<ProviderService> providers = providerServiceMap.get(serviceItfKey);
                if (providers == null) {
                    providers = Lists.newArrayList();
                }
                providers.add(provider);
                providerServiceMap.put(serviceItfKey, providers);
            }
            if (zkClient == null) {
                zkClient = new ZkClient(ZK_SERVICE, ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
            }
            String APP_KEY = serviceMetaData.get(0).getAppKey();
            String ZK_PATH = ROOT_PATH + "/" + APP_KEY;
            boolean exist = zkClient.exists(ZK_PATH);
            if (!exist) {
                zkClient.createPersistent(ZK_PATH, true);
            }
            for (Map.Entry<String, List<ProviderService>> entry : providerServiceMap.entrySet()) {
                String groupName = entry.getValue().get(0).getGroupName();
                String serviceNode = entry.getKey();
                String servicePath = ZK_PATH + "/" + groupName + "/" + serviceNode + "/" + PROVIDER_TYPE;
                exist = zkClient.exists(servicePath);
                if (!exist) {
                    zkClient.createPersistent(servicePath, true);
                }
                int serverPort = entry.getValue().get(0).getServerPort();
                int weight = entry.getValue().get(0).getWeight();
                int workThreads = entry.getValue().get(0).getWorkerThreads();
                String localIp = IPHelper.localIp();
                String currentServiceIpNodes = serviceNode + "/" + localIp + "|" + serverPort + "|" + weight + "|" + workThreads + "|" + groupName;
                exist = zkClient.exists(currentServiceIpNodes);
                if (!exist) {
                    zkClient.createEphemeral(currentServiceIpNodes);
                }
                zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
                    @Override
                    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                        if (currentChilds == null) {
                            currentChilds = Lists.newArrayList();
                        }
                        List<String> activityServiceIpList = Lists.newArrayList(Lists.transform(currentChilds, new Function<String, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable String s) {
                                return StringUtils.split(s, "|")[0];
                            }
                        }));
                        refreshActivityService(activityServiceIpList);
                    }
                });
            }
        }
    }
    @Override
    public Map<String, List<ProviderService>> getProviderServiceMap() {
        return providerServiceMap;
    }
    private void refreshActivityService(List<String> serviceIpList) {
        if (serviceIpList == null) {
            serviceIpList = Lists.newArrayList();
        }
        Map<String, List<ProviderService>> currentServiceMetaDataMap = Maps.newHashMap();
        for (Map.Entry<String, List<ProviderService>> entry : providerServiceMap.entrySet()) {
            String key = entry.getKey();
            List<ProviderService> providerServices = entry.getValue();
            List<ProviderService> serviceMetaDataModelList = currentServiceMetaDataMap.get(key);
            if (serviceMetaDataModelList == null) {
                serviceMetaDataModelList = Lists.newArrayList();
            }
            for (ProviderService serviceMetaData : providerServices) {
                if (serviceIpList.contains(serviceMetaData.getServerIp())) {
                    serviceMetaDataModelList.add(serviceMetaData);
                }
            }
            currentServiceMetaDataMap.put(key, serviceMetaDataModelList);
        }
        providerServiceMap.clear();
        providerServiceMap.putAll(currentServiceMetaDataMap);
    }

    @Override
    public Pair<List<ProviderService>, List<InvokerService>> queryProvidersAndInvokers(String serviceName, String appKey) {
        List<InvokerService> invokerServices = Lists.newArrayList();
        List<ProviderService> providerServices = Lists.newArrayList();
        if (zkClient == null) {
            synchronized (RegisterCenter.class) {
                if (zkClient == null) {
                    zkClient = new ZkClient(ZK_SERVICE, ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
                }
            }
        }
        String parentPath = ROOT_PATH + "/" + appKey;
        List<String> groupServiceList = zkClient.getChildren(parentPath);
        if (CollectionUtils.isEmpty(groupServiceList)) {
            return Pair.of(providerServices, invokerServices);
        }
        for (String group : groupServiceList) {
            String groupPath = parentPath + "/" + group;
            List<String> serviceList = zkClient.getChildren(groupPath);
            if (CollectionUtils.isEmpty(serviceList)) {
                continue;
            }
            for (String service : serviceList) {
                String servicePath = groupPath + "/" + service;
                List<String> serviceTypes = zkClient.getChildren(servicePath);
                if (CollectionUtils.isEmpty(serviceTypes)) {
                    continue;
                }
                for (String serviceType : serviceTypes) {
                    if (StringUtils.equals(serviceType, PROVIDER_TYPE)) {
                        String providerPath = servicePath + "/" + serviceType;
                        List<String> providers = zkClient.getChildren(providerPath);
                        if (CollectionUtils.isEmpty(providers)) {
                            continue;
                        }
                        for (String provider : providers) {
                            String[] providerNodeArr = StringUtils.split(provider, "|");
                            ProviderService providerService = new ProviderService();
                            providerService.setAppKey(appKey);
                            providerService.setGroupName(group);
                            providerService.setServerIp(providerNodeArr[0]);
                            providerService.setServerPort(Integer.parseInt(providerNodeArr[1]));
                            providerService.setWeight(Integer.parseInt(providerNodeArr[2]));
                            providerService.setWorkerThreads(Integer.parseInt(providerNodeArr[3]));
                            providerServices.add(providerService);
                        }
                    } else if (StringUtils.equals(serviceType, INVOKER_TYPE)) {
                        String invokerPath = servicePath + "/" + serviceType;
                        List<String> invokers = zkClient.getChildren(invokerPath);
                        if (CollectionUtils.isEmpty(invokers)) {
                            continue;
                        }
                        for (String invoker : invokers) {
                            InvokerService invokerService = new InvokerService();
                            invokerService.setRemoteAppKey(appKey);
                            invokerService.setGroupName(group);
                            invokerService.setInvokerIp(invoker);
                            invokerServices.add(invokerService);
                        }
                    }
                }
            }
        }
        return Pair.of(providerServices, invokerServices);
    }

    public static void main(String[] args) {
        IRegisterCenter4Governance invoker = RegisterCenter.singleton();
        Pair<List<ProviderService>, List<InvokerService>> pair = invoker.queryProvidersAndInvokers("com.dubbo.HelloService", "hello");
        List<ProviderService> providerServices = pair.getLeft();
        List<InvokerService> invokerServices = pair.getRight();
        System.out.println(providerServices.size() + " " + invokerServices.size());
    }
}
