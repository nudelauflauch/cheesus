package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Deadmau5EarsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   public Deadmau5EarsLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> p_116860_) {
      super(p_116860_);
   }

   public void m_6494_(PoseStack p_116873_, MultiBufferSource p_116874_, int p_116875_, AbstractClientPlayer p_116876_, float p_116877_, float p_116878_, float p_116879_, float p_116880_, float p_116881_, float p_116882_) {
      if ("deadmau5".equals(p_116876_.m_7755_().getString()) && p_116876_.m_108559_() && !p_116876_.m_20145_()) {
         VertexConsumer vertexconsumer = p_116874_.m_6299_(RenderType.m_110446_(p_116876_.m_108560_()));
         int i = LivingEntityRenderer.m_115338_(p_116876_, 0.0F);

         for(int j = 0; j < 2; ++j) {
            float f = Mth.m_14179_(p_116879_, p_116876_.f_19859_, p_116876_.m_146908_()) - Mth.m_14179_(p_116879_, p_116876_.f_20884_, p_116876_.f_20883_);
            float f1 = Mth.m_14179_(p_116879_, p_116876_.f_19860_, p_116876_.m_146909_());
            p_116873_.m_85836_();
            p_116873_.m_85845_(Vector3f.f_122225_.m_122240_(f));
            p_116873_.m_85845_(Vector3f.f_122223_.m_122240_(f1));
            p_116873_.m_85837_((double)(0.375F * (float)(j * 2 - 1)), 0.0D, 0.0D);
            p_116873_.m_85837_(0.0D, -0.375D, 0.0D);
            p_116873_.m_85845_(Vector3f.f_122223_.m_122240_(-f1));
            p_116873_.m_85845_(Vector3f.f_122225_.m_122240_(-f));
            float f2 = 1.3333334F;
            p_116873_.m_85841_(1.3333334F, 1.3333334F, 1.3333334F);
            this.m_117386_().m_103401_(p_116873_, vertexconsumer, p_116875_, i);
            p_116873_.m_85849_();
         }

      }
   }
}