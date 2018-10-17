package practice.rpcfs.chp7;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import practice.rpcfs.chp4.ProviderService;

import java.util.List;

public class WeightRandomClusterStrategyImpl implements ClusterStrategy {
    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        List<ProviderService> providerServiceList = Lists.newArrayList();
        for (ProviderService providerService : providerServices) {
            int weight = providerService.getWeight();
            for (int i = 0; i < weight; ++i) {
                providerServiceList.add(providerService);
            }
        }
        int MAX_LEN = providerServiceList.size();
        int index = RandomUtils.nextInt(0, MAX_LEN - 1);
        return providerServiceList.get(index);
    }
}
