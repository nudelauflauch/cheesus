package net.minecraft.client.profiling;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import net.minecraft.util.profiling.metrics.profiling.ProfilerSamplerAdapter;
import net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientMetricsSamplersProvider implements MetricsSamplerProvider {
   private final LevelRenderer f_172536_;
   private final Set<MetricSampler> f_172537_ = new ObjectOpenHashSet<>();
   private final ProfilerSamplerAdapter f_172538_ = new ProfilerSamplerAdapter();

   public ClientMetricsSamplersProvider(LongSupplier p_172540_, LevelRenderer p_172541_) {
      this.f_172536_ = p_172541_;
      this.f_172537_.add(ServerMetricsSamplersProvider.m_146188_(p_172540_));
      this.m_172542_();
   }

   private void m_172542_() {
      this.f_172537_.addAll(ServerMetricsSamplersProvider.m_146182_());
      this.f_172537_.add(MetricSampler.m_146004_("totalChunks", MetricCategory.CHUNK_RENDERING, this.f_172536_, LevelRenderer::m_173016_));
      this.f_172537_.add(MetricSampler.m_146004_("renderedChunks", MetricCategory.CHUNK_RENDERING, this.f_172536_, LevelRenderer::m_109821_));
      this.f_172537_.add(MetricSampler.m_146004_("lastViewDistance", MetricCategory.CHUNK_RENDERING, this.f_172536_, LevelRenderer::m_173017_));
      ChunkRenderDispatcher chunkrenderdispatcher = this.f_172536_.m_173015_();
      this.f_172537_.add(MetricSampler.m_146004_("toUpload", MetricCategory.CHUNK_RENDERING_DISPATCHING, chunkrenderdispatcher, ChunkRenderDispatcher::m_173713_));
      this.f_172537_.add(MetricSampler.m_146004_("freeBufferCount", MetricCategory.CHUNK_RENDERING_DISPATCHING, chunkrenderdispatcher, ChunkRenderDispatcher::m_173714_));
      this.f_172537_.add(MetricSampler.m_146004_("toBatchCount", MetricCategory.CHUNK_RENDERING_DISPATCHING, chunkrenderdispatcher, ChunkRenderDispatcher::m_173712_));
   }

   public Set<MetricSampler> m_142531_(Supplier<ProfileCollector> p_172544_) {
      this.f_172537_.addAll(this.f_172538_.m_146163_(p_172544_));
      return this.f_172537_;
   }
}