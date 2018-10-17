package practice.rpcfs.chp7;

import practice.rpcfs.chp4.ProviderService;

import java.util.List;

public interface ClusterStrategy {
    ProviderService select(List<ProviderService> providerServices);
}
