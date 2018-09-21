package practice.rpcfs.chp5;

import practice.rpcfs.chp4.ProviderService;

import java.util.List;
import java.util.Map;

public interface IRegisterCenter4Invoker {
    void initProviderMap();
    Map<String, List<ProviderService>> getServiceMetaDataMap4Consume();
    void registerInvoker(final InvokerService invoker);
}
