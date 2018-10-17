package practice.rpcfs.chp6;

import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import practice.rpcfs.chp4.ProviderService;
import practice.rpcfs.chp5.IRegisterCenter4Provider;
import practice.rpcfs.chp5.RegisterCenter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class NettyServerInvokeHandler extends SimpleChannelInboundHandler<AresRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerInvokeHandler.class);
    private static final Map<String, Semaphore> serviceKeySemaphoreMap = Maps.newConcurrentMap();
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, AresRequest request) throws Exception {
        if (ctx.channel().isWritable()) {
            ProviderService medatDataModel = request.getProviderService();
            long consumeTimeout = request.getInvokeTimeout();
            final String methodName = request.getInvokedMethodName();
            String serviceKey = medatDataModel.getServiceItf().getName();
            int workerThread = medatDataModel.getWorkerThreads();
            Semaphore semaphore = serviceKeySemaphoreMap.get(serviceKey);
            if (semaphore == null) {
                synchronized (serviceKeySemaphoreMap) {
                    semaphore = serviceKeySemaphoreMap.get(serviceKey);
                    if (semaphore == null) {
                        semaphore = new Semaphore(workerThread);
                        serviceKeySemaphoreMap.put(serviceKey, semaphore);
                    }
                }
            }
            IRegisterCenter4Provider registerCenter4Provider = RegisterCenter.singleton();
            List<ProviderService> localProviderCaches = registerCenter4Provider.getProviderServiceMap().get(serviceKey);
            ProviderService localProviderCache = Collections2.filter(localProviderCaches, input -> StringUtils.equals(input.getServiceMethod().getName(), methodName)).iterator().next();
            Object serviceObject = localProviderCache.getServiceObject();
            Method method = localProviderCache.getServiceMethod();
            Object result = null;
            boolean acquire = false;
            try {
                acquire = semaphore.tryAcquire(consumeTimeout, TimeUnit.MILLISECONDS);
                if (acquire) {
                    result = method.invoke(serviceObject, request.getArgs());
                }
            } catch (Exception e) {
                if (acquire) {
                    semaphore.release();
                }
            }
            AresResponse response = new AresResponse();
            response.setInvokeTimeout(consumeTimeout);
            response.setUniqueKey(request.getUniqueKey());
            response.setResult(result);
            ctx.writeAndFlush(response);
        } else {
            logger.error("channel close!");
        }
    }
}
