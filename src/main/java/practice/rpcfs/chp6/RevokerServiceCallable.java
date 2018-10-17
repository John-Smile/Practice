package practice.rpcfs.chp6;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class RevokerServiceCallable implements Callable<AresResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RevokerServiceCallable.class);
    private Channel channel;
    private InetSocketAddress inetSocketAddress;
    private AresRequest request;
    public static RevokerServiceCallable of(InetSocketAddress inetSocketAddress, AresRequest request) {
        return new RevokerServiceCallable(inetSocketAddress, request);
    }
    public RevokerServiceCallable(InetSocketAddress socketAddress, AresRequest request) {
        this.inetSocketAddress = socketAddress;
        this.request = request;
    }
    @Override
    public AresResponse call() throws Exception {
        RevokerResponseHolder.initResponseData(request.getUniqueKey());
        ArrayBlockingQueue<Channel> blockingQueue = NettyChannelPoolFactory.channelPoolFactoryInstance().acquire(inetSocketAddress);
        try {
            if (channel == null) {
                channel = blockingQueue.poll(request.getInvokeTimeout(), TimeUnit.MILLISECONDS);
            }
            while (!channel.isOpen()
                    || !channel.isActive()
                    || !channel.isWritable()) {
                logger.warn("retry get new Channel");
                channel = blockingQueue.poll(request.getInvokeTimeout(), TimeUnit.MILLISECONDS);
                if (channel == null) {
                    channel = NettyChannelPoolFactory.channelPoolFactoryInstance().registerChannel(inetSocketAddress);
                }
            }
            ChannelFuture channelFuture = channel.writeAndFlush(request);
            channelFuture.syncUninterruptibly();
            long invokeTimeout = request.getInvokeTimeout();
            return RevokerResponseHolder.getValue(request.getUniqueKey(), invokeTimeout);
        } catch (Exception e) {
            logger.error("service invoke error.", e);
        } finally {
            NettyChannelPoolFactory.channelPoolFactoryInstance().release(blockingQueue, channel, inetSocketAddress);
        }
        return null;
    }
}
