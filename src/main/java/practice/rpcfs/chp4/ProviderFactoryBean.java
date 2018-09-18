package practice.rpcfs.chp4;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.List;

public class ProviderFactoryBean implements FactoryBean, InitializingBean {
    // 服务接口
    private Class<?> serviceItf;
    // 服务实现
    private Object serviceObject;
    // 服务端口
    private String servicePort;
    // 服务超时时间
    private long timeout;
    // 服务代理对象
    private Object serviceProxyObject;
    // 服务提供者唯一标识
    private String appKey;
    // 服务分组组名
    private String groupName = "default";
    // 服务提供者权重，默认1，范围[1-100]
    private int weight = 1;
    // 服务端线程数，默认10个线程
    private int workerThreads = 10;

    @Override
    public Object getObject() throws Exception {
        return serviceObject;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceItf;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 启动Netty服务端
        // TODO 注册到Zookeeper，元数据注册中心
        List<ProviderService> providerServiceList = buildProviderServiceInfos();
    }

    private List<ProviderService> buildProviderServiceInfos() {
        List<ProviderService> providerServiceList = Lists.newArrayList();
        Method[] methods = serviceObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            ProviderService providerService = new ProviderService();
            providerService.setServiceItf(serviceItf);
            providerService.setServiceObject(serviceObject);
            providerService.setServerIp(IPHelper.localIp());
            providerService.setServicePort(Integer.parseInt(servicePort));
            providerService.setTimeout(timeout);
            providerService.setServiceMethod(method);
            providerService.setWeight(weight);
            providerService.setWorkerThreads(workerThreads);
            providerService.setGroupName(groupName);
            providerServiceList.add(providerService);
        }
        return  providerServiceList;
    }

    public Class<?> getServiceItf() {
        return serviceItf;
    }

    public void setServiceItf(Class<?> serviceItf) {
        this.serviceItf = serviceItf;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Object getServiceProxyObject() {
        return serviceProxyObject;
    }

    public void setServiceProxyObject(Object serviceProxyObject) {
        this.serviceProxyObject = serviceProxyObject;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }
}
