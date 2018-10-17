package practice.rpcfs.chp6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientInvokerHandler extends SimpleChannelInboundHandler<AresResponse> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, AresResponse response) throws Exception {
        RevokerResponseHolder.putResultValue(response);
    }
}
