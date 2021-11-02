package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class SwordItem extends TieredItem implements Vanishable {
   private final float f_43266_;
   private final Multimap<Attribute, AttributeModifier> f_43267_;

   public SwordItem(Tier p_43269_, int p_43270_, float p_43271_, Item.Properties p_43272_) {
      super(p_43269_, p_43272_);
      this.f_43266_ = (float)p_43270_ + p_43269_.m_6631_();
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Weapon modifier", (double)this.f_43266_, AttributeModifier.Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Weapon modifier", (double)p_43271_, AttributeModifier.Operation.ADDITION));
      this.f_43267_ = builder.build();
   }

   public float m_43299_() {
      return this.f_43266_;
   }

   public boolean m_6777_(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
      return !p_43294_.m_7500_();
   }

   public float m_8102_(ItemStack p_43288_, BlockState p_43289_) {
      if (p_43289_.m_60713_(Blocks.f_50033_)) {
         return 15.0F;
      } else {
         Material material = p_43289_.m_60767_();
         return material != Material.f_76300_ && material != Material.f_76302_ && !p_43289_.m_60620_(BlockTags.f_13035_) && material != Material.f_76285_ ? 1.0F : 1.5F;
      }
   }

   public boolean m_7579_(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
      p_43278_.m_41622_(1, p_43280_, (p_43296_) -> {
         p_43296_.m_21166_(EquipmentSlot.MAINHAND);
      });
      return true;
   }

   public boolean m_6813_(ItemStack p_43282_, Level p_43283_, BlockState p_43284_, BlockPos p_43285_, LivingEntity p_43286_) {
      if (p_43284_.m_60800_(p_43283_, p_43285_) != 0.0F) {
         p_43282_.m_41622_(2, p_43286_, (p_43276_) -> {
            p_43276_.m_21166_(EquipmentSlot.MAINHAND);
         });
      }

      return true;
   }

   public boolean m_8096_(BlockState p_43298_) {
      return p_43298_.m_60713_(Blocks.f_50033_);
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot p_43274_) {
      return p_43274_ == EquipmentSlot.MAINHAND ? this.f_43267_ : super.m_7167_(p_43274_);
   }

   @Override
   public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
      return net.minecraftforge.common.ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
   }
}
