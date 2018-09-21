package practice.rpcfs.chp5;

import practice.rpcfs.chp4.ProviderService;

import java.util.List;
import java.util.Map;

public interface IRegisterCenter4Provider {
    void registerProvider(final List<ProviderService> serviceMetaData);
    Map<String, List<ProviderService>> getProviderServiceMap();
}
