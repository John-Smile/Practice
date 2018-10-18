package practice.rpcfs.chp7;

import com.google.common.collect.Lists;
import practice.rpcfs.chp4.ProviderService;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WeightPollingClusterStragetryImpl implements ClusterStrategy {
    private int index = 0;
    private Lock lock  = new ReentrantLock();
    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        ProviderService providerService = null;
        try {
            lock.tryLock(10, TimeUnit.MILLISECONDS);
            List<ProviderService> providerServiceList = Lists.newArrayList();
            for (ProviderService provider : providerServices) {
                int weight = provider.getWeight();
                for (int i = 0; i < weight; ++i) {
                    providerServiceList.add(provider);
                }
            }
            if (index >= providerServiceList.size()) {
                index = 0;
            }
            providerService = providerServiceList.get(index);
            index++;
            return providerService;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return providerServices.get(0);
    }
}
