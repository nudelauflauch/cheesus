package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class UntouchingEnchantment extends Enchantment {
   protected UntouchingEnchantment(Enchantment.Rarity p_45260_, EquipmentSlot... p_45261_) {
      super(p_45260_, EnchantmentCategory.DIGGER, p_45261_);
   }

   public int m_6183_(int p_45264_) {
      return 15;
   }

   public int m_6175_(int p_45268_) {
      return super.m_6183_(p_45268_) + 50;
   }

   public int m_6586_() {
      return 1;
   }

   public boolean m_5975_(Enchantment p_45266_) {
      return super.m_5975_(p_45266_) && p_45266_ != Enchantments.f_44987_;
   }
}