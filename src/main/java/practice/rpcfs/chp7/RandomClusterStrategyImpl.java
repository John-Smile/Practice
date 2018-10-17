package practice.rpcfs.chp7;

import org.apache.commons.lang3.RandomUtils;
import practice.rpcfs.chp4.ProviderService;

import java.util.List;

public class RandomClusterStrategyImpl implements ClusterStrategy {
    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        int MAX_LEN = providerServices.size();
        int index = RandomUtils.nextInt(0, MAX_LEN - 1);
        return providerServices.get(index);
    }
}
