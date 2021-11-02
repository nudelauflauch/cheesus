package net.minecraft.util.profiling.metrics;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;

public class MetricSampler {
   private final String f_145987_;
   private final MetricCategory f_145988_;
   private final DoubleSupplier f_145989_;
   private final ByteBuf f_145990_;
   private final ByteBuf f_145991_;
   private volatile boolean f_145992_;
   @Nullable
   private final Runnable f_145993_;
   @Nullable
   final MetricSampler.ThresholdTest f_145986_;
   private double f_145994_;

   protected MetricSampler(String p_145996_, MetricCategory p_145997_, DoubleSupplier p_145998_, @Nullable Runnable p_145999_, @Nullable MetricSampler.ThresholdTest p_146000_) {
      this.f_145987_ = p_145996_;
      this.f_145988_ = p_145997_;
      this.f_145993_ = p_145999_;
      this.f_145989_ = p_145998_;
      this.f_145986_ = p_146000_;
      this.f_145991_ = ByteBufAllocator.DEFAULT.buffer();
      this.f_145990_ = ByteBufAllocator.DEFAULT.buffer();
      this.f_145992_ = true;
   }

   public static MetricSampler m_146009_(String p_146010_, MetricCategory p_146011_, DoubleSupplier p_146012_) {
      return new MetricSampler(p_146010_, p_146011_, p_146012_, (Runnable)null, (MetricSampler.ThresholdTest)null);
   }

   public static <T> MetricSampler m_146004_(String p_146005_, MetricCategory p_146006_, T p_146007_, ToDoubleFunction<T> p_146008_) {
      return m_146013_(p_146005_, p_146006_, p_146008_, p_146007_).m_146039_();
   }

   public static <T> MetricSampler.MetricSamplerBuilder<T> m_146013_(String p_146014_, MetricCategory p_146015_, ToDoubleFunction<T> p_146016_, T p_146017_) {
      return new MetricSampler.MetricSamplerBuilder<>(p_146014_, p_146015_, p_146016_, p_146017_);
   }

   public void m_146001_() {
      if (!this.f_145992_) {
         throw new IllegalStateException("Not running");
      } else {
         if (this.f_145993_ != null) {
            this.f_145993_.run();
         }

      }
   }

   public void m_146002_(int p_146003_) {
      this.m_146026_();
      this.f_145994_ = this.f_145989_.getAsDouble();
      this.f_145991_.writeDouble(this.f_145994_);
      this.f_145990_.writeInt(p_146003_);
   }

   public void m_146018_() {
      this.m_146026_();
      this.f_145991_.release();
      this.f_145990_.release();
      this.f_145992_ = false;
   }

   private void m_146026_() {
      if (!this.f_145992_) {
         throw new IllegalStateException(String.format("Sampler for metric %s not started!", this.f_145987_));
      }
   }

   DoubleSupplier m_146019_() {
      return this.f_145989_;
   }

   public String m_146020_() {
      return this.f_145987_;
   }

   public MetricCategory m_146021_() {
      return this.f_145988_;
   }

   public MetricSampler.SamplerResult m_146024_() {
      Int2DoubleMap int2doublemap = new Int2DoubleOpenHashMap();
      int i = Integer.MIN_VALUE;

      int j;
      int k;
      for(j = Integer.MIN_VALUE; this.f_145991_.isReadable(8); j = k) {
         k = this.f_145990_.readInt();
         if (i == Integer.MIN_VALUE) {
            i = k;
         }

         int2doublemap.put(k, this.f_145991_.readDouble());
      }

      return new MetricSampler.SamplerResult(i, j, int2doublemap);
   }

   public boolean m_146025_() {
      return this.f_145986_ != null && this.f_145986_.m_142488_(this.f_145994_);
   }

   public boolean equals(Object p_146023_) {
      if (this == p_146023_) {
         return true;
      } else if (p_146023_ != null && this.getClass() == p_146023_.getClass()) {
         MetricSampler metricsampler = (MetricSampler)p_146023_;
         return this.f_145987_.equals(metricsampler.f_145987_) && this.f_145988_.equals(metricsampler.f_145988_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.f_145987_.hashCode();
   }

   public static class MetricSamplerBuilder<T> {
      private final String f_146028_;
      private final MetricCategory f_146029_;
      private final DoubleSupplier f_146030_;
      private final T f_146031_;
      @Nullable
      private Runnable f_146032_;
      @Nullable
      private MetricSampler.ThresholdTest f_146033_;

      public MetricSamplerBuilder(String p_146035_, MetricCategory p_146036_, ToDoubleFunction<T> p_146037_, T p_146038_) {
         this.f_146028_ = p_146035_;
         this.f_146029_ = p_146036_;
         this.f_146030_ = () -> {
            return p_146037_.applyAsDouble(p_146038_);
         };
         this.f_146031_ = p_146038_;
      }

      public MetricSampler.MetricSamplerBuilder<T> m_146042_(Consumer<T> p_146043_) {
         this.f_146032_ = () -> {
            p_146043_.accept(this.f_146031_);
         };
         return this;
      }

      public MetricSampler.MetricSamplerBuilder<T> m_146040_(MetricSampler.ThresholdTest p_146041_) {
         this.f_146033_ = p_146041_;
         return this;
      }

      public MetricSampler m_146039_() {
         return new MetricSampler(this.f_146028_, this.f_146029_, this.f_146030_, this.f_146032_, this.f_146033_);
      }
   }

   public static class SamplerResult {
      private final Int2DoubleMap f_146049_;
      private final int f_146050_;
      private final int f_146051_;

      public SamplerResult(int p_146053_, int p_146054_, Int2DoubleMap p_146055_) {
         this.f_146050_ = p_146053_;
         this.f_146051_ = p_146054_;
         this.f_146049_ = p_146055_;
      }

      public double m_146057_(int p_146058_) {
         return this.f_146049_.get(p_146058_);
      }

      public int m_146056_() {
         return this.f_146050_;
      }

      public int m_146059_() {
         return this.f_146051_;
      }
   }

   public interface ThresholdTest {
      boolean m_142488_(double p_146060_);
   }

   public static class ValueIncreasedByPercentage implements MetricSampler.ThresholdTest {
      private final float f_146061_;
      private double f_146062_ = Double.MIN_VALUE;

      public ValueIncreasedByPercentage(float p_146064_) {
         this.f_146061_ = p_146064_;
      }

      public boolean m_142488_(double p_146066_) {
         boolean flag;
         if (this.f_146062_ != Double.MIN_VALUE && !(p_146066_ <= this.f_146062_)) {
            flag = (p_146066_ - this.f_146062_) / this.f_146062_ >= (double)this.f_146061_;
         } else {
            flag = false;
         }

         this.f_146062_ = p_146066_;
         return flag;
      }
   }
}