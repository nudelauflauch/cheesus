package net.minecraft.world.level.block.piston;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PistonHeadBlock extends DirectionalBlock {
   public static final EnumProperty<PistonType> f_60235_ = BlockStateProperties.f_61396_;
   public static final BooleanProperty f_60236_ = BlockStateProperties.f_61449_;
   public static final float f_155892_ = 4.0F;
   protected static final VoxelShape f_60237_ = Block.m_49796_(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_60238_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_60239_ = Block.m_49796_(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_60240_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
   protected static final VoxelShape f_60241_ = Block.m_49796_(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_60242_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
   protected static final float f_155893_ = 2.0F;
   protected static final float f_155894_ = 6.0F;
   protected static final float f_155895_ = 10.0F;
   protected static final VoxelShape f_60243_ = Block.m_49796_(6.0D, -4.0D, 6.0D, 10.0D, 12.0D, 10.0D);
   protected static final VoxelShape f_60244_ = Block.m_49796_(6.0D, 4.0D, 6.0D, 10.0D, 20.0D, 10.0D);
   protected static final VoxelShape f_60245_ = Block.m_49796_(6.0D, 6.0D, -4.0D, 10.0D, 10.0D, 12.0D);
   protected static final VoxelShape f_60246_ = Block.m_49796_(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 20.0D);
   protected static final VoxelShape f_60247_ = Block.m_49796_(-4.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D);
   protected static final VoxelShape f_60248_ = Block.m_49796_(4.0D, 6.0D, 6.0D, 20.0D, 10.0D, 10.0D);
   protected static final VoxelShape f_60249_ = Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D);
   protected static final VoxelShape f_60250_ = Block.m_49796_(6.0D, 4.0D, 6.0D, 10.0D, 16.0D, 10.0D);
   protected static final VoxelShape f_60251_ = Block.m_49796_(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 12.0D);
   protected static final VoxelShape f_60252_ = Block.m_49796_(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 16.0D);
   protected static final VoxelShape f_60253_ = Block.m_49796_(0.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D);
   protected static final VoxelShape f_60254_ = Block.m_49796_(4.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
   private static final VoxelShape[] f_60255_ = m_60312_(true);
   private static final VoxelShape[] f_60256_ = m_60312_(false);

   private static VoxelShape[] m_60312_(boolean p_60313_) {
      return Arrays.stream(Direction.values()).map((p_60316_) -> {
         return m_60309_(p_60316_, p_60313_);
      }).toArray((p_60318_) -> {
         return new VoxelShape[p_60318_];
      });
   }

   private static VoxelShape m_60309_(Direction p_60310_, boolean p_60311_) {
      switch(p_60310_) {
      case DOWN:
      default:
         return Shapes.m_83110_(f_60242_, p_60311_ ? f_60250_ : f_60244_);
      case UP:
         return Shapes.m_83110_(f_60241_, p_60311_ ? f_60249_ : f_60243_);
      case NORTH:
         return Shapes.m_83110_(f_60240_, p_60311_ ? f_60252_ : f_60246_);
      case SOUTH:
         return Shapes.m_83110_(f_60239_, p_60311_ ? f_60251_ : f_60245_);
      case WEST:
         return Shapes.m_83110_(f_60238_, p_60311_ ? f_60254_ : f_60248_);
      case EAST:
         return Shapes.m_83110_(f_60237_, p_60311_ ? f_60253_ : f_60247_);
      }
   }

   public PistonHeadBlock(BlockBehaviour.Properties p_60259_) {
      super(p_60259_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52588_, Direction.NORTH).m_61124_(f_60235_, PistonType.DEFAULT).m_61124_(f_60236_, Boolean.valueOf(false)));
   }

   public boolean m_7923_(BlockState p_60325_) {
      return true;
   }

   public VoxelShape m_5940_(BlockState p_60320_, BlockGetter p_60321_, BlockPos p_60322_, CollisionContext p_60323_) {
      return (p_60320_.m_61143_(f_60236_) ? f_60255_ : f_60256_)[p_60320_.m_61143_(f_52588_).ordinal()];
   }

   private boolean m_60297_(BlockState p_60298_, BlockState p_60299_) {
      Block block = p_60298_.m_61143_(f_60235_) == PistonType.DEFAULT ? Blocks.f_50039_ : Blocks.f_50032_;
      return p_60299_.m_60713_(block) && p_60299_.m_61143_(PistonBaseBlock.f_60153_) && p_60299_.m_61143_(f_52588_) == p_60298_.m_61143_(f_52588_);
   }

   public void m_5707_(Level p_60265_, BlockPos p_60266_, BlockState p_60267_, Player p_60268_) {
      if (!p_60265_.f_46443_ && p_60268_.m_150110_().f_35937_) {
         BlockPos blockpos = p_60266_.m_142300_(p_60267_.m_61143_(f_52588_).m_122424_());
         if (this.m_60297_(p_60267_, p_60265_.m_8055_(blockpos))) {
            p_60265_.m_46961_(blockpos, false);
         }
      }

      super.m_5707_(p_60265_, p_60266_, p_60267_, p_60268_);
   }

   public void m_6810_(BlockState p_60282_, Level p_60283_, BlockPos p_60284_, BlockState p_60285_, boolean p_60286_) {
      if (!p_60282_.m_60713_(p_60285_.m_60734_())) {
         super.m_6810_(p_60282_, p_60283_, p_60284_, p_60285_, p_60286_);
         BlockPos blockpos = p_60284_.m_142300_(p_60282_.m_61143_(f_52588_).m_122424_());
         if (this.m_60297_(p_60282_, p_60283_.m_8055_(blockpos))) {
            p_60283_.m_46961_(blockpos, true);
         }

      }
   }

   public BlockState m_7417_(BlockState p_60301_, Direction p_60302_, BlockState p_60303_, LevelAccessor p_60304_, BlockPos p_60305_, BlockPos p_60306_) {
      return p_60302_.m_122424_() == p_60301_.m_61143_(f_52588_) && !p_60301_.m_60710_(p_60304_, p_60305_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_60301_, p_60302_, p_60303_, p_60304_, p_60305_, p_60306_);
   }

   public boolean m_7898_(BlockState p_60288_, LevelReader p_60289_, BlockPos p_60290_) {
      BlockState blockstate = p_60289_.m_8055_(p_60290_.m_142300_(p_60288_.m_61143_(f_52588_).m_122424_()));
      return this.m_60297_(p_60288_, blockstate) || blockstate.m_60713_(Blocks.f_50110_) && blockstate.m_61143_(f_52588_) == p_60288_.m_61143_(f_52588_);
   }

   public void m_6861_(BlockState p_60275_, Level p_60276_, BlockPos p_60277_, Block p_60278_, BlockPos p_60279_, boolean p_60280_) {
      if (p_60275_.m_60710_(p_60276_, p_60277_)) {
         BlockPos blockpos = p_60277_.m_142300_(p_60275_.m_61143_(f_52588_).m_122424_());
         p_60276_.m_8055_(blockpos).m_60690_(p_60276_, blockpos, p_60278_, p_60279_, false);
      }

   }

   public ItemStack m_7397_(BlockGetter p_60261_, BlockPos p_60262_, BlockState p_60263_) {
      return new ItemStack(p_60263_.m_61143_(f_60235_) == PistonType.STICKY ? Blocks.f_50032_ : Blocks.f_50039_);
   }

   public BlockState m_6843_(BlockState p_60295_, Rotation p_60296_) {
      return p_60295_.m_61124_(f_52588_, p_60296_.m_55954_(p_60295_.m_61143_(f_52588_)));
   }

   public BlockState m_6943_(BlockState p_60292_, Mirror p_60293_) {
      return p_60292_.m_60717_(p_60293_.m_54846_(p_60292_.m_61143_(f_52588_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_60308_) {
      p_60308_.m_61104_(f_52588_, f_60235_, f_60236_);
   }

   public boolean m_7357_(BlockState p_60270_, BlockGetter p_60271_, BlockPos p_60272_, PathComputationType p_60273_) {
      return false;
   }
}