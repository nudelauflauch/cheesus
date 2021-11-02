package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreeperPowerLayer extends EnergySwirlLayer<Creeper, CreeperModel<Creeper>> {
   private static final ResourceLocation f_116676_ = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
   private final CreeperModel<Creeper> f_116677_;

   public CreeperPowerLayer(RenderLayerParent<Creeper, CreeperModel<Creeper>> p_174471_, EntityModelSet p_174472_) {
      super(p_174471_);
      this.f_116677_ = new CreeperModel<>(p_174472_.m_171103_(ModelLayers.f_171129_));
   }

   protected float m_7631_(float p_116683_) {
      return p_116683_ * 0.01F;
   }

   protected ResourceLocation m_7029_() {
      return f_116676_;
   }

   protected EntityModel<Creeper> m_7193_() {
      return this.f_116677_;
   }
}