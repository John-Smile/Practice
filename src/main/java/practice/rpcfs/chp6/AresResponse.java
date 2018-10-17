package practice.rpcfs.chp6;

public class AresResponse {
    private long invokeTimeout;
    private String uniqueKey;
    private Object result;

    public long getInvokeTimeout() {
        return invokeTimeout;
    }

    public void setInvokeTimeout(long invokeTimeout) {
        this.invokeTimeout = invokeTimeout;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }
}
