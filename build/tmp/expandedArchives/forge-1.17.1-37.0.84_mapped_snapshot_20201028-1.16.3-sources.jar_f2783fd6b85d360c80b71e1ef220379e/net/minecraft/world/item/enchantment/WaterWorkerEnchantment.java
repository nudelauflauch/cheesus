package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class WaterWorkerEnchantment extends Enchantment {
   public WaterWorkerEnchantment(Enchantment.Rarity p_45290_, EquipmentSlot... p_45291_) {
      super(p_45290_, EnchantmentCategory.ARMOR_HEAD, p_45291_);
   }

   public int m_6183_(int p_45294_) {
      return 1;
   }

   public int m_6175_(int p_45296_) {
      return this.m_6183_(p_45296_) + 40;
   }

   public int m_6586_() {
      return 1;
   }
}