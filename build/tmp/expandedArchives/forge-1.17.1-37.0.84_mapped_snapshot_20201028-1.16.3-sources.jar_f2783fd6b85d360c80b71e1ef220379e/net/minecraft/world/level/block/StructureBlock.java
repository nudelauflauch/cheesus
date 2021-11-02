package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.phys.BlockHitResult;

public class StructureBlock extends BaseEntityBlock implements GameMasterBlock {
   public static final EnumProperty<StructureMode> f_57110_ = BlockStateProperties.f_61399_;

   public StructureBlock(BlockBehaviour.Properties p_57113_) {
      super(p_57113_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57110_, StructureMode.LOAD));
   }

   public BlockEntity m_142194_(BlockPos p_154732_, BlockState p_154733_) {
      return new StructureBlockEntity(p_154732_, p_154733_);
   }

   public InteractionResult m_6227_(BlockState p_57128_, Level p_57129_, BlockPos p_57130_, Player p_57131_, InteractionHand p_57132_, BlockHitResult p_57133_) {
      BlockEntity blockentity = p_57129_.m_7702_(p_57130_);
      if (blockentity instanceof StructureBlockEntity) {
         return ((StructureBlockEntity)blockentity).m_59853_(p_57131_) ? InteractionResult.m_19078_(p_57129_.f_46443_) : InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public void m_6402_(Level p_57122_, BlockPos p_57123_, BlockState p_57124_, @Nullable LivingEntity p_57125_, ItemStack p_57126_) {
      if (!p_57122_.f_46443_) {
         if (p_57125_ != null) {
            BlockEntity blockentity = p_57122_.m_7702_(p_57123_);
            if (blockentity instanceof StructureBlockEntity) {
               ((StructureBlockEntity)blockentity).m_59851_(p_57125_);
            }
         }

      }
   }

   public RenderShape m_7514_(BlockState p_57144_) {
      return RenderShape.MODEL;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57142_) {
      p_57142_.m_61104_(f_57110_);
   }

   public void m_6861_(BlockState p_57135_, Level p_57136_, BlockPos p_57137_, Block p_57138_, BlockPos p_57139_, boolean p_57140_) {
      if (p_57136_ instanceof ServerLevel) {
         BlockEntity blockentity = p_57136_.m_7702_(p_57137_);
         if (blockentity instanceof StructureBlockEntity) {
            StructureBlockEntity structureblockentity = (StructureBlockEntity)blockentity;
            boolean flag = p_57136_.m_46753_(p_57137_);
            boolean flag1 = structureblockentity.m_59833_();
            if (flag && !flag1) {
               structureblockentity.m_59893_(true);
               this.m_57114_((ServerLevel)p_57136_, structureblockentity);
            } else if (!flag && flag1) {
               structureblockentity.m_59893_(false);
            }

         }
      }
   }

   private void m_57114_(ServerLevel p_57115_, StructureBlockEntity p_57116_) {
      switch(p_57116_.m_59908_()) {
      case SAVE:
         p_57116_.m_59889_(false);
         break;
      case LOAD:
         p_57116_.m_59844_(p_57115_, false);
         break;
      case CORNER:
         p_57116_.m_59831_();
      case DATA:
      }

   }
}