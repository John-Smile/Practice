package practice.nia.chp5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBufDerivedBufTest1 {
    public static void foo() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf sliced = buf.slice(0, 14);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }


}
