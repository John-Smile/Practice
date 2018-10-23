package practice.rpcfs.chp4;

import java.lang.reflect.Method;

public class ProviderService {
    private Class<?> serviceItf;
    private Object serviceObject;
    private String serverIp;
    private int servicePort;
    private long timeout;
    private Method serviceMethod;
    private int weight;
    private int workerThreads;
    private String groupName;
    private int serverPort;

    public Class<?> getServiceItf() {
        return serviceItf;
    }

    public Object getServiceObject() {
        return serviceObject;
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServicePort() {
        return servicePort;
    }

    public long getTimeout() {
        return timeout;
    }

    public Method getServiceMethod() {
        return serviceMethod;
    }

    public int getWeight() {
        return weight;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setServiceItf(Class<?> serviceItf) {
        this.serviceItf = serviceItf;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setServiceMethod(Method serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public ProviderService copy() {
        return null;
    }

    private String appKey;
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
