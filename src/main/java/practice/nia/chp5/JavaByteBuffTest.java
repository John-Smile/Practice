package practice.nia.chp5;

import java.nio.ByteBuffer;

public class JavaByteBuffTest {
    public static void foo() {
        ByteBuffer a = ByteBuffer.wrap("Hello".getBytes());
        ByteBuffer b = ByteBuffer.allocate(1024);
        b.putChar('S');
        b.putChar('K');
        b.putChar('Y');

        ByteBuffer[] message = new ByteBuffer[]{a, b};
        ByteBuffer message2 = ByteBuffer.allocate(a.remaining() + b.remaining());
        message2.put(a);
        message2.put(b);
        message2.flip();
        System.out.println(new String(message2.array()));

//        b.put("Sky".getBytes(), 0, "Sky".getBytes().length);
//        System.out.println(new String(message2.array()));

        b.putChar(0, 'F');
        System.out.println(new String(message2.array()));
    }

    public static void main(String[] args) {
        foo();
    }

    public static void boo() {
        ByteBuffer bf = ByteBuffer.allocate(256);
        bf.putChar('S');
        bf.putChar('K');
        bf.putChar('Y');
        System.out.println(new String(bf.array()));
        bf.putChar(0, 'F');
        System.out.println(new String(bf.array()));
    }
}
