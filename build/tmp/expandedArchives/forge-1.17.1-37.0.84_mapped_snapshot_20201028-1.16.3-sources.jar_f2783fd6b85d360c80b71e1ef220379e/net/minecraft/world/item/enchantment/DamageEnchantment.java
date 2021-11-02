package net.minecraft.world.item.enchantment;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;

public class DamageEnchantment extends Enchantment {
   public static final int f_151289_ = 0;
   public static final int f_151290_ = 1;
   public static final int f_151291_ = 2;
   private static final String[] f_44622_ = new String[]{"all", "undead", "arthropods"};
   private static final int[] f_44623_ = new int[]{1, 5, 5};
   private static final int[] f_44624_ = new int[]{11, 8, 8};
   private static final int[] f_44625_ = new int[]{20, 20, 20};
   public final int f_44621_;

   public DamageEnchantment(Enchantment.Rarity p_44628_, int p_44629_, EquipmentSlot... p_44630_) {
      super(p_44628_, EnchantmentCategory.WEAPON, p_44630_);
      this.f_44621_ = p_44629_;
   }

   public int m_6183_(int p_44633_) {
      return f_44623_[this.f_44621_] + (p_44633_ - 1) * f_44624_[this.f_44621_];
   }

   public int m_6175_(int p_44646_) {
      return this.m_6183_(p_44646_) + f_44625_[this.f_44621_];
   }

   public int m_6586_() {
      return 5;
   }

   public float m_7335_(int p_44635_, MobType p_44636_) {
      if (this.f_44621_ == 0) {
         return 1.0F + (float)Math.max(0, p_44635_ - 1) * 0.5F;
      } else if (this.f_44621_ == 1 && p_44636_ == MobType.f_21641_) {
         return (float)p_44635_ * 2.5F;
      } else {
         return this.f_44621_ == 2 && p_44636_ == MobType.f_21642_ ? (float)p_44635_ * 2.5F : 0.0F;
      }
   }

   public boolean m_5975_(Enchantment p_44644_) {
      return !(p_44644_ instanceof DamageEnchantment);
   }

   public boolean m_6081_(ItemStack p_44642_) {
      return p_44642_.m_41720_() instanceof AxeItem ? true : super.m_6081_(p_44642_);
   }

   public void m_7677_(LivingEntity p_44638_, Entity p_44639_, int p_44640_) {
      if (p_44639_ instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)p_44639_;
         if (this.f_44621_ == 2 && p_44640_ > 0 && livingentity.m_6336_() == MobType.f_21642_) {
            int i = 20 + p_44638_.m_21187_().nextInt(10 * p_44640_);
            livingentity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, i, 3));
         }
      }

   }
}