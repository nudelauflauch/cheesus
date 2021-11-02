package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class FireAspectEnchantment extends Enchantment {
   protected FireAspectEnchantment(Enchantment.Rarity p_44996_, EquipmentSlot... p_44997_) {
      super(p_44996_, EnchantmentCategory.WEAPON, p_44997_);
   }

   public int m_6183_(int p_45000_) {
      return 10 + 20 * (p_45000_ - 1);
   }

   public int m_6175_(int p_45002_) {
      return super.m_6183_(p_45002_) + 50;
   }

   public int m_6586_() {
      return 2;
   }
}