package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundRemoveMobEffectPacket implements Packet<ClientGamePacketListener> {
   private final int f_132895_;
   private final MobEffect f_132896_;

   public ClientboundRemoveMobEffectPacket(int p_132899_, MobEffect p_132900_) {
      this.f_132895_ = p_132899_;
      this.f_132896_ = p_132900_;
   }

   public ClientboundRemoveMobEffectPacket(FriendlyByteBuf p_179177_) {
      this.f_132895_ = p_179177_.m_130242_();
      this.f_132896_ = MobEffect.m_19453_(p_179177_.readUnsignedByte());
   }

   public void m_5779_(FriendlyByteBuf p_132911_) {
      p_132911_.m_130130_(this.f_132895_);
      p_132911_.writeByte(MobEffect.m_19459_(this.f_132896_));
   }

   public void m_5797_(ClientGamePacketListener p_132908_) {
      p_132908_.m_6476_(this);
   }

   @Nullable
   public Entity m_132901_(Level p_132902_) {
      return p_132902_.m_6815_(this.f_132895_);
   }

   @Nullable
   public MobEffect m_132909_() {
      return this.f_132896_;
   }
}