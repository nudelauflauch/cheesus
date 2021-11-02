package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BreakingItemParticle extends TextureSheetParticle {
   private final float f_105643_;
   private final float f_105644_;

   BreakingItemParticle(ClientLevel p_105646_, double p_105647_, double p_105648_, double p_105649_, double p_105650_, double p_105651_, double p_105652_, ItemStack p_105653_) {
      this(p_105646_, p_105647_, p_105648_, p_105649_, p_105653_);
      this.f_107215_ *= (double)0.1F;
      this.f_107216_ *= (double)0.1F;
      this.f_107217_ *= (double)0.1F;
      this.f_107215_ += p_105650_;
      this.f_107216_ += p_105651_;
      this.f_107217_ += p_105652_;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107429_;
   }

   protected BreakingItemParticle(ClientLevel p_105665_, double p_105666_, double p_105667_, double p_105668_, ItemStack p_105669_) {
      super(p_105665_, p_105666_, p_105667_, p_105668_, 0.0D, 0.0D, 0.0D);
      this.m_108337_(Minecraft.m_91087_().m_91291_().m_174264_(p_105669_, p_105665_, (LivingEntity)null, 0).m_6160_());
      this.f_107226_ = 1.0F;
      this.f_107663_ /= 2.0F;
      this.f_105643_ = this.f_107223_.nextFloat() * 3.0F;
      this.f_105644_ = this.f_107223_.nextFloat() * 3.0F;
   }

   protected float m_5970_() {
      return this.f_108321_.m_118367_((double)((this.f_105643_ + 1.0F) / 4.0F * 16.0F));
   }

   protected float m_5952_() {
      return this.f_108321_.m_118367_((double)(this.f_105643_ / 4.0F * 16.0F));
   }

   protected float m_5951_() {
      return this.f_108321_.m_118393_((double)(this.f_105644_ / 4.0F * 16.0F));
   }

   protected float m_5950_() {
      return this.f_108321_.m_118393_((double)((this.f_105644_ + 1.0F) / 4.0F * 16.0F));
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<ItemParticleOption> {
      public Particle m_6966_(ItemParticleOption p_105677_, ClientLevel p_105678_, double p_105679_, double p_105680_, double p_105681_, double p_105682_, double p_105683_, double p_105684_) {
         return new BreakingItemParticle(p_105678_, p_105679_, p_105680_, p_105681_, p_105682_, p_105683_, p_105684_, p_105677_.m_123718_());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SlimeProvider implements ParticleProvider<SimpleParticleType> {
      public Particle m_6966_(SimpleParticleType p_105705_, ClientLevel p_105706_, double p_105707_, double p_105708_, double p_105709_, double p_105710_, double p_105711_, double p_105712_) {
         return new BreakingItemParticle(p_105706_, p_105707_, p_105708_, p_105709_, new ItemStack(Items.f_42518_));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SnowballProvider implements ParticleProvider<SimpleParticleType> {
      public Particle m_6966_(SimpleParticleType p_105724_, ClientLevel p_105725_, double p_105726_, double p_105727_, double p_105728_, double p_105729_, double p_105730_, double p_105731_) {
         return new BreakingItemParticle(p_105725_, p_105726_, p_105727_, p_105728_, new ItemStack(Items.f_42452_));
      }
   }
}