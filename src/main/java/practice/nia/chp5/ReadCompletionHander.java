package practice.nia.chp5;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class ReadCompletionHander implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel asynchronousSocketChannel;

    public ReadCompletionHander(AsynchronousSocketChannel result) {
        if (asynchronousSocketChannel == null) {
            asynchronousSocketChannel = result;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("The time server receive order: " + req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
            doWrite(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String currentTime) {

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

    }
}
