package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EmptyMapItem extends ComplexItem {
   public EmptyMapItem(Item.Properties p_41143_) {
      super(p_41143_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41145_, Player p_41146_, InteractionHand p_41147_) {
      ItemStack itemstack = p_41146_.m_21120_(p_41147_);
      if (p_41145_.f_46443_) {
         return InteractionResultHolder.m_19090_(itemstack);
      } else {
         if (!p_41146_.m_150110_().f_35937_) {
            itemstack.m_41774_(1);
         }

         p_41146_.m_36246_(Stats.f_12982_.m_12902_(this));
         p_41146_.f_19853_.m_6269_((Player)null, p_41146_, SoundEvents.f_12493_, p_41146_.m_5720_(), 1.0F, 1.0F);
         ItemStack itemstack1 = MapItem.m_42886_(p_41145_, p_41146_.m_146903_(), p_41146_.m_146907_(), (byte)0, true, false);
         if (itemstack.m_41619_()) {
            return InteractionResultHolder.m_19096_(itemstack1);
         } else {
            if (!p_41146_.m_150109_().m_36054_(itemstack1.m_41777_())) {
               p_41146_.m_36176_(itemstack1, false);
            }

            return InteractionResultHolder.m_19096_(itemstack);
         }
      }
   }
}