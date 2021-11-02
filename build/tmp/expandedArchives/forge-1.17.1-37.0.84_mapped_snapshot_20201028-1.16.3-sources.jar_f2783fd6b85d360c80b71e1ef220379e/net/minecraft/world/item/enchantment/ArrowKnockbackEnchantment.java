package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class ArrowKnockbackEnchantment extends Enchantment {
   public ArrowKnockbackEnchantment(Enchantment.Rarity p_44594_, EquipmentSlot... p_44595_) {
      super(p_44594_, EnchantmentCategory.BOW, p_44595_);
   }

   public int m_6183_(int p_44598_) {
      return 12 + (p_44598_ - 1) * 20;
   }

   public int m_6175_(int p_44600_) {
      return this.m_6183_(p_44600_) + 25;
   }

   public int m_6586_() {
      return 2;
   }
}