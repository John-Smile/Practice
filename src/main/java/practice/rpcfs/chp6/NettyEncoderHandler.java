package practice.rpcfs.chp6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

public class NettyEncoderHandler extends MessageToByteEncoder {
    private SerializeType serializeType;
    public NettyEncoderHandler(SerializeType serializeType) {
        this.serializeType = serializeType;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] data = SerializerEngine.serialize(msg, serializeType.getSerializeType());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
