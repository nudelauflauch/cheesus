package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BabyFollowAdult<E extends AgeableMob> extends Behavior<E> {
   private final UniformInt f_22468_;
   private final Function<LivingEntity, Float> f_22469_;

   public BabyFollowAdult(UniformInt p_147414_, float p_147415_) {
      this(p_147414_, (p_147421_) -> {
         return p_147415_;
      });
   }

   public BabyFollowAdult(UniformInt p_147417_, Function<LivingEntity, Float> p_147418_) {
      super(ImmutableMap.of(MemoryModuleType.f_26331_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT));
      this.f_22468_ = p_147417_;
      this.f_22469_ = p_147418_;
   }

   protected boolean m_6114_(ServerLevel p_147423_, E p_147424_) {
      if (!p_147424_.m_6162_()) {
         return false;
      } else {
         AgeableMob ageablemob = this.m_147429_(p_147424_);
         return p_147424_.m_19950_(ageablemob, (double)(this.f_22468_.m_142737_() + 1)) && !p_147424_.m_19950_(ageablemob, (double)this.f_22468_.m_142739_());
      }
   }

   protected void m_6735_(ServerLevel p_147426_, E p_147427_, long p_147428_) {
      BehaviorUtils.m_22590_(p_147427_, this.m_147429_(p_147427_), this.f_22469_.apply(p_147427_), this.f_22468_.m_142739_() - 1);
   }

   private AgeableMob m_147429_(E p_147430_) {
      return p_147430_.m_6274_().m_21952_(MemoryModuleType.f_26331_).get();
   }
}