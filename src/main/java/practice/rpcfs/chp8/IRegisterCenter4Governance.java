package practice.rpcfs.chp8;

import org.apache.commons.lang3.tuple.Pair;
import practice.rpcfs.chp4.ProviderService;
import practice.rpcfs.chp5.InvokerService;

import java.util.List;

public interface IRegisterCenter4Governance {
    Pair<List<ProviderService>, List<InvokerService>> queryProvidersAndInvokers(String serviceName, String appKey);
}
