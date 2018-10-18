package practice.rpcfs.chp6;

import practice.rpcfs.chp4.ProviderService;

public class AresRequest {
    private ProviderService providerService;
    public ProviderService getProviderService() {
        return providerService;
    }

    private long invokerTimeout;
    public long getInvokeTimeout() {
        return invokerTimeout;
    }

    private String invokedMethodName;
    public String getInvokedMethodName() {
        return invokedMethodName;
    }

    private String uniqueKey;
    public String getUniqueKey() {
        return uniqueKey;
    }

    private Object[] args;
    public Object[] getArgs() {
        return args;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public void setProviderService(ProviderService providerService) {
        this.providerService = providerService;
    }

    private int invokeTimeout;
    public void setInvokeTimeout(int invokeTimeout) {
        this.invokeTimeout = invokeTimeout;
    }

    public void setInvokedMethodName(String invokedMethodName) {
        this.invokedMethodName = invokedMethodName;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
