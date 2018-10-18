package practice.rpcfs.chp7;

import practice.rpcfs.chp4.IPHelper;
import practice.rpcfs.chp4.ProviderService;

import java.util.List;

public class HashClusterStrategyImpl implements ClusterStrategy {
    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        String localIP = IPHelper.localIp();
        int hashCode = localIP.hashCode();
        int size = providerServices.size();
        return providerServices.get(hashCode % size);
    }
}
