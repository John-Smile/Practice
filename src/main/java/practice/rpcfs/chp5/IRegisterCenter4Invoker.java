package practice.rpcfs.chp5;

import practice.rpcfs.chp4.ProviderService;

import java.util.List;
import java.util.Map;

public interface IRegisterCenter4Invoker {
    void initProviderMap(String remoteAppKey, String groupName);
    Map<String, List<ProviderService>> getServiceMetaDataMap4Consumer();
    void registerInvoker(final InvokerService invoker);
}
