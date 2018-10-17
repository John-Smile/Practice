package practice.rpcfs.chp7;

import practice.rpcfs.chp4.ProviderService;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PollingClusterStrategyImpl implements ClusterStrategy {
    private int index = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public ProviderService select(List<ProviderService> providerServices) {
        ProviderService providerService = null;
        try {
            lock.tryLock(10, TimeUnit.MILLISECONDS);
            if (index >= providerServices.size()) {
                index = 0;
            }
            providerService = providerServices.get(index);
            index++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        if (providerService == null) {
            providerService = providerServices.get(0);
        }
        return providerService;
    }
}
