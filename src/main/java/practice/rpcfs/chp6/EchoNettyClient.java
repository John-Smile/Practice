package practice.rpcfs.chp6;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoNettyClient {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        new EchoNettyClient().connect(port, "127.0.0.1");
    }

    private void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoNettyClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            byte[] req = "Hello, Netty".getBytes();
            ByteBuf messageBuffer = Unpooled.buffer(req.length);
            messageBuffer.writeBytes(req);
            ChannelFuture channelFuture = f.channel().writeAndFlush(messageBuffer);
            channelFuture.sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
