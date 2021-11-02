package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class LookAtPlayerGoal extends Goal {
   public static final float f_148115_ = 0.02F;
   protected final Mob f_25512_;
   protected Entity f_25513_;
   protected final float f_25514_;
   private int f_25518_;
   protected final float f_25515_;
   private final boolean f_148116_;
   protected final Class<? extends LivingEntity> f_25516_;
   protected final TargetingConditions f_25517_;

   public LookAtPlayerGoal(Mob p_25520_, Class<? extends LivingEntity> p_25521_, float p_25522_) {
      this(p_25520_, p_25521_, p_25522_, 0.02F);
   }

   public LookAtPlayerGoal(Mob p_25524_, Class<? extends LivingEntity> p_25525_, float p_25526_, float p_25527_) {
      this(p_25524_, p_25525_, p_25526_, p_25527_, false);
   }

   public LookAtPlayerGoal(Mob p_148118_, Class<? extends LivingEntity> p_148119_, float p_148120_, float p_148121_, boolean p_148122_) {
      this.f_25512_ = p_148118_;
      this.f_25516_ = p_148119_;
      this.f_25514_ = p_148120_;
      this.f_25515_ = p_148121_;
      this.f_148116_ = p_148122_;
      this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
      if (p_148119_ == Player.class) {
         this.f_25517_ = TargetingConditions.m_148353_().m_26883_((double)p_148120_).m_26888_((p_25531_) -> {
            return EntitySelector.m_20431_(p_148118_).test(p_25531_);
         });
      } else {
         this.f_25517_ = TargetingConditions.m_148353_().m_26883_((double)p_148120_);
      }

   }

   public boolean m_8036_() {
      if (this.f_25512_.m_21187_().nextFloat() >= this.f_25515_) {
         return false;
      } else {
         if (this.f_25512_.m_5448_() != null) {
            this.f_25513_ = this.f_25512_.m_5448_();
         }

         if (this.f_25516_ == Player.class) {
            this.f_25513_ = this.f_25512_.f_19853_.m_45949_(this.f_25517_, this.f_25512_, this.f_25512_.m_20185_(), this.f_25512_.m_20188_(), this.f_25512_.m_20189_());
         } else {
            this.f_25513_ = this.f_25512_.f_19853_.m_45982_(this.f_25512_.f_19853_.m_6443_(this.f_25516_, this.f_25512_.m_142469_().m_82377_((double)this.f_25514_, 3.0D, (double)this.f_25514_), (p_148124_) -> {
               return true;
            }), this.f_25517_, this.f_25512_, this.f_25512_.m_20185_(), this.f_25512_.m_20188_(), this.f_25512_.m_20189_());
         }

         return this.f_25513_ != null;
      }
   }

   public boolean m_8045_() {
      if (!this.f_25513_.m_6084_()) {
         return false;
      } else if (this.f_25512_.m_20280_(this.f_25513_) > (double)(this.f_25514_ * this.f_25514_)) {
         return false;
      } else {
         return this.f_25518_ > 0;
      }
   }

   public void m_8056_() {
      this.f_25518_ = 40 + this.f_25512_.m_21187_().nextInt(40);
   }

   public void m_8041_() {
      this.f_25513_ = null;
   }

   public void m_8037_() {
      double d0 = this.f_148116_ ? this.f_25512_.m_20188_() : this.f_25513_.m_20188_();
      this.f_25512_.m_21563_().m_24946_(this.f_25513_.m_20185_(), d0, this.f_25513_.m_20189_());
      --this.f_25518_;
   }
}