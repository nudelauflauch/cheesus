package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BatRenderer extends MobRenderer<Bat, BatModel> {
   private static final ResourceLocation f_113859_ = new ResourceLocation("textures/entity/bat.png");

   public BatRenderer(EntityRendererProvider.Context p_173929_) {
      super(p_173929_, new BatModel(p_173929_.m_174023_(ModelLayers.f_171265_)), 0.25F);
   }

   public ResourceLocation m_5478_(Bat p_113876_) {
      return f_113859_;
   }

   protected void m_7546_(Bat p_113878_, PoseStack p_113879_, float p_113880_) {
      p_113879_.m_85841_(0.35F, 0.35F, 0.35F);
   }

   protected void m_7523_(Bat p_113882_, PoseStack p_113883_, float p_113884_, float p_113885_, float p_113886_) {
      if (p_113882_.m_27452_()) {
         p_113883_.m_85837_(0.0D, (double)-0.1F, 0.0D);
      } else {
         p_113883_.m_85837_(0.0D, (double)(Mth.m_14089_(p_113884_ * 0.3F) * 0.1F), 0.0D);
      }

      super.m_7523_(p_113882_, p_113883_, p_113884_, p_113885_, p_113886_);
   }
}