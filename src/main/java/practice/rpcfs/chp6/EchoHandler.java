package practice.rpcfs.chp6;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class EchoHandler implements Runnable {
    private SocketChannel socketChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public EchoHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
                socketChannel.write(buffer);
                if (buffer.hasRemaining()) {
                    buffer.compact();
                } else {
                    buffer.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
