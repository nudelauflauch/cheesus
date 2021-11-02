package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.PngInfo;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class TextureAtlas extends AbstractTexture implements Tickable {
   private static final Logger f_118261_ = LogManager.getLogger();
   @Deprecated
   public static final ResourceLocation f_118259_ = InventoryMenu.f_39692_;
   @Deprecated
   public static final ResourceLocation f_118260_ = new ResourceLocation("textures/atlas/particles.png");
   private static final String f_174697_ = ".png";
   private final List<Tickable> f_118262_ = Lists.newArrayList();
   private final Set<ResourceLocation> f_118263_ = Sets.newHashSet();
   private final Map<ResourceLocation, TextureAtlasSprite> f_118264_ = Maps.newHashMap();
   private final ResourceLocation f_118265_;
   private final int f_118266_;

   public TextureAtlas(ResourceLocation p_118269_) {
      this.f_118265_ = p_118269_;
      this.f_118266_ = RenderSystem.m_69839_();
   }

   public void m_6704_(ResourceManager p_118282_) {
   }

   public void m_118312_(TextureAtlas.Preparations p_118313_) {
      this.f_118263_.clear();
      this.f_118263_.addAll(p_118313_.f_118331_);
      f_118261_.info("Created: {}x{}x{} {}-atlas", p_118313_.f_118332_, p_118313_.f_118333_, p_118313_.f_118334_, this.f_118265_);
      TextureUtil.m_85287_(this.m_117963_(), p_118313_.f_118334_, p_118313_.f_118332_, p_118313_.f_118333_);
      this.m_118329_();

      for(TextureAtlasSprite textureatlassprite : p_118313_.f_118335_) {
         this.f_118264_.put(textureatlassprite.m_118413_(), textureatlassprite);

         try {
            textureatlassprite.m_118416_();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.m_127521_(throwable, "Stitching texture atlas");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Texture being stitched together");
            crashreportcategory.m_128159_("Atlas path", this.f_118265_);
            crashreportcategory.m_128159_("Sprite", textureatlassprite);
            throw new ReportedException(crashreport);
         }

         Tickable tickable = textureatlassprite.m_174746_();
         if (tickable != null) {
            this.f_118262_.add(tickable);
         }
      }

      net.minecraftforge.client.ForgeHooksClient.onTextureStitchedPost(this);
   }

   public TextureAtlas.Preparations m_118307_(ResourceManager p_118308_, Stream<ResourceLocation> p_118309_, ProfilerFiller p_118310_, int p_118311_) {
      p_118310_.m_6180_("preparing");
      Set<ResourceLocation> set = p_118309_.peek((p_118327_) -> {
         if (p_118327_ == null) {
            throw new IllegalArgumentException("Location cannot be null!");
         }
      }).collect(Collectors.toSet());
      int i = this.f_118266_;
      Stitcher stitcher = new Stitcher(i, i, p_118311_);
      int j = Integer.MAX_VALUE;
      int k = 1 << p_118311_;
      p_118310_.m_6182_("extracting_frames");
      net.minecraftforge.client.ForgeHooksClient.onTextureStitchedPre(this, set);

      for(TextureAtlasSprite.Info textureatlassprite$info : this.m_118304_(p_118308_, set)) {
         j = Math.min(j, Math.min(textureatlassprite$info.m_118434_(), textureatlassprite$info.m_118437_()));
         int l = Math.min(Integer.lowestOneBit(textureatlassprite$info.m_118434_()), Integer.lowestOneBit(textureatlassprite$info.m_118437_()));
         if (l < k) {
            f_118261_.warn("Texture {} with size {}x{} limits mip level from {} to {}", textureatlassprite$info.m_118431_(), textureatlassprite$info.m_118434_(), textureatlassprite$info.m_118437_(), Mth.m_14173_(k), Mth.m_14173_(l));
            k = l;
         }

         stitcher.m_118185_(textureatlassprite$info);
      }

      int i1 = Math.min(j, k);
      int j1 = Mth.m_14173_(i1);
      int k1 = p_118311_;
      if (false) // FORGE: do not lower the mipmap level
      if (j1 < p_118311_) {
         f_118261_.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.f_118265_, p_118311_, j1, i1);
         k1 = j1;
      } else {
         k1 = p_118311_;
      }

      p_118310_.m_6182_("register");
      stitcher.m_118185_(MissingTextureAtlasSprite.m_118079_());
      p_118310_.m_6182_("stitching");

      try {
         stitcher.m_118193_();
      } catch (StitcherException stitcherexception) {
         CrashReport crashreport = CrashReport.m_127521_(stitcherexception, "Stitching");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Stitcher");
         crashreportcategory.m_128159_("Sprites", stitcherexception.m_118258_().stream().map((p_118315_) -> {
            return String.format("%s[%dx%d]", p_118315_.m_118431_(), p_118315_.m_118434_(), p_118315_.m_118437_());
         }).collect(Collectors.joining(",")));
         crashreportcategory.m_128159_("Max Texture Size", i);
         throw new ReportedException(crashreport);
      }

      p_118310_.m_6182_("loading");
      List<TextureAtlasSprite> list = this.m_118283_(p_118308_, stitcher, k1);
      p_118310_.m_7238_();
      return new TextureAtlas.Preparations(set, stitcher.m_118174_(), stitcher.m_118187_(), k1, list);
   }

   private Collection<TextureAtlasSprite.Info> m_118304_(ResourceManager p_118305_, Set<ResourceLocation> p_118306_) {
      List<CompletableFuture<?>> list = Lists.newArrayList();
      Queue<TextureAtlasSprite.Info> queue = new ConcurrentLinkedQueue<>();

      for(ResourceLocation resourcelocation : p_118306_) {
         if (!MissingTextureAtlasSprite.m_118071_().equals(resourcelocation)) {
            list.add(CompletableFuture.runAsync(() -> {
               ResourceLocation resourcelocation1 = this.m_118324_(resourcelocation);

               TextureAtlasSprite.Info textureatlassprite$info;
               try {
                  Resource resource = p_118305_.m_142591_(resourcelocation1);

                  try {
                     PngInfo pnginfo = new PngInfo(resource.toString(), resource.m_6679_());
                     AnimationMetadataSection animationmetadatasection = resource.m_5507_(AnimationMetadataSection.f_119011_);
                     if (animationmetadatasection == null) {
                        animationmetadatasection = AnimationMetadataSection.f_119012_;
                     }

                     Pair<Integer, Integer> pair = animationmetadatasection.m_7117_(pnginfo.f_85207_, pnginfo.f_85208_);
                     textureatlassprite$info = new TextureAtlasSprite.Info(resourcelocation, pair.getFirst(), pair.getSecond(), animationmetadatasection);
                  } catch (Throwable throwable1) {
                     if (resource != null) {
                        try {
                           resource.close();
                        } catch (Throwable throwable) {
                           throwable1.addSuppressed(throwable);
                        }
                     }

                     throw throwable1;
                  }

                  if (resource != null) {
                     resource.close();
                  }
               } catch (RuntimeException runtimeexception) {
                  f_118261_.error("Unable to parse metadata from {} : {}", resourcelocation1, runtimeexception);
                  return;
               } catch (IOException ioexception) {
                  f_118261_.error("Using missing texture, unable to load {} : {}", resourcelocation1, ioexception);
                  return;
               }

               queue.add(textureatlassprite$info);
            }, Util.m_137578_()));
         }
      }

      CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
      return queue;
   }

   private List<TextureAtlasSprite> m_118283_(ResourceManager p_118284_, Stitcher p_118285_, int p_118286_) {
      Queue<TextureAtlasSprite> queue = new ConcurrentLinkedQueue<>();
      List<CompletableFuture<?>> list = Lists.newArrayList();
      p_118285_.m_118180_((p_174703_, p_174704_, p_174705_, p_174706_, p_174707_) -> {
         if (p_174703_ == MissingTextureAtlasSprite.m_118079_()) {
            MissingTextureAtlasSprite missingtextureatlassprite = MissingTextureAtlasSprite.m_118072_(this, p_118286_, p_174704_, p_174705_, p_174706_, p_174707_);
            queue.add(missingtextureatlassprite);
         } else {
            list.add(CompletableFuture.runAsync(() -> {
               TextureAtlasSprite textureatlassprite = this.m_118287_(p_118284_, p_174703_, p_174704_, p_174705_, p_118286_, p_174706_, p_174707_);
               if (textureatlassprite != null) {
                  queue.add(textureatlassprite);
               }

            }, Util.m_137578_()));
         }

      });
      CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
      return Lists.newArrayList(queue);
   }

   @Nullable
   private TextureAtlasSprite m_118287_(ResourceManager p_118288_, TextureAtlasSprite.Info p_118289_, int p_118290_, int p_118291_, int p_118292_, int p_118293_, int p_118294_) {
      ResourceLocation resourcelocation = this.m_118324_(p_118289_.m_118431_());

      try {
         Resource resource = p_118288_.m_142591_(resourcelocation);

         TextureAtlasSprite textureatlassprite;
         try {
            NativeImage nativeimage = NativeImage.m_85058_(resource.m_6679_());
            textureatlassprite = net.minecraftforge.client.ForgeHooksClient.loadTextureAtlasSprite(this, p_118288_, p_118289_, resource, p_118290_, p_118291_, p_118293_, p_118294_, p_118292_, nativeimage);
            if (textureatlassprite == null)
            textureatlassprite = new TextureAtlasSprite(this, p_118289_, p_118292_, p_118290_, p_118291_, p_118293_, p_118294_, nativeimage);
         } catch (Throwable throwable1) {
            if (resource != null) {
               try {
                  resource.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (resource != null) {
            resource.close();
         }

         return textureatlassprite;
      } catch (RuntimeException runtimeexception) {
         f_118261_.error("Unable to parse metadata from {}", resourcelocation, runtimeexception);
         return null;
      } catch (IOException ioexception) {
         f_118261_.error("Using missing texture, unable to load {}", resourcelocation, ioexception);
         return null;
      }
   }

   private ResourceLocation m_118324_(ResourceLocation p_118325_) {
      return new ResourceLocation(p_118325_.m_135827_(), String.format("textures/%s%s", p_118325_.m_135815_(), ".png"));
   }

   public void m_118270_() {
      this.m_117966_();

      for(Tickable tickable : this.f_118262_) {
         tickable.m_7673_();
      }

   }

   public void m_7673_() {
      if (!RenderSystem.m_69586_()) {
         RenderSystem.m_69879_(this::m_118270_);
      } else {
         this.m_118270_();
      }

   }

   public TextureAtlasSprite m_118316_(ResourceLocation p_118317_) {
      TextureAtlasSprite textureatlassprite = this.f_118264_.get(p_118317_);
      return textureatlassprite == null ? this.f_118264_.get(MissingTextureAtlasSprite.m_118071_()) : textureatlassprite;
   }

   public void m_118329_() {
      for(TextureAtlasSprite textureatlassprite : this.f_118264_.values()) {
         textureatlassprite.close();
      }

      this.f_118264_.clear();
      this.f_118262_.clear();
   }

   public ResourceLocation m_118330_() {
      return this.f_118265_;
   }

   public void m_118322_(TextureAtlas.Preparations p_118323_) {
      this.m_117960_(false, p_118323_.f_118334_ > 0);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Preparations {
      final Set<ResourceLocation> f_118331_;
      final int f_118332_;
      final int f_118333_;
      final int f_118334_;
      final List<TextureAtlasSprite> f_118335_;

      public Preparations(Set<ResourceLocation> p_118337_, int p_118338_, int p_118339_, int p_118340_, List<TextureAtlasSprite> p_118341_) {
         this.f_118331_ = p_118337_;
         this.f_118332_ = p_118338_;
         this.f_118333_ = p_118339_;
         this.f_118334_ = p_118340_;
         this.f_118335_ = p_118341_;
      }
   }
}
