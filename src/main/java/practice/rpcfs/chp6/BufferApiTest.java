package practice.rpcfs.chp6;

import java.nio.CharBuffer;

public class BufferApiTest {
    public static void main(String[] args) {
        String content = "Hello!java Non-Blocking I/O";
        CharBuffer buffer = CharBuffer.allocate(50);
        for (int i = 0; i < content.length(); ++i) {
            buffer.put(content.charAt(i));
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        buffer.rewind();
        System.out.println();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();
        buffer.clear();
        buffer.put("Hel").put("lo").put("!");
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
    }
}
