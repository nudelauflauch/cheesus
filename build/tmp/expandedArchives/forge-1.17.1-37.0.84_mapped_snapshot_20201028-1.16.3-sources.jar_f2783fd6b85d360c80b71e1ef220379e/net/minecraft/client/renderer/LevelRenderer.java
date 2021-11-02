package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class LevelRenderer implements ResourceManagerReloadListener, AutoCloseable {
   private static final Logger f_109453_ = LogManager.getLogger();
   public static final int f_172937_ = 16;
   public static final int f_172939_ = 66;
   public static final int f_172940_ = 4356;
   private static final float f_172941_ = 512.0F;
   private static final int f_172942_ = 32;
   private static final int f_172943_ = 10;
   private static final int f_172944_ = 21;
   private static final int f_172945_ = 15;
   private static final ResourceLocation f_109454_ = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation f_109455_ = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation f_109456_ = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation f_109457_ = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation f_109458_ = new ResourceLocation("textures/misc/forcefield.png");
   private static final ResourceLocation f_109459_ = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation f_109460_ = new ResourceLocation("textures/environment/snow.png");
   public static final Direction[] f_109434_ = Direction.values();
   private final Minecraft f_109461_;
   private final TextureManager f_109462_;
   private final EntityRenderDispatcher f_109463_;
   private final BlockEntityRenderDispatcher f_172946_;
   private final RenderBuffers f_109464_;
   private ClientLevel f_109465_;
   private Set<ChunkRenderDispatcher.RenderChunk> f_109466_ = Sets.newLinkedHashSet();
   private final ObjectArrayList<LevelRenderer.RenderChunkInfo> f_109467_ = new ObjectArrayList<>();
   private final Set<BlockEntity> f_109468_ = Sets.newHashSet();
   private ViewArea f_109469_;
   private LevelRenderer.RenderInfoMap f_172936_;
   @Nullable
   private VertexBuffer f_109471_;
   @Nullable
   private VertexBuffer f_109472_;
   @Nullable
   private VertexBuffer f_109473_;
   private boolean f_109474_ = true;
   @Nullable
   private VertexBuffer f_109475_;
   private final RunningTrimmedMean f_109476_ = new RunningTrimmedMean(100);
   private int f_109477_;
   private final Int2ObjectMap<BlockDestructionProgress> f_109408_ = new Int2ObjectOpenHashMap<>();
   private final Long2ObjectMap<SortedSet<BlockDestructionProgress>> f_109409_ = new Long2ObjectOpenHashMap<>();
   private final Map<BlockPos, SoundInstance> f_109410_ = Maps.newHashMap();
   @Nullable
   private RenderTarget f_109411_;
   @Nullable
   private PostChain f_109412_;
   @Nullable
   private RenderTarget f_109413_;
   @Nullable
   private RenderTarget f_109414_;
   @Nullable
   private RenderTarget f_109415_;
   @Nullable
   private RenderTarget f_109416_;
   @Nullable
   private RenderTarget f_109417_;
   @Nullable
   private PostChain f_109418_;
   private double f_109419_ = Double.MIN_VALUE;
   private double f_109420_ = Double.MIN_VALUE;
   private double f_109421_ = Double.MIN_VALUE;
   private int f_109422_ = Integer.MIN_VALUE;
   private int f_109423_ = Integer.MIN_VALUE;
   private int f_109424_ = Integer.MIN_VALUE;
   private double f_109425_ = Double.MIN_VALUE;
   private double f_109426_ = Double.MIN_VALUE;
   private double f_109427_ = Double.MIN_VALUE;
   private double f_109428_ = Double.MIN_VALUE;
   private double f_109429_ = Double.MIN_VALUE;
   private int f_109430_ = Integer.MIN_VALUE;
   private int f_109431_ = Integer.MIN_VALUE;
   private int f_109432_ = Integer.MIN_VALUE;
   private Vec3 f_109433_ = Vec3.f_82478_;
   private CloudStatus f_109435_;
   private ChunkRenderDispatcher f_109436_;
   private int f_109438_ = -1;
   private int f_109439_;
   private int f_109440_;
   private Frustum f_172938_;
   private boolean f_109441_;
   @Nullable
   private Frustum f_109442_;
   private final Vector4f[] f_109443_ = new Vector4f[8];
   private final Vector3d f_109444_ = new Vector3d(0.0D, 0.0D, 0.0D);
   private double f_109445_;
   private double f_109446_;
   private double f_109447_;
   private boolean f_109448_ = true;
   private int f_109449_;
   private int f_109450_;
   private final float[] f_109451_ = new float[1024];
   private final float[] f_109452_ = new float[1024];

   public LevelRenderer(Minecraft p_109480_, RenderBuffers p_109481_) {
      this.f_109461_ = p_109480_;
      this.f_109463_ = p_109480_.m_91290_();
      this.f_172946_ = p_109480_.m_167982_();
      this.f_109464_ = p_109481_;
      this.f_109462_ = p_109480_.m_91097_();

      for(int i = 0; i < 32; ++i) {
         for(int j = 0; j < 32; ++j) {
            float f = (float)(j - 16);
            float f1 = (float)(i - 16);
            float f2 = Mth.m_14116_(f * f + f1 * f1);
            this.f_109451_[i << 5 | j] = -f1 / f2;
            this.f_109452_[i << 5 | j] = f / f2;
         }
      }

      this.m_109837_();
      this.m_109836_();
      this.m_109835_();
   }

   private void m_109703_(LightTexture p_109704_, float p_109705_, double p_109706_, double p_109707_, double p_109708_) {
      net.minecraftforge.client.IWeatherRenderHandler renderHandler = f_109465_.m_104583_().getWeatherRenderHandler();
      if (renderHandler != null) {
         renderHandler.render(f_109477_, p_109705_, f_109465_, f_109461_, p_109704_, p_109706_, p_109707_, p_109708_);
         return;
      }
      float f = this.f_109461_.f_91073_.m_46722_(p_109705_);
      if (!(f <= 0.0F)) {
         p_109704_.m_109896_();
         Level level = this.f_109461_.f_91073_;
         int i = Mth.m_14107_(p_109706_);
         int j = Mth.m_14107_(p_109707_);
         int k = Mth.m_14107_(p_109708_);
         Tesselator tesselator = Tesselator.m_85913_();
         BufferBuilder bufferbuilder = tesselator.m_85915_();
         RenderSystem.m_69464_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         RenderSystem.m_69482_();
         int l = 5;
         if (Minecraft.m_91405_()) {
            l = 10;
         }

         RenderSystem.m_69458_(Minecraft.m_91085_());
         int i1 = -1;
         float f1 = (float)this.f_109477_ + p_109705_;
         RenderSystem.m_157427_(GameRenderer::m_172829_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int j1 = k - l; j1 <= k + l; ++j1) {
            for(int k1 = i - l; k1 <= i + l; ++k1) {
               int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
               double d0 = (double)this.f_109451_[l1] * 0.5D;
               double d1 = (double)this.f_109452_[l1] * 0.5D;
               blockpos$mutableblockpos.m_122178_(k1, 0, j1);
               Biome biome = level.m_46857_(blockpos$mutableblockpos);
               if (biome.m_47530_() != Biome.Precipitation.NONE) {
                  int i2 = level.m_5452_(Heightmap.Types.MOTION_BLOCKING, blockpos$mutableblockpos).m_123342_();
                  int j2 = j - l;
                  int k2 = j + l;
                  if (j2 < i2) {
                     j2 = i2;
                  }

                  if (k2 < i2) {
                     k2 = i2;
                  }

                  int l2 = i2;
                  if (i2 < j) {
                     l2 = j;
                  }

                  if (j2 != k2) {
                     Random random = new Random((long)(k1 * k1 * 3121 + k1 * 45238971 ^ j1 * j1 * 418711 + j1 * 13761));
                     blockpos$mutableblockpos.m_122178_(k1, j2, j1);
                     float f2 = biome.m_47505_(blockpos$mutableblockpos);
                     if (f2 >= 0.15F) {
                        if (i1 != 0) {
                           if (i1 >= 0) {
                              tesselator.m_85914_();
                           }

                           i1 = 0;
                           RenderSystem.m_157456_(0, f_109459_);
                           bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
                        }

                        int i3 = this.f_109477_ + k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 31;
                        float f3 = -((float)i3 + p_109705_) / 32.0F * (3.0F + random.nextFloat());
                        double d2 = (double)k1 + 0.5D - p_109706_;
                        double d4 = (double)j1 + 0.5D - p_109708_;
                        float f4 = (float)Math.sqrt(d2 * d2 + d4 * d4) / (float)l;
                        float f5 = ((1.0F - f4 * f4) * 0.5F + 0.5F) * f;
                        blockpos$mutableblockpos.m_122178_(k1, l2, j1);
                        int j3 = m_109541_(level, blockpos$mutableblockpos);
                        bufferbuilder.m_5483_((double)k1 - p_109706_ - d0 + 0.5D, (double)k2 - p_109707_, (double)j1 - p_109708_ - d1 + 0.5D).m_7421_(0.0F, (float)j2 * 0.25F + f3).m_85950_(1.0F, 1.0F, 1.0F, f5).m_85969_(j3).m_5752_();
                        bufferbuilder.m_5483_((double)k1 - p_109706_ + d0 + 0.5D, (double)k2 - p_109707_, (double)j1 - p_109708_ + d1 + 0.5D).m_7421_(1.0F, (float)j2 * 0.25F + f3).m_85950_(1.0F, 1.0F, 1.0F, f5).m_85969_(j3).m_5752_();
                        bufferbuilder.m_5483_((double)k1 - p_109706_ + d0 + 0.5D, (double)j2 - p_109707_, (double)j1 - p_109708_ + d1 + 0.5D).m_7421_(1.0F, (float)k2 * 0.25F + f3).m_85950_(1.0F, 1.0F, 1.0F, f5).m_85969_(j3).m_5752_();
                        bufferbuilder.m_5483_((double)k1 - p_109706_ - d0 + 0.5D, (double)j2 - p_109707_, (double)j1 - p_109708_ - d1 + 0.5D).m_7421_(0.0F, (float)k2 * 0.25F + f3).m_85950_(1.0F, 1.0F, 1.0F, f5).m_85969_(j3).m_5752_();
                     } else {
                        if (i1 != 1) {
                           if (i1 >= 0) {
                              tesselator.m_85914_();
                           }

                           i1 = 1;
                           RenderSystem.m_157456_(0, f_109460_);
                           bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
                        }

                        float f6 = -((float)(this.f_109477_ & 511) + p_109705_) / 512.0F;
                        float f7 = (float)(random.nextDouble() + (double)f1 * 0.01D * (double)((float)random.nextGaussian()));
                        float f8 = (float)(random.nextDouble() + (double)(f1 * (float)random.nextGaussian()) * 0.001D);
                        double d3 = (double)k1 + 0.5D - p_109706_;
                        double d5 = (double)j1 + 0.5D - p_109708_;
                        float f9 = (float)Math.sqrt(d3 * d3 + d5 * d5) / (float)l;
                        float f10 = ((1.0F - f9 * f9) * 0.3F + 0.5F) * f;
                        blockpos$mutableblockpos.m_122178_(k1, l2, j1);
                        int k3 = m_109541_(level, blockpos$mutableblockpos);
                        int l3 = k3 >> 16 & '\uffff';
                        int i4 = k3 & '\uffff';
                        int j4 = (l3 * 3 + 240) / 4;
                        int k4 = (i4 * 3 + 240) / 4;
                        bufferbuilder.m_5483_((double)k1 - p_109706_ - d0 + 0.5D, (double)k2 - p_109707_, (double)j1 - p_109708_ - d1 + 0.5D).m_7421_(0.0F + f7, (float)j2 * 0.25F + f6 + f8).m_85950_(1.0F, 1.0F, 1.0F, f10).m_7120_(k4, j4).m_5752_();
                        bufferbuilder.m_5483_((double)k1 - p_109706_ + d0 + 0.5D, (double)k2 - p_109707_, (double)j1 - p_109708_ + d1 + 0.5D).m_7421_(1.0F + f7, (float)j2 * 0.25F + f6 + f8).m_85950_(1.0F, 1.0F, 1.0F, f10).m_7120_(k4, j4).m_5752_();
                        bufferbuilder.m_5483_((double)k1 - p_109706_ + d0 + 0.5D, (double)j2 - p_109707_, (double)j1 - p_109708_ + d1 + 0.5D).m_7421_(1.0F + f7, (float)k2 * 0.25F + f6 + f8).m_85950_(1.0F, 1.0F, 1.0F, f10).m_7120_(k4, j4).m_5752_();
                        bufferbuilder.m_5483_((double)k1 - p_109706_ - d0 + 0.5D, (double)j2 - p_109707_, (double)j1 - p_109708_ - d1 + 0.5D).m_7421_(0.0F + f7, (float)k2 * 0.25F + f6 + f8).m_85950_(1.0F, 1.0F, 1.0F, f10).m_7120_(k4, j4).m_5752_();
                     }
                  }
               }
            }
         }

         if (i1 >= 0) {
            tesselator.m_85914_();
         }

         RenderSystem.m_69481_();
         RenderSystem.m_69461_();
         p_109704_.m_109891_();
      }
   }

   public void m_109693_(Camera p_109694_) {
      net.minecraftforge.client.IWeatherParticleRenderHandler renderHandler = f_109465_.m_104583_().getWeatherParticleRenderHandler();
      if (renderHandler != null) {
         renderHandler.render(f_109477_, f_109465_, f_109461_, p_109694_);
         return;
      }
      float f = this.f_109461_.f_91073_.m_46722_(1.0F) / (Minecraft.m_91405_() ? 1.0F : 2.0F);
      if (!(f <= 0.0F)) {
         Random random = new Random((long)this.f_109477_ * 312987231L);
         LevelReader levelreader = this.f_109461_.f_91073_;
         BlockPos blockpos = new BlockPos(p_109694_.m_90583_());
         BlockPos blockpos1 = null;
         int i = (int)(100.0F * f * f) / (this.f_109461_.f_91066_.f_92073_ == ParticleStatus.DECREASED ? 2 : 1);

         for(int j = 0; j < i; ++j) {
            int k = random.nextInt(21) - 10;
            int l = random.nextInt(21) - 10;
            BlockPos blockpos2 = levelreader.m_5452_(Heightmap.Types.MOTION_BLOCKING, blockpos.m_142082_(k, 0, l)).m_7495_();
            Biome biome = levelreader.m_46857_(blockpos2);
            if (blockpos2.m_123342_() > levelreader.m_141937_() && blockpos2.m_123342_() <= blockpos.m_123342_() + 10 && blockpos2.m_123342_() >= blockpos.m_123342_() - 10 && biome.m_47530_() == Biome.Precipitation.RAIN && biome.m_47505_(blockpos2) >= 0.15F) {
               blockpos1 = blockpos2;
               if (this.f_109461_.f_91066_.f_92073_ == ParticleStatus.MINIMAL) {
                  break;
               }

               double d0 = random.nextDouble();
               double d1 = random.nextDouble();
               BlockState blockstate = levelreader.m_8055_(blockpos2);
               FluidState fluidstate = levelreader.m_6425_(blockpos2);
               VoxelShape voxelshape = blockstate.m_60812_(levelreader, blockpos2);
               double d2 = voxelshape.m_83290_(Direction.Axis.Y, d0, d1);
               double d3 = (double)fluidstate.m_76155_(levelreader, blockpos2);
               double d4 = Math.max(d2, d3);
               ParticleOptions particleoptions = !fluidstate.m_76153_(FluidTags.f_13132_) && !blockstate.m_60713_(Blocks.f_50450_) && !CampfireBlock.m_51319_(blockstate) ? ParticleTypes.f_123761_ : ParticleTypes.f_123762_;
               this.f_109461_.f_91073_.m_7106_(particleoptions, (double)blockpos2.m_123341_() + d0, (double)blockpos2.m_123342_() + d4, (double)blockpos2.m_123343_() + d1, 0.0D, 0.0D, 0.0D);
            }
         }

         if (blockpos1 != null && random.nextInt(3) < this.f_109450_++) {
            this.f_109450_ = 0;
            if (blockpos1.m_123342_() > blockpos.m_123342_() + 1 && levelreader.m_5452_(Heightmap.Types.MOTION_BLOCKING, blockpos).m_123342_() > Mth.m_14143_((float)blockpos.m_123342_())) {
               this.f_109461_.f_91073_.m_104677_(blockpos1, SoundEvents.f_12542_, SoundSource.WEATHER, 0.1F, 0.5F, false);
            } else {
               this.f_109461_.f_91073_.m_104677_(blockpos1, SoundEvents.f_12541_, SoundSource.WEATHER, 0.2F, 1.0F, false);
            }
         }

      }
   }

   public void close() {
      if (this.f_109412_ != null) {
         this.f_109412_.close();
      }

      if (this.f_109418_ != null) {
         this.f_109418_.close();
      }

   }

   public void m_6213_(ResourceManager p_109513_) {
      this.m_109482_();
      if (Minecraft.m_91085_()) {
         this.m_109833_();
      }

   }

   public void m_109482_() {
      if (this.f_109412_ != null) {
         this.f_109412_.close();
      }

      ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");

      try {
         this.f_109412_ = new PostChain(this.f_109461_.m_91097_(), this.f_109461_.m_91098_(), this.f_109461_.m_91385_(), resourcelocation);
         this.f_109412_.m_110025_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
         this.f_109411_ = this.f_109412_.m_110036_("final");
      } catch (IOException ioexception) {
         f_109453_.warn("Failed to load shader: {}", resourcelocation, ioexception);
         this.f_109412_ = null;
         this.f_109411_ = null;
      } catch (JsonSyntaxException jsonsyntaxexception) {
         f_109453_.warn("Failed to parse shader: {}", resourcelocation, jsonsyntaxexception);
         this.f_109412_ = null;
         this.f_109411_ = null;
      }

   }

   private void m_109833_() {
      this.m_109834_();
      ResourceLocation resourcelocation = new ResourceLocation("shaders/post/transparency.json");

      try {
         PostChain postchain = new PostChain(this.f_109461_.m_91097_(), this.f_109461_.m_91098_(), this.f_109461_.m_91385_(), resourcelocation);
         postchain.m_110025_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_());
         RenderTarget rendertarget1 = postchain.m_110036_("translucent");
         RenderTarget rendertarget2 = postchain.m_110036_("itemEntity");
         RenderTarget rendertarget3 = postchain.m_110036_("particles");
         RenderTarget rendertarget4 = postchain.m_110036_("weather");
         RenderTarget rendertarget = postchain.m_110036_("clouds");
         this.f_109418_ = postchain;
         this.f_109413_ = rendertarget1;
         this.f_109414_ = rendertarget2;
         this.f_109415_ = rendertarget3;
         this.f_109416_ = rendertarget4;
         this.f_109417_ = rendertarget;
      } catch (Exception exception) {
         String s = exception instanceof JsonSyntaxException ? "parse" : "load";
         String s1 = "Failed to " + s + " shader: " + resourcelocation;
         LevelRenderer.TransparencyShaderException levelrenderer$transparencyshaderexception = new LevelRenderer.TransparencyShaderException(s1, exception);
         if (this.f_109461_.m_91099_().m_10523_().size() > 1) {
            Component component;
            try {
               component = new TextComponent(this.f_109461_.m_91098_().m_142591_(resourcelocation).m_7816_());
            } catch (IOException ioexception) {
               component = null;
            }

            this.f_109461_.f_91066_.f_92115_ = GraphicsStatus.FANCY;
            this.f_109461_.m_91241_(levelrenderer$transparencyshaderexception, component);
         } else {
            CrashReport crashreport = this.f_109461_.m_91354_(new CrashReport(s1, levelrenderer$transparencyshaderexception));
            this.f_109461_.f_91066_.f_92115_ = GraphicsStatus.FANCY;
            this.f_109461_.f_91066_.m_92169_();
            f_109453_.fatal(s1, (Throwable)levelrenderer$transparencyshaderexception);
            this.f_109461_.m_91394_();
            Minecraft.m_91332_(crashreport);
         }
      }

   }

   private void m_109834_() {
      if (this.f_109418_ != null) {
         this.f_109418_.close();
         this.f_109413_.m_83930_();
         this.f_109414_.m_83930_();
         this.f_109415_.m_83930_();
         this.f_109416_.m_83930_();
         this.f_109417_.m_83930_();
         this.f_109418_ = null;
         this.f_109413_ = null;
         this.f_109414_ = null;
         this.f_109415_ = null;
         this.f_109416_ = null;
         this.f_109417_ = null;
      }

   }

   public void m_109769_() {
      if (this.m_109817_()) {
         RenderSystem.m_69478_();
         RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
         this.f_109411_.m_83957_(this.f_109461_.m_91268_().m_85441_(), this.f_109461_.m_91268_().m_85442_(), false);
         RenderSystem.m_69461_();
      }

   }

   protected boolean m_109817_() {
      return !this.f_109461_.f_91063_.m_172715_() && this.f_109411_ != null && this.f_109412_ != null && this.f_109461_.f_91074_ != null;
   }

   private void m_109835_() {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      if (this.f_109473_ != null) {
         this.f_109473_.close();
      }

      this.f_109473_ = new VertexBuffer();
      m_172947_(bufferbuilder, -16.0F);
      this.f_109473_.m_85925_(bufferbuilder);
   }

   private void m_109836_() {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      if (this.f_109472_ != null) {
         this.f_109472_.close();
      }

      this.f_109472_ = new VertexBuffer();
      m_172947_(bufferbuilder, 16.0F);
      this.f_109472_.m_85925_(bufferbuilder);
   }

   private static void m_172947_(BufferBuilder p_172948_, float p_172949_) {
      float f = Math.signum(p_172949_) * 512.0F;
      float f1 = 512.0F;
      RenderSystem.m_157427_(GameRenderer::m_172808_);
      p_172948_.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85814_);
      p_172948_.m_5483_(0.0D, (double)p_172949_, 0.0D).m_5752_();

      for(int i = -180; i <= 180; i += 45) {
         p_172948_.m_5483_((double)(f * Mth.m_14089_((float)i * ((float)Math.PI / 180F))), (double)p_172949_, (double)(512.0F * Mth.m_14031_((float)i * ((float)Math.PI / 180F)))).m_5752_();
      }

      p_172948_.m_85721_();
   }

   private void m_109837_() {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172808_);
      if (this.f_109471_ != null) {
         this.f_109471_.close();
      }

      this.f_109471_ = new VertexBuffer();
      this.m_109554_(bufferbuilder);
      bufferbuilder.m_85721_();
      this.f_109471_.m_85925_(bufferbuilder);
   }

   private void m_109554_(BufferBuilder p_109555_) {
      Random random = new Random(10842L);
      p_109555_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);

      for(int i = 0; i < 1500; ++i) {
         double d0 = (double)(random.nextFloat() * 2.0F - 1.0F);
         double d1 = (double)(random.nextFloat() * 2.0F - 1.0F);
         double d2 = (double)(random.nextFloat() * 2.0F - 1.0F);
         double d3 = (double)(0.15F + random.nextFloat() * 0.1F);
         double d4 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d4 < 1.0D && d4 > 0.01D) {
            d4 = 1.0D / Math.sqrt(d4);
            d0 = d0 * d4;
            d1 = d1 * d4;
            d2 = d2 * d4;
            double d5 = d0 * 100.0D;
            double d6 = d1 * 100.0D;
            double d7 = d2 * 100.0D;
            double d8 = Math.atan2(d0, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = random.nextDouble() * Math.PI * 2.0D;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);

            for(int j = 0; j < 4; ++j) {
               double d17 = 0.0D;
               double d18 = (double)((j & 2) - 1) * d3;
               double d19 = (double)((j + 1 & 2) - 1) * d3;
               double d20 = 0.0D;
               double d21 = d18 * d16 - d19 * d15;
               double d22 = d19 * d16 + d18 * d15;
               double d23 = d21 * d12 + 0.0D * d13;
               double d24 = 0.0D * d12 - d21 * d13;
               double d25 = d24 * d9 - d22 * d10;
               double d26 = d22 * d9 + d24 * d10;
               p_109555_.m_5483_(d5 + d25, d6 + d23, d7 + d26).m_5752_();
            }
         }
      }

   }

   public void m_109701_(@Nullable ClientLevel p_109702_) {
      this.f_109419_ = Double.MIN_VALUE;
      this.f_109420_ = Double.MIN_VALUE;
      this.f_109421_ = Double.MIN_VALUE;
      this.f_109422_ = Integer.MIN_VALUE;
      this.f_109423_ = Integer.MIN_VALUE;
      this.f_109424_ = Integer.MIN_VALUE;
      this.f_109463_.m_114406_(p_109702_);
      this.f_109465_ = p_109702_;
      if (p_109702_ != null) {
         this.f_109467_.ensureCapacity(4356 * p_109702_.m_151559_());
         this.m_109818_();
      } else {
         this.f_109466_.clear();
         this.f_109467_.clear();
         if (this.f_109469_ != null) {
            this.f_109469_.m_110849_();
            this.f_109469_ = null;
         }

         if (this.f_109436_ != null) {
            this.f_109436_.m_112733_();
         }

         this.f_109436_ = null;
         this.f_109468_.clear();
      }

   }

   public void m_173014_() {
      if (Minecraft.m_91085_()) {
         this.m_109833_();
      } else {
         this.m_109834_();
      }

   }

   public void m_109818_() {
      if (this.f_109465_ != null) {
         this.m_173014_();
         this.f_109465_.m_104810_();
         if (this.f_109436_ == null) {
            this.f_109436_ = new ChunkRenderDispatcher(this.f_109465_, this, Util.m_137578_(), this.f_109461_.m_91103_(), this.f_109464_.m_110098_());
         } else {
            this.f_109436_.m_112691_(this.f_109465_);
         }

         this.f_109448_ = true;
         this.f_109474_ = true;
         ItemBlockRenderTypes.m_109291_(Minecraft.m_91405_());
         this.f_109438_ = this.f_109461_.f_91066_.f_92106_;
         if (this.f_109469_ != null) {
            this.f_109469_.m_110849_();
         }

         this.m_109819_();
         synchronized(this.f_109468_) {
            this.f_109468_.clear();
         }

         this.f_109469_ = new ViewArea(this.f_109436_, this.f_109465_, this.f_109461_.f_91066_.f_92106_, this);
         this.f_172936_ = new LevelRenderer.RenderInfoMap(this.f_109469_.f_110843_.length);
         if (this.f_109465_ != null) {
            Entity entity = this.f_109461_.m_91288_();
            if (entity != null) {
               this.f_109469_.m_110850_(entity.m_20185_(), entity.m_20189_());
            }
         }

      }
   }

   protected void m_109819_() {
      this.f_109466_.clear();
      this.f_109436_.m_112731_();
   }

   public void m_109487_(int p_109488_, int p_109489_) {
      this.m_109826_();
      if (this.f_109412_ != null) {
         this.f_109412_.m_110025_(p_109488_, p_109489_);
      }

      if (this.f_109418_ != null) {
         this.f_109418_.m_110025_(p_109488_, p_109489_);
      }

   }

   public String m_109820_() {
      int i = this.f_109469_.f_110843_.length;
      int j = this.m_109821_();
      return String.format("C: %d/%d %sD: %d, %s", j, i, this.f_109461_.f_90980_ ? "(s) " : "", this.f_109438_, this.f_109436_ == null ? "null" : this.f_109436_.m_112719_());
   }

   public ChunkRenderDispatcher m_173015_() {
      return this.f_109436_;
   }

   public double m_173016_() {
      return (double)this.f_109469_.f_110843_.length;
   }

   public double m_173017_() {
      return (double)this.f_109438_;
   }

   public int m_109821_() {
      int i = 0;

      for(LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo : this.f_109467_) {
         if (!levelrenderer$renderchunkinfo.f_109839_.m_112835_().m_112757_()) {
            ++i;
         }
      }

      return i;
   }

   public String m_109822_() {
      return "E: " + this.f_109439_ + "/" + this.f_109465_.m_104813_() + ", B: " + this.f_109440_;
   }

   private void m_109695_(Camera p_109696_, Frustum p_109697_, boolean p_109698_, int p_109699_, boolean p_109700_) {
      Vec3 vec3 = p_109696_.m_90583_();
      if (this.f_109461_.f_91066_.f_92106_ != this.f_109438_) {
         this.m_109818_();
      }

      this.f_109465_.m_46473_().m_6180_("camera");
      double d0 = this.f_109461_.f_91074_.m_20185_();
      double d1 = this.f_109461_.f_91074_.m_20186_();
      double d2 = this.f_109461_.f_91074_.m_20189_();
      double d3 = d0 - this.f_109419_;
      double d4 = d1 - this.f_109420_;
      double d5 = d2 - this.f_109421_;
      int i = SectionPos.m_175552_(d0);
      int j = SectionPos.m_175552_(d1);
      int k = SectionPos.m_175552_(d2);
      if (this.f_109422_ != i || this.f_109423_ != j || this.f_109424_ != k || d3 * d3 + d4 * d4 + d5 * d5 > 16.0D) {
         this.f_109419_ = d0;
         this.f_109420_ = d1;
         this.f_109421_ = d2;
         this.f_109422_ = i;
         this.f_109423_ = j;
         this.f_109424_ = k;
         this.f_109469_.m_110850_(d0, d2);
      }

      this.f_109436_.m_112693_(vec3);
      this.f_109465_.m_46473_().m_6182_("cull");
      this.f_109461_.m_91307_().m_6182_("culling");
      BlockPos blockpos = p_109696_.m_90588_();
      ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = this.f_109469_.m_110866_(blockpos);
      int l = 16;
      BlockPos blockpos1 = new BlockPos(Mth.m_14107_(vec3.f_82479_ / 16.0D) * 16, Mth.m_14107_(vec3.f_82480_ / 16.0D) * 16, Mth.m_14107_(vec3.f_82481_ / 16.0D) * 16);
      float f = p_109696_.m_90589_();
      float f1 = p_109696_.m_90590_();
      this.f_109448_ = this.f_109448_ || !this.f_109466_.isEmpty() || vec3.f_82479_ != this.f_109425_ || vec3.f_82480_ != this.f_109426_ || vec3.f_82481_ != this.f_109427_ || (double)f != this.f_109428_ || (double)f1 != this.f_109429_;
      this.f_109425_ = vec3.f_82479_;
      this.f_109426_ = vec3.f_82480_;
      this.f_109427_ = vec3.f_82481_;
      this.f_109428_ = (double)f;
      this.f_109429_ = (double)f1;
      this.f_109461_.m_91307_().m_6182_("update");
      if (!p_109698_ && this.f_109448_) {
         this.f_109448_ = false;
         this.m_173000_(p_109697_, p_109699_, p_109700_, vec3, blockpos, chunkrenderdispatcher$renderchunk, 16, blockpos1);
      }

      this.f_109461_.m_91307_().m_6182_("rebuildNear");
      Set<ChunkRenderDispatcher.RenderChunk> set = this.f_109466_;
      this.f_109466_ = Sets.newLinkedHashSet();

      for(LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo : this.f_109467_) {
         ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk1 = levelrenderer$renderchunkinfo.f_109839_;
         if (chunkrenderdispatcher$renderchunk1.m_112841_() || set.contains(chunkrenderdispatcher$renderchunk1)) {
            this.f_109448_ = true;
            BlockPos blockpos2 = chunkrenderdispatcher$renderchunk1.m_112839_().m_142082_(8, 8, 8);
            boolean flag = blockpos2.m_123331_(blockpos) < 768.0D;
            if (net.minecraftforge.common.ForgeConfig.CLIENT.alwaysSetupTerrainOffThread.get() || !chunkrenderdispatcher$renderchunk1.m_112842_() && !flag) {
               this.f_109466_.add(chunkrenderdispatcher$renderchunk1);
            } else {
               this.f_109461_.m_91307_().m_6180_("build near");
               this.f_109436_.m_112715_(chunkrenderdispatcher$renderchunk1);
               chunkrenderdispatcher$renderchunk1.m_112840_();
               this.f_109461_.m_91307_().m_7238_();
            }
         }
      }

      this.f_109466_.addAll(set);
      this.f_109461_.m_91307_().m_7238_();
   }

   private void m_173000_(Frustum p_173001_, int p_173002_, boolean p_173003_, Vec3 p_173004_, BlockPos p_173005_, ChunkRenderDispatcher.RenderChunk p_173006_, int p_173007_, BlockPos p_173008_) {
      this.f_109467_.clear();
      Queue<LevelRenderer.RenderChunkInfo> queue = Queues.newArrayDeque();
      Entity.m_20103_(Mth.m_14008_((double)this.f_109461_.f_91066_.f_92106_ / 8.0D, 1.0D, 2.5D) * (double)this.f_109461_.f_91066_.f_92112_);
      boolean flag = this.f_109461_.f_90980_;
      if (p_173006_ == null) {
         int i = p_173005_.m_123342_() > this.f_109465_.m_141937_() ? this.f_109465_.m_151558_() - 8 : this.f_109465_.m_141937_() + 8;
         int j = Mth.m_14107_(p_173004_.f_82479_ / (double)p_173007_) * p_173007_;
         int k = Mth.m_14107_(p_173004_.f_82481_ / (double)p_173007_) * p_173007_;
         List<LevelRenderer.RenderChunkInfo> list = Lists.newArrayList();

         for(int l = -this.f_109438_; l <= this.f_109438_; ++l) {
            for(int i1 = -this.f_109438_; i1 <= this.f_109438_; ++i1) {
               ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = this.f_109469_.m_110866_(new BlockPos(j + SectionPos.m_175554_(l, 8), i, k + SectionPos.m_175554_(i1, 8)));
               if (chunkrenderdispatcher$renderchunk != null && p_173001_.m_113029_(chunkrenderdispatcher$renderchunk.f_112785_)) {
                  chunkrenderdispatcher$renderchunk.m_112799_(p_173002_);
                  list.add(new LevelRenderer.RenderChunkInfo(chunkrenderdispatcher$renderchunk, (Direction)null, 0));
               }
            }
         }

         list.sort(Comparator.comparingDouble((p_173011_) -> {
            return p_173005_.m_123331_(p_173011_.f_109839_.m_112839_().m_142082_(8, 8, 8));
         }));
         queue.addAll(list);
      } else {
         if (p_173003_ && this.f_109465_.m_8055_(p_173005_).m_60804_(this.f_109465_, p_173005_)) {
            flag = false;
         }

         p_173006_.m_112799_(p_173002_);
         queue.add(new LevelRenderer.RenderChunkInfo(p_173006_, (Direction)null, 0));
      }

      this.f_109461_.m_91307_().m_6180_("iteration");
      int k1 = this.f_109461_.f_91066_.f_92106_;
      this.f_172936_.m_173034_();

      while(!queue.isEmpty()) {
         LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo = queue.poll();
         ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk2 = levelrenderer$renderchunkinfo.f_109839_;
         this.f_109467_.add(levelrenderer$renderchunkinfo);

         for(Direction direction : f_109434_) {
            ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk1 = this.m_109728_(p_173008_, chunkrenderdispatcher$renderchunk2, direction);
            if (!flag || !levelrenderer$renderchunkinfo.m_109859_(direction.m_122424_())) {
               if (flag && levelrenderer$renderchunkinfo.m_173025_()) {
                  ChunkRenderDispatcher.CompiledChunk chunkrenderdispatcher$compiledchunk = chunkrenderdispatcher$renderchunk2.m_112835_();
                  boolean flag1 = false;

                  for(int j1 = 0; j1 < f_109434_.length; ++j1) {
                     if (levelrenderer$renderchunkinfo.m_173026_(j1) && chunkrenderdispatcher$compiledchunk.m_7259_(f_109434_[j1].m_122424_(), direction)) {
                        flag1 = true;
                        break;
                     }
                  }

                  if (!flag1) {
                     continue;
                  }
               }

               if (chunkrenderdispatcher$renderchunk1 != null && chunkrenderdispatcher$renderchunk1.m_112798_()) {
                  if (!chunkrenderdispatcher$renderchunk1.m_112799_(p_173002_)) {
                     LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo1 = this.f_172936_.m_173035_(chunkrenderdispatcher$renderchunk1);
                     if (levelrenderer$renderchunkinfo1 != null) {
                        levelrenderer$renderchunkinfo1.m_173028_(direction);
                     }
                  } else if (p_173001_.m_113029_(chunkrenderdispatcher$renderchunk1.f_112785_)) {
                     LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo2 = new LevelRenderer.RenderChunkInfo(chunkrenderdispatcher$renderchunk1, direction, levelrenderer$renderchunkinfo.f_109842_ + 1);
                     levelrenderer$renderchunkinfo2.m_109854_(levelrenderer$renderchunkinfo.f_109841_, direction);
                     queue.add(levelrenderer$renderchunkinfo2);
                     this.f_172936_.m_173037_(chunkrenderdispatcher$renderchunk1, levelrenderer$renderchunkinfo2);
                  }
               }
            }
         }
      }

      this.f_109461_.m_91307_().m_7238_();
   }

   @Nullable
   private ChunkRenderDispatcher.RenderChunk m_109728_(BlockPos p_109729_, ChunkRenderDispatcher.RenderChunk p_109730_, Direction p_109731_) {
      BlockPos blockpos = p_109730_.m_112824_(p_109731_);
      if (Mth.m_14040_(p_109729_.m_123341_() - blockpos.m_123341_()) > this.f_109438_ * 16) {
         return null;
      } else if (blockpos.m_123342_() >= this.f_109465_.m_141937_() && blockpos.m_123342_() < this.f_109465_.m_151558_()) {
         return Mth.m_14040_(p_109729_.m_123343_() - blockpos.m_123343_()) > this.f_109438_ * 16 ? null : this.f_109469_.m_110866_(blockpos);
      } else {
         return null;
      }
   }

   private void m_109525_(Matrix4f p_109526_, Matrix4f p_109527_, double p_109528_, double p_109529_, double p_109530_, Frustum p_109531_) {
      this.f_109442_ = p_109531_;
      Matrix4f matrix4f = p_109527_.m_27658_();
      matrix4f.m_27644_(p_109526_);
      matrix4f.m_27657_();
      this.f_109444_.f_86214_ = p_109528_;
      this.f_109444_.f_86215_ = p_109529_;
      this.f_109444_.f_86216_ = p_109530_;
      this.f_109443_[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.f_109443_[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.f_109443_[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.f_109443_[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.f_109443_[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.f_109443_[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.f_109443_[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.f_109443_[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for(int i = 0; i < 8; ++i) {
         this.f_109443_[i].m_123607_(matrix4f);
         this.f_109443_[i].m_123621_();
      }

   }

   public void m_172961_(PoseStack p_172962_, Vec3 p_172963_, Matrix4f p_172964_) {
      Matrix4f matrix4f = p_172962_.m_85850_().m_85861_();
      double d0 = p_172963_.m_7096_();
      double d1 = p_172963_.m_7098_();
      double d2 = p_172963_.m_7094_();
      this.f_172938_ = new Frustum(matrix4f, p_172964_);
      this.f_172938_.m_113002_(d0, d1, d2);
   }

   public void m_109599_(PoseStack p_109600_, float p_109601_, long p_109602_, boolean p_109603_, Camera p_109604_, GameRenderer p_109605_, LightTexture p_109606_, Matrix4f p_109607_) {
      RenderSystem.m_157447_(this.f_109465_.m_46467_(), p_109601_);
      this.f_172946_.m_173564_(this.f_109465_, p_109604_, this.f_109461_.f_91077_);
      this.f_109463_.m_114408_(this.f_109465_, p_109604_, this.f_109461_.f_91076_);
      ProfilerFiller profilerfiller = this.f_109465_.m_46473_();
      profilerfiller.m_6182_("light_updates");
      this.f_109461_.f_91073_.m_7726_().m_7827_().m_142528_(Integer.MAX_VALUE, true, true);
      Vec3 vec3 = p_109604_.m_90583_();
      double d0 = vec3.m_7096_();
      double d1 = vec3.m_7098_();
      double d2 = vec3.m_7094_();
      Matrix4f matrix4f = p_109600_.m_85850_().m_85861_();
      profilerfiller.m_6182_("culling");
      boolean flag = this.f_109442_ != null;
      Frustum frustum;
      if (flag) {
         frustum = this.f_109442_;
         frustum.m_113002_(this.f_109444_.f_86214_, this.f_109444_.f_86215_, this.f_109444_.f_86216_);
      } else {
         frustum = this.f_172938_;
      }

      this.f_109461_.m_91307_().m_6182_("captureFrustum");
      if (this.f_109441_) {
         this.m_109525_(matrix4f, p_109607_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, flag ? new Frustum(matrix4f, p_109607_) : frustum);
         this.f_109441_ = false;
      }

      profilerfiller.m_6182_("clear");
      FogRenderer.m_109018_(p_109604_, p_109601_, this.f_109461_.f_91073_, this.f_109461_.f_91066_.f_92106_, p_109605_.m_109131_(p_109601_));
      FogRenderer.m_109036_();
      RenderSystem.m_69421_(16640, Minecraft.f_91002_);
      float f = p_109605_.m_109152_();
      boolean flag1 = this.f_109461_.f_91073_.m_104583_().m_5781_(Mth.m_14107_(d0), Mth.m_14107_(d1)) || this.f_109461_.f_91065_.m_93090_().m_93715_();
      FogRenderer.setupFog(p_109604_, FogRenderer.FogMode.FOG_SKY, f, flag1, p_109601_);
      profilerfiller.m_6182_("sky");
      RenderSystem.m_157427_(GameRenderer::m_172808_);
      this.m_181409_(p_109600_, p_109607_, p_109601_, () -> {
         FogRenderer.m_109024_(p_109604_, FogRenderer.FogMode.FOG_SKY, f, flag1);
      });
      profilerfiller.m_6182_("fog");
      FogRenderer.setupFog(p_109604_, FogRenderer.FogMode.FOG_TERRAIN, Math.max(f - 16.0F, 32.0F), flag1, p_109601_);
      profilerfiller.m_6182_("terrain_setup");
      this.m_109695_(p_109604_, frustum, flag, this.f_109449_++, this.f_109461_.f_91074_.m_5833_());
      profilerfiller.m_6182_("updatechunks");
      int i = 30;
      int j = this.f_109461_.f_91066_.f_92113_;
      long k = 33333333L;
      long l;
      if ((double)j == Option.f_91670_.m_92235_()) {
         l = 0L;
      } else {
         l = (long)(1000000000 / j);
      }

      long i1 = Util.m_137569_() - p_109602_;
      long j1 = this.f_109476_.m_110712_(i1);
      long k1 = j1 * 3L / 2L;
      long l1 = Mth.m_14053_(k1, l, 33333333L);
      this.m_109510_(p_109602_ + l1);
      profilerfiller.m_6182_("terrain");
      this.m_172993_(RenderType.m_110451_(), p_109600_, d0, d1, d2, p_109607_);
      this.f_109461_.m_91304_().m_119428_(TextureAtlas.f_118259_).setBlurMipmap(false, this.f_109461_.f_91066_.f_92027_ > 0); // FORGE: fix flickering leaves when mods mess up the blurMipmap settings
      this.m_172993_(RenderType.m_110457_(), p_109600_, d0, d1, d2, p_109607_);
      this.f_109461_.m_91304_().m_119428_(TextureAtlas.f_118259_).restoreLastBlurMipmap();
      this.m_172993_(RenderType.m_110463_(), p_109600_, d0, d1, d2, p_109607_);
      if (this.f_109465_.m_104583_().m_108885_()) {
         Lighting.m_84925_(p_109600_.m_85850_().m_85861_());
      } else {
         Lighting.m_84928_(p_109600_.m_85850_().m_85861_());
      }

      profilerfiller.m_6182_("entities");
      this.f_109439_ = 0;
      this.f_109440_ = 0;
      if (this.f_109414_ != null) {
         this.f_109414_.m_83954_(Minecraft.f_91002_);
         this.f_109414_.m_83945_(this.f_109461_.m_91385_());
         this.f_109461_.m_91385_().m_83947_(false);
      }

      if (this.f_109416_ != null) {
         this.f_109416_.m_83954_(Minecraft.f_91002_);
      }

      if (this.m_109817_()) {
         this.f_109411_.m_83954_(Minecraft.f_91002_);
         this.f_109461_.m_91385_().m_83947_(false);
      }

      boolean flag2 = false;
      MultiBufferSource.BufferSource multibuffersource$buffersource = this.f_109464_.m_110104_();

      for(Entity entity : this.f_109465_.m_104735_()) {
         if ((this.f_109463_.m_114397_(entity, frustum, d0, d1, d2) || entity.m_20367_(this.f_109461_.f_91074_)) && (entity != p_109604_.m_90592_() || p_109604_.m_90594_() || p_109604_.m_90592_() instanceof LivingEntity && ((LivingEntity)p_109604_.m_90592_()).m_5803_()) && (!(entity instanceof LocalPlayer) || p_109604_.m_90592_() == entity || (entity == f_109461_.f_91074_ && !f_109461_.f_91074_.m_5833_()))) { //FORGE: render local player entity when it is not the renderViewEntity
            ++this.f_109439_;
            if (entity.f_19797_ == 0) {
               entity.f_19790_ = entity.m_20185_();
               entity.f_19791_ = entity.m_20186_();
               entity.f_19792_ = entity.m_20189_();
            }

            MultiBufferSource multibuffersource;
            if (this.m_109817_() && this.f_109461_.m_91314_(entity)) {
               flag2 = true;
               OutlineBufferSource outlinebuffersource = this.f_109464_.m_110109_();
               multibuffersource = outlinebuffersource;
               int i2 = entity.m_19876_();
               int j2 = 255;
               int k2 = i2 >> 16 & 255;
               int l2 = i2 >> 8 & 255;
               int i3 = i2 & 255;
               outlinebuffersource.m_109929_(k2, l2, i3, 255);
            } else {
               multibuffersource = multibuffersource$buffersource;
            }

            this.m_109517_(entity, d0, d1, d2, p_109601_, p_109600_, multibuffersource);
         }
      }

      multibuffersource$buffersource.m_173043_();
      this.m_109588_(p_109600_);
      multibuffersource$buffersource.m_109912_(RenderType.m_110446_(TextureAtlas.f_118259_));
      multibuffersource$buffersource.m_109912_(RenderType.m_110452_(TextureAtlas.f_118259_));
      multibuffersource$buffersource.m_109912_(RenderType.m_110458_(TextureAtlas.f_118259_));
      multibuffersource$buffersource.m_109912_(RenderType.m_110476_(TextureAtlas.f_118259_));
      profilerfiller.m_6182_("blockentities");

      for(LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo : this.f_109467_) {
         List<BlockEntity> list = levelrenderer$renderchunkinfo.f_109839_.m_112835_().m_112773_();
         if (!list.isEmpty()) {
            for(BlockEntity blockentity1 : list) {
               if(!frustum.m_113029_(blockentity1.getRenderBoundingBox())) continue;
               BlockPos blockpos3 = blockentity1.m_58899_();
               MultiBufferSource multibuffersource1 = multibuffersource$buffersource;
               p_109600_.m_85836_();
               p_109600_.m_85837_((double)blockpos3.m_123341_() - d0, (double)blockpos3.m_123342_() - d1, (double)blockpos3.m_123343_() - d2);
               SortedSet<BlockDestructionProgress> sortedset = this.f_109409_.get(blockpos3.m_121878_());
               if (sortedset != null && !sortedset.isEmpty()) {
                  int j3 = sortedset.last().m_139988_();
                  if (j3 >= 0) {
                     PoseStack.Pose posestack$pose = p_109600_.m_85850_();
                     VertexConsumer vertexconsumer = new SheetedDecalTextureGenerator(this.f_109464_.m_110108_().m_6299_(ModelBakery.f_119229_.get(j3)), posestack$pose.m_85861_(), posestack$pose.m_85864_());
                     multibuffersource1 = (p_109712_) -> {
                        VertexConsumer vertexconsumer3 = multibuffersource$buffersource.m_6299_(p_109712_);
                        return p_109712_.m_110405_() ? VertexMultiConsumer.m_86168_(vertexconsumer, vertexconsumer3) : vertexconsumer3;
                     };
                  }
               }

               this.f_172946_.m_112267_(blockentity1, p_109601_, p_109600_, multibuffersource1);
               p_109600_.m_85849_();
            }
         }
      }

      synchronized(this.f_109468_) {
         for(BlockEntity blockentity : this.f_109468_) {
            if(!frustum.m_113029_(blockentity.getRenderBoundingBox())) continue;
            BlockPos blockpos2 = blockentity.m_58899_();
            p_109600_.m_85836_();
            p_109600_.m_85837_((double)blockpos2.m_123341_() - d0, (double)blockpos2.m_123342_() - d1, (double)blockpos2.m_123343_() - d2);
            this.f_172946_.m_112267_(blockentity, p_109601_, p_109600_, multibuffersource$buffersource);
            p_109600_.m_85849_();
         }
      }

      this.m_109588_(p_109600_);
      multibuffersource$buffersource.m_109912_(RenderType.m_110451_());
      multibuffersource$buffersource.m_109912_(RenderType.m_173239_());
      multibuffersource$buffersource.m_109912_(RenderType.m_173242_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110789_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110790_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110785_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110786_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110787_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110788_());
      this.f_109464_.m_110109_().m_109928_();
      if (flag2) {
         this.f_109412_.m_110023_(p_109601_);
         this.f_109461_.m_91385_().m_83947_(false);
      }

      profilerfiller.m_6182_("destroyProgress");

      for(Entry<SortedSet<BlockDestructionProgress>> entry : this.f_109409_.long2ObjectEntrySet()) {
         BlockPos blockpos1 = BlockPos.m_122022_(entry.getLongKey());
         double d3 = (double)blockpos1.m_123341_() - d0;
         double d4 = (double)blockpos1.m_123342_() - d1;
         double d5 = (double)blockpos1.m_123343_() - d2;
         if (!(d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D)) {
            SortedSet<BlockDestructionProgress> sortedset1 = entry.getValue();
            if (sortedset1 != null && !sortedset1.isEmpty()) {
               int k3 = sortedset1.last().m_139988_();
               p_109600_.m_85836_();
               p_109600_.m_85837_((double)blockpos1.m_123341_() - d0, (double)blockpos1.m_123342_() - d1, (double)blockpos1.m_123343_() - d2);
               PoseStack.Pose posestack$pose1 = p_109600_.m_85850_();
               VertexConsumer vertexconsumer1 = new SheetedDecalTextureGenerator(this.f_109464_.m_110108_().m_6299_(ModelBakery.f_119229_.get(k3)), posestack$pose1.m_85861_(), posestack$pose1.m_85864_());
               this.f_109461_.m_91289_().m_110918_(this.f_109465_.m_8055_(blockpos1), blockpos1, this.f_109465_, p_109600_, vertexconsumer1);
               p_109600_.m_85849_();
            }
         }
      }

      this.m_109588_(p_109600_);
      HitResult hitresult = this.f_109461_.f_91077_;
      if (p_109603_ && hitresult != null && hitresult.m_6662_() == HitResult.Type.BLOCK) {
         profilerfiller.m_6182_("outline");
         BlockPos blockpos = ((BlockHitResult)hitresult).m_82425_();
         BlockState blockstate = this.f_109465_.m_8055_(blockpos);
         if (!net.minecraftforge.client.ForgeHooksClient.onDrawBlockHighlight(this, p_109604_, hitresult, p_109601_, p_109600_, multibuffersource$buffersource))
         if (!blockstate.m_60795_() && this.f_109465_.m_6857_().m_61937_(blockpos)) {
            VertexConsumer vertexconsumer2 = multibuffersource$buffersource.m_6299_(RenderType.m_110504_());
            this.m_109637_(p_109600_, vertexconsumer2, p_109604_.m_90592_(), d0, d1, d2, blockpos, blockstate);
         }
      } else if (hitresult != null && hitresult.m_6662_() == HitResult.Type.ENTITY) {
         net.minecraftforge.client.ForgeHooksClient.onDrawBlockHighlight(this, p_109604_, hitresult, p_109601_, p_109600_, multibuffersource$buffersource);
      }

      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      posestack.m_166854_(p_109600_.m_85850_().m_85861_());
      RenderSystem.m_157182_();
      this.f_109461_.f_91064_.m_113457_(p_109600_, multibuffersource$buffersource, d0, d1, d2);
      posestack.m_85849_();
      RenderSystem.m_157182_();
      multibuffersource$buffersource.m_109912_(Sheets.m_110792_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110762_());
      multibuffersource$buffersource.m_109912_(Sheets.m_110782_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110481_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110484_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110490_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110493_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110487_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110496_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110499_());
      multibuffersource$buffersource.m_109912_(RenderType.m_110478_());
      this.f_109464_.m_110108_().m_109911_();
      if (this.f_109418_ != null) {
         multibuffersource$buffersource.m_109912_(RenderType.m_110504_());
         multibuffersource$buffersource.m_109911_();
         this.f_109413_.m_83954_(Minecraft.f_91002_);
         this.f_109413_.m_83945_(this.f_109461_.m_91385_());
         profilerfiller.m_6182_("translucent");
         this.m_172993_(RenderType.m_110466_(), p_109600_, d0, d1, d2, p_109607_);
         profilerfiller.m_6182_("string");
         this.m_172993_(RenderType.m_110503_(), p_109600_, d0, d1, d2, p_109607_);
         this.f_109415_.m_83954_(Minecraft.f_91002_);
         this.f_109415_.m_83945_(this.f_109461_.m_91385_());
         RenderStateShard.f_110126_.m_110185_();
         profilerfiller.m_6182_("particles");
         this.f_109461_.f_91061_.render(p_109600_, multibuffersource$buffersource, p_109606_, p_109604_, p_109601_, frustum);
         RenderStateShard.f_110126_.m_110188_();
      } else {
         profilerfiller.m_6182_("translucent");
         if (this.f_109413_ != null) {
            this.f_109413_.m_83954_(Minecraft.f_91002_);
         }

         this.m_172993_(RenderType.m_110466_(), p_109600_, d0, d1, d2, p_109607_);
         multibuffersource$buffersource.m_109912_(RenderType.m_110504_());
         multibuffersource$buffersource.m_109911_();
         profilerfiller.m_6182_("string");
         this.m_172993_(RenderType.m_110503_(), p_109600_, d0, d1, d2, p_109607_);
         profilerfiller.m_6182_("particles");
         this.f_109461_.f_91061_.render(p_109600_, multibuffersource$buffersource, p_109606_, p_109604_, p_109601_, frustum);
      }

      posestack.m_85836_();
      posestack.m_166854_(p_109600_.m_85850_().m_85861_());
      RenderSystem.m_157182_();
      if (this.f_109461_.f_91066_.m_92174_() != CloudStatus.OFF) {
         if (this.f_109418_ != null) {
            this.f_109417_.m_83954_(Minecraft.f_91002_);
            RenderStateShard.f_110128_.m_110185_();
            profilerfiller.m_6182_("clouds");
            this.m_172954_(p_109600_, p_109607_, p_109601_, d0, d1, d2);
            RenderStateShard.f_110128_.m_110188_();
         } else {
            profilerfiller.m_6182_("clouds");
            RenderSystem.m_157427_(GameRenderer::m_172838_);
            this.m_172954_(p_109600_, p_109607_, p_109601_, d0, d1, d2);
         }
      }

      if (this.f_109418_ != null) {
         RenderStateShard.f_110127_.m_110185_();
         profilerfiller.m_6182_("weather");
         this.m_109703_(p_109606_, p_109601_, d0, d1, d2);
         this.m_173012_(p_109604_);
         RenderStateShard.f_110127_.m_110188_();
         this.f_109418_.m_110023_(p_109601_);
         this.f_109461_.m_91385_().m_83947_(false);
      } else {
         RenderSystem.m_69458_(false);
         profilerfiller.m_6182_("weather");
         this.m_109703_(p_109606_, p_109601_, d0, d1, d2);
         this.m_173012_(p_109604_);
         RenderSystem.m_69458_(true);
      }

      this.m_109793_(p_109604_);
      RenderSystem.m_69458_(true);
      RenderSystem.m_69461_();
      posestack.m_85849_();
      RenderSystem.m_157182_();
      FogRenderer.m_109017_();
   }

   private void m_109588_(PoseStack p_109589_) {
      if (!p_109589_.m_85851_()) {
         throw new IllegalStateException("Pose stack not empty");
      }
   }

   private void m_109517_(Entity p_109518_, double p_109519_, double p_109520_, double p_109521_, float p_109522_, PoseStack p_109523_, MultiBufferSource p_109524_) {
      double d0 = Mth.m_14139_((double)p_109522_, p_109518_.f_19790_, p_109518_.m_20185_());
      double d1 = Mth.m_14139_((double)p_109522_, p_109518_.f_19791_, p_109518_.m_20186_());
      double d2 = Mth.m_14139_((double)p_109522_, p_109518_.f_19792_, p_109518_.m_20189_());
      float f = Mth.m_14179_(p_109522_, p_109518_.f_19859_, p_109518_.m_146908_());
      this.f_109463_.m_114384_(p_109518_, d0 - p_109519_, d1 - p_109520_, d2 - p_109521_, f, p_109522_, p_109523_, p_109524_, this.f_109463_.m_114394_(p_109518_, p_109522_));
   }

   private void m_172993_(RenderType p_172994_, PoseStack p_172995_, double p_172996_, double p_172997_, double p_172998_, Matrix4f p_172999_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      p_172994_.m_110185_();
      if (p_172994_ == RenderType.m_110466_()) {
         this.f_109461_.m_91307_().m_6180_("translucent_sort");
         double d0 = p_172996_ - this.f_109445_;
         double d1 = p_172997_ - this.f_109446_;
         double d2 = p_172998_ - this.f_109447_;
         if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
            this.f_109445_ = p_172996_;
            this.f_109446_ = p_172997_;
            this.f_109447_ = p_172998_;
            int j = 0;

            for(LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo : this.f_109467_) {
               if (j < 15 && levelrenderer$renderchunkinfo.f_109839_.m_112809_(p_172994_, this.f_109436_)) {
                  ++j;
               }
            }
         }

         this.f_109461_.m_91307_().m_7238_();
      }

      this.f_109461_.m_91307_().m_6180_("filterempty");
      this.f_109461_.m_91307_().m_6523_(() -> {
         return "render_" + p_172994_;
      });
      boolean flag = p_172994_ != RenderType.m_110466_();
      ObjectListIterator<LevelRenderer.RenderChunkInfo> objectlistiterator = this.f_109467_.listIterator(flag ? 0 : this.f_109467_.size());
      VertexFormat vertexformat = p_172994_.m_110508_();
      ShaderInstance shaderinstance = RenderSystem.m_157196_();
      BufferUploader.m_166835_();

      for(int k = 0; k < 12; ++k) {
         int i = RenderSystem.m_157203_(k);
         shaderinstance.m_173350_("Sampler" + k, i);
      }

      if (shaderinstance.f_173308_ != null) {
         shaderinstance.f_173308_.m_5679_(p_172995_.m_85850_().m_85861_());
      }

      if (shaderinstance.f_173309_ != null) {
         shaderinstance.f_173309_.m_5679_(p_172999_);
      }

      if (shaderinstance.f_173312_ != null) {
         shaderinstance.f_173312_.m_5941_(RenderSystem.m_157197_());
      }

      if (shaderinstance.f_173315_ != null) {
         shaderinstance.f_173315_.m_5985_(RenderSystem.m_157200_());
      }

      if (shaderinstance.f_173316_ != null) {
         shaderinstance.f_173316_.m_5985_(RenderSystem.m_157199_());
      }

      if (shaderinstance.f_173317_ != null) {
         shaderinstance.f_173317_.m_5941_(RenderSystem.m_157198_());
      }

      if (shaderinstance.f_173310_ != null) {
         shaderinstance.f_173310_.m_5679_(RenderSystem.m_157207_());
      }

      if (shaderinstance.f_173319_ != null) {
         shaderinstance.f_173319_.m_5985_(RenderSystem.m_157201_());
      }

      RenderSystem.m_157461_(shaderinstance);
      shaderinstance.m_173363_();
      Uniform uniform = shaderinstance.f_173320_;
      boolean flag1 = false;

      while(true) {
         if (flag) {
            if (!objectlistiterator.hasNext()) {
               break;
            }
         } else if (!objectlistiterator.hasPrevious()) {
            break;
         }

         LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo1 = flag ? objectlistiterator.next() : objectlistiterator.previous();
         ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = levelrenderer$renderchunkinfo1.f_109839_;
         if (!chunkrenderdispatcher$renderchunk.m_112835_().m_112758_(p_172994_)) {
            VertexBuffer vertexbuffer = chunkrenderdispatcher$renderchunk.m_112807_(p_172994_);
            BlockPos blockpos = chunkrenderdispatcher$renderchunk.m_112839_();
            if (uniform != null) {
               uniform.m_5889_((float)((double)blockpos.m_123341_() - p_172996_), (float)((double)blockpos.m_123342_() - p_172997_), (float)((double)blockpos.m_123343_() - p_172998_));
               uniform.m_85633_();
            }

            vertexbuffer.m_166887_();
            flag1 = true;
         }
      }

      if (uniform != null) {
         uniform.m_142276_(Vector3f.f_176763_);
      }

      shaderinstance.m_173362_();
      if (flag1) {
         vertexformat.m_86024_();
      }

      VertexBuffer.m_85931_();
      VertexBuffer.m_166875_();
      this.f_109461_.m_91307_().m_7238_();
      p_172994_.m_110188_();
   }

   private void m_109793_(Camera p_109794_) {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      if (this.f_109461_.f_90978_ || this.f_109461_.f_90979_) {
         double d0 = p_109794_.m_90583_().m_7096_();
         double d1 = p_109794_.m_90583_().m_7098_();
         double d2 = p_109794_.m_90583_().m_7094_();
         RenderSystem.m_69458_(true);
         RenderSystem.m_69464_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         RenderSystem.m_69472_();

         for(LevelRenderer.RenderChunkInfo levelrenderer$renderchunkinfo : this.f_109467_) {
            ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = levelrenderer$renderchunkinfo.f_109839_;
            BlockPos blockpos = chunkrenderdispatcher$renderchunk.m_112839_();
            PoseStack posestack = RenderSystem.m_157191_();
            posestack.m_85836_();
            posestack.m_85837_((double)blockpos.m_123341_() - d0, (double)blockpos.m_123342_() - d1, (double)blockpos.m_123343_() - d2);
            RenderSystem.m_157182_();
            if (this.f_109461_.f_90978_) {
               bufferbuilder.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_85815_);
               RenderSystem.m_69832_(10.0F);
               int i = levelrenderer$renderchunkinfo.f_109842_ == 0 ? 0 : Mth.m_14169_((float)levelrenderer$renderchunkinfo.f_109842_ / 50.0F, 0.9F, 0.9F);
               int j = i >> 16 & 255;
               int k = i >> 8 & 255;
               int l = i & 255;

               for(int i1 = 0; i1 < f_109434_.length; ++i1) {
                  if (levelrenderer$renderchunkinfo.m_173026_(i1)) {
                     Direction direction = f_109434_[i1];
                     bufferbuilder.m_5483_(8.0D, 8.0D, 8.0D).m_6122_(j, k, l, 255).m_5752_();
                     bufferbuilder.m_5483_((double)(8 - 16 * direction.m_122429_()), (double)(8 - 16 * direction.m_122430_()), (double)(8 - 16 * direction.m_122431_())).m_6122_(j, k, l, 255).m_5752_();
                  }
               }

               tesselator.m_85914_();
               RenderSystem.m_69832_(1.0F);
            }

            if (this.f_109461_.f_90979_ && !chunkrenderdispatcher$renderchunk.m_112835_().m_112757_()) {
               bufferbuilder.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_85815_);
               RenderSystem.m_69832_(10.0F);
               int j1 = 0;

               for(Direction direction2 : f_109434_) {
                  for(Direction direction1 : f_109434_) {
                     boolean flag = chunkrenderdispatcher$renderchunk.m_112835_().m_7259_(direction2, direction1);
                     if (!flag) {
                        ++j1;
                        bufferbuilder.m_5483_((double)(8 + 8 * direction2.m_122429_()), (double)(8 + 8 * direction2.m_122430_()), (double)(8 + 8 * direction2.m_122431_())).m_6122_(1, 0, 0, 1).m_5752_();
                        bufferbuilder.m_5483_((double)(8 + 8 * direction1.m_122429_()), (double)(8 + 8 * direction1.m_122430_()), (double)(8 + 8 * direction1.m_122431_())).m_6122_(1, 0, 0, 1).m_5752_();
                     }
                  }
               }

               tesselator.m_85914_();
               RenderSystem.m_69832_(1.0F);
               if (j1 > 0) {
                  bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
                  float f = 0.5F;
                  float f1 = 0.2F;
                  bufferbuilder.m_5483_(0.5D, 15.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 15.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 15.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 15.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 0.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 0.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 0.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 0.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 15.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 15.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 0.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 0.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 0.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 0.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 15.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 15.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 0.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 0.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 15.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 15.5D, 0.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 15.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 15.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(15.5D, 0.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  bufferbuilder.m_5483_(0.5D, 0.5D, 15.5D).m_85950_(0.9F, 0.9F, 0.0F, 0.2F).m_5752_();
                  tesselator.m_85914_();
               }
            }

            posestack.m_85849_();
            RenderSystem.m_157182_();
         }

         RenderSystem.m_69458_(true);
         RenderSystem.m_69461_();
         RenderSystem.m_69481_();
         RenderSystem.m_69493_();
      }

      if (this.f_109442_ != null) {
         RenderSystem.m_69464_();
         RenderSystem.m_69472_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         RenderSystem.m_69832_(10.0F);
         PoseStack posestack1 = RenderSystem.m_157191_();
         posestack1.m_85836_();
         posestack1.m_85837_((double)((float)(this.f_109444_.f_86214_ - p_109794_.m_90583_().f_82479_)), (double)((float)(this.f_109444_.f_86215_ - p_109794_.m_90583_().f_82480_)), (double)((float)(this.f_109444_.f_86216_ - p_109794_.m_90583_().f_82481_)));
         RenderSystem.m_157182_();
         RenderSystem.m_69458_(true);
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
         this.m_109668_(bufferbuilder, 0, 1, 2, 3, 0, 1, 1);
         this.m_109668_(bufferbuilder, 4, 5, 6, 7, 1, 0, 0);
         this.m_109668_(bufferbuilder, 0, 1, 5, 4, 1, 1, 0);
         this.m_109668_(bufferbuilder, 2, 3, 7, 6, 0, 0, 1);
         this.m_109668_(bufferbuilder, 0, 4, 7, 3, 0, 1, 0);
         this.m_109668_(bufferbuilder, 1, 5, 6, 2, 1, 0, 1);
         tesselator.m_85914_();
         RenderSystem.m_69458_(false);
         RenderSystem.m_157427_(GameRenderer::m_172808_);
         bufferbuilder.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_85814_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         this.m_109665_(bufferbuilder, 0);
         this.m_109665_(bufferbuilder, 1);
         this.m_109665_(bufferbuilder, 1);
         this.m_109665_(bufferbuilder, 2);
         this.m_109665_(bufferbuilder, 2);
         this.m_109665_(bufferbuilder, 3);
         this.m_109665_(bufferbuilder, 3);
         this.m_109665_(bufferbuilder, 0);
         this.m_109665_(bufferbuilder, 4);
         this.m_109665_(bufferbuilder, 5);
         this.m_109665_(bufferbuilder, 5);
         this.m_109665_(bufferbuilder, 6);
         this.m_109665_(bufferbuilder, 6);
         this.m_109665_(bufferbuilder, 7);
         this.m_109665_(bufferbuilder, 7);
         this.m_109665_(bufferbuilder, 4);
         this.m_109665_(bufferbuilder, 0);
         this.m_109665_(bufferbuilder, 4);
         this.m_109665_(bufferbuilder, 1);
         this.m_109665_(bufferbuilder, 5);
         this.m_109665_(bufferbuilder, 2);
         this.m_109665_(bufferbuilder, 6);
         this.m_109665_(bufferbuilder, 3);
         this.m_109665_(bufferbuilder, 7);
         tesselator.m_85914_();
         posestack1.m_85849_();
         RenderSystem.m_157182_();
         RenderSystem.m_69458_(true);
         RenderSystem.m_69461_();
         RenderSystem.m_69481_();
         RenderSystem.m_69493_();
         RenderSystem.m_69832_(1.0F);
      }

   }

   private void m_109665_(VertexConsumer p_109666_, int p_109667_) {
      p_109666_.m_5483_((double)this.f_109443_[p_109667_].m_123601_(), (double)this.f_109443_[p_109667_].m_123615_(), (double)this.f_109443_[p_109667_].m_123616_()).m_5752_();
   }

   private void m_109668_(VertexConsumer p_109669_, int p_109670_, int p_109671_, int p_109672_, int p_109673_, int p_109674_, int p_109675_, int p_109676_) {
      float f = 0.25F;
      p_109669_.m_5483_((double)this.f_109443_[p_109670_].m_123601_(), (double)this.f_109443_[p_109670_].m_123615_(), (double)this.f_109443_[p_109670_].m_123616_()).m_85950_((float)p_109674_, (float)p_109675_, (float)p_109676_, 0.25F).m_5752_();
      p_109669_.m_5483_((double)this.f_109443_[p_109671_].m_123601_(), (double)this.f_109443_[p_109671_].m_123615_(), (double)this.f_109443_[p_109671_].m_123616_()).m_85950_((float)p_109674_, (float)p_109675_, (float)p_109676_, 0.25F).m_5752_();
      p_109669_.m_5483_((double)this.f_109443_[p_109672_].m_123601_(), (double)this.f_109443_[p_109672_].m_123615_(), (double)this.f_109443_[p_109672_].m_123616_()).m_85950_((float)p_109674_, (float)p_109675_, (float)p_109676_, 0.25F).m_5752_();
      p_109669_.m_5483_((double)this.f_109443_[p_109673_].m_123601_(), (double)this.f_109443_[p_109673_].m_123615_(), (double)this.f_109443_[p_109673_].m_123616_()).m_85950_((float)p_109674_, (float)p_109675_, (float)p_109676_, 0.25F).m_5752_();
   }

   public void m_173018_() {
      this.f_109441_ = true;
   }

   public void m_173019_() {
      this.f_109442_ = null;
   }

   public void m_109823_() {
      ++this.f_109477_;
      if (this.f_109477_ % 20 == 0) {
         Iterator<BlockDestructionProgress> iterator = this.f_109408_.values().iterator();

         while(iterator.hasNext()) {
            BlockDestructionProgress blockdestructionprogress = iterator.next();
            int i = blockdestructionprogress.m_139991_();
            if (this.f_109477_ - i > 400) {
               iterator.remove();
               this.m_109765_(blockdestructionprogress);
            }
         }

      }
   }

   private void m_109765_(BlockDestructionProgress p_109766_) {
      long i = p_109766_.m_139985_().m_121878_();
      Set<BlockDestructionProgress> set = this.f_109409_.get(i);
      set.remove(p_109766_);
      if (set.isEmpty()) {
         this.f_109409_.remove(i);
      }

   }

   private void m_109780_(PoseStack p_109781_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      RenderSystem.m_157456_(0, f_109457_);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();

      for(int i = 0; i < 6; ++i) {
         p_109781_.m_85836_();
         if (i == 1) {
            p_109781_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         }

         if (i == 2) {
            p_109781_.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
         }

         if (i == 3) {
            p_109781_.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
         }

         if (i == 4) {
            p_109781_.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
         }

         if (i == 5) {
            p_109781_.m_85845_(Vector3f.f_122227_.m_122240_(-90.0F));
         }

         Matrix4f matrix4f = p_109781_.m_85850_().m_85861_();
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
         bufferbuilder.m_85982_(matrix4f, -100.0F, -100.0F, -100.0F).m_7421_(0.0F, 0.0F).m_6122_(40, 40, 40, 255).m_5752_();
         bufferbuilder.m_85982_(matrix4f, -100.0F, -100.0F, 100.0F).m_7421_(0.0F, 16.0F).m_6122_(40, 40, 40, 255).m_5752_();
         bufferbuilder.m_85982_(matrix4f, 100.0F, -100.0F, 100.0F).m_7421_(16.0F, 16.0F).m_6122_(40, 40, 40, 255).m_5752_();
         bufferbuilder.m_85982_(matrix4f, 100.0F, -100.0F, -100.0F).m_7421_(16.0F, 0.0F).m_6122_(40, 40, 40, 255).m_5752_();
         tesselator.m_85914_();
         p_109781_.m_85849_();
      }

      RenderSystem.m_69458_(true);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }

   public void m_181409_(PoseStack p_181410_, Matrix4f p_181411_, float p_181412_, Runnable p_181413_) {
      p_181413_.run();
      net.minecraftforge.client.ISkyRenderHandler renderHandler = f_109465_.m_104583_().getSkyRenderHandler();
      if (renderHandler != null) {
         renderHandler.render(f_109477_, p_181412_, p_181410_, f_109465_, f_109461_);
         return;
      }
      if (this.f_109461_.f_91073_.m_104583_().m_108883_() == DimensionSpecialEffects.SkyType.END) {
         this.m_109780_(p_181410_);
      } else if (this.f_109461_.f_91073_.m_104583_().m_108883_() == DimensionSpecialEffects.SkyType.NORMAL) {
         RenderSystem.m_69472_();
         Vec3 vec3 = this.f_109465_.m_171660_(this.f_109461_.f_91063_.m_109153_().m_90583_(), p_181412_);
         float f = (float)vec3.f_82479_;
         float f1 = (float)vec3.f_82480_;
         float f2 = (float)vec3.f_82481_;
         FogRenderer.m_109036_();
         BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
         RenderSystem.m_69458_(false);
         RenderSystem.m_157429_(f, f1, f2, 1.0F);
         ShaderInstance shaderinstance = RenderSystem.m_157196_();
         this.f_109472_.m_166867_(p_181410_.m_85850_().m_85861_(), p_181411_, shaderinstance);
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         float[] afloat = this.f_109465_.m_104583_().m_7518_(this.f_109465_.m_46942_(p_181412_), p_181412_);
         if (afloat != null) {
            RenderSystem.m_157427_(GameRenderer::m_172811_);
            RenderSystem.m_69472_();
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            p_181410_.m_85836_();
            p_181410_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
            float f3 = Mth.m_14031_(this.f_109465_.m_46490_(p_181412_)) < 0.0F ? 180.0F : 0.0F;
            p_181410_.m_85845_(Vector3f.f_122227_.m_122240_(f3));
            p_181410_.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
            float f4 = afloat[0];
            float f5 = afloat[1];
            float f6 = afloat[2];
            Matrix4f matrix4f = p_181410_.m_85850_().m_85861_();
            bufferbuilder.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
            bufferbuilder.m_85982_(matrix4f, 0.0F, 100.0F, 0.0F).m_85950_(f4, f5, f6, afloat[3]).m_5752_();
            int i = 16;

            for(int j = 0; j <= 16; ++j) {
               float f7 = (float)j * ((float)Math.PI * 2F) / 16.0F;
               float f8 = Mth.m_14031_(f7);
               float f9 = Mth.m_14089_(f7);
               bufferbuilder.m_85982_(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).m_85950_(afloat[0], afloat[1], afloat[2], 0.0F).m_5752_();
            }

            bufferbuilder.m_85721_();
            BufferUploader.m_85761_(bufferbuilder);
            p_181410_.m_85849_();
         }

         RenderSystem.m_69493_();
         RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         p_181410_.m_85836_();
         float f11 = 1.0F - this.f_109465_.m_46722_(p_181412_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, f11);
         p_181410_.m_85845_(Vector3f.f_122225_.m_122240_(-90.0F));
         p_181410_.m_85845_(Vector3f.f_122223_.m_122240_(this.f_109465_.m_46942_(p_181412_) * 360.0F));
         Matrix4f matrix4f1 = p_181410_.m_85850_().m_85861_();
         float f12 = 30.0F;
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_109455_);
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
         bufferbuilder.m_85982_(matrix4f1, -f12, 100.0F, -f12).m_7421_(0.0F, 0.0F).m_5752_();
         bufferbuilder.m_85982_(matrix4f1, f12, 100.0F, -f12).m_7421_(1.0F, 0.0F).m_5752_();
         bufferbuilder.m_85982_(matrix4f1, f12, 100.0F, f12).m_7421_(1.0F, 1.0F).m_5752_();
         bufferbuilder.m_85982_(matrix4f1, -f12, 100.0F, f12).m_7421_(0.0F, 1.0F).m_5752_();
         bufferbuilder.m_85721_();
         BufferUploader.m_85761_(bufferbuilder);
         f12 = 20.0F;
         RenderSystem.m_157456_(0, f_109454_);
         int k = this.f_109465_.m_46941_();
         int l = k % 4;
         int i1 = k / 4 % 2;
         float f13 = (float)(l + 0) / 4.0F;
         float f14 = (float)(i1 + 0) / 2.0F;
         float f15 = (float)(l + 1) / 4.0F;
         float f16 = (float)(i1 + 1) / 2.0F;
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
         bufferbuilder.m_85982_(matrix4f1, -f12, -100.0F, f12).m_7421_(f15, f16).m_5752_();
         bufferbuilder.m_85982_(matrix4f1, f12, -100.0F, f12).m_7421_(f13, f16).m_5752_();
         bufferbuilder.m_85982_(matrix4f1, f12, -100.0F, -f12).m_7421_(f13, f14).m_5752_();
         bufferbuilder.m_85982_(matrix4f1, -f12, -100.0F, -f12).m_7421_(f15, f14).m_5752_();
         bufferbuilder.m_85721_();
         BufferUploader.m_85761_(bufferbuilder);
         RenderSystem.m_69472_();
         float f10 = this.f_109465_.m_104811_(p_181412_) * f11;
         if (f10 > 0.0F) {
            RenderSystem.m_157429_(f10, f10, f10, f10);
            FogRenderer.m_109017_();
            this.f_109471_.m_166867_(p_181410_.m_85850_().m_85861_(), p_181411_, GameRenderer.m_172808_());
            p_181413_.run();
         }

         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_69461_();
         p_181410_.m_85849_();
         RenderSystem.m_69472_();
         RenderSystem.m_157429_(0.0F, 0.0F, 0.0F, 1.0F);
         double d0 = this.f_109461_.f_91074_.m_20299_(p_181412_).f_82480_ - this.f_109465_.m_6106_().m_171687_(this.f_109465_);
         if (d0 < 0.0D) {
            p_181410_.m_85836_();
            p_181410_.m_85837_(0.0D, 12.0D, 0.0D);
            this.f_109473_.m_166867_(p_181410_.m_85850_().m_85861_(), p_181411_, shaderinstance);
            p_181410_.m_85849_();
         }

         if (this.f_109465_.m_104583_().m_108882_()) {
            RenderSystem.m_157429_(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0F);
         } else {
            RenderSystem.m_157429_(f, f1, f2, 1.0F);
         }

         RenderSystem.m_69493_();
         RenderSystem.m_69458_(true);
      }
   }

   public void m_172954_(PoseStack p_172955_, Matrix4f p_172956_, float p_172957_, double p_172958_, double p_172959_, double p_172960_) {
      net.minecraftforge.client.ICloudRenderHandler renderHandler = f_109465_.m_104583_().getCloudRenderHandler();
      if (renderHandler != null) {
         renderHandler.render(f_109477_, p_172957_, p_172955_, f_109465_, f_109461_, p_172958_, p_172959_, p_172960_);
         return;
      }
      float f = this.f_109465_.m_104583_().m_108871_();
      if (!Float.isNaN(f)) {
         RenderSystem.m_69464_();
         RenderSystem.m_69478_();
         RenderSystem.m_69482_();
         RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.m_69458_(true);
         float f1 = 12.0F;
         float f2 = 4.0F;
         double d0 = 2.0E-4D;
         double d1 = (double)(((float)this.f_109477_ + p_172957_) * 0.03F);
         double d2 = (p_172958_ + d1) / 12.0D;
         double d3 = (double)(f - (float)p_172959_ + 0.33F);
         double d4 = p_172960_ / 12.0D + (double)0.33F;
         d2 = d2 - (double)(Mth.m_14107_(d2 / 2048.0D) * 2048);
         d4 = d4 - (double)(Mth.m_14107_(d4 / 2048.0D) * 2048);
         float f3 = (float)(d2 - (double)Mth.m_14107_(d2));
         float f4 = (float)(d3 / 4.0D - (double)Mth.m_14107_(d3 / 4.0D)) * 4.0F;
         float f5 = (float)(d4 - (double)Mth.m_14107_(d4));
         Vec3 vec3 = this.f_109465_.m_104808_(p_172957_);
         int i = (int)Math.floor(d2);
         int j = (int)Math.floor(d3 / 4.0D);
         int k = (int)Math.floor(d4);
         if (i != this.f_109430_ || j != this.f_109431_ || k != this.f_109432_ || this.f_109461_.f_91066_.m_92174_() != this.f_109435_ || this.f_109433_.m_82557_(vec3) > 2.0E-4D) {
            this.f_109430_ = i;
            this.f_109431_ = j;
            this.f_109432_ = k;
            this.f_109433_ = vec3;
            this.f_109435_ = this.f_109461_.f_91066_.m_92174_();
            this.f_109474_ = true;
         }

         if (this.f_109474_) {
            this.f_109474_ = false;
            BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
            if (this.f_109475_ != null) {
               this.f_109475_.close();
            }

            this.f_109475_ = new VertexBuffer();
            this.m_109578_(bufferbuilder, d2, d3, d4, vec3);
            bufferbuilder.m_85721_();
            this.f_109475_.m_85925_(bufferbuilder);
         }

         RenderSystem.m_157427_(GameRenderer::m_172838_);
         RenderSystem.m_157456_(0, f_109456_);
         FogRenderer.m_109036_();
         p_172955_.m_85836_();
         p_172955_.m_85841_(12.0F, 1.0F, 12.0F);
         p_172955_.m_85837_((double)(-f3), (double)f4, (double)(-f5));
         if (this.f_109475_ != null) {
            int i1 = this.f_109435_ == CloudStatus.FANCY ? 0 : 1;

            for(int l = i1; l < 2; ++l) {
               if (l == 0) {
                  RenderSystem.m_69444_(false, false, false, false);
               } else {
                  RenderSystem.m_69444_(true, true, true, true);
               }

               ShaderInstance shaderinstance = RenderSystem.m_157196_();
               this.f_109475_.m_166867_(p_172955_.m_85850_().m_85861_(), p_172956_, shaderinstance);
            }
         }

         p_172955_.m_85849_();
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_69481_();
         RenderSystem.m_69461_();
      }
   }

   private void m_109578_(BufferBuilder p_109579_, double p_109580_, double p_109581_, double p_109582_, Vec3 p_109583_) {
      float f = 4.0F;
      float f1 = 0.00390625F;
      int i = 8;
      int j = 4;
      float f2 = 9.765625E-4F;
      float f3 = (float)Mth.m_14107_(p_109580_) * 0.00390625F;
      float f4 = (float)Mth.m_14107_(p_109582_) * 0.00390625F;
      float f5 = (float)p_109583_.f_82479_;
      float f6 = (float)p_109583_.f_82480_;
      float f7 = (float)p_109583_.f_82481_;
      float f8 = f5 * 0.9F;
      float f9 = f6 * 0.9F;
      float f10 = f7 * 0.9F;
      float f11 = f5 * 0.7F;
      float f12 = f6 * 0.7F;
      float f13 = f7 * 0.7F;
      float f14 = f5 * 0.8F;
      float f15 = f6 * 0.8F;
      float f16 = f7 * 0.8F;
      RenderSystem.m_157427_(GameRenderer::m_172838_);
      p_109579_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85822_);
      float f17 = (float)Math.floor(p_109581_ / 4.0D) * 4.0F;
      if (this.f_109435_ == CloudStatus.FANCY) {
         for(int k = -3; k <= 4; ++k) {
            for(int l = -3; l <= 4; ++l) {
               float f18 = (float)(k * 8);
               float f19 = (float)(l * 8);
               if (f17 > -5.0F) {
                  p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f11, f12, f13, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
                  p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f11, f12, f13, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
                  p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f11, f12, f13, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
                  p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f11, f12, f13, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
               }

               if (f17 <= 5.0F) {
                  p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
                  p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
                  p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
                  p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
               }

               if (k > -1) {
                  for(int i1 = 0; i1 < 8; ++i1) {
                     p_109579_.m_5483_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).m_7421_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).m_7421_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).m_7421_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).m_7421_((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
                  }
               }

               if (k <= 1) {
                  for(int j2 = 0; j2 < 8; ++j2) {
                     p_109579_.m_5483_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).m_7421_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).m_7421_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).m_7421_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).m_7421_((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).m_85950_(f8, f9, f10, 0.8F).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
                  }
               }

               if (l > -1) {
                  for(int k2 = 0; k2 < 8; ++k2) {
                     p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
                  }
               }

               if (l <= 1) {
                  for(int l2 = 0; l2 < 8; ++l2) {
                     p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).m_7421_((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
                     p_109579_.m_5483_((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).m_7421_((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).m_85950_(f14, f15, f16, 0.8F).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
                  }
               }
            }
         }
      } else {
         int j1 = 1;
         int k1 = 32;

         for(int l1 = -32; l1 < 32; l1 += 32) {
            for(int i2 = -32; i2 < 32; i2 += 32) {
               p_109579_.m_5483_((double)(l1 + 0), (double)f17, (double)(i2 + 32)).m_7421_((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
               p_109579_.m_5483_((double)(l1 + 32), (double)f17, (double)(i2 + 32)).m_7421_((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
               p_109579_.m_5483_((double)(l1 + 32), (double)f17, (double)(i2 + 0)).m_7421_((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
               p_109579_.m_5483_((double)(l1 + 0), (double)f17, (double)(i2 + 0)).m_7421_((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).m_85950_(f5, f6, f7, 0.8F).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
            }
         }
      }

   }

   private void m_109510_(long p_109511_) {
      this.f_109448_ |= this.f_109436_.m_112730_();
      long i = Util.m_137569_();
      int j = 0;
      if (!this.f_109466_.isEmpty()) {
         Iterator<ChunkRenderDispatcher.RenderChunk> iterator = this.f_109466_.iterator();

         while(iterator.hasNext()) {
            ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = iterator.next();
            if (!net.minecraftforge.common.ForgeConfig.CLIENT.alwaysSetupTerrainOffThread.get() && chunkrenderdispatcher$renderchunk.m_112842_()) {
               this.f_109436_.m_112715_(chunkrenderdispatcher$renderchunk);
            } else {
               chunkrenderdispatcher$renderchunk.m_112820_(this.f_109436_);
            }

            chunkrenderdispatcher$renderchunk.m_112840_();
            iterator.remove();
            ++j;
            long k = Util.m_137569_();
            long l = k - i;
            long i1 = l / (long)j;
            long j1 = p_109511_ - k;
            if (j1 < i1) {
               break;
            }
         }
      }

   }

   private void m_173012_(Camera p_173013_) {
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      WorldBorder worldborder = this.f_109465_.m_6857_();
      double d0 = (double)(this.f_109461_.f_91066_.f_92106_ * 16);
      if (!(p_173013_.m_90583_().f_82479_ < worldborder.m_61957_() - d0) || !(p_173013_.m_90583_().f_82479_ > worldborder.m_61955_() + d0) || !(p_173013_.m_90583_().f_82481_ < worldborder.m_61958_() - d0) || !(p_173013_.m_90583_().f_82481_ > worldborder.m_61956_() + d0)) {
         double d1 = 1.0D - worldborder.m_61941_(p_173013_.m_90583_().f_82479_, p_173013_.m_90583_().f_82481_) / d0;
         d1 = Math.pow(d1, 4.0D);
         d1 = Mth.m_14008_(d1, 0.0D, 1.0D);
         double d2 = p_173013_.m_90583_().f_82479_;
         double d3 = p_173013_.m_90583_().f_82481_;
         double d4 = (double)this.f_109461_.f_91063_.m_172790_();
         RenderSystem.m_69478_();
         RenderSystem.m_69482_();
         RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         RenderSystem.m_157456_(0, f_109458_);
         RenderSystem.m_69458_(Minecraft.m_91085_());
         PoseStack posestack = RenderSystem.m_157191_();
         posestack.m_85836_();
         RenderSystem.m_157182_();
         int i = worldborder.m_61954_().m_61901_();
         float f = (float)(i >> 16 & 255) / 255.0F;
         float f1 = (float)(i >> 8 & 255) / 255.0F;
         float f2 = (float)(i & 255) / 255.0F;
         RenderSystem.m_157429_(f, f1, f2, (float)d1);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_69863_(-3.0F, -3.0F);
         RenderSystem.m_69486_();
         RenderSystem.m_69464_();
         float f3 = (float)(Util.m_137550_() % 3000L) / 3000.0F;
         float f4 = 0.0F;
         float f5 = 0.0F;
         float f6 = (float)(d4 - Mth.m_14185_(p_173013_.m_90583_().f_82480_));
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
         double d5 = Math.max((double)Mth.m_14107_(d3 - d0), worldborder.m_61956_());
         double d6 = Math.min((double)Mth.m_14165_(d3 + d0), worldborder.m_61958_());
         if (d2 > worldborder.m_61957_() - d0) {
            float f7 = 0.0F;

            for(double d7 = d5; d7 < d6; f7 += 0.5F) {
               double d8 = Math.min(1.0D, d6 - d7);
               float f8 = (float)d8 * 0.5F;
               bufferbuilder.m_5483_(worldborder.m_61957_() - d2, -d4, d7 - d3).m_7421_(f3 - f7, f3 + f6).m_5752_();
               bufferbuilder.m_5483_(worldborder.m_61957_() - d2, -d4, d7 + d8 - d3).m_7421_(f3 - (f8 + f7), f3 + f6).m_5752_();
               bufferbuilder.m_5483_(worldborder.m_61957_() - d2, d4, d7 + d8 - d3).m_7421_(f3 - (f8 + f7), f3 + 0.0F).m_5752_();
               bufferbuilder.m_5483_(worldborder.m_61957_() - d2, d4, d7 - d3).m_7421_(f3 - f7, f3 + 0.0F).m_5752_();
               ++d7;
            }
         }

         if (d2 < worldborder.m_61955_() + d0) {
            float f9 = 0.0F;

            for(double d9 = d5; d9 < d6; f9 += 0.5F) {
               double d12 = Math.min(1.0D, d6 - d9);
               float f12 = (float)d12 * 0.5F;
               bufferbuilder.m_5483_(worldborder.m_61955_() - d2, -d4, d9 - d3).m_7421_(f3 + f9, f3 + f6).m_5752_();
               bufferbuilder.m_5483_(worldborder.m_61955_() - d2, -d4, d9 + d12 - d3).m_7421_(f3 + f12 + f9, f3 + f6).m_5752_();
               bufferbuilder.m_5483_(worldborder.m_61955_() - d2, d4, d9 + d12 - d3).m_7421_(f3 + f12 + f9, f3 + 0.0F).m_5752_();
               bufferbuilder.m_5483_(worldborder.m_61955_() - d2, d4, d9 - d3).m_7421_(f3 + f9, f3 + 0.0F).m_5752_();
               ++d9;
            }
         }

         d5 = Math.max((double)Mth.m_14107_(d2 - d0), worldborder.m_61955_());
         d6 = Math.min((double)Mth.m_14165_(d2 + d0), worldborder.m_61957_());
         if (d3 > worldborder.m_61958_() - d0) {
            float f10 = 0.0F;

            for(double d10 = d5; d10 < d6; f10 += 0.5F) {
               double d13 = Math.min(1.0D, d6 - d10);
               float f13 = (float)d13 * 0.5F;
               bufferbuilder.m_5483_(d10 - d2, -d4, worldborder.m_61958_() - d3).m_7421_(f3 + f10, f3 + f6).m_5752_();
               bufferbuilder.m_5483_(d10 + d13 - d2, -d4, worldborder.m_61958_() - d3).m_7421_(f3 + f13 + f10, f3 + f6).m_5752_();
               bufferbuilder.m_5483_(d10 + d13 - d2, d4, worldborder.m_61958_() - d3).m_7421_(f3 + f13 + f10, f3 + 0.0F).m_5752_();
               bufferbuilder.m_5483_(d10 - d2, d4, worldborder.m_61958_() - d3).m_7421_(f3 + f10, f3 + 0.0F).m_5752_();
               ++d10;
            }
         }

         if (d3 < worldborder.m_61956_() + d0) {
            float f11 = 0.0F;

            for(double d11 = d5; d11 < d6; f11 += 0.5F) {
               double d14 = Math.min(1.0D, d6 - d11);
               float f14 = (float)d14 * 0.5F;
               bufferbuilder.m_5483_(d11 - d2, -d4, worldborder.m_61956_() - d3).m_7421_(f3 - f11, f3 + f6).m_5752_();
               bufferbuilder.m_5483_(d11 + d14 - d2, -d4, worldborder.m_61956_() - d3).m_7421_(f3 - (f14 + f11), f3 + f6).m_5752_();
               bufferbuilder.m_5483_(d11 + d14 - d2, d4, worldborder.m_61956_() - d3).m_7421_(f3 - (f14 + f11), f3 + 0.0F).m_5752_();
               bufferbuilder.m_5483_(d11 - d2, d4, worldborder.m_61956_() - d3).m_7421_(f3 - f11, f3 + 0.0F).m_5752_();
               ++d11;
            }
         }

         bufferbuilder.m_85721_();
         BufferUploader.m_85761_(bufferbuilder);
         RenderSystem.m_69481_();
         RenderSystem.m_69863_(0.0F, 0.0F);
         RenderSystem.m_69469_();
         RenderSystem.m_69461_();
         posestack.m_85849_();
         RenderSystem.m_157182_();
         RenderSystem.m_69458_(true);
      }
   }

   private void m_109637_(PoseStack p_109638_, VertexConsumer p_109639_, Entity p_109640_, double p_109641_, double p_109642_, double p_109643_, BlockPos p_109644_, BlockState p_109645_) {
      m_109782_(p_109638_, p_109639_, p_109645_.m_60651_(this.f_109465_, p_109644_, CollisionContext.m_82750_(p_109640_)), (double)p_109644_.m_123341_() - p_109641_, (double)p_109644_.m_123342_() - p_109642_, (double)p_109644_.m_123343_() - p_109643_, 0.0F, 0.0F, 0.0F, 0.4F);
   }

   public static void m_109654_(PoseStack p_109655_, VertexConsumer p_109656_, VoxelShape p_109657_, double p_109658_, double p_109659_, double p_109660_, float p_109661_, float p_109662_, float p_109663_, float p_109664_) {
      List<AABB> list = p_109657_.m_83299_();
      int i = Mth.m_14165_((double)list.size() / 3.0D);

      for(int j = 0; j < list.size(); ++j) {
         AABB aabb = list.get(j);
         float f = ((float)j % (float)i + 1.0F) / (float)i;
         float f1 = (float)(j / i);
         float f2 = f * (float)(f1 == 0.0F ? 1 : 0);
         float f3 = f * (float)(f1 == 1.0F ? 1 : 0);
         float f4 = f * (float)(f1 == 2.0F ? 1 : 0);
         m_109782_(p_109655_, p_109656_, Shapes.m_83064_(aabb.m_82386_(0.0D, 0.0D, 0.0D)), p_109658_, p_109659_, p_109660_, f2, f3, f4, 1.0F);
      }

   }

   private static void m_109782_(PoseStack p_109783_, VertexConsumer p_109784_, VoxelShape p_109785_, double p_109786_, double p_109787_, double p_109788_, float p_109789_, float p_109790_, float p_109791_, float p_109792_) {
      PoseStack.Pose posestack$pose = p_109783_.m_85850_();
      p_109785_.m_83224_((p_172987_, p_172988_, p_172989_, p_172990_, p_172991_, p_172992_) -> {
         float f = (float)(p_172990_ - p_172987_);
         float f1 = (float)(p_172991_ - p_172988_);
         float f2 = (float)(p_172992_ - p_172989_);
         float f3 = Mth.m_14116_(f * f + f1 * f1 + f2 * f2);
         f = f / f3;
         f1 = f1 / f3;
         f2 = f2 / f3;
         p_109784_.m_85982_(posestack$pose.m_85861_(), (float)(p_172987_ + p_109786_), (float)(p_172988_ + p_109787_), (float)(p_172989_ + p_109788_)).m_85950_(p_109789_, p_109790_, p_109791_, p_109792_).m_85977_(posestack$pose.m_85864_(), f, f1, f2).m_5752_();
         p_109784_.m_85982_(posestack$pose.m_85861_(), (float)(p_172990_ + p_109786_), (float)(p_172991_ + p_109787_), (float)(p_172992_ + p_109788_)).m_85950_(p_109789_, p_109790_, p_109791_, p_109792_).m_85977_(posestack$pose.m_85864_(), f, f1, f2).m_5752_();
      });
   }

   public static void m_172965_(VertexConsumer p_172966_, double p_172967_, double p_172968_, double p_172969_, double p_172970_, double p_172971_, double p_172972_, float p_172973_, float p_172974_, float p_172975_, float p_172976_) {
      m_109621_(new PoseStack(), p_172966_, p_172967_, p_172968_, p_172969_, p_172970_, p_172971_, p_172972_, p_172973_, p_172974_, p_172975_, p_172976_, p_172973_, p_172974_, p_172975_);
   }

   public static void m_109646_(PoseStack p_109647_, VertexConsumer p_109648_, AABB p_109649_, float p_109650_, float p_109651_, float p_109652_, float p_109653_) {
      m_109621_(p_109647_, p_109648_, p_109649_.f_82288_, p_109649_.f_82289_, p_109649_.f_82290_, p_109649_.f_82291_, p_109649_.f_82292_, p_109649_.f_82293_, p_109650_, p_109651_, p_109652_, p_109653_, p_109650_, p_109651_, p_109652_);
   }

   public static void m_109608_(PoseStack p_109609_, VertexConsumer p_109610_, double p_109611_, double p_109612_, double p_109613_, double p_109614_, double p_109615_, double p_109616_, float p_109617_, float p_109618_, float p_109619_, float p_109620_) {
      m_109621_(p_109609_, p_109610_, p_109611_, p_109612_, p_109613_, p_109614_, p_109615_, p_109616_, p_109617_, p_109618_, p_109619_, p_109620_, p_109617_, p_109618_, p_109619_);
   }

   public static void m_109621_(PoseStack p_109622_, VertexConsumer p_109623_, double p_109624_, double p_109625_, double p_109626_, double p_109627_, double p_109628_, double p_109629_, float p_109630_, float p_109631_, float p_109632_, float p_109633_, float p_109634_, float p_109635_, float p_109636_) {
      Matrix4f matrix4f = p_109622_.m_85850_().m_85861_();
      Matrix3f matrix3f = p_109622_.m_85850_().m_85864_();
      float f = (float)p_109624_;
      float f1 = (float)p_109625_;
      float f2 = (float)p_109626_;
      float f3 = (float)p_109627_;
      float f4 = (float)p_109628_;
      float f5 = (float)p_109629_;
      p_109623_.m_85982_(matrix4f, f, f1, f2).m_85950_(p_109630_, p_109635_, p_109636_, p_109633_).m_85977_(matrix3f, 1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f1, f2).m_85950_(p_109630_, p_109635_, p_109636_, p_109633_).m_85977_(matrix3f, 1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f1, f2).m_85950_(p_109634_, p_109631_, p_109636_, p_109633_).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f4, f2).m_85950_(p_109634_, p_109631_, p_109636_, p_109633_).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f1, f2).m_85950_(p_109634_, p_109635_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, 1.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f1, f5).m_85950_(p_109634_, p_109635_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, 1.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f1, f2).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f4, f2).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f4, f2).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, -1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f4, f2).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, -1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f4, f2).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, 1.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f4, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, 1.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f4, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, -1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f1, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, -1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f1, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f1, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f1, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, -1.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f1, f2).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, -1.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f, f4, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f4, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 1.0F, 0.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f1, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f4, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 1.0F, 0.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f4, f2).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, 1.0F).m_5752_();
      p_109623_.m_85982_(matrix4f, f3, f4, f5).m_85950_(p_109630_, p_109631_, p_109632_, p_109633_).m_85977_(matrix3f, 0.0F, 0.0F, 1.0F).m_5752_();
   }

   public static void m_109556_(BufferBuilder p_109557_, double p_109558_, double p_109559_, double p_109560_, double p_109561_, double p_109562_, double p_109563_, float p_109564_, float p_109565_, float p_109566_, float p_109567_) {
      p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109559_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109559_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109558_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109560_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
      p_109557_.m_5483_(p_109561_, p_109562_, p_109563_).m_85950_(p_109564_, p_109565_, p_109566_, p_109567_).m_5752_();
   }

   public void m_109544_(BlockGetter p_109545_, BlockPos p_109546_, BlockState p_109547_, BlockState p_109548_, int p_109549_) {
      this.m_109732_(p_109546_, (p_109549_ & 8) != 0);
   }

   private void m_109732_(BlockPos p_109733_, boolean p_109734_) {
      for(int i = p_109733_.m_123343_() - 1; i <= p_109733_.m_123343_() + 1; ++i) {
         for(int j = p_109733_.m_123341_() - 1; j <= p_109733_.m_123341_() + 1; ++j) {
            for(int k = p_109733_.m_123342_() - 1; k <= p_109733_.m_123342_() + 1; ++k) {
               this.m_109501_(SectionPos.m_123171_(j), SectionPos.m_123171_(k), SectionPos.m_123171_(i), p_109734_);
            }
         }
      }

   }

   public void m_109494_(int p_109495_, int p_109496_, int p_109497_, int p_109498_, int p_109499_, int p_109500_) {
      for(int i = p_109497_ - 1; i <= p_109500_ + 1; ++i) {
         for(int j = p_109495_ - 1; j <= p_109498_ + 1; ++j) {
            for(int k = p_109496_ - 1; k <= p_109499_ + 1; ++k) {
               this.m_109770_(SectionPos.m_123171_(j), SectionPos.m_123171_(k), SectionPos.m_123171_(i));
            }
         }
      }

   }

   public void m_109721_(BlockPos p_109722_, BlockState p_109723_, BlockState p_109724_) {
      if (this.f_109461_.m_91304_().m_119415_(p_109723_, p_109724_)) {
         this.m_109494_(p_109722_.m_123341_(), p_109722_.m_123342_(), p_109722_.m_123343_(), p_109722_.m_123341_(), p_109722_.m_123342_(), p_109722_.m_123343_());
      }

   }

   public void m_109490_(int p_109491_, int p_109492_, int p_109493_) {
      for(int i = p_109493_ - 1; i <= p_109493_ + 1; ++i) {
         for(int j = p_109491_ - 1; j <= p_109491_ + 1; ++j) {
            for(int k = p_109492_ - 1; k <= p_109492_ + 1; ++k) {
               this.m_109770_(j, k, i);
            }
         }
      }

   }

   public void m_109770_(int p_109771_, int p_109772_, int p_109773_) {
      this.m_109501_(p_109771_, p_109772_, p_109773_, false);
   }

   private void m_109501_(int p_109502_, int p_109503_, int p_109504_, boolean p_109505_) {
      this.f_109469_.m_110859_(p_109502_, p_109503_, p_109504_, p_109505_);
   }

   @Deprecated // Forge: use item aware function below
   public void m_109514_(@Nullable SoundEvent p_109515_, BlockPos p_109516_) {
      this.playStreamingMusic(p_109515_, p_109516_, p_109515_ == null? null : RecordItem.m_43040_(p_109515_));
   }

   public void playStreamingMusic(@Nullable SoundEvent p_109515_, BlockPos p_109516_, @Nullable RecordItem musicDiscItem) {
      SoundInstance soundinstance = this.f_109410_.get(p_109516_);
      if (soundinstance != null) {
         this.f_109461_.m_91106_().m_120399_(soundinstance);
         this.f_109410_.remove(p_109516_);
      }

      if (p_109515_ != null) {
         RecordItem recorditem = musicDiscItem;
         if (recorditem != null) {
            this.f_109461_.f_91065_.m_93055_(recorditem.m_43050_());
         }

         SoundInstance simplesoundinstance = SimpleSoundInstance.m_119747_(p_109515_, (double)p_109516_.m_123341_(), (double)p_109516_.m_123342_(), (double)p_109516_.m_123343_());
         this.f_109410_.put(p_109516_, simplesoundinstance);
         this.f_109461_.m_91106_().m_120367_(simplesoundinstance);
      }

      this.m_109550_(this.f_109465_, p_109516_, p_109515_ != null);
   }

   private void m_109550_(Level p_109551_, BlockPos p_109552_, boolean p_109553_) {
      for(LivingEntity livingentity : p_109551_.m_45976_(LivingEntity.class, (new AABB(p_109552_)).m_82400_(3.0D))) {
         livingentity.m_6818_(p_109552_, p_109553_);
      }

   }

   public void m_109743_(ParticleOptions p_109744_, boolean p_109745_, double p_109746_, double p_109747_, double p_109748_, double p_109749_, double p_109750_, double p_109751_) {
      this.m_109752_(p_109744_, p_109745_, false, p_109746_, p_109747_, p_109748_, p_109749_, p_109750_, p_109751_);
   }

   public void m_109752_(ParticleOptions p_109753_, boolean p_109754_, boolean p_109755_, double p_109756_, double p_109757_, double p_109758_, double p_109759_, double p_109760_, double p_109761_) {
      try {
         this.m_109804_(p_109753_, p_109754_, p_109755_, p_109756_, p_109757_, p_109758_, p_109759_, p_109760_, p_109761_);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Exception while adding particle");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Particle being added");
         crashreportcategory.m_128159_("ID", Registry.f_122829_.m_7981_(p_109753_.m_6012_()));
         crashreportcategory.m_128159_("Parameters", p_109753_.m_5942_());
         crashreportcategory.m_128165_("Position", () -> {
            return CrashReportCategory.m_178937_(this.f_109465_, p_109756_, p_109757_, p_109758_);
         });
         throw new ReportedException(crashreport);
      }
   }

   private <T extends ParticleOptions> void m_109735_(T p_109736_, double p_109737_, double p_109738_, double p_109739_, double p_109740_, double p_109741_, double p_109742_) {
      this.m_109743_(p_109736_, p_109736_.m_6012_().m_123742_(), p_109737_, p_109738_, p_109739_, p_109740_, p_109741_, p_109742_);
   }

   @Nullable
   private Particle m_109795_(ParticleOptions p_109796_, boolean p_109797_, double p_109798_, double p_109799_, double p_109800_, double p_109801_, double p_109802_, double p_109803_) {
      return this.m_109804_(p_109796_, p_109797_, false, p_109798_, p_109799_, p_109800_, p_109801_, p_109802_, p_109803_);
   }

   @Nullable
   private Particle m_109804_(ParticleOptions p_109805_, boolean p_109806_, boolean p_109807_, double p_109808_, double p_109809_, double p_109810_, double p_109811_, double p_109812_, double p_109813_) {
      Camera camera = this.f_109461_.f_91063_.m_109153_();
      if (this.f_109461_ != null && camera.m_90593_() && this.f_109461_.f_91061_ != null) {
         ParticleStatus particlestatus = this.m_109767_(p_109807_);
         if (p_109806_) {
            return this.f_109461_.f_91061_.m_107370_(p_109805_, p_109808_, p_109809_, p_109810_, p_109811_, p_109812_, p_109813_);
         } else if (camera.m_90583_().m_82531_(p_109808_, p_109809_, p_109810_) > 1024.0D) {
            return null;
         } else {
            return particlestatus == ParticleStatus.MINIMAL ? null : this.f_109461_.f_91061_.m_107370_(p_109805_, p_109808_, p_109809_, p_109810_, p_109811_, p_109812_, p_109813_);
         }
      } else {
         return null;
      }
   }

   private ParticleStatus m_109767_(boolean p_109768_) {
      ParticleStatus particlestatus = this.f_109461_.f_91066_.f_92073_;
      if (p_109768_ && particlestatus == ParticleStatus.MINIMAL && this.f_109465_.f_46441_.nextInt(10) == 0) {
         particlestatus = ParticleStatus.DECREASED;
      }

      if (particlestatus == ParticleStatus.DECREASED && this.f_109465_.f_46441_.nextInt(3) == 0) {
         particlestatus = ParticleStatus.MINIMAL;
      }

      return particlestatus;
   }

   public void m_109824_() {
   }

   public void m_109506_(int p_109507_, BlockPos p_109508_, int p_109509_) {
      switch(p_109507_) {
      case 1023:
      case 1028:
      case 1038:
         Camera camera = this.f_109461_.f_91063_.m_109153_();
         if (camera.m_90593_()) {
            double d0 = (double)p_109508_.m_123341_() - camera.m_90583_().f_82479_;
            double d1 = (double)p_109508_.m_123342_() - camera.m_90583_().f_82480_;
            double d2 = (double)p_109508_.m_123343_() - camera.m_90583_().f_82481_;
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            double d4 = camera.m_90583_().f_82479_;
            double d5 = camera.m_90583_().f_82480_;
            double d6 = camera.m_90583_().f_82481_;
            if (d3 > 0.0D) {
               d4 += d0 / d3 * 2.0D;
               d5 += d1 / d3 * 2.0D;
               d6 += d2 / d3 * 2.0D;
            }

            if (p_109507_ == 1023) {
               this.f_109465_.m_7785_(d4, d5, d6, SoundEvents.f_12563_, SoundSource.HOSTILE, 1.0F, 1.0F, false);
            } else if (p_109507_ == 1038) {
               this.f_109465_.m_7785_(d4, d5, d6, SoundEvents.f_11860_, SoundSource.HOSTILE, 1.0F, 1.0F, false);
            } else {
               this.f_109465_.m_7785_(d4, d5, d6, SoundEvents.f_11891_, SoundSource.HOSTILE, 5.0F, 1.0F, false);
            }
         }
      default:
      }
   }

   public void m_109532_(Player p_109533_, int p_109534_, BlockPos p_109535_, int p_109536_) {
      Random random = this.f_109465_.f_46441_;
      switch(p_109534_) {
      case 1000:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11796_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1001:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11797_, SoundSource.BLOCKS, 1.0F, 1.2F, false);
         break;
      case 1002:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11798_, SoundSource.BLOCKS, 1.0F, 1.2F, false);
         break;
      case 1003:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11898_, SoundSource.NEUTRAL, 1.0F, 1.2F, false);
         break;
      case 1004:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11933_, SoundSource.NEUTRAL, 1.0F, 1.2F, false);
         break;
      case 1005:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12056_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1006:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12627_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1007:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12629_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1008:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11873_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1009:
         if (p_109536_ == 0) {
            this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
         } else if (p_109536_ == 1) {
            this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11914_, SoundSource.BLOCKS, 0.7F, 1.6F + (random.nextFloat() - random.nextFloat()) * 0.4F, false);
         }
         break;
      case 1010:
         if (Item.m_41445_(p_109536_) instanceof RecordItem) {
            this.playStreamingMusic(((RecordItem)Item.m_41445_(p_109536_)).m_43051_(), p_109535_, (RecordItem) Item.m_41445_(p_109536_));
         } else {
            this.m_109514_((SoundEvent)null, p_109535_);
         }
         break;
      case 1011:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12055_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1012:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12626_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1013:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12628_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1014:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11872_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1015:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11924_, SoundSource.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1016:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11923_, SoundSource.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1017:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11896_, SoundSource.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1018:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11705_, SoundSource.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1019:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12599_, SoundSource.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1020:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12600_, SoundSource.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1021:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12601_, SoundSource.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1022:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12555_, SoundSource.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1024:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12558_, SoundSource.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1025:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11735_, SoundSource.NEUTRAL, 0.05F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1026:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12609_, SoundSource.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1027:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12616_, SoundSource.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1029:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11665_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1030:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11671_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1031:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11668_, SoundSource.BLOCKS, 0.3F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1032:
         this.f_109461_.m_91106_().m_120367_(SimpleSoundInstance.m_119766_(SoundEvents.f_12287_, random.nextFloat() * 0.4F + 0.8F, 0.25F));
         break;
      case 1033:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11756_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1034:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11755_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1035:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11772_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 1036:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12011_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1037:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12012_, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1039:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12228_, SoundSource.HOSTILE, 0.3F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1040:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12602_, SoundSource.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1041:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12044_, SoundSource.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1042:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11998_, SoundSource.BLOCKS, 1.0F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1043:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11713_, SoundSource.BLOCKS, 1.0F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1044:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12471_, SoundSource.BLOCKS, 1.0F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1045:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_144126_, SoundSource.BLOCKS, 2.0F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1046:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_144129_, SoundSource.BLOCKS, 2.0F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1047:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_144130_, SoundSource.BLOCKS, 2.0F, this.f_109465_.f_46441_.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1048:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_144211_, SoundSource.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1500:
         ComposterBlock.m_51923_(this.f_109465_, p_109535_, p_109536_ > 0);
         break;
      case 1501:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12031_, SoundSource.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);

         for(int i2 = 0; i2 < 8; ++i2) {
            this.f_109465_.m_7106_(ParticleTypes.f_123755_, (double)p_109535_.m_123341_() + random.nextDouble(), (double)p_109535_.m_123342_() + 1.2D, (double)p_109535_.m_123343_() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
         }
         break;
      case 1502:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12374_, SoundSource.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);

         for(int l1 = 0; l1 < 5; ++l1) {
            double d15 = (double)p_109535_.m_123341_() + random.nextDouble() * 0.6D + 0.2D;
            double d20 = (double)p_109535_.m_123342_() + random.nextDouble() * 0.6D + 0.2D;
            double d26 = (double)p_109535_.m_123343_() + random.nextDouble() * 0.6D + 0.2D;
            this.f_109465_.m_7106_(ParticleTypes.f_123762_, d15, d20, d26, 0.0D, 0.0D, 0.0D);
         }
         break;
      case 1503:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11859_, SoundSource.BLOCKS, 1.0F, 1.0F, false);

         for(int k1 = 0; k1 < 16; ++k1) {
            double d14 = (double)p_109535_.m_123341_() + (5.0D + random.nextDouble() * 6.0D) / 16.0D;
            double d19 = (double)p_109535_.m_123342_() + 0.8125D;
            double d25 = (double)p_109535_.m_123343_() + (5.0D + random.nextDouble() * 6.0D) / 16.0D;
            this.f_109465_.m_7106_(ParticleTypes.f_123762_, d14, d19, d25, 0.0D, 0.0D, 0.0D);
         }
         break;
      case 1504:
         PointedDripstoneBlock.m_154062_(this.f_109465_, p_109535_, this.f_109465_.m_8055_(p_109535_));
         break;
      case 1505:
         BoneMealItem.m_40638_(this.f_109465_, p_109535_, p_109536_);
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_144074_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 2000:
         Direction direction = Direction.m_122376_(p_109536_);
         int j1 = direction.m_122429_();
         int j2 = direction.m_122430_();
         int k2 = direction.m_122431_();
         double d18 = (double)p_109535_.m_123341_() + (double)j1 * 0.6D + 0.5D;
         double d24 = (double)p_109535_.m_123342_() + (double)j2 * 0.6D + 0.5D;
         double d28 = (double)p_109535_.m_123343_() + (double)k2 * 0.6D + 0.5D;

         for(int i3 = 0; i3 < 10; ++i3) {
            double d4 = random.nextDouble() * 0.2D + 0.01D;
            double d6 = d18 + (double)j1 * 0.01D + (random.nextDouble() - 0.5D) * (double)k2 * 0.5D;
            double d8 = d24 + (double)j2 * 0.01D + (random.nextDouble() - 0.5D) * (double)j2 * 0.5D;
            double d30 = d28 + (double)k2 * 0.01D + (random.nextDouble() - 0.5D) * (double)j1 * 0.5D;
            double d9 = (double)j1 * d4 + random.nextGaussian() * 0.01D;
            double d10 = (double)j2 * d4 + random.nextGaussian() * 0.01D;
            double d11 = (double)k2 * d4 + random.nextGaussian() * 0.01D;
            this.m_109735_(ParticleTypes.f_123762_, d6, d8, d30, d9, d10, d11);
         }
         break;
      case 2001:
         BlockState blockstate = Block.m_49803_(p_109536_);
         if (!blockstate.m_60795_()) {
            SoundType soundtype = blockstate.getSoundType(this.f_109465_, p_109535_, null);
            this.f_109465_.m_104677_(p_109535_, soundtype.m_56775_(), SoundSource.BLOCKS, (soundtype.m_56773_() + 1.0F) / 2.0F, soundtype.m_56774_() * 0.8F, false);
         }

         this.f_109465_.m_142052_(p_109535_, blockstate);
         break;
      case 2002:
      case 2007:
         Vec3 vec3 = Vec3.m_82539_(p_109535_);

         for(int i1 = 0; i1 < 8; ++i1) {
            this.m_109735_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(Items.f_42736_)), vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
         }

         float f3 = (float)(p_109536_ >> 16 & 255) / 255.0F;
         float f4 = (float)(p_109536_ >> 8 & 255) / 255.0F;
         float f5 = (float)(p_109536_ >> 0 & 255) / 255.0F;
         ParticleOptions particleoptions = p_109534_ == 2007 ? ParticleTypes.f_123751_ : ParticleTypes.f_123806_;

         for(int j = 0; j < 100; ++j) {
            double d23 = random.nextDouble() * 4.0D;
            double d27 = random.nextDouble() * Math.PI * 2.0D;
            double d29 = Math.cos(d27) * d23;
            double d5 = 0.01D + random.nextDouble() * 0.5D;
            double d7 = Math.sin(d27) * d23;
            Particle particle1 = this.m_109795_(particleoptions, particleoptions.m_6012_().m_123742_(), vec3.f_82479_ + d29 * 0.1D, vec3.f_82480_ + 0.3D, vec3.f_82481_ + d7 * 0.1D, d29, d5, d7);
            if (particle1 != null) {
               float f2 = 0.75F + random.nextFloat() * 0.25F;
               particle1.m_107253_(f3 * f2, f4 * f2, f5 * f2);
               particle1.m_107268_((float)d23);
            }
         }

         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_12436_, SoundSource.NEUTRAL, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2003:
         double d0 = (double)p_109535_.m_123341_() + 0.5D;
         double d13 = (double)p_109535_.m_123342_();
         double d17 = (double)p_109535_.m_123343_() + 0.5D;

         for(int l2 = 0; l2 < 8; ++l2) {
            this.m_109735_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(Items.f_42545_)), d0, d13, d17, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
         }

         for(double d22 = 0.0D; d22 < (Math.PI * 2D); d22 += 0.15707963267948966D) {
            this.m_109735_(ParticleTypes.f_123760_, d0 + Math.cos(d22) * 5.0D, d13 - 0.4D, d17 + Math.sin(d22) * 5.0D, Math.cos(d22) * -5.0D, 0.0D, Math.sin(d22) * -5.0D);
            this.m_109735_(ParticleTypes.f_123760_, d0 + Math.cos(d22) * 5.0D, d13 - 0.4D, d17 + Math.sin(d22) * 5.0D, Math.cos(d22) * -7.0D, 0.0D, Math.sin(d22) * -7.0D);
         }
         break;
      case 2004:
         for(int l = 0; l < 20; ++l) {
            double d12 = (double)p_109535_.m_123341_() + 0.5D + (random.nextDouble() - 0.5D) * 2.0D;
            double d16 = (double)p_109535_.m_123342_() + 0.5D + (random.nextDouble() - 0.5D) * 2.0D;
            double d21 = (double)p_109535_.m_123343_() + 0.5D + (random.nextDouble() - 0.5D) * 2.0D;
            this.f_109465_.m_7106_(ParticleTypes.f_123762_, d12, d16, d21, 0.0D, 0.0D, 0.0D);
            this.f_109465_.m_7106_(ParticleTypes.f_123744_, d12, d16, d21, 0.0D, 0.0D, 0.0D);
         }
         break;
      case 2005:
         BoneMealItem.m_40638_(this.f_109465_, p_109535_, p_109536_);
         break;
      case 2006:
         for(int k = 0; k < 200; ++k) {
            float f = random.nextFloat() * 4.0F;
            float f1 = random.nextFloat() * ((float)Math.PI * 2F);
            double d1 = (double)(Mth.m_14089_(f1) * f);
            double d2 = 0.01D + random.nextDouble() * 0.5D;
            double d3 = (double)(Mth.m_14031_(f1) * f);
            Particle particle = this.m_109795_(ParticleTypes.f_123799_, false, (double)p_109535_.m_123341_() + d1 * 0.1D, (double)p_109535_.m_123342_() + 0.3D, (double)p_109535_.m_123343_() + d3 * 0.1D, d1, d2, d3);
            if (particle != null) {
               particle.m_107268_(f);
            }
         }

         if (p_109536_ == 1) {
            this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11892_, SoundSource.HOSTILE, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
         }
         break;
      case 2008:
         this.f_109465_.m_7106_(ParticleTypes.f_123813_, (double)p_109535_.m_123341_() + 0.5D, (double)p_109535_.m_123342_() + 0.5D, (double)p_109535_.m_123343_() + 0.5D, 0.0D, 0.0D, 0.0D);
         break;
      case 2009:
         for(int i = 0; i < 8; ++i) {
            this.f_109465_.m_7106_(ParticleTypes.f_123796_, (double)p_109535_.m_123341_() + random.nextDouble(), (double)p_109535_.m_123342_() + 1.2D, (double)p_109535_.m_123343_() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
         }
         break;
      case 3000:
         this.f_109465_.m_6493_(ParticleTypes.f_123812_, true, (double)p_109535_.m_123341_() + 0.5D, (double)p_109535_.m_123342_() + 0.5D, (double)p_109535_.m_123343_() + 0.5D, 0.0D, 0.0D, 0.0D);
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11858_, SoundSource.BLOCKS, 10.0F, (1.0F + (this.f_109465_.f_46441_.nextFloat() - this.f_109465_.f_46441_.nextFloat()) * 0.2F) * 0.7F, false);
         break;
      case 3001:
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_11894_, SoundSource.HOSTILE, 64.0F, 0.8F + this.f_109465_.f_46441_.nextFloat() * 0.3F, false);
         break;
      case 3002:
         if (p_109536_ >= 0 && p_109536_ < Direction.Axis.f_122448_.length) {
            ParticleUtils.m_144967_(Direction.Axis.f_122448_[p_109536_], this.f_109465_, p_109535_, 0.125D, ParticleTypes.f_175830_, UniformInt.m_146622_(10, 19));
         } else {
            ParticleUtils.m_144962_(this.f_109465_, p_109535_, ParticleTypes.f_175830_, UniformInt.m_146622_(3, 5));
         }
         break;
      case 3003:
         ParticleUtils.m_144962_(this.f_109465_, p_109535_, ParticleTypes.f_175828_, UniformInt.m_146622_(3, 5));
         this.f_109465_.m_104677_(p_109535_, SoundEvents.f_144178_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         break;
      case 3004:
         ParticleUtils.m_144962_(this.f_109465_, p_109535_, ParticleTypes.f_175829_, UniformInt.m_146622_(3, 5));
         break;
      case 3005:
         ParticleUtils.m_144962_(this.f_109465_, p_109535_, ParticleTypes.f_175831_, UniformInt.m_146622_(3, 5));
      }

   }

   public void m_109774_(int p_109775_, BlockPos p_109776_, int p_109777_) {
      if (p_109777_ >= 0 && p_109777_ < 10) {
         BlockDestructionProgress blockdestructionprogress1 = this.f_109408_.get(p_109775_);
         if (blockdestructionprogress1 != null) {
            this.m_109765_(blockdestructionprogress1);
         }

         if (blockdestructionprogress1 == null || blockdestructionprogress1.m_139985_().m_123341_() != p_109776_.m_123341_() || blockdestructionprogress1.m_139985_().m_123342_() != p_109776_.m_123342_() || blockdestructionprogress1.m_139985_().m_123343_() != p_109776_.m_123343_()) {
            blockdestructionprogress1 = new BlockDestructionProgress(p_109775_, p_109776_);
            this.f_109408_.put(p_109775_, blockdestructionprogress1);
         }

         blockdestructionprogress1.m_139981_(p_109777_);
         blockdestructionprogress1.m_139986_(this.f_109477_);
         this.f_109409_.computeIfAbsent(blockdestructionprogress1.m_139985_().m_121878_(), (p_109779_) -> {
            return Sets.newTreeSet();
         }).add(blockdestructionprogress1);
      } else {
         BlockDestructionProgress blockdestructionprogress = this.f_109408_.remove(p_109775_);
         if (blockdestructionprogress != null) {
            this.m_109765_(blockdestructionprogress);
         }
      }

   }

   public boolean m_109825_() {
      return this.f_109466_.isEmpty() && this.f_109436_.m_112732_();
   }

   public void m_109826_() {
      this.f_109448_ = true;
      this.f_109474_ = true;
   }

   public void m_109762_(Collection<BlockEntity> p_109763_, Collection<BlockEntity> p_109764_) {
      synchronized(this.f_109468_) {
         this.f_109468_.removeAll(p_109763_);
         this.f_109468_.addAll(p_109764_);
      }
   }

   public static int m_109541_(BlockAndTintGetter p_109542_, BlockPos p_109543_) {
      return m_109537_(p_109542_, p_109542_.m_8055_(p_109543_), p_109543_);
   }

   public static int m_109537_(BlockAndTintGetter p_109538_, BlockState p_109539_, BlockPos p_109540_) {
      if (p_109539_.m_60788_(p_109538_, p_109540_)) {
         return 15728880;
      } else {
         int i = p_109538_.m_45517_(LightLayer.SKY, p_109540_);
         int j = p_109538_.m_45517_(LightLayer.BLOCK, p_109540_);
         int k = p_109539_.getLightEmission(p_109538_, p_109540_);
         if (j < k) {
            j = k;
         }

         return i << 20 | j << 4;
      }
   }

   @Nullable
   public RenderTarget m_109827_() {
      return this.f_109411_;
   }

   @Nullable
   public RenderTarget m_109828_() {
      return this.f_109413_;
   }

   @Nullable
   public RenderTarget m_109829_() {
      return this.f_109414_;
   }

   @Nullable
   public RenderTarget m_109830_() {
      return this.f_109415_;
   }

   @Nullable
   public RenderTarget m_109831_() {
      return this.f_109416_;
   }

   @Nullable
   public RenderTarget m_109832_() {
      return this.f_109417_;
   }

   @Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.MODELS;
   }

   @OnlyIn(Dist.CLIENT)
   static class RenderChunkInfo {
      final ChunkRenderDispatcher.RenderChunk f_109839_;
      private byte f_173020_;
      byte f_109841_;
      final int f_109842_;

      RenderChunkInfo(ChunkRenderDispatcher.RenderChunk p_173022_, @Nullable Direction p_173023_, int p_173024_) {
         this.f_109839_ = p_173022_;
         if (p_173023_ != null) {
            this.m_173028_(p_173023_);
         }

         this.f_109842_ = p_173024_;
      }

      public void m_109854_(byte p_109855_, Direction p_109856_) {
         this.f_109841_ = (byte)(this.f_109841_ | p_109855_ | 1 << p_109856_.ordinal());
      }

      public boolean m_109859_(Direction p_109860_) {
         return (this.f_109841_ & 1 << p_109860_.ordinal()) > 0;
      }

      public void m_173028_(Direction p_173029_) {
         this.f_173020_ = (byte)(this.f_173020_ | this.f_173020_ | 1 << p_173029_.ordinal());
      }

      public boolean m_173026_(int p_173027_) {
         return (this.f_173020_ & 1 << p_173027_) > 0;
      }

      public boolean m_173025_() {
         return this.f_173020_ != 0;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class RenderInfoMap {
      private final LevelRenderer.RenderChunkInfo[] f_173030_;
      private final LevelRenderer.RenderChunkInfo[] f_173031_;

      RenderInfoMap(int p_173033_) {
         this.f_173030_ = new LevelRenderer.RenderChunkInfo[p_173033_];
         this.f_173031_ = new LevelRenderer.RenderChunkInfo[p_173033_];
      }

      void m_173034_() {
         System.arraycopy(this.f_173031_, 0, this.f_173030_, 0, this.f_173030_.length);
      }

      public void m_173037_(ChunkRenderDispatcher.RenderChunk p_173038_, LevelRenderer.RenderChunkInfo p_173039_) {
         this.f_173030_[p_173038_.f_173717_] = p_173039_;
      }

      public LevelRenderer.RenderChunkInfo m_173035_(ChunkRenderDispatcher.RenderChunk p_173036_) {
         return this.f_173030_[p_173036_.f_173717_];
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class TransparencyShaderException extends RuntimeException {
      public TransparencyShaderException(String p_109868_, Throwable p_109869_) {
         super(p_109868_, p_109869_);
      }
   }
}
