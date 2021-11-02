package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class OwnerHurtTargetGoal extends TargetGoal {
   private final TamableAnimal f_26110_;
   private LivingEntity f_26111_;
   private int f_26112_;

   public OwnerHurtTargetGoal(TamableAnimal p_26114_) {
      super(p_26114_, false);
      this.f_26110_ = p_26114_;
      this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
   }

   public boolean m_8036_() {
      if (this.f_26110_.m_21824_() && !this.f_26110_.m_21827_()) {
         LivingEntity livingentity = this.f_26110_.m_142480_();
         if (livingentity == null) {
            return false;
         } else {
            this.f_26111_ = livingentity.m_21214_();
            int i = livingentity.m_21215_();
            return i != this.f_26112_ && this.m_26150_(this.f_26111_, TargetingConditions.f_26872_) && this.f_26110_.m_7757_(this.f_26111_, livingentity);
         }
      } else {
         return false;
      }
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.f_26111_);
      LivingEntity livingentity = this.f_26110_.m_142480_();
      if (livingentity != null) {
         this.f_26112_ = livingentity.m_21215_();
      }

      super.m_8056_();
   }
}