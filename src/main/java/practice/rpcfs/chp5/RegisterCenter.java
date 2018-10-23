package practice.rpcfs.chp5;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import practice.rpcfs.chp4.IPHelper;
import practice.rpcfs.chp4.ProviderService;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class RegisterCenter implements IRegisterCenter4Invoker, IRegisterCenter4Provider {
    private static RegisterCenter registerCenter = new RegisterCenter();
    private static final Map<String, List<ProviderService>> providerServiceMap = Maps.newConcurrentMap();
    private static final Map<String, List<ProviderService>> serviceMetaDtaMap4Consume = Maps.newConcurrentMap();
    private static String ZK_SERVICE = PropertyConfigeHelper.getZkService();
    private static int ZK_SESSION_TIME_OUT = PropertyConfigeHelper.getZkSessionTimeout();
    private static int ZK_CONNECTION_TIME_OUT = PropertyConfigeHelper.getZkConnectTimeout();
    private static String ROOT_PATH = "/config_register/" + PropertyConfigeHelper.getAppName();
    public static String PROVIDER_TYPE = "/provider";
    public static String INVOKER_TYPE = "/consumer";
    private static volatile ZkClient zkClient = null;

    private RegisterCenter() {
    }

    public static RegisterCenter singleton() {
        return registerCenter;
    }

//    @Override
    public void initProviderMap() {
        if (MapUtils.isEmpty(serviceMetaDtaMap4Consume)) {
            serviceMetaDtaMap4Consume.putAll(fetchOrUpdateServiceMetaData());
        }
    }

    private Map<? extends String,? extends List<ProviderService>> fetchOrUpdateServiceMetaData() {
        final Map<String, List<ProviderService>> providerServiceMap = Maps.newConcurrentMap();

        synchronized (RegisterCenter.class) {
            if (zkClient == null) {
                zkClient = new ZkClient(ZK_SERVICE, ZK_SESSION_TIME_OUT, ZK_CONNECTION_TIME_OUT, new SerializableSerializer());
            }

            String  providerPath = ROOT_PATH;
            List<String> providerServices = zkClient.getChildren(providerPath);
            for (String serviceName : providerServices) {
                String servicePath = providerPath + "/" + serviceName + PROVIDER_TYPE;
                List<String> ipPathList = zkClient.getChildren(servicePath);
                for (String ipPath : ipPathList) {
                    String serverIp = StringUtils.split(ipPath, "|")[0];
                    String serverPort = StringUtils.split(ipPath, "|")[1];

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
                            public String apply(@Nullable String input) {
                                return StringUtils.split(input, "|")[0];
                            }
                        }));
                        refreshServiceMetaDataMap(currentChilds);
                    }
                });
            }
        }
        return providerServiceMap;
    }

    @Override
    public void initProviderMap(String remoteAppKey, String groupName) {

    }

    @Override
    public Map<String, List<ProviderService>> getServiceMetaDataMap4Consumer() {
        return serviceMetaDtaMap4Consume;
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

            // TOOD 创建服务提供者节点

            String serviceNode = invoker.getServiceItf.getName();
            String servicePath = ROOT_PATH + "/" + serviceNode + INVOKER_TYPE;
            exist = zkClient.exists(servicePath);
            if (!exist) {
                zkClient.createPersistent(servicePath);
            }

            String localIp = IPHelper.localIp();
            String currentServiceIpNode = servicePath + "/" + localIp;
            exist = zkClient.exists(currentServiceIpNode);
            if (!exist) {
                zkClient.createEphemeral(currentServiceIpNode);
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
            boolean exists = zkClient.exists(ROOT_PATH);
            if (!exists) {
                zkClient.createPersistent(ROOT_PATH, true);
            }
            exists = zkClient.exists(ROOT_PATH + PROVIDER_TYPE);
            if (!exists) {
                zkClient.createPersistent(ROOT_PATH + PROVIDER_TYPE);
            }

            for (Map.Entry<String, List<ProviderService>> entry : providerServiceMap.entrySet()) {
                String serviceNode = entry.getKey();
                String servicePath = ROOT_PATH + "/" + serviceNode + PROVIDER_TYPE;
                exists = zkClient.exists(servicePath);
                if (!exists) {
                    zkClient.createPersistent(servicePath, true);
                }

                int servicePort = entry.getValue().get(0).getServicePort();
                String localIp = IPHelper.localIp();
                String currentServiceIpNode = servicePath + "/" + localIp + "|" + servicePort;
                exists = zkClient.exists(currentServiceIpNode);
                if (!exists) {
                    zkClient.createEphemeral(currentServiceIpNode);
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
                            public String apply(@Nullable String input) {
                                return StringUtils.split(input, "|")[0];
                            }
                        }));
                        refreshActivityService(activityServiceIpList);
                    }
                });
             }
        }
    }

    // FIXME
    // 写的不知所云
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
        providerServiceMap.putAll(currentServiceMetaDataMap);
    }

    @Override
    public Map<String, List<ProviderService>> getProviderServiceMap() {
        return providerServiceMap;
    }

    private void refreshServiceMetaDataMap(List<String> serviceIpList) {
        if (serviceIpList == null) {
            serviceIpList = Lists.newArrayList();
        }

        Map<String, List<ProviderService>> currentServiceMetaDataMap = Maps.newHashMap();
        for (Map.Entry<String, List<ProviderService>> entry : serviceMetaDtaMap4Consume.entrySet()) {
            String serviceItfKey = entry.getKey();
            List<ProviderService> serviceList = entry.getValue();

            List<ProviderService> providerServiceList = currentServiceMetaDataMap.get(serviceItfKey);
            if (providerServiceList == null) {
                providerServiceList = Lists.newArrayList();
            }

            for (ProviderService serviceMetaData : serviceList) {
                if (serviceIpList.contains(serviceMetaData.getServerIp())) {
                    providerServiceList.add(serviceMetaData);
                }
            }
            currentServiceMetaDataMap.put(serviceItfKey, providerServiceList);
        }
        serviceMetaDtaMap4Consume.putAll(currentServiceMetaDataMap);
        System.out.println("serviceMetaDataMap4Consume:" + JSON.toJSONString(serviceMetaDtaMap4Consume));
    }
}
