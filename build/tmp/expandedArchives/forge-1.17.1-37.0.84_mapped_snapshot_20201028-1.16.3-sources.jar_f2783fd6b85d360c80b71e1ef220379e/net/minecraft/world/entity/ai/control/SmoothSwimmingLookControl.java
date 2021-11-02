package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class SmoothSwimmingLookControl extends LookControl {
   private final int f_148057_;
   private static final int f_148058_ = 10;
   private static final int f_148059_ = 20;

   public SmoothSwimmingLookControl(Mob p_148061_, int p_148062_) {
      super(p_148061_);
      this.f_148057_ = p_148062_;
   }

   public void m_8128_() {
      if (this.f_24940_) {
         this.f_24940_ = false;
         this.m_180896_().ifPresent((p_181134_) -> {
            this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, p_181134_ + 20.0F, this.f_24938_);
         });
         this.m_180897_().ifPresent((p_181132_) -> {
            this.f_24937_.m_146926_(this.m_24956_(this.f_24937_.m_146909_(), p_181132_ + 10.0F, this.f_24939_));
         });
      } else {
         if (this.f_24937_.m_21573_().m_26571_()) {
            this.f_24937_.m_146926_(this.m_24956_(this.f_24937_.m_146909_(), 0.0F, 5.0F));
         }

         this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, this.f_24938_);
      }

      float f = Mth.m_14177_(this.f_24937_.f_20885_ - this.f_24937_.f_20883_);
      if (f < (float)(-this.f_148057_)) {
         this.f_24937_.f_20883_ -= 4.0F;
      } else if (f > (float)this.f_148057_) {
         this.f_24937_.f_20883_ += 4.0F;
      }

   }
}