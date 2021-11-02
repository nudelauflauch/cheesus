package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BannerBlock extends AbstractBannerBlock {
   public static final IntegerProperty f_49007_ = BlockStateProperties.f_61390_;
   private static final Map<DyeColor, Block> f_49008_ = Maps.newHashMap();
   private static final VoxelShape f_49009_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

   public BannerBlock(DyeColor p_49012_, BlockBehaviour.Properties p_49013_) {
      super(p_49012_, p_49013_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_49007_, Integer.valueOf(0)));
      f_49008_.put(p_49012_, this);
   }

   public boolean m_7898_(BlockState p_49019_, LevelReader p_49020_, BlockPos p_49021_) {
      return p_49020_.m_8055_(p_49021_.m_7495_()).m_60767_().m_76333_();
   }

   public VoxelShape m_5940_(BlockState p_49038_, BlockGetter p_49039_, BlockPos p_49040_, CollisionContext p_49041_) {
      return f_49009_;
   }

   public BlockState m_5573_(BlockPlaceContext p_49017_) {
      return this.m_49966_().m_61124_(f_49007_, Integer.valueOf(Mth.m_14107_((double)((180.0F + p_49017_.m_7074_()) * 16.0F / 360.0F) + 0.5D) & 15));
   }

   public BlockState m_7417_(BlockState p_49029_, Direction p_49030_, BlockState p_49031_, LevelAccessor p_49032_, BlockPos p_49033_, BlockPos p_49034_) {
      return p_49030_ == Direction.DOWN && !p_49029_.m_60710_(p_49032_, p_49033_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_49029_, p_49030_, p_49031_, p_49032_, p_49033_, p_49034_);
   }

   public BlockState m_6843_(BlockState p_49026_, Rotation p_49027_) {
      return p_49026_.m_61124_(f_49007_, Integer.valueOf(p_49027_.m_55949_(p_49026_.m_61143_(f_49007_), 16)));
   }

   public BlockState m_6943_(BlockState p_49023_, Mirror p_49024_) {
      return p_49023_.m_61124_(f_49007_, Integer.valueOf(p_49024_.m_54843_(p_49023_.m_61143_(f_49007_), 16)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49036_) {
      p_49036_.m_61104_(f_49007_);
   }

   public static Block m_49014_(DyeColor p_49015_) {
      return f_49008_.getOrDefault(p_49015_, Blocks.f_50414_);
   }
}