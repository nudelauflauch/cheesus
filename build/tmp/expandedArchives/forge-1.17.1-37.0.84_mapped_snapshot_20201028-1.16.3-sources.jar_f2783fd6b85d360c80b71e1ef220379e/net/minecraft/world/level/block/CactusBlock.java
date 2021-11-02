package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CactusBlock extends Block implements net.minecraftforge.common.IPlantable {
   public static final IntegerProperty f_51131_ = BlockStateProperties.f_61410_;
   public static final int f_152740_ = 15;
   protected static final int f_152741_ = 1;
   protected static final VoxelShape f_51132_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
   protected static final VoxelShape f_51133_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public CactusBlock(BlockBehaviour.Properties p_51136_) {
      super(p_51136_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51131_, Integer.valueOf(0)));
   }

   public void m_7458_(BlockState p_51138_, ServerLevel p_51139_, BlockPos p_51140_, Random p_51141_) {
      if (!p_51139_.isAreaLoaded(p_51140_, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update
      if (!p_51138_.m_60710_(p_51139_, p_51140_)) {
         p_51139_.m_46961_(p_51140_, true);
      }

   }

   public void m_7455_(BlockState p_51166_, ServerLevel p_51167_, BlockPos p_51168_, Random p_51169_) {
      BlockPos blockpos = p_51168_.m_7494_();
      if (p_51167_.m_46859_(blockpos)) {
         int i;
         for(i = 1; p_51167_.m_8055_(p_51168_.m_6625_(i)).m_60713_(this); ++i) {
         }

         if (i < 3) {
            int j = p_51166_.m_61143_(f_51131_);
            if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_51167_, blockpos, p_51166_, true)) {
            if (j == 15) {
               p_51167_.m_46597_(blockpos, this.m_49966_());
               BlockState blockstate = p_51166_.m_61124_(f_51131_, Integer.valueOf(0));
               p_51167_.m_7731_(p_51168_, blockstate, 4);
               blockstate.m_60690_(p_51167_, blockpos, this, p_51168_, false);
            } else {
               p_51167_.m_7731_(p_51168_, p_51166_.m_61124_(f_51131_, Integer.valueOf(j + 1)), 4);
            }
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_51167_, p_51168_, p_51166_);
            }
         }
      }
   }

   public VoxelShape m_5939_(BlockState p_51176_, BlockGetter p_51177_, BlockPos p_51178_, CollisionContext p_51179_) {
      return f_51132_;
   }

   public VoxelShape m_5940_(BlockState p_51171_, BlockGetter p_51172_, BlockPos p_51173_, CollisionContext p_51174_) {
      return f_51133_;
   }

   public BlockState m_7417_(BlockState p_51157_, Direction p_51158_, BlockState p_51159_, LevelAccessor p_51160_, BlockPos p_51161_, BlockPos p_51162_) {
      if (!p_51157_.m_60710_(p_51160_, p_51161_)) {
         p_51160_.m_6219_().m_5945_(p_51161_, this, 1);
      }

      return super.m_7417_(p_51157_, p_51158_, p_51159_, p_51160_, p_51161_, p_51162_);
   }

   public boolean m_7898_(BlockState p_51153_, LevelReader p_51154_, BlockPos p_51155_) {
      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockState blockstate = p_51154_.m_8055_(p_51155_.m_142300_(direction));
         Material material = blockstate.m_60767_();
         if (material.m_76333_() || p_51154_.m_6425_(p_51155_.m_142300_(direction)).m_76153_(FluidTags.f_13132_)) {
            return false;
         }
      }

      BlockState blockstate1 = p_51154_.m_8055_(p_51155_.m_7495_());
      return blockstate1.canSustainPlant(p_51154_, p_51155_, Direction.UP, this) && !p_51154_.m_8055_(p_51155_.m_7494_()).m_60767_().m_76332_();
   }

   public void m_7892_(BlockState p_51148_, Level p_51149_, BlockPos p_51150_, Entity p_51151_) {
      p_51151_.m_6469_(DamageSource.f_19314_, 1.0F);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51164_) {
      p_51164_.m_61104_(f_51131_);
   }

   public boolean m_7357_(BlockState p_51143_, BlockGetter p_51144_, BlockPos p_51145_, PathComputationType p_51146_) {
      return false;
   }

   @Override
   public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
      return net.minecraftforge.common.PlantType.DESERT;
   }

   @Override
   public BlockState getPlant(BlockGetter world, BlockPos pos) {
      return m_49966_();
   }
}
