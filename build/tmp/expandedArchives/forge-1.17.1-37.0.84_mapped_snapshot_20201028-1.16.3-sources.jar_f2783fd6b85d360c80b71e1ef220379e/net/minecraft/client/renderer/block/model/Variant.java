package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Transformation;
import java.lang.reflect.Type;
import java.util.Objects;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Variant implements ModelState {
   private final ResourceLocation f_111874_;
   private final Transformation f_111875_;
   private final boolean f_111876_;
   private final int f_111877_;

   public Variant(ResourceLocation p_111879_, Transformation p_111880_, boolean p_111881_, int p_111882_) {
      this.f_111874_ = p_111879_;
      this.f_111875_ = p_111880_;
      this.f_111876_ = p_111881_;
      this.f_111877_ = p_111882_;
   }

   public ResourceLocation m_111883_() {
      return this.f_111874_;
   }

   public Transformation m_6189_() {
      return this.f_111875_;
   }

   public boolean m_7538_() {
      return this.f_111876_;
   }

   public int m_111886_() {
      return this.f_111877_;
   }

   public String toString() {
      return "Variant{modelLocation=" + this.f_111874_ + ", rotation=" + this.f_111875_ + ", uvLock=" + this.f_111876_ + ", weight=" + this.f_111877_ + "}";
   }

   public boolean equals(Object p_111888_) {
      if (this == p_111888_) {
         return true;
      } else if (!(p_111888_ instanceof Variant)) {
         return false;
      } else {
         Variant variant = (Variant)p_111888_;
         return this.f_111874_.equals(variant.f_111874_) && Objects.equals(this.f_111875_, variant.f_111875_) && this.f_111876_ == variant.f_111876_ && this.f_111877_ == variant.f_111877_;
      }
   }

   public int hashCode() {
      int i = this.f_111874_.hashCode();
      i = 31 * i + this.f_111875_.hashCode();
      i = 31 * i + Boolean.valueOf(this.f_111876_).hashCode();
      return 31 * i + this.f_111877_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<Variant> {
      @VisibleForTesting
      static final boolean f_173495_ = false;
      @VisibleForTesting
      static final int f_173496_ = 1;
      @VisibleForTesting
      static final int f_173497_ = 0;
      @VisibleForTesting
      static final int f_173498_ = 0;

      public Variant deserialize(JsonElement p_111893_, Type p_111894_, JsonDeserializationContext p_111895_) throws JsonParseException {
         JsonObject jsonobject = p_111893_.getAsJsonObject();
         ResourceLocation resourcelocation = this.m_111898_(jsonobject);
         BlockModelRotation blockmodelrotation = this.m_111896_(jsonobject);
         boolean flag = this.m_111902_(jsonobject);
         int i = this.m_111900_(jsonobject);
         return new Variant(resourcelocation, blockmodelrotation.m_6189_(), flag, i);
      }

      private boolean m_111902_(JsonObject p_111903_) {
         return GsonHelper.m_13855_(p_111903_, "uvlock", false);
      }

      protected BlockModelRotation m_111896_(JsonObject p_111897_) {
         int i = GsonHelper.m_13824_(p_111897_, "x", 0);
         int j = GsonHelper.m_13824_(p_111897_, "y", 0);
         BlockModelRotation blockmodelrotation = BlockModelRotation.m_119153_(i, j);
         if (blockmodelrotation == null) {
            throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j);
         } else {
            return blockmodelrotation;
         }
      }

      protected ResourceLocation m_111898_(JsonObject p_111899_) {
         return new ResourceLocation(GsonHelper.m_13906_(p_111899_, "model"));
      }

      protected int m_111900_(JsonObject p_111901_) {
         int i = GsonHelper.m_13824_(p_111901_, "weight", 1);
         if (i < 1) {
            throw new JsonParseException("Invalid weight " + i + " found, expected integer >= 1");
         } else {
            return i;
         }
      }
   }
}