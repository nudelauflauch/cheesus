package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WaterCurrentDownParticle extends TextureSheetParticle {
   private float f_108448_;

   WaterCurrentDownParticle(ClientLevel p_108450_, double p_108451_, double p_108452_, double p_108453_) {
      super(p_108450_, p_108451_, p_108452_, p_108453_);
      this.f_107225_ = (int)(Math.random() * 60.0D) + 30;
      this.f_107219_ = false;
      this.f_107215_ = 0.0D;
      this.f_107216_ = -0.05D;
      this.f_107217_ = 0.0D;
      this.m_107250_(0.02F, 0.02F);
      this.f_107663_ *= this.f_107223_.nextFloat() * 0.6F + 0.2F;
      this.f_107226_ = 0.002F;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         float f = 0.6F;
         this.f_107215_ += (double)(0.6F * Mth.m_14089_(this.f_108448_));
         this.f_107217_ += (double)(0.6F * Mth.m_14031_(this.f_108448_));
         this.f_107215_ *= 0.07D;
         this.f_107217_ *= 0.07D;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         if (!this.f_107208_.m_6425_(new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_)).m_76153_(FluidTags.f_13131_) || this.f_107218_) {
            this.m_107274_();
         }

         this.f_108448_ = (float)((double)this.f_108448_ + 0.08D);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108462_;

      public Provider(SpriteSet p_108464_) {
         this.f_108462_ = p_108464_;
      }

      public Particle m_6966_(SimpleParticleType p_108475_, ClientLevel p_108476_, double p_108477_, double p_108478_, double p_108479_, double p_108480_, double p_108481_, double p_108482_) {
         WaterCurrentDownParticle watercurrentdownparticle = new WaterCurrentDownParticle(p_108476_, p_108477_, p_108478_, p_108479_);
         watercurrentdownparticle.m_108335_(this.f_108462_);
         return watercurrentdownparticle;
      }
   }
}