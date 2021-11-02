package net.minecraft.network.protocol.game;

import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class ClientboundSetPassengersPacket implements Packet<ClientGamePacketListener> {
   private final int f_133272_;
   private final int[] f_133273_;

   public ClientboundSetPassengersPacket(Entity p_133276_) {
      this.f_133272_ = p_133276_.m_142049_();
      List<Entity> list = p_133276_.m_20197_();
      this.f_133273_ = new int[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         this.f_133273_[i] = list.get(i).m_142049_();
      }

   }

   public ClientboundSetPassengersPacket(FriendlyByteBuf p_179308_) {
      this.f_133272_ = p_179308_.m_130242_();
      this.f_133273_ = p_179308_.m_130100_();
   }

   public void m_5779_(FriendlyByteBuf p_133285_) {
      p_133285_.m_130130_(this.f_133272_);
      p_133285_.m_130089_(this.f_133273_);
   }

   public void m_5797_(ClientGamePacketListener p_133282_) {
      p_133282_.m_6403_(this);
   }

   public int[] m_133283_() {
      return this.f_133273_;
   }

   public int m_133286_() {
      return this.f_133272_;
   }
}