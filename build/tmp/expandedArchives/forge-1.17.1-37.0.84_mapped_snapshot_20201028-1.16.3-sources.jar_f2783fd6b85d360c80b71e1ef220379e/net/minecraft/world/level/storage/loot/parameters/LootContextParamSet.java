package net.minecraft.world.level.storage.loot.parameters;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.ValidationContext;

public class LootContextParamSet {
   private final Set<LootContextParam<?>> f_81385_;
   private final Set<LootContextParam<?>> f_81386_;

   LootContextParamSet(Set<LootContextParam<?>> p_81388_, Set<LootContextParam<?>> p_81389_) {
      this.f_81385_ = ImmutableSet.copyOf(p_81388_);
      this.f_81386_ = ImmutableSet.copyOf(Sets.union(p_81388_, p_81389_));
   }

   public boolean m_165475_(LootContextParam<?> p_165476_) {
      return this.f_81386_.contains(p_165476_);
   }

   public Set<LootContextParam<?>> m_81394_() {
      return this.f_81385_;
   }

   public Set<LootContextParam<?>> m_81398_() {
      return this.f_81386_;
   }

   public String toString() {
      return "[" + Joiner.on(", ").join(this.f_81386_.stream().map((p_81400_) -> {
         return (this.f_81385_.contains(p_81400_) ? "!" : "") + p_81400_.m_81284_();
      }).iterator()) + "]";
   }

   public void m_81395_(ValidationContext p_81396_, LootContextUser p_81397_) {
      Set<LootContextParam<?>> set = p_81397_.m_6231_();
      Set<LootContextParam<?>> set1 = Sets.difference(set, this.f_81386_);
      if (!set1.isEmpty()) {
         p_81396_.m_79357_("Parameters " + set1 + " are not provided in this context");
      }

   }

   public static LootContextParamSet.Builder m_165477_() {
      return new LootContextParamSet.Builder();
   }

   public static class Builder {
      private final Set<LootContextParam<?>> f_81402_ = Sets.newIdentityHashSet();
      private final Set<LootContextParam<?>> f_81403_ = Sets.newIdentityHashSet();

      public LootContextParamSet.Builder m_81406_(LootContextParam<?> p_81407_) {
         if (this.f_81403_.contains(p_81407_)) {
            throw new IllegalArgumentException("Parameter " + p_81407_.m_81284_() + " is already optional");
         } else {
            this.f_81402_.add(p_81407_);
            return this;
         }
      }

      public LootContextParamSet.Builder m_81408_(LootContextParam<?> p_81409_) {
         if (this.f_81402_.contains(p_81409_)) {
            throw new IllegalArgumentException("Parameter " + p_81409_.m_81284_() + " is already required");
         } else {
            this.f_81403_.add(p_81409_);
            return this;
         }
      }

      public LootContextParamSet m_81405_() {
         return new LootContextParamSet(this.f_81402_, this.f_81403_);
      }
   }
}