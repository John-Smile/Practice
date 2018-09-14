package practice.ndg.chp15;

import java.nio.ByteBuffer;

public class Test1 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(88);
        String value = "Netty 权威指南";
        buffer.put(value.getBytes());
        buffer.flip();
        byte[] vArray = new byte[buffer.remaining()];
        buffer.get(vArray);
        String decodeValue = new String(vArray);
        System.out.println(decodeValue);
    }
}
