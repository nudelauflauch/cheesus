package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CakeBlock extends Block {
   public static final int f_152742_ = 6;
   public static final IntegerProperty f_51180_ = BlockStateProperties.f_61412_;
   public static final int f_152743_ = m_152746_(0);
   protected static final float f_152744_ = 1.0F;
   protected static final float f_152745_ = 2.0F;
   protected static final VoxelShape[] f_51181_ = new VoxelShape[]{Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.m_49796_(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.m_49796_(5.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.m_49796_(7.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.m_49796_(9.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.m_49796_(11.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.m_49796_(13.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)};

   public CakeBlock(BlockBehaviour.Properties p_51184_) {
      super(p_51184_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51180_, Integer.valueOf(0)));
   }

   public VoxelShape m_5940_(BlockState p_51222_, BlockGetter p_51223_, BlockPos p_51224_, CollisionContext p_51225_) {
      return f_51181_[p_51222_.m_61143_(f_51180_)];
   }

   public InteractionResult m_6227_(BlockState p_51202_, Level p_51203_, BlockPos p_51204_, Player p_51205_, InteractionHand p_51206_, BlockHitResult p_51207_) {
      ItemStack itemstack = p_51205_.m_21120_(p_51206_);
      Item item = itemstack.m_41720_();
      if (itemstack.m_150922_(ItemTags.f_144319_) && p_51202_.m_61143_(f_51180_) == 0) {
         Block block = Block.m_49814_(item);
         if (block instanceof CandleBlock) {
            if (!p_51205_.m_7500_()) {
               itemstack.m_41774_(1);
            }

            p_51203_.m_5594_((Player)null, p_51204_, SoundEvents.f_144090_, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_51203_.m_46597_(p_51204_, CandleCakeBlock.m_152865_(block));
            p_51203_.m_142346_(p_51205_, GameEvent.f_157792_, p_51204_);
            p_51205_.m_36246_(Stats.f_12982_.m_12902_(item));
            return InteractionResult.SUCCESS;
         }
      }

      if (p_51203_.f_46443_) {
         if (m_51185_(p_51203_, p_51204_, p_51202_, p_51205_).m_19077_()) {
            return InteractionResult.SUCCESS;
         }

         if (itemstack.m_41619_()) {
            return InteractionResult.CONSUME;
         }
      }

      return m_51185_(p_51203_, p_51204_, p_51202_, p_51205_);
   }

   protected static InteractionResult m_51185_(LevelAccessor p_51186_, BlockPos p_51187_, BlockState p_51188_, Player p_51189_) {
      if (!p_51189_.m_36391_(false)) {
         return InteractionResult.PASS;
      } else {
         p_51189_.m_36220_(Stats.f_12942_);
         p_51189_.m_36324_().m_38707_(2, 0.1F);
         int i = p_51188_.m_61143_(f_51180_);
         p_51186_.m_142346_(p_51189_, GameEvent.f_157806_, p_51187_);
         if (i < 6) {
            p_51186_.m_7731_(p_51187_, p_51188_.m_61124_(f_51180_, Integer.valueOf(i + 1)), 3);
         } else {
            p_51186_.m_7471_(p_51187_, false);
            p_51186_.m_142346_(p_51189_, GameEvent.f_157794_, p_51187_);
         }

         return InteractionResult.SUCCESS;
      }
   }

   public BlockState m_7417_(BlockState p_51213_, Direction p_51214_, BlockState p_51215_, LevelAccessor p_51216_, BlockPos p_51217_, BlockPos p_51218_) {
      return p_51214_ == Direction.DOWN && !p_51213_.m_60710_(p_51216_, p_51217_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_51213_, p_51214_, p_51215_, p_51216_, p_51217_, p_51218_);
   }

   public boolean m_7898_(BlockState p_51209_, LevelReader p_51210_, BlockPos p_51211_) {
      return p_51210_.m_8055_(p_51211_.m_7495_()).m_60767_().m_76333_();
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51220_) {
      p_51220_.m_61104_(f_51180_);
   }

   public int m_6782_(BlockState p_51198_, Level p_51199_, BlockPos p_51200_) {
      return m_152746_(p_51198_.m_61143_(f_51180_));
   }

   public static int m_152746_(int p_152747_) {
      return (7 - p_152747_) * 2;
   }

   public boolean m_7278_(BlockState p_51191_) {
      return true;
   }

   public boolean m_7357_(BlockState p_51193_, BlockGetter p_51194_, BlockPos p_51195_, PathComputationType p_51196_) {
      return false;
   }
}