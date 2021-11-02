package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SoulFireBlock extends BaseFireBlock {
   public SoulFireBlock(BlockBehaviour.Properties p_56653_) {
      super(p_56653_, 2.0F);
   }

   public BlockState m_7417_(BlockState p_56659_, Direction p_56660_, BlockState p_56661_, LevelAccessor p_56662_, BlockPos p_56663_, BlockPos p_56664_) {
      return this.m_7898_(p_56659_, p_56662_, p_56663_) ? this.m_49966_() : Blocks.f_50016_.m_49966_();
   }

   public boolean m_7898_(BlockState p_56655_, LevelReader p_56656_, BlockPos p_56657_) {
      return m_154650_(p_56656_.m_8055_(p_56657_.m_7495_()));
   }

   public static boolean m_154650_(BlockState p_154651_) {
      return p_154651_.m_60620_(BlockTags.f_13085_);
   }

   protected boolean m_7599_(BlockState p_56668_) {
      return true;
   }
}