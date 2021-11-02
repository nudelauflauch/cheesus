package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SocializeAtBell extends Behavior<LivingEntity> {
   private static final float f_147969_ = 0.3F;

   public SocializeAtBell() {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.REGISTERED, MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_26362_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26374_, MemoryStatus.VALUE_ABSENT));
   }

   protected boolean m_6114_(ServerLevel p_24170_, LivingEntity p_24171_) {
      Brain<?> brain = p_24171_.m_6274_();
      Optional<GlobalPos> optional = brain.m_21952_(MemoryModuleType.f_26362_);
      return p_24170_.m_5822_().nextInt(100) == 0 && optional.isPresent() && p_24170_.m_46472_() == optional.get().m_122640_() && optional.get().m_122646_().m_123306_(p_24171_.m_20182_(), 4.0D) && brain.m_21952_(MemoryModuleType.f_148205_).get().stream().anyMatch((p_24189_) -> {
         return EntityType.f_20492_.equals(p_24189_.m_6095_());
      });
   }

   protected void m_6735_(ServerLevel p_24173_, LivingEntity p_24174_, long p_24175_) {
      Brain<?> brain = p_24174_.m_6274_();
      brain.m_21952_(MemoryModuleType.f_148205_).ifPresent((p_24184_) -> {
         p_24184_.stream().filter((p_147971_) -> {
            return EntityType.f_20492_.equals(p_147971_.m_6095_());
         }).filter((p_147974_) -> {
            return p_147974_.m_20280_(p_24174_) <= 32.0D;
         }).findFirst().ifPresent((p_147977_) -> {
            brain.m_21879_(MemoryModuleType.f_26374_, p_147977_);
            brain.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_147977_, true));
            brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker(p_147977_, false), 0.3F, 1));
         });
      });
   }
}