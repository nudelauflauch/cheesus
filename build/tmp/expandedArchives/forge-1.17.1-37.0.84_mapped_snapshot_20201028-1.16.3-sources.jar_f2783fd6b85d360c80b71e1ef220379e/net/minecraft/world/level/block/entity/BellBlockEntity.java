package net.minecraft.world.level.block.entity;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.MutableInt;

public class BellBlockEntity extends BlockEntity {
   private static final int f_155164_ = 50;
   private static final int f_155165_ = 60;
   private static final int f_155166_ = 60;
   private static final int f_155167_ = 40;
   private static final int f_155168_ = 5;
   private static final int f_155169_ = 48;
   private static final int f_155170_ = 32;
   private static final int f_155171_ = 48;
   private long f_58816_;
   public int f_58813_;
   public boolean f_58814_;
   public Direction f_58815_;
   private List<LivingEntity> f_58817_;
   private boolean f_58818_;
   private int f_58819_;

   public BellBlockEntity(BlockPos p_155173_, BlockState p_155174_) {
      super(BlockEntityType.f_58909_, p_155173_, p_155174_);
   }

   public boolean m_7531_(int p_58837_, int p_58838_) {
      if (p_58837_ == 1) {
         this.m_58845_();
         this.f_58819_ = 0;
         this.f_58815_ = Direction.m_122376_(p_58838_);
         this.f_58813_ = 0;
         this.f_58814_ = true;
         return true;
      } else {
         return super.m_7531_(p_58837_, p_58838_);
      }
   }

   private static void m_155180_(Level p_155181_, BlockPos p_155182_, BlockState p_155183_, BellBlockEntity p_155184_, BellBlockEntity.ResonationEndAction p_155185_) {
      if (p_155184_.f_58814_) {
         ++p_155184_.f_58813_;
      }

      if (p_155184_.f_58813_ >= 50) {
         p_155184_.f_58814_ = false;
         p_155184_.f_58813_ = 0;
      }

      if (p_155184_.f_58813_ >= 5 && p_155184_.f_58819_ == 0 && m_155199_(p_155182_, p_155184_.f_58817_)) {
         p_155184_.f_58818_ = true;
         p_155181_.m_5594_((Player)null, p_155182_, SoundEvents.f_11700_, SoundSource.BLOCKS, 1.0F, 1.0F);
      }

      if (p_155184_.f_58818_) {
         if (p_155184_.f_58819_ < 40) {
            ++p_155184_.f_58819_;
         } else {
            p_155185_.m_155220_(p_155181_, p_155182_, p_155184_.f_58817_);
            p_155184_.f_58818_ = false;
         }
      }

   }

   public static void m_155175_(Level p_155176_, BlockPos p_155177_, BlockState p_155178_, BellBlockEntity p_155179_) {
      m_155180_(p_155176_, p_155177_, p_155178_, p_155179_, BellBlockEntity::m_155207_);
   }

   public static void m_155202_(Level p_155203_, BlockPos p_155204_, BlockState p_155205_, BellBlockEntity p_155206_) {
      m_155180_(p_155203_, p_155204_, p_155205_, p_155206_, BellBlockEntity::m_155186_);
   }

   public void m_58834_(Direction p_58835_) {
      BlockPos blockpos = this.m_58899_();
      this.f_58815_ = p_58835_;
      if (this.f_58814_) {
         this.f_58813_ = 0;
      } else {
         this.f_58814_ = true;
      }

      this.f_58857_.m_7696_(blockpos, this.m_58900_().m_60734_(), 1, p_58835_.m_122411_());
   }

   private void m_58845_() {
      BlockPos blockpos = this.m_58899_();
      if (this.f_58857_.m_46467_() > this.f_58816_ + 60L || this.f_58817_ == null) {
         this.f_58816_ = this.f_58857_.m_46467_();
         AABB aabb = (new AABB(blockpos)).m_82400_(48.0D);
         this.f_58817_ = this.f_58857_.m_45976_(LivingEntity.class, aabb);
      }

      if (!this.f_58857_.f_46443_) {
         for(LivingEntity livingentity : this.f_58817_) {
            if (livingentity.m_6084_() && !livingentity.m_146910_() && blockpos.m_123306_(livingentity.m_20182_(), 32.0D)) {
               livingentity.m_6274_().m_21879_(MemoryModuleType.f_26325_, this.f_58857_.m_46467_());
            }
         }
      }

   }

   private static boolean m_155199_(BlockPos p_155200_, List<LivingEntity> p_155201_) {
      for(LivingEntity livingentity : p_155201_) {
         if (livingentity.m_6084_() && !livingentity.m_146910_() && p_155200_.m_123306_(livingentity.m_20182_(), 32.0D) && livingentity.m_6095_().m_20609_(EntityTypeTags.f_13121_)) {
            return true;
         }
      }

      return false;
   }

   private static void m_155186_(Level p_155187_, BlockPos p_155188_, List<LivingEntity> p_155189_) {
      p_155189_.stream().filter((p_155219_) -> {
         return m_155196_(p_155188_, p_155219_);
      }).forEach(BellBlockEntity::m_58840_);
   }

   private static void m_155207_(Level p_155208_, BlockPos p_155209_, List<LivingEntity> p_155210_) {
      MutableInt mutableint = new MutableInt(16700985);
      int i = (int)p_155210_.stream().filter((p_155216_) -> {
         return p_155209_.m_123306_(p_155216_.m_20182_(), 48.0D);
      }).count();
      p_155210_.stream().filter((p_155213_) -> {
         return m_155196_(p_155209_, p_155213_);
      }).forEach((p_155195_) -> {
         float f = 1.0F;
         double d0 = Math.sqrt((p_155195_.m_20185_() - (double)p_155209_.m_123341_()) * (p_155195_.m_20185_() - (double)p_155209_.m_123341_()) + (p_155195_.m_20189_() - (double)p_155209_.m_123343_()) * (p_155195_.m_20189_() - (double)p_155209_.m_123343_()));
         double d1 = (double)((float)p_155209_.m_123341_() + 0.5F) + 1.0D / d0 * (p_155195_.m_20185_() - (double)p_155209_.m_123341_());
         double d2 = (double)((float)p_155209_.m_123343_() + 0.5F) + 1.0D / d0 * (p_155195_.m_20189_() - (double)p_155209_.m_123343_());
         int j = Mth.m_14045_((i - 21) / -2, 3, 15);

         for(int k = 0; k < j; ++k) {
            int l = mutableint.addAndGet(5);
            double d3 = (double)FastColor.ARGB32.m_13665_(l) / 255.0D;
            double d4 = (double)FastColor.ARGB32.m_13667_(l) / 255.0D;
            double d5 = (double)FastColor.ARGB32.m_13669_(l) / 255.0D;
            p_155208_.m_7106_(ParticleTypes.f_123811_, d1, (double)((float)p_155209_.m_123342_() + 0.5F), d2, d3, d4, d5);
         }

      });
   }

   private static boolean m_155196_(BlockPos p_155197_, LivingEntity p_155198_) {
      return p_155198_.m_6084_() && !p_155198_.m_146910_() && p_155197_.m_123306_(p_155198_.m_20182_(), 48.0D) && p_155198_.m_6095_().m_20609_(EntityTypeTags.f_13121_);
   }

   private static void m_58840_(LivingEntity p_58841_) {
      p_58841_.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 60));
   }

   @FunctionalInterface
   interface ResonationEndAction {
      void m_155220_(Level p_155221_, BlockPos p_155222_, List<LivingEntity> p_155223_);
   }
}