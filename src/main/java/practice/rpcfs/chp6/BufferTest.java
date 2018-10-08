package practice.rpcfs.chp6;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferTest {
    public static void main(String[] args) {
        Buffer buffer = ByteBuffer.allocate(10);
        System.out.println("Capacity:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());
        System.out.println("Position:" + buffer.position());
        System.out.println("Remaining:" + buffer.remaining());
        System.out.println("set buffer's limit to 6");
        buffer.limit(6);
        System.out.println("limit:" + buffer.limit());
        System.out.println("Position:" + buffer.position());
        System.out.println("Remaining:" + buffer.remaining());
        buffer.position(2);
        System.out.println("Position:" + buffer.position());
        System.out.println("Remaining:" + buffer.remaining());
        System.out.println(buffer);
    }
}
