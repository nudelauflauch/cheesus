package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class LootBonusEnchantment extends Enchantment {
   protected LootBonusEnchantment(Enchantment.Rarity p_45087_, EnchantmentCategory p_45088_, EquipmentSlot... p_45089_) {
      super(p_45087_, p_45088_, p_45089_);
   }

   public int m_6183_(int p_45092_) {
      return 15 + (p_45092_ - 1) * 9;
   }

   public int m_6175_(int p_45096_) {
      return super.m_6183_(p_45096_) + 50;
   }

   public int m_6586_() {
      return 3;
   }

   public boolean m_5975_(Enchantment p_45094_) {
      return super.m_5975_(p_45094_) && p_45094_ != Enchantments.f_44985_;
   }
}