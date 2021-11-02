package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ClientboundSelectAdvancementsTabPacket implements Packet<ClientGamePacketListener> {
   @Nullable
   private final ResourceLocation f_133003_;

   public ClientboundSelectAdvancementsTabPacket(@Nullable ResourceLocation p_133006_) {
      this.f_133003_ = p_133006_;
   }

   public void m_5797_(ClientGamePacketListener p_133012_) {
      p_133012_.m_7553_(this);
   }

   public ClientboundSelectAdvancementsTabPacket(FriendlyByteBuf p_179198_) {
      if (p_179198_.readBoolean()) {
         this.f_133003_ = p_179198_.m_130281_();
      } else {
         this.f_133003_ = null;
      }

   }

   public void m_5779_(FriendlyByteBuf p_133015_) {
      p_133015_.writeBoolean(this.f_133003_ != null);
      if (this.f_133003_ != null) {
         p_133015_.m_130085_(this.f_133003_);
      }

   }

   @Nullable
   public ResourceLocation m_133013_() {
      return this.f_133003_;
   }
}