package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ConduitBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_52090_ = BlockStateProperties.f_61362_;
   private static final int f_153092_ = 3;
   protected static final VoxelShape f_52091_ = Block.m_49796_(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

   public ConduitBlock(BlockBehaviour.Properties p_52094_) {
      super(p_52094_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52090_, Boolean.valueOf(true)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52118_) {
      p_52118_.m_61104_(f_52090_);
   }

   public BlockEntity m_142194_(BlockPos p_153098_, BlockState p_153099_) {
      return new ConduitBlockEntity(p_153098_, p_153099_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153094_, BlockState p_153095_, BlockEntityType<T> p_153096_) {
      return m_152132_(p_153096_, BlockEntityType.f_58941_, p_153094_.f_46443_ ? ConduitBlockEntity::m_155403_ : ConduitBlockEntity::m_155438_);
   }

   public RenderShape m_7514_(BlockState p_52120_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public FluidState m_5888_(BlockState p_52127_) {
      return p_52127_.m_61143_(f_52090_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_52127_);
   }

   public BlockState m_7417_(BlockState p_52111_, Direction p_52112_, BlockState p_52113_, LevelAccessor p_52114_, BlockPos p_52115_, BlockPos p_52116_) {
      if (p_52111_.m_61143_(f_52090_)) {
         p_52114_.m_6217_().m_5945_(p_52115_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_52114_));
      }

      return super.m_7417_(p_52111_, p_52112_, p_52113_, p_52114_, p_52115_, p_52116_);
   }

   public VoxelShape m_5940_(BlockState p_52122_, BlockGetter p_52123_, BlockPos p_52124_, CollisionContext p_52125_) {
      return f_52091_;
   }

   public void m_6402_(Level p_52100_, BlockPos p_52101_, BlockState p_52102_, @Nullable LivingEntity p_52103_, ItemStack p_52104_) {
      if (p_52104_.m_41788_()) {
         BlockEntity blockentity = p_52100_.m_7702_(p_52101_);
         if (blockentity instanceof BeaconBlockEntity) {
            ((BeaconBlockEntity)blockentity).m_58681_(p_52104_.m_41786_());
         }
      }

   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_52096_) {
      FluidState fluidstate = p_52096_.m_43725_().m_6425_(p_52096_.m_8083_());
      return this.m_49966_().m_61124_(f_52090_, Boolean.valueOf(fluidstate.m_76153_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8));
   }

   public boolean m_7357_(BlockState p_52106_, BlockGetter p_52107_, BlockPos p_52108_, PathComputationType p_52109_) {
      return false;
   }
}