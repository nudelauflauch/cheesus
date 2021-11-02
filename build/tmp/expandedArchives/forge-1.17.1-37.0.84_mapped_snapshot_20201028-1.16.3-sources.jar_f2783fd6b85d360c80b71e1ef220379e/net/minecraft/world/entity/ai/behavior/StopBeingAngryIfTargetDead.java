package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.GameRules;

public class StopBeingAngryIfTargetDead<E extends Mob> extends Behavior<E> {
   public StopBeingAngryIfTargetDead() {
      super(ImmutableMap.of(MemoryModuleType.f_26334_, MemoryStatus.VALUE_PRESENT));
   }

   protected void m_6735_(ServerLevel p_24263_, E p_24264_, long p_24265_) {
      BehaviorUtils.m_22610_(p_24264_, MemoryModuleType.f_26334_).ifPresent((p_24269_) -> {
         if (p_24269_.m_21224_() && (p_24269_.m_6095_() != EntityType.f_20532_ || p_24263_.m_46469_().m_46207_(GameRules.f_46126_))) {
            p_24264_.m_6274_().m_21936_(MemoryModuleType.f_26334_);
         }

      });
   }
}