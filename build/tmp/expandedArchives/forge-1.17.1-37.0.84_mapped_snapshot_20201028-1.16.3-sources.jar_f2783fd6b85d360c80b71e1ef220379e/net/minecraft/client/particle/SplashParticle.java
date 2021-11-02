package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SplashParticle extends WaterDropParticle {
   SplashParticle(ClientLevel p_107929_, double p_107930_, double p_107931_, double p_107932_, double p_107933_, double p_107934_, double p_107935_) {
      super(p_107929_, p_107930_, p_107931_, p_107932_);
      this.f_107226_ = 0.04F;
      if (p_107934_ == 0.0D && (p_107933_ != 0.0D || p_107935_ != 0.0D)) {
         this.f_107215_ = p_107933_;
         this.f_107216_ = 0.1D;
         this.f_107217_ = p_107935_;
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_107945_;

      public Provider(SpriteSet p_107947_) {
         this.f_107945_ = p_107947_;
      }

      public Particle m_6966_(SimpleParticleType p_107958_, ClientLevel p_107959_, double p_107960_, double p_107961_, double p_107962_, double p_107963_, double p_107964_, double p_107965_) {
         SplashParticle splashparticle = new SplashParticle(p_107959_, p_107960_, p_107961_, p_107962_, p_107963_, p_107964_, p_107965_);
         splashparticle.m_108335_(this.f_107945_);
         return splashparticle;
      }
   }
}