package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlowerPotBlock extends Block {
   private static final Map<Block, Block> f_53524_ = Maps.newHashMap();
   public static final float f_153266_ = 3.0F;
   protected static final VoxelShape f_53523_ = Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
   private final Block f_53525_;

   @Deprecated // Mods should use the constructor below
   public FlowerPotBlock(Block p_53528_, BlockBehaviour.Properties p_53529_) {
       this(Blocks.f_50276_ == null ? null : () -> (FlowerPotBlock) Blocks.f_50276_.delegate.get(), () -> p_53528_.delegate.get(), p_53529_);
       if (Blocks.f_50276_ != null) {
           ((FlowerPotBlock)Blocks.f_50276_).addPlant(p_53528_.getRegistryName(), () -> this);
       }
   }

   /**
    * For mod use, eliminates the need to extend this class, and prevents modded
    * flower pots from altering vanilla behavior.
    *
    * @param emptyPot    The empty pot for this pot, or null for self.
    * @param block The flower block.
    * @param properties
    */
   public FlowerPotBlock(@javax.annotation.Nullable java.util.function.Supplier<FlowerPotBlock> emptyPot, java.util.function.Supplier<? extends Block> p_53528_, BlockBehaviour.Properties properties) {
      super(properties);
      this.f_53525_ = null; // Unused, redirected by coremod
      this.flowerDelegate = p_53528_;
      if (emptyPot == null) {
         this.fullPots = Maps.newHashMap();
         this.emptyPot = null;
      } else {
         this.fullPots = java.util.Collections.emptyMap();
         this.emptyPot = emptyPot;
      }
   }

   public VoxelShape m_5940_(BlockState p_53556_, BlockGetter p_53557_, BlockPos p_53558_, CollisionContext p_53559_) {
      return f_53523_;
   }

   public RenderShape m_7514_(BlockState p_53554_) {
      return RenderShape.MODEL;
   }

   public InteractionResult m_6227_(BlockState p_53540_, Level p_53541_, BlockPos p_53542_, Player p_53543_, InteractionHand p_53544_, BlockHitResult p_53545_) {
      ItemStack itemstack = p_53543_.m_21120_(p_53544_);
      Item item = itemstack.m_41720_();
      BlockState blockstate = (item instanceof BlockItem ? getEmptyPot().fullPots.getOrDefault(((BlockItem)item).m_40614_().getRegistryName(), Blocks.f_50016_.delegate).get() : Blocks.f_50016_).m_49966_();
      boolean flag = blockstate.m_60713_(Blocks.f_50016_);
      boolean flag1 = this.m_153267_();
      if (flag != flag1) {
         if (flag1) {
            p_53541_.m_7731_(p_53542_, blockstate, 3);
            p_53543_.m_36220_(Stats.f_12961_);
            if (!p_53543_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }
         } else {
            ItemStack itemstack1 = new ItemStack(this.f_53525_);
            if (itemstack.m_41619_()) {
               p_53543_.m_21008_(p_53544_, itemstack1);
            } else if (!p_53543_.m_36356_(itemstack1)) {
               p_53543_.m_36176_(itemstack1, false);
            }

            p_53541_.m_7731_(p_53542_, getEmptyPot().m_49966_(), 3);
         }

         p_53541_.m_142346_(p_53543_, GameEvent.f_157792_, p_53542_);
         return InteractionResult.m_19078_(p_53541_.f_46443_);
      } else {
         return InteractionResult.CONSUME;
      }
   }

   public ItemStack m_7397_(BlockGetter p_53531_, BlockPos p_53532_, BlockState p_53533_) {
      return this.m_153267_() ? super.m_7397_(p_53531_, p_53532_, p_53533_) : new ItemStack(this.f_53525_);
   }

   private boolean m_153267_() {
      return this.f_53525_ == Blocks.f_50016_;
   }

   public BlockState m_7417_(BlockState p_53547_, Direction p_53548_, BlockState p_53549_, LevelAccessor p_53550_, BlockPos p_53551_, BlockPos p_53552_) {
      return p_53548_ == Direction.DOWN && !p_53547_.m_60710_(p_53550_, p_53551_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_53547_, p_53548_, p_53549_, p_53550_, p_53551_, p_53552_);
   }

   public Block m_53560_() {
      return flowerDelegate.get();
   }

   public boolean m_7357_(BlockState p_53535_, BlockGetter p_53536_, BlockPos p_53537_, PathComputationType p_53538_) {
      return false;
   }

   //Forge Start
   private final Map<net.minecraft.resources.ResourceLocation, java.util.function.Supplier<? extends Block>> fullPots;
   private final java.util.function.Supplier<FlowerPotBlock> emptyPot;
   private final java.util.function.Supplier<? extends Block> flowerDelegate;

   public FlowerPotBlock getEmptyPot() {
       return emptyPot == null ? this : emptyPot.get();
   }

   public void addPlant(net.minecraft.resources.ResourceLocation flower, java.util.function.Supplier<? extends Block> fullPot) {
       if (getEmptyPot() != this) {
           throw new IllegalArgumentException("Cannot add plant to non-empty pot: " + this);
       }
       fullPots.put(flower, fullPot);
   }
   //Forge End
}
