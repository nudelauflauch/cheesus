package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class Varint21LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
   private static final int f_178385_ = 3;

   protected void encode(ChannelHandlerContext p_130571_, ByteBuf p_130572_, ByteBuf p_130573_) {
      int i = p_130572_.readableBytes();
      int j = FriendlyByteBuf.m_130053_(i);
      if (j > 3) {
         throw new IllegalArgumentException("unable to fit " + i + " into 3");
      } else {
         FriendlyByteBuf friendlybytebuf = new FriendlyByteBuf(p_130573_);
         friendlybytebuf.ensureWritable(j + i);
         friendlybytebuf.m_130130_(i);
         friendlybytebuf.writeBytes(p_130572_, p_130572_.readerIndex(), i);
      }
   }
}