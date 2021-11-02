package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class CipherEncoder extends MessageToByteEncoder<ByteBuf> {
   private final CipherBase f_129423_;

   public CipherEncoder(Cipher p_129425_) {
      this.f_129423_ = new CipherBase(p_129425_);
   }

   protected void encode(ChannelHandlerContext p_129427_, ByteBuf p_129428_, ByteBuf p_129429_) throws Exception {
      this.f_129423_.m_129406_(p_129428_, p_129429_);
   }
}