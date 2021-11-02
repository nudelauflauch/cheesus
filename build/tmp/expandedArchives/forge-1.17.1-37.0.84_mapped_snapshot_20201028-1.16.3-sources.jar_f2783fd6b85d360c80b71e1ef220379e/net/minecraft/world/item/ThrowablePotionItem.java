package net.minecraft.world.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;

public class ThrowablePotionItem extends PotionItem {
   public ThrowablePotionItem(Item.Properties p_43301_) {
      super(p_43301_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_43303_, Player p_43304_, InteractionHand p_43305_) {
      ItemStack itemstack = p_43304_.m_21120_(p_43305_);
      if (!p_43303_.f_46443_) {
         ThrownPotion thrownpotion = new ThrownPotion(p_43303_, p_43304_);
         thrownpotion.m_37446_(itemstack);
         thrownpotion.m_37251_(p_43304_, p_43304_.m_146909_(), p_43304_.m_146908_(), -20.0F, 0.5F, 1.0F);
         p_43303_.m_7967_(thrownpotion);
      }

      p_43304_.m_36246_(Stats.f_12982_.m_12902_(this));
      if (!p_43304_.m_150110_().f_35937_) {
         itemstack.m_41774_(1);
      }

      return InteractionResultHolder.m_19092_(itemstack, p_43303_.m_5776_());
   }
}