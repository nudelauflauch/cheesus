package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

public class TippedArrowRecipe extends CustomRecipe {
   public TippedArrowRecipe(ResourceLocation p_44503_) {
      super(p_44503_);
   }

   public boolean m_5818_(CraftingContainer p_44515_, Level p_44516_) {
      if (p_44515_.m_39347_() == 3 && p_44515_.m_39346_() == 3) {
         for(int i = 0; i < p_44515_.m_39347_(); ++i) {
            for(int j = 0; j < p_44515_.m_39346_(); ++j) {
               ItemStack itemstack = p_44515_.m_8020_(i + j * p_44515_.m_39347_());
               if (itemstack.m_41619_()) {
                  return false;
               }

               if (i == 1 && j == 1) {
                  if (!itemstack.m_150930_(Items.f_42739_)) {
                     return false;
                  }
               } else if (!itemstack.m_150930_(Items.f_42412_)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack m_5874_(CraftingContainer p_44513_) {
      ItemStack itemstack = p_44513_.m_8020_(1 + p_44513_.m_39347_());
      if (!itemstack.m_150930_(Items.f_42739_)) {
         return ItemStack.f_41583_;
      } else {
         ItemStack itemstack1 = new ItemStack(Items.f_42738_, 8);
         PotionUtils.m_43549_(itemstack1, PotionUtils.m_43579_(itemstack));
         PotionUtils.m_43552_(itemstack1, PotionUtils.m_43571_(itemstack));
         return itemstack1;
      }
   }

   public boolean m_8004_(int p_44505_, int p_44506_) {
      return p_44505_ >= 2 && p_44506_ >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44085_;
   }
}