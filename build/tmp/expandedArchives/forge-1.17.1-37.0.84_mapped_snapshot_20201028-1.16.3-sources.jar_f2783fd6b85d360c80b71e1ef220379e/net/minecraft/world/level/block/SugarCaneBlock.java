package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SugarCaneBlock extends Block implements net.minecraftforge.common.IPlantable {
   public static final IntegerProperty f_57164_ = BlockStateProperties.f_61410_;
   protected static final float f_154735_ = 6.0F;
   protected static final VoxelShape f_57165_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

   public SugarCaneBlock(BlockBehaviour.Properties p_57168_) {
      super(p_57168_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57164_, Integer.valueOf(0)));
   }

   public VoxelShape m_5940_(BlockState p_57193_, BlockGetter p_57194_, BlockPos p_57195_, CollisionContext p_57196_) {
      return f_57165_;
   }

   public void m_7458_(BlockState p_57170_, ServerLevel p_57171_, BlockPos p_57172_, Random p_57173_) {
      if (!p_57170_.m_60710_(p_57171_, p_57172_)) {
         p_57171_.m_46961_(p_57172_, true);
      }

   }

   public void m_7455_(BlockState p_57188_, ServerLevel p_57189_, BlockPos p_57190_, Random p_57191_) {
      if (p_57189_.m_46859_(p_57190_.m_7494_())) {
         int i;
         for(i = 1; p_57189_.m_8055_(p_57190_.m_6625_(i)).m_60713_(this); ++i) {
         }

         if (i < 3) {
            int j = p_57188_.m_61143_(f_57164_);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_57189_, p_57190_, p_57188_, true)) {
            if (j == 15) {
               p_57189_.m_46597_(p_57190_.m_7494_(), this.m_49966_());
               p_57189_.m_7731_(p_57190_, p_57188_.m_61124_(f_57164_, Integer.valueOf(0)), 4);
            } else {
               p_57189_.m_7731_(p_57190_, p_57188_.m_61124_(f_57164_, Integer.valueOf(j + 1)), 4);
            }
            }
         }
      }

   }

   public BlockState m_7417_(BlockState p_57179_, Direction p_57180_, BlockState p_57181_, LevelAccessor p_57182_, BlockPos p_57183_, BlockPos p_57184_) {
      if (!p_57179_.m_60710_(p_57182_, p_57183_)) {
         p_57182_.m_6219_().m_5945_(p_57183_, this, 1);
      }

      return super.m_7417_(p_57179_, p_57180_, p_57181_, p_57182_, p_57183_, p_57184_);
   }

   public boolean m_7898_(BlockState p_57175_, LevelReader p_57176_, BlockPos p_57177_) {
      BlockState soil = p_57176_.m_8055_(p_57177_.m_7495_());
      if (soil.canSustainPlant(p_57176_, p_57177_.m_7495_(), Direction.UP, this)) return true;
      BlockState blockstate = p_57176_.m_8055_(p_57177_.m_7495_());
      if (blockstate.m_60713_(this)) {
         return true;
      } else {
         if (blockstate.m_60620_(BlockTags.f_144274_) || blockstate.m_60713_(Blocks.f_49992_) || blockstate.m_60713_(Blocks.f_49993_)) {
            BlockPos blockpos = p_57177_.m_7495_();

            for(Direction direction : Direction.Plane.HORIZONTAL) {
               BlockState blockstate1 = p_57176_.m_8055_(blockpos.m_142300_(direction));
               FluidState fluidstate = p_57176_.m_6425_(blockpos.m_142300_(direction));
               if (fluidstate.m_76153_(FluidTags.f_13131_) || blockstate1.m_60713_(Blocks.f_50449_)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57186_) {
      p_57186_.m_61104_(f_57164_);
   }

   @Override
   public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
       return net.minecraftforge.common.PlantType.BEACH;
   }

   @Override
   public BlockState getPlant(BlockGetter world, BlockPos pos) {
      return m_49966_();
   }
}
