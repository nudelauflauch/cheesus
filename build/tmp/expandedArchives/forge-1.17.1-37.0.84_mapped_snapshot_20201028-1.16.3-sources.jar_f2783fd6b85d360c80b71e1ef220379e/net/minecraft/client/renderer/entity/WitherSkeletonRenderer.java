package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitherSkeletonRenderer extends SkeletonRenderer {
   private static final ResourceLocation f_116445_ = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public WitherSkeletonRenderer(EntityRendererProvider.Context p_174447_) {
      super(p_174447_, ModelLayers.f_171216_, ModelLayers.f_171217_, ModelLayers.f_171218_);
   }

   public ResourceLocation m_5478_(AbstractSkeleton p_116458_) {
      return f_116445_;
   }

   protected void m_7546_(AbstractSkeleton p_116460_, PoseStack p_116461_, float p_116462_) {
      p_116461_.m_85841_(1.2F, 1.2F, 1.2F);
   }
}