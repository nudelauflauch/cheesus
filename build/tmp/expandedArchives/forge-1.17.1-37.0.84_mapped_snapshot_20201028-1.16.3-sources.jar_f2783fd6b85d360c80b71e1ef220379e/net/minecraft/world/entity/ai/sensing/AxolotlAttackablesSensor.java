package net.minecraft.world.entity.ai.sensing;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class AxolotlAttackablesSensor extends NearestVisibleLivingEntitySensor {
   public static final float f_148263_ = 8.0F;

   protected boolean m_142628_(LivingEntity p_148266_, LivingEntity p_148267_) {
      if (Sensor.m_148312_(p_148266_, p_148267_) && (this.m_148269_(p_148267_) || this.m_148271_(p_148266_, p_148267_))) {
         return this.m_148274_(p_148266_, p_148267_) && p_148267_.m_20072_();
      } else {
         return false;
      }
   }

   private boolean m_148271_(LivingEntity p_148272_, LivingEntity p_148273_) {
      return !p_148272_.m_6274_().m_21874_(MemoryModuleType.f_148201_) && EntityTypeTags.f_144293_.m_8110_(p_148273_.m_6095_());
   }

   private boolean m_148269_(LivingEntity p_148270_) {
      return EntityTypeTags.f_144292_.m_8110_(p_148270_.m_6095_());
   }

   private boolean m_148274_(LivingEntity p_148275_, LivingEntity p_148276_) {
      return p_148276_.m_20280_(p_148275_) <= 64.0D;
   }

   protected MemoryModuleType<LivingEntity> m_142149_() {
      return MemoryModuleType.f_148194_;
   }
}