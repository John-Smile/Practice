package practice.nia.chp5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuffTest {

    public static void foo() {
        ByteBuf dest = Unpooled.buffer(1024);
        ByteBuf src = Unpooled.copiedBuffer("Hello world!", Charset.forName("UTF-8"));
        System.out.println("src readerIndex:" + src.readerIndex() + " writerIndex:" + src.writerIndex());
        System.out.println("dest readerIndex:" + dest.readerIndex() + " writerIndex:" + dest.writerIndex());
        src.readBytes(dest, 10, 10);
        System.out.println("src readerIndex:" + src.readerIndex() + " writerIndex:" + src.writerIndex());
        System.out.println("dest readerIndex:" + dest.readerIndex() + " writerIndex:" + dest.writerIndex());
    }

    public static void main(String[] args) {
        foo();
    }
}
