package practice.rpcfs.chp4;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class RevokerFactoryBean implements FactoryBean, InitializingBean {
    // 服务接口
    private Class<?> targetInterface;
    // 超时时间
    private  int timeout;
    // 服务bean
    private Object serviceObject;
    // 负载均衡策略
    private String clusterStrategy;
    // 服务提供者唯一标识
    private String remoteAppKey;
    // 服务分组组名
    private String groupName = "default";
    @Override
    public Object getObject() throws Exception {
        return serviceObject;
    }

    @Override
    public Class<?> getObjectType() {
        return targetInterface;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 获取服务注册中心
        // TODO 初始化服务提供者列表到本地缓存
        // TODO 初始化Netty Channel
        // TODO 获取服务提供者代理对象
        // TODO 将消费者信息注册到注册中心
    }

    public Class<?> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<?> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    public String getClusterStrategy() {
        return clusterStrategy;
    }

    public void setClusterStrategy(String clusterStrategy) {
        this.clusterStrategy = clusterStrategy;
    }

    public String getRemoteAppKey() {
        return remoteAppKey;
    }

    public void setRemoteAppKey(String remoteAppKey) {
        this.remoteAppKey = remoteAppKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
