package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CarpetBlock extends Block {
   protected static final VoxelShape f_152912_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

   public CarpetBlock(BlockBehaviour.Properties p_152915_) {
      super(p_152915_);
   }

   public VoxelShape m_5940_(BlockState p_152917_, BlockGetter p_152918_, BlockPos p_152919_, CollisionContext p_152920_) {
      return f_152912_;
   }

   public BlockState m_7417_(BlockState p_152926_, Direction p_152927_, BlockState p_152928_, LevelAccessor p_152929_, BlockPos p_152930_, BlockPos p_152931_) {
      return !p_152926_.m_60710_(p_152929_, p_152930_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_152926_, p_152927_, p_152928_, p_152929_, p_152930_, p_152931_);
   }

   public boolean m_7898_(BlockState p_152922_, LevelReader p_152923_, BlockPos p_152924_) {
      return !p_152923_.m_46859_(p_152924_.m_7495_());
   }
}