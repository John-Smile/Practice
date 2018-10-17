package practice.rpcfs.chp6;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import practice.rpcfs.chp5.PropertyConfigeHelper;

public class NettyServer {
    private static NettyServer nettyServer = new NettyServer();
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private SerializeType serializeType = PropertyConfigeHelper.getSerializeType();
    public void start(final int port) {
        synchronized (NettyServer.class) {
            if (bossGroup != null || workerGroup != null) {
                return;
            }
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler((LogLevel.INFO)))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyDecoderHandler(AresRequest.class, serializeType));
                            ch.pipeline().addLast(new NettyEncoderHandler(serializeType));
                            ch.pipeline().addLast(new NettyServerInvokeHandler());
                        }
                    });
            try {
                serverBootstrap.bind(port).sync().channel();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private NettyServer() {}
    public static NettyServer singleton() {
        return nettyServer;
    }
}
