package practice.rpcfs.chp6;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CompletionHandlerDemo {
    public static void main(String[] args) {

    }

    private void write() throws Exception {
        Path path = Paths.get("");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        long position = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Hello, java Blocking I/O!".getBytes());
        buffer.flip();
        final CountDownLatch latch = new CountDownLatch(1);
        fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                latch.countDown();
                exc.printStackTrace();
            }
        });
        latch.await();
        System.out.println("Async Write File done!");

    }

    private void read() throws Exception {
        AsynchronousFileChannel ch = AsynchronousFileChannel.open(Paths.get(""), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        final List<Integer> byteReadResultHolder = new ArrayList<>(1);
        List<Byte> totalByteList = new ArrayList<>();
        while (true) {
            byteReadResultHolder.clear();
            final CountDownLatch latch = new CountDownLatch(1);
            ch.read(buffer, position, null, new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(Integer result, Void attachment) {
                    byteReadResultHolder.add(result);
                    latch.countDown();
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    System.out.println("read failure:" + exc.toString());
                    latch.countDown();
                }
            });
            latch.await();
            if (byteReadResultHolder.size() <= 0
                    || (byteReadResultHolder.size() > 0 && byteReadResultHolder.get(0) == -1)) {
                break;
            }
            buffer.flip();
            position = position + buffer.limit();
            while (buffer.hasRemaining()) {
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                for (byte by : data) {
                    totalByteList.add(by);
                }
            }
            buffer.clear();
        }
        byte[] bytes = new byte[totalByteList.size()];
        for (int i = 0; i < totalByteList.size(); i++) {
            bytes[i] = totalByteList.get(i);
        }
        String fileContent = new String(bytes);
        System.out.println("content :" + fileContent);
        ch.close();
    }
}
