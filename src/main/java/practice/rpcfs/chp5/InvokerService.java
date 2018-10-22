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
}
