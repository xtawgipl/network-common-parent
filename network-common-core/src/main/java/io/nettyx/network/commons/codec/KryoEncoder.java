package io.nettyx.network.commons.codec;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * kyro实现序列化编码器
 *
 * @author zhangjj
 * @create 2018-07-04 15:31
 **/
public class KryoEncoder extends MessageToByteEncoder<Object> {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        int lengthPos = out.writerIndex();
        out.writeBytes(LENGTH_PLACEHOLDER);
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        Kryo kryo = new Kryo();
        Output output = new Output(bout);
        kryo.writeClassAndObject(output, msg);
        output.close();
        out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
    }

}
