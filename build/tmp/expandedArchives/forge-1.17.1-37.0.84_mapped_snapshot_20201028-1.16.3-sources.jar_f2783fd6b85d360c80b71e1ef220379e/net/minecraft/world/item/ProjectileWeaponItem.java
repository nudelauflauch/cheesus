package net.minecraft.world.item;

import java.util.function.Predicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public abstract class ProjectileWeaponItem extends Item {
   public static final Predicate<ItemStack> f_43005_ = (p_43017_) -> {
      return p_43017_.m_150922_(ItemTags.f_13161_);
   };
   public static final Predicate<ItemStack> f_43006_ = f_43005_.or((p_43015_) -> {
      return p_43015_.m_150930_(Items.f_42688_);
   });

   public ProjectileWeaponItem(Item.Properties p_43009_) {
      super(p_43009_);
   }

   public Predicate<ItemStack> m_6442_() {
      return this.m_6437_();
   }

   public abstract Predicate<ItemStack> m_6437_();

   public static ItemStack m_43010_(LivingEntity p_43011_, Predicate<ItemStack> p_43012_) {
      if (p_43012_.test(p_43011_.m_21120_(InteractionHand.OFF_HAND))) {
         return p_43011_.m_21120_(InteractionHand.OFF_HAND);
      } else {
         return p_43012_.test(p_43011_.m_21120_(InteractionHand.MAIN_HAND)) ? p_43011_.m_21120_(InteractionHand.MAIN_HAND) : ItemStack.f_41583_;
      }
   }

   public int m_6473_() {
      return 1;
   }

   public abstract int m_6615_();
}