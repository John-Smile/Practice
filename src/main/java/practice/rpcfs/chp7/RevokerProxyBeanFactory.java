package practice.rpcfs.chp7;

import practice.rpcfs.chp4.ProviderService;
import practice.rpcfs.chp5.IRegisterCenter4Invoker;
import practice.rpcfs.chp5.RegisterCenter;
import practice.rpcfs.chp6.AresRequest;
import practice.rpcfs.chp6.AresResponse;
import practice.rpcfs.chp6.RevokerServiceCallable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RevokerProxyBeanFactory implements InvocationHandler {
    private ExecutorService fixedThreadPool = null;
    private Class<?> targetInterface;
    private int consumeTimeout;
    private static int threadWorkNumber = 10;
    private String clusterStrategy;

    public RevokerProxyBeanFactory(Class<?> targetInterface, int consumeTimeout, String clusterStrategy) {
        this.targetInterface = targetInterface;
        this.consumeTimeout = consumeTimeout;
        this.clusterStrategy = clusterStrategy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceKey = targetInterface.getName();
        IRegisterCenter4Invoker registerCenter4Invoker = RegisterCenter.singleton();
        List<ProviderService> providerServices = registerCenter4Invoker.getServiceMetaDataMap4Consumer().get(serviceKey);
        ClusterStrategy clusterStrategy = ClusterEngine.queryClusterStrategy(this.clusterStrategy);
        ProviderService providerService = clusterStrategy.select(providerServices);
        ProviderService newProvider = providerService.copy();
        newProvider.setServiceMethod(method);
        newProvider.setServiceItf(targetInterface);
        final AresRequest request = new AresRequest();
        request.setUniqueKey(UUID.randomUUID().toString() + "-" + Thread.currentThread().getId());
        request.setProviderService(newProvider);
        request.setInvokeTimeout(consumeTimeout);
        request.setInvokedMethodName(method.getName());
        request.setArgs(args);
        try {
            if (fixedThreadPool == null) {
                synchronized (RevokerProxyBeanFactory.class) {
                    if (null == fixedThreadPool) {
                        fixedThreadPool = Executors.newFixedThreadPool(threadWorkNumber);
                    }
                }
            }
            String serverIp = request.getProviderService().getServerIp();
            int serverPort = request.getProviderService().getServerPort();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(serverIp, serverPort);
            Future<AresResponse> responseFuture = fixedThreadPool.submit(RevokerServiceCallable.of(inetSocketAddress, request));
            AresResponse aresResponse = responseFuture.get(request.getInvokeTimeout(), TimeUnit.MILLISECONDS);
            if (aresResponse != null) {
                return aresResponse.getResult();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{targetInterface}, this);
    }
    private static volatile RevokerProxyBeanFactory singleton;
    public static RevokerProxyBeanFactory singleton(Class<?> targetInterface, int consumeTimeout, String clusterStrategy) throws Exception {
        if (null == singleton) {
            synchronized (RevokerProxyBeanFactory.class) {
                if (null == singleton) {
                    singleton = new RevokerProxyBeanFactory(targetInterface, consumeTimeout, clusterStrategy);
                }
            }
        }
        return singleton;
    }
}
