package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DrownedRenderer extends AbstractZombieRenderer<Drowned, DrownedModel<Drowned>> {
   private static final ResourceLocation f_114098_ = new ResourceLocation("textures/entity/zombie/drowned.png");

   public DrownedRenderer(EntityRendererProvider.Context p_173964_) {
      super(p_173964_, new DrownedModel<>(p_173964_.m_174023_(ModelLayers.f_171136_)), new DrownedModel<>(p_173964_.m_174023_(ModelLayers.f_171137_)), new DrownedModel<>(p_173964_.m_174023_(ModelLayers.f_171138_)));
      this.m_115326_(new DrownedOuterLayer<>(this, p_173964_.m_174027_()));
   }

   public ResourceLocation m_5478_(Zombie p_114115_) {
      return f_114098_;
   }

   protected void m_7523_(Drowned p_114109_, PoseStack p_114110_, float p_114111_, float p_114112_, float p_114113_) {
      super.m_7523_(p_114109_, p_114110_, p_114111_, p_114112_, p_114113_);
      float f = p_114109_.m_20998_(p_114113_);
      if (f > 0.0F) {
         p_114110_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14179_(f, p_114109_.m_146909_(), -10.0F - p_114109_.m_146909_())));
      }

   }
}