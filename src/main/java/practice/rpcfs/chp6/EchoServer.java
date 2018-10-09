package practice.rpcfs.chp6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            if (serverSocketChannel.isOpen()) {
                serverSocketChannel.configureBlocking(true);
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8085));

                while (true) {
                    try {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        executor.submit(new EchoHandler(socketChannel));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                throw new RuntimeException("server socket channel cannot be opened!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
