package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class MendingEnchantment extends Enchantment {
   public MendingEnchantment(Enchantment.Rarity p_45098_, EquipmentSlot... p_45099_) {
      super(p_45098_, EnchantmentCategory.BREAKABLE, p_45099_);
   }

   public int m_6183_(int p_45102_) {
      return p_45102_ * 25;
   }

   public int m_6175_(int p_45105_) {
      return this.m_6183_(p_45105_) + 50;
   }

   public boolean m_6591_() {
      return true;
   }

   public int m_6586_() {
      return 1;
   }
}