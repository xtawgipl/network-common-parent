package io.nettyx.network.commons.codec;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * kyro实现序列化解码器
 *
 * @author zhangjj
 * @create 2018-07-04 15:06
 **/
public class KryoDecoder extends LengthFieldBasedFrameDecoder {

    public KryoDecoder(int maxObjectSize) {
        super(maxObjectSize, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        Kryo kryo = new Kryo();
        Input input = new Input(new ByteBufInputStream(frame));
        Object result = kryo.readClassAndObject(input);
        input.close();
        return result;
    }

    @Override
    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        return buffer.slice(index, length);
    }
}
