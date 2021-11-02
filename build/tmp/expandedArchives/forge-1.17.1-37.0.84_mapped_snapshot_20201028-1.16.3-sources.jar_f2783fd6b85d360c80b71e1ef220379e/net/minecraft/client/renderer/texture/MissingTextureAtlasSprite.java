package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.NativeImage;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.metadata.animation.AnimationFrame;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class MissingTextureAtlasSprite extends TextureAtlasSprite {
   private static final int f_174688_ = 16;
   private static final int f_174689_ = 16;
   private static final String f_174690_ = "missingno";
   private static final ResourceLocation f_118059_ = new ResourceLocation("missingno");
   @Nullable
   private static DynamicTexture f_118060_;
   private static final LazyLoadedValue<NativeImage> f_118061_ = new LazyLoadedValue<>(() -> {
      NativeImage nativeimage = new NativeImage(16, 16, false);
      int i = -16777216;
      int j = -524040;

      for(int k = 0; k < 16; ++k) {
         for(int l = 0; l < 16; ++l) {
            if (k < 8 ^ l < 8) {
               nativeimage.m_84988_(l, k, -524040);
            } else {
               nativeimage.m_84988_(l, k, -16777216);
            }
         }
      }

      nativeimage.m_85123_();
      return nativeimage;
   });
   private static final TextureAtlasSprite.Info f_118062_ = new TextureAtlasSprite.Info(f_118059_, 16, 16, new AnimationMetadataSection(ImmutableList.of(new AnimationFrame(0, -1)), 16, 16, 1, false));

   private MissingTextureAtlasSprite(TextureAtlas p_118065_, int p_118066_, int p_118067_, int p_118068_, int p_118069_, int p_118070_) {
      super(p_118065_, f_118062_, p_118066_, p_118067_, p_118068_, p_118069_, p_118070_, f_118061_.m_13971_());
   }

   public static MissingTextureAtlasSprite m_118072_(TextureAtlas p_118073_, int p_118074_, int p_118075_, int p_118076_, int p_118077_, int p_118078_) {
      return new MissingTextureAtlasSprite(p_118073_, p_118074_, p_118075_, p_118076_, p_118077_, p_118078_);
   }

   public static ResourceLocation m_118071_() {
      return f_118059_;
   }

   public static TextureAtlasSprite.Info m_118079_() {
      return f_118062_;
   }

   public void close() {
      for(int i = 1; i < this.f_118342_.length; ++i) {
         this.f_118342_[i].close();
      }

   }

   public static DynamicTexture m_118080_() {
      if (f_118060_ == null) {
         f_118060_ = new DynamicTexture(f_118061_.m_13971_());
         Minecraft.m_91087_().m_91097_().m_118495_(f_118059_, f_118060_);
      }

      return f_118060_;
   }
}