package practice.rpcfs.chp6;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncEchoServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncEchoServerHandler attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncEchoServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
