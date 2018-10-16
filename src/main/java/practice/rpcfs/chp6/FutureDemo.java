package practice.rpcfs.chp6;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class FutureDemo {
    private void read() throws Exception {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(""), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        List<Byte> totalByteList = new ArrayList<>();
        while (true) {
            Future<Integer> operation = channel.read(buffer, position);
            while (!operation.isDone());
            buffer.flip();
            if (!buffer.hasRemaining()) {
                break;
            }
            position += buffer.limit();
            while (buffer.hasRemaining()) {
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                for (byte by : data) {
                    totalByteList.add(by);
                }
                buffer.clear();
            }
            byte[] bytes = new byte[totalByteList.size()];
            for (int i = 0; i < totalByteList.size(); ++i) {
                bytes[i] = totalByteList.get(i);
            }
            String fileContent = new String(bytes);
            System.out.println("content :" + fileContent);
            channel.close();
        }
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
        Future<Integer> operation = fileChannel.write(buffer, position);
        buffer.clear();
        while (!operation.isDone());
        fileChannel.close();
        System.out.println("Async Write File done");
    }
}
