package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CowRenderer extends MobRenderer<Cow, CowModel<Cow>> {
   private static final ResourceLocation f_114022_ = new ResourceLocation("textures/entity/cow/cow.png");

   public CowRenderer(EntityRendererProvider.Context p_173956_) {
      super(p_173956_, new CowModel<>(p_173956_.m_174023_(ModelLayers.f_171284_)), 0.7F);
   }

   public ResourceLocation m_5478_(Cow p_114029_) {
      return f_114022_;
   }
}