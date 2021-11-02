package net.minecraft.world.level.lighting;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class LayerLightEngine<M extends DataLayerStorageMap<M>, S extends LayerLightSectionStorage<M>> extends DynamicGraphMinFixedPoint implements LayerLightEventListener {
   public static final long f_164424_ = Long.MAX_VALUE;
   private static final Direction[] f_75634_ = Direction.values();
   protected final LightChunkGetter f_75630_;
   protected final LightLayer f_75631_;
   protected final S f_75632_;
   private boolean f_75635_;
   protected final BlockPos.MutableBlockPos f_75633_ = new BlockPos.MutableBlockPos();
   private static final int f_164425_ = 2;
   private final long[] f_75636_ = new long[2];
   private final BlockGetter[] f_75637_ = new BlockGetter[2];

   public LayerLightEngine(LightChunkGetter p_75640_, LightLayer p_75641_, S p_75642_) {
      super(16, 256, 8192);
      this.f_75630_ = p_75640_;
      this.f_75631_ = p_75641_;
      this.f_75632_ = p_75642_;
      this.m_75706_();
   }

   protected void m_6185_(long p_75708_) {
      this.f_75632_.m_75785_();
      if (this.f_75632_.m_75791_(SectionPos.m_123235_(p_75708_))) {
         super.m_6185_(p_75708_);
      }

   }

   @Nullable
   private BlockGetter m_75644_(int p_75645_, int p_75646_) {
      long i = ChunkPos.m_45589_(p_75645_, p_75646_);

      for(int j = 0; j < 2; ++j) {
         if (i == this.f_75636_[j]) {
            return this.f_75637_[j];
         }
      }

      BlockGetter blockgetter = this.f_75630_.m_6196_(p_75645_, p_75646_);

      for(int k = 1; k > 0; --k) {
         this.f_75636_[k] = this.f_75636_[k - 1];
         this.f_75637_[k] = this.f_75637_[k - 1];
      }

      this.f_75636_[0] = i;
      this.f_75637_[0] = blockgetter;
      return blockgetter;
   }

   private void m_75706_() {
      Arrays.fill(this.f_75636_, ChunkPos.f_45577_);
      Arrays.fill(this.f_75637_, (Object)null);
   }

   protected BlockState m_75664_(long p_75665_, @Nullable MutableInt p_75666_) {
      if (p_75665_ == Long.MAX_VALUE) {
         if (p_75666_ != null) {
            p_75666_.setValue(0);
         }

         return Blocks.f_50016_.m_49966_();
      } else {
         int i = SectionPos.m_123171_(BlockPos.m_121983_(p_75665_));
         int j = SectionPos.m_123171_(BlockPos.m_122015_(p_75665_));
         BlockGetter blockgetter = this.m_75644_(i, j);
         if (blockgetter == null) {
            if (p_75666_ != null) {
               p_75666_.setValue(16);
            }

            return Blocks.f_50752_.m_49966_();
         } else {
            this.f_75633_.m_122188_(p_75665_);
            BlockState blockstate = blockgetter.m_8055_(this.f_75633_);
            boolean flag = blockstate.m_60815_() && blockstate.m_60787_();
            if (p_75666_ != null) {
               p_75666_.setValue(blockstate.m_60739_(this.f_75630_.m_7653_(), this.f_75633_));
            }

            return flag ? blockstate : Blocks.f_50016_.m_49966_();
         }
      }
   }

   protected VoxelShape m_75678_(BlockState p_75679_, long p_75680_, Direction p_75681_) {
      return p_75679_.m_60815_() ? p_75679_.m_60655_(this.f_75630_.m_7653_(), this.f_75633_.m_122188_(p_75680_), p_75681_) : Shapes.m_83040_();
   }

   public static int m_75667_(BlockGetter p_75668_, BlockState p_75669_, BlockPos p_75670_, BlockState p_75671_, BlockPos p_75672_, Direction p_75673_, int p_75674_) {
      boolean flag = p_75669_.m_60815_() && p_75669_.m_60787_();
      boolean flag1 = p_75671_.m_60815_() && p_75671_.m_60787_();
      if (!flag && !flag1) {
         return p_75674_;
      } else {
         VoxelShape voxelshape = flag ? p_75669_.m_60768_(p_75668_, p_75670_) : Shapes.m_83040_();
         VoxelShape voxelshape1 = flag1 ? p_75671_.m_60768_(p_75668_, p_75672_) : Shapes.m_83040_();
         return Shapes.m_83152_(voxelshape, voxelshape1, p_75673_) ? 16 : p_75674_;
      }
   }

   protected boolean m_6163_(long p_75652_) {
      return p_75652_ == Long.MAX_VALUE;
   }

   protected int m_6357_(long p_75657_, long p_75658_, int p_75659_) {
      return 0;
   }

   protected int m_6172_(long p_75705_) {
      return p_75705_ == Long.MAX_VALUE ? 0 : 15 - this.f_75632_.m_75795_(p_75705_);
   }

   protected int m_75682_(DataLayer p_75683_, long p_75684_) {
      return 15 - p_75683_.m_62560_(SectionPos.m_123207_(BlockPos.m_121983_(p_75684_)), SectionPos.m_123207_(BlockPos.m_122008_(p_75684_)), SectionPos.m_123207_(BlockPos.m_122015_(p_75684_)));
   }

   protected void m_7351_(long p_75654_, int p_75655_) {
      this.f_75632_.m_75772_(p_75654_, Math.min(15, 15 - p_75655_));
   }

   protected int m_6359_(long p_75696_, long p_75697_, int p_75698_) {
      return 0;
   }

   public boolean m_142182_() {
      return this.m_75587_() || this.f_75632_.m_75587_() || this.f_75632_.m_6808_();
   }

   public int m_142528_(int p_75648_, boolean p_75649_, boolean p_75650_) {
      if (!this.f_75635_) {
         if (this.f_75632_.m_75587_()) {
            p_75648_ = this.f_75632_.m_75588_(p_75648_);
            if (p_75648_ == 0) {
               return p_75648_;
            }
         }

         this.f_75632_.m_6716_(this, p_75649_, p_75650_);
      }

      this.f_75635_ = true;
      if (this.m_75587_()) {
         p_75648_ = this.m_75588_(p_75648_);
         this.m_75706_();
         if (p_75648_ == 0) {
            return p_75648_;
         }
      }

      this.f_75635_ = false;
      this.f_75632_.m_75790_();
      return p_75648_;
   }

   protected void m_75660_(long p_75661_, @Nullable DataLayer p_75662_, boolean p_75663_) {
      this.f_75632_.m_75754_(p_75661_, p_75662_, p_75663_);
   }

   @Nullable
   public DataLayer m_8079_(SectionPos p_75690_) {
      return this.f_75632_.m_75793_(p_75690_.m_123252_());
   }

   public int m_7768_(BlockPos p_75703_) {
      return this.f_75632_.m_6181_(p_75703_.m_121878_());
   }

   public String m_6647_(long p_75694_) {
      return "" + this.f_75632_.m_6172_(p_75694_);
   }

   public void m_142202_(BlockPos p_75686_) {
      long i = p_75686_.m_121878_();
      this.m_6185_(i);

      for(Direction direction : f_75634_) {
         this.m_6185_(BlockPos.m_121915_(i, direction));
      }

   }

   public void m_142519_(BlockPos p_75687_, int p_75688_) {
   }

   public void m_6191_(SectionPos p_75692_, boolean p_75693_) {
      this.f_75632_.m_75787_(p_75692_.m_123252_(), p_75693_);
   }

   public void m_141940_(ChunkPos p_75676_, boolean p_75677_) {
      long i = SectionPos.m_123240_(SectionPos.m_123209_(p_75676_.f_45578_, 0, p_75676_.f_45579_));
      this.f_75632_.m_7358_(i, p_75677_);
   }

   public void m_75699_(ChunkPos p_75700_, boolean p_75701_) {
      long i = SectionPos.m_123240_(SectionPos.m_123209_(p_75700_.f_45578_, 0, p_75700_.f_45579_));
      this.f_75632_.m_75782_(i, p_75701_);
   }

   public abstract int queuedUpdateSize();
}
