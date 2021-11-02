package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonRenderer extends HumanoidMobRenderer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>> {
   private static final ResourceLocation f_115932_ = new ResourceLocation("textures/entity/skeleton/skeleton.png");

   public SkeletonRenderer(EntityRendererProvider.Context p_174380_) {
      this(p_174380_, ModelLayers.f_171236_, ModelLayers.f_171238_, ModelLayers.f_171239_);
   }

   public SkeletonRenderer(EntityRendererProvider.Context p_174382_, ModelLayerLocation p_174383_, ModelLayerLocation p_174384_, ModelLayerLocation p_174385_) {
      super(p_174382_, new SkeletonModel<>(p_174382_.m_174023_(p_174383_)), 0.5F);
      this.m_115326_(new HumanoidArmorLayer<>(this, new SkeletonModel(p_174382_.m_174023_(p_174384_)), new SkeletonModel(p_174382_.m_174023_(p_174385_))));
   }

   public ResourceLocation m_5478_(AbstractSkeleton p_115941_) {
      return f_115932_;
   }

   protected boolean m_5936_(AbstractSkeleton p_174389_) {
      return p_174389_.m_142548_();
   }
}