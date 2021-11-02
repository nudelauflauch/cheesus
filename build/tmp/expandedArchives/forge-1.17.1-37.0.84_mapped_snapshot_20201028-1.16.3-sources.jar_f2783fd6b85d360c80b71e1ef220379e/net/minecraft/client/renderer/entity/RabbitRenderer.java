package net.minecraft.client.renderer.entity;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RabbitRenderer extends MobRenderer<Rabbit, RabbitModel<Rabbit>> {
   private static final ResourceLocation f_115789_ = new ResourceLocation("textures/entity/rabbit/brown.png");
   private static final ResourceLocation f_115790_ = new ResourceLocation("textures/entity/rabbit/white.png");
   private static final ResourceLocation f_115791_ = new ResourceLocation("textures/entity/rabbit/black.png");
   private static final ResourceLocation f_115792_ = new ResourceLocation("textures/entity/rabbit/gold.png");
   private static final ResourceLocation f_115793_ = new ResourceLocation("textures/entity/rabbit/salt.png");
   private static final ResourceLocation f_115794_ = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
   private static final ResourceLocation f_115795_ = new ResourceLocation("textures/entity/rabbit/toast.png");
   private static final ResourceLocation f_115796_ = new ResourceLocation("textures/entity/rabbit/caerbannog.png");

   public RabbitRenderer(EntityRendererProvider.Context p_174360_) {
      super(p_174360_, new RabbitModel<>(p_174360_.m_174023_(ModelLayers.f_171174_)), 0.3F);
   }

   public ResourceLocation m_5478_(Rabbit p_115803_) {
      String s = ChatFormatting.m_126649_(p_115803_.m_7755_().getString());
      if (s != null && "Toast".equals(s)) {
         return f_115795_;
      } else {
         switch(p_115803_.m_29719_()) {
         case 0:
         default:
            return f_115789_;
         case 1:
            return f_115790_;
         case 2:
            return f_115791_;
         case 3:
            return f_115794_;
         case 4:
            return f_115792_;
         case 5:
            return f_115793_;
         case 99:
            return f_115796_;
         }
      }
   }
}