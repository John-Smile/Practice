package practice.rpcfs.chp5;

public class InvokerService {
    public Class<?> getServiceItf;

    private String remoteAppKey;
    public String getRemoteAppKey() {
        return remoteAppKey;
    }

    private String groupName;
    public String getGroupName() {
        return groupName;
    }

    public void setRemoteAppKey(String remoteAppKey) {
        this.remoteAppKey = remoteAppKey;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private String invokerIp;
    public void setInvokerIp(String invokerIp) {
        this.invokerIp = invokerIp;
    }
}
