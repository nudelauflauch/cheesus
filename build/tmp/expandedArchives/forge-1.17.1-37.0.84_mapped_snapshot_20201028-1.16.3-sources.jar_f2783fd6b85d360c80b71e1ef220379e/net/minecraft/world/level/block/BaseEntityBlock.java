package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseEntityBlock extends Block implements EntityBlock {
   protected BaseEntityBlock(BlockBehaviour.Properties p_49224_) {
      super(p_49224_);
   }

   public RenderShape m_7514_(BlockState p_49232_) {
      return RenderShape.INVISIBLE;
   }

   public boolean m_8133_(BlockState p_49226_, Level p_49227_, BlockPos p_49228_, int p_49229_, int p_49230_) {
      super.m_8133_(p_49226_, p_49227_, p_49228_, p_49229_, p_49230_);
      BlockEntity blockentity = p_49227_.m_7702_(p_49228_);
      return blockentity == null ? false : blockentity.m_7531_(p_49229_, p_49230_);
   }

   @Nullable
   public MenuProvider m_7246_(BlockState p_49234_, Level p_49235_, BlockPos p_49236_) {
      BlockEntity blockentity = p_49235_.m_7702_(p_49236_);
      return blockentity instanceof MenuProvider ? (MenuProvider)blockentity : null;
   }

   @Nullable
   protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> m_152132_(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
      return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
   }
}