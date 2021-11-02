package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

@Immutable
public class LockCode {
   public static final LockCode f_19102_ = new LockCode("");
   public static final String f_146668_ = "Lock";
   private final String f_19103_;

   public LockCode(String p_19106_) {
      this.f_19103_ = p_19106_;
   }

   public boolean m_19107_(ItemStack p_19108_) {
      return this.f_19103_.isEmpty() || !p_19108_.m_41619_() && p_19108_.m_41788_() && this.f_19103_.equals(p_19108_.m_41786_().getString());
   }

   public void m_19109_(CompoundTag p_19110_) {
      if (!this.f_19103_.isEmpty()) {
         p_19110_.m_128359_("Lock", this.f_19103_);
      }

   }

   public static LockCode m_19111_(CompoundTag p_19112_) {
      return p_19112_.m_128425_("Lock", 8) ? new LockCode(p_19112_.m_128461_("Lock")) : f_19102_;
   }
}