package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BeaconBlock extends BaseEntityBlock implements BeaconBeamBlock {
   public BeaconBlock(BlockBehaviour.Properties p_49421_) {
      super(p_49421_);
   }

   public DyeColor m_7988_() {
      return DyeColor.WHITE;
   }

   public BlockEntity m_142194_(BlockPos p_152164_, BlockState p_152165_) {
      return new BeaconBlockEntity(p_152164_, p_152165_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
      return m_152132_(p_152162_, BlockEntityType.f_58930_, BeaconBlockEntity::m_155107_);
   }

   public InteractionResult m_6227_(BlockState p_49432_, Level p_49433_, BlockPos p_49434_, Player p_49435_, InteractionHand p_49436_, BlockHitResult p_49437_) {
      if (p_49433_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         BlockEntity blockentity = p_49433_.m_7702_(p_49434_);
         if (blockentity instanceof BeaconBlockEntity) {
            p_49435_.m_5893_((BeaconBlockEntity)blockentity);
            p_49435_.m_36220_(Stats.f_12955_);
         }

         return InteractionResult.CONSUME;
      }
   }

   public RenderShape m_7514_(BlockState p_49439_) {
      return RenderShape.MODEL;
   }

   public void m_6402_(Level p_49426_, BlockPos p_49427_, BlockState p_49428_, LivingEntity p_49429_, ItemStack p_49430_) {
      if (p_49430_.m_41788_()) {
         BlockEntity blockentity = p_49426_.m_7702_(p_49427_);
         if (blockentity instanceof BeaconBlockEntity) {
            ((BeaconBlockEntity)blockentity).m_58681_(p_49430_.m_41786_());
         }
      }

   }
}