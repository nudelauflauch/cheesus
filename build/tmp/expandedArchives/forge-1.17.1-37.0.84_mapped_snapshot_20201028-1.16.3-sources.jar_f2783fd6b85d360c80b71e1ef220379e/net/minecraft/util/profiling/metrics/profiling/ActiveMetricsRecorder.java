package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ContinuousProfiler;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.profiling.metrics.storage.RecordedDeviation;

public class ActiveMetricsRecorder implements MetricsRecorder {
   public static final int f_146104_ = 10;
   @Nullable
   private static Consumer<Path> f_146105_ = null;
   private final Map<MetricSampler, List<RecordedDeviation>> f_146106_ = new Object2ObjectOpenHashMap<>();
   private final ContinuousProfiler f_146107_;
   private final Executor f_146108_;
   private final MetricsPersister f_146109_;
   private final Consumer<ProfileResults> f_146110_;
   private final Consumer<Path> f_146111_;
   private final MetricsSamplerProvider f_146112_;
   private final LongSupplier f_146113_;
   private final long f_146114_;
   private int f_146115_;
   private ProfileCollector f_146116_;
   private volatile boolean f_146117_;
   private Set<MetricSampler> f_146118_ = ImmutableSet.of();

   private ActiveMetricsRecorder(MetricsSamplerProvider p_146121_, LongSupplier p_146122_, Executor p_146123_, MetricsPersister p_146124_, Consumer<ProfileResults> p_146125_, Consumer<Path> p_146126_) {
      this.f_146112_ = p_146121_;
      this.f_146113_ = p_146122_;
      this.f_146107_ = new ContinuousProfiler(p_146122_, () -> {
         return this.f_146115_;
      });
      this.f_146108_ = p_146123_;
      this.f_146109_ = p_146124_;
      this.f_146110_ = p_146125_;
      this.f_146111_ = f_146105_ == null ? p_146126_ : p_146126_.andThen(f_146105_);
      this.f_146114_ = p_146122_.getAsLong() + TimeUnit.NANOSECONDS.convert(10L, TimeUnit.SECONDS);
      this.f_146116_ = new ActiveProfiler(this.f_146113_, () -> {
         return this.f_146115_;
      }, false);
      this.f_146107_.m_18438_();
   }

   public static ActiveMetricsRecorder m_146132_(MetricsSamplerProvider p_146133_, LongSupplier p_146134_, Executor p_146135_, MetricsPersister p_146136_, Consumer<ProfileResults> p_146137_, Consumer<Path> p_146138_) {
      return new ActiveMetricsRecorder(p_146133_, p_146134_, p_146135_, p_146136_, p_146137_, p_146138_);
   }

   public synchronized void m_142760_() {
      if (this.m_142763_()) {
         this.f_146117_ = true;
      }
   }

   public void m_142759_() {
      this.m_146148_();
      this.f_146118_ = this.f_146112_.m_142531_(() -> {
         return this.f_146116_;
      });

      for(MetricSampler metricsampler : this.f_146118_) {
         metricsampler.m_146001_();
      }

      ++this.f_146115_;
   }

   public void m_142758_() {
      this.m_146148_();
      if (this.f_146115_ != 0) {
         for(MetricSampler metricsampler : this.f_146118_) {
            metricsampler.m_146002_(this.f_146115_);
            if (metricsampler.m_146025_()) {
               RecordedDeviation recordeddeviation = new RecordedDeviation(Instant.now(), this.f_146115_, this.f_146116_.m_5948_());
               this.f_146106_.computeIfAbsent(metricsampler, (p_146131_) -> {
                  return Lists.newArrayList();
               }).add(recordeddeviation);
            }
         }

         if (!this.f_146117_ && this.f_146113_.getAsLong() <= this.f_146114_) {
            this.f_146116_ = new ActiveProfiler(this.f_146113_, () -> {
               return this.f_146115_;
            }, false);
         } else {
            this.f_146117_ = false;
            this.f_146116_ = InactiveProfiler.f_18554_;
            ProfileResults profileresults = this.f_146107_.m_18440_();
            this.f_146110_.accept(profileresults);
            this.m_146128_(profileresults);
         }
      }
   }

   public boolean m_142763_() {
      return this.f_146107_.m_18436_();
   }

   public ProfilerFiller m_142610_() {
      return ProfilerFiller.m_18578_(this.f_146107_.m_18439_(), this.f_146116_);
   }

   private void m_146148_() {
      if (!this.m_142763_()) {
         throw new IllegalStateException("Not started!");
      }
   }

   private void m_146128_(ProfileResults p_146129_) {
      HashSet<MetricSampler> hashset = new HashSet<>(this.f_146118_);
      this.f_146108_.execute(() -> {
         Path path = this.f_146109_.m_146250_(hashset, this.f_146106_, p_146129_);

         for(MetricSampler metricsampler : hashset) {
            metricsampler.m_146018_();
         }

         this.f_146106_.clear();
         this.f_146107_.m_18437_();
         this.f_146111_.accept(path);
      });
   }

   public static void m_146142_(Consumer<Path> p_146143_) {
      f_146105_ = p_146143_;
   }
}