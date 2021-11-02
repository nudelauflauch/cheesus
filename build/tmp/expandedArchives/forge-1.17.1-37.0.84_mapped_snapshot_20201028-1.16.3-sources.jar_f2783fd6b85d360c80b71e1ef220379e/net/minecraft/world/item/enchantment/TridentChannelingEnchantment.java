package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class TridentChannelingEnchantment extends Enchantment {
   public TridentChannelingEnchantment(Enchantment.Rarity p_45219_, EquipmentSlot... p_45220_) {
      super(p_45219_, EnchantmentCategory.TRIDENT, p_45220_);
   }

   public int m_6183_(int p_45223_) {
      return 25;
   }

   public int m_6175_(int p_45227_) {
      return 50;
   }

   public int m_6586_() {
      return 1;
   }
}