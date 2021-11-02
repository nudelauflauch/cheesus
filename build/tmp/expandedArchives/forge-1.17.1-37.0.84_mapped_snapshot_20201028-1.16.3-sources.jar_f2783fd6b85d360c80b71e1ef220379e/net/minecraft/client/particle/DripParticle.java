package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DripParticle extends TextureSheetParticle {
   private final Fluid f_106049_;
   protected boolean f_106048_;

   DripParticle(ClientLevel p_106051_, double p_106052_, double p_106053_, double p_106054_, Fluid p_106055_) {
      super(p_106051_, p_106052_, p_106053_, p_106054_);
      this.m_107250_(0.01F, 0.01F);
      this.f_107226_ = 0.06F;
      this.f_106049_ = p_106055_;
   }

   protected Fluid m_171928_() {
      return this.f_106049_;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public int m_6355_(float p_106065_) {
      return this.f_106048_ ? 240 : super.m_6355_(p_106065_);
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      this.m_5956_();
      if (!this.f_107220_) {
         this.f_107216_ -= (double)this.f_107226_;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.m_5949_();
         if (!this.f_107220_) {
            this.f_107215_ *= (double)0.98F;
            this.f_107216_ *= (double)0.98F;
            this.f_107217_ *= (double)0.98F;
            BlockPos blockpos = new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_);
            FluidState fluidstate = this.f_107208_.m_6425_(blockpos);
            if (fluidstate.m_76152_() == this.f_106049_ && this.f_107213_ < (double)((float)blockpos.m_123342_() + fluidstate.m_76155_(this.f_107208_, blockpos))) {
               this.m_107274_();
            }

         }
      }
   }

   protected void m_5956_() {
      if (this.f_107225_-- <= 0) {
         this.m_107274_();
      }

   }

   protected void m_5949_() {
   }

   @OnlyIn(Dist.CLIENT)
   static class CoolingDripHangParticle extends DripParticle.DripHangParticle {
      CoolingDripHangParticle(ClientLevel p_106068_, double p_106069_, double p_106070_, double p_106071_, Fluid p_106072_, ParticleOptions p_106073_) {
         super(p_106068_, p_106069_, p_106070_, p_106071_, p_106072_, p_106073_);
      }

      protected void m_5956_() {
         this.f_107227_ = 1.0F;
         this.f_107228_ = 16.0F / (float)(40 - this.f_107225_ + 16);
         this.f_107229_ = 4.0F / (float)(40 - this.f_107225_ + 8);
         super.m_5956_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class DripHangParticle extends DripParticle {
      private final ParticleOptions f_106083_;

      DripHangParticle(ClientLevel p_106085_, double p_106086_, double p_106087_, double p_106088_, Fluid p_106089_, ParticleOptions p_106090_) {
         super(p_106085_, p_106086_, p_106087_, p_106088_, p_106089_);
         this.f_106083_ = p_106090_;
         this.f_107226_ *= 0.02F;
         this.f_107225_ = 40;
      }

      protected void m_5956_() {
         if (this.f_107225_-- <= 0) {
            this.m_107274_();
            this.f_107208_.m_7106_(this.f_106083_, this.f_107212_, this.f_107213_, this.f_107214_, this.f_107215_, this.f_107216_, this.f_107217_);
         }

      }

      protected void m_5949_() {
         this.f_107215_ *= 0.02D;
         this.f_107216_ *= 0.02D;
         this.f_107217_ *= 0.02D;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class DripLandParticle extends DripParticle {
      DripLandParticle(ClientLevel p_106102_, double p_106103_, double p_106104_, double p_106105_, Fluid p_106106_) {
         super(p_106102_, p_106103_, p_106104_, p_106105_, p_106106_);
         this.f_107225_ = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class DripstoneFallAndLandParticle extends DripParticle.FallAndLandParticle {
      DripstoneFallAndLandParticle(ClientLevel p_171930_, double p_171931_, double p_171932_, double p_171933_, Fluid p_171934_, ParticleOptions p_171935_) {
         super(p_171930_, p_171931_, p_171932_, p_171933_, p_171934_, p_171935_);
      }

      protected void m_5949_() {
         if (this.f_107218_) {
            this.m_107274_();
            this.f_107208_.m_7106_(this.f_106114_, this.f_107212_, this.f_107213_, this.f_107214_, 0.0D, 0.0D, 0.0D);
            SoundEvent soundevent = this.m_171928_() == Fluids.f_76195_ ? SoundEvents.f_144127_ : SoundEvents.f_144128_;
            float f = Mth.m_144924_(this.f_107223_, 0.3F, 1.0F);
            this.f_107208_.m_7785_(this.f_107212_, this.f_107213_, this.f_107214_, soundevent, SoundSource.BLOCKS, f, 1.0F, false);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DripstoneLavaFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_171937_;

      public DripstoneLavaFallProvider(SpriteSet p_171939_) {
         this.f_171937_ = p_171939_;
      }

      public Particle m_6966_(SimpleParticleType p_171950_, ClientLevel p_171951_, double p_171952_, double p_171953_, double p_171954_, double p_171955_, double p_171956_, double p_171957_) {
         DripParticle dripparticle = new DripParticle.DripstoneFallAndLandParticle(p_171951_, p_171952_, p_171953_, p_171954_, Fluids.f_76195_, ParticleTypes.f_123802_);
         dripparticle.m_107253_(1.0F, 0.2857143F, 0.083333336F);
         dripparticle.m_108335_(this.f_171937_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DripstoneLavaHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_171958_;

      public DripstoneLavaHangProvider(SpriteSet p_171960_) {
         this.f_171958_ = p_171960_;
      }

      public Particle m_6966_(SimpleParticleType p_171971_, ClientLevel p_171972_, double p_171973_, double p_171974_, double p_171975_, double p_171976_, double p_171977_, double p_171978_) {
         DripParticle dripparticle = new DripParticle.CoolingDripHangParticle(p_171972_, p_171973_, p_171974_, p_171975_, Fluids.f_76195_, ParticleTypes.f_175823_);
         dripparticle.m_108335_(this.f_171958_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DripstoneWaterFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_171979_;

      public DripstoneWaterFallProvider(SpriteSet p_171981_) {
         this.f_171979_ = p_171981_;
      }

      public Particle m_6966_(SimpleParticleType p_171992_, ClientLevel p_171993_, double p_171994_, double p_171995_, double p_171996_, double p_171997_, double p_171998_, double p_171999_) {
         DripParticle dripparticle = new DripParticle.DripstoneFallAndLandParticle(p_171993_, p_171994_, p_171995_, p_171996_, Fluids.f_76193_, ParticleTypes.f_123769_);
         dripparticle.m_107253_(0.2F, 0.3F, 1.0F);
         dripparticle.m_108335_(this.f_171979_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DripstoneWaterHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_172000_;

      public DripstoneWaterHangProvider(SpriteSet p_172002_) {
         this.f_172000_ = p_172002_;
      }

      public Particle m_6966_(SimpleParticleType p_172013_, ClientLevel p_172014_, double p_172015_, double p_172016_, double p_172017_, double p_172018_, double p_172019_, double p_172020_) {
         DripParticle dripparticle = new DripParticle.DripHangParticle(p_172014_, p_172015_, p_172016_, p_172017_, Fluids.f_76193_, ParticleTypes.f_175825_);
         dripparticle.m_107253_(0.2F, 0.3F, 1.0F);
         dripparticle.m_108335_(this.f_172000_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallAndLandParticle extends DripParticle.FallingParticle {
      protected final ParticleOptions f_106114_;

      FallAndLandParticle(ClientLevel p_106116_, double p_106117_, double p_106118_, double p_106119_, Fluid p_106120_, ParticleOptions p_106121_) {
         super(p_106116_, p_106117_, p_106118_, p_106119_, p_106120_);
         this.f_106114_ = p_106121_;
      }

      protected void m_5949_() {
         if (this.f_107218_) {
            this.m_107274_();
            this.f_107208_.m_7106_(this.f_106114_, this.f_107212_, this.f_107213_, this.f_107214_, 0.0D, 0.0D, 0.0D);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallingParticle extends DripParticle {
      FallingParticle(ClientLevel p_106132_, double p_106133_, double p_106134_, double p_106135_, Fluid p_106136_) {
         this(p_106132_, p_106133_, p_106134_, p_106135_, p_106136_, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
      }

      FallingParticle(ClientLevel p_172022_, double p_172023_, double p_172024_, double p_172025_, Fluid p_172026_, int p_172027_) {
         super(p_172022_, p_172023_, p_172024_, p_172025_, p_172026_);
         this.f_107225_ = p_172027_;
      }

      protected void m_5949_() {
         if (this.f_107218_) {
            this.m_107274_();
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class HoneyFallAndLandParticle extends DripParticle.FallAndLandParticle {
      HoneyFallAndLandParticle(ClientLevel p_106146_, double p_106147_, double p_106148_, double p_106149_, Fluid p_106150_, ParticleOptions p_106151_) {
         super(p_106146_, p_106147_, p_106148_, p_106149_, p_106150_, p_106151_);
      }

      protected void m_5949_() {
         if (this.f_107218_) {
            this.m_107274_();
            this.f_107208_.m_7106_(this.f_106114_, this.f_107212_, this.f_107213_, this.f_107214_, 0.0D, 0.0D, 0.0D);
            float f = Mth.m_144924_(this.f_107223_, 0.3F, 1.0F);
            this.f_107208_.m_7785_(this.f_107212_, this.f_107213_, this.f_107214_, SoundEvents.f_11694_, SoundSource.BLOCKS, f, 1.0F, false);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class HoneyFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106161_;

      public HoneyFallProvider(SpriteSet p_106163_) {
         this.f_106161_ = p_106163_;
      }

      public Particle m_6966_(SimpleParticleType p_106174_, ClientLevel p_106175_, double p_106176_, double p_106177_, double p_106178_, double p_106179_, double p_106180_, double p_106181_) {
         DripParticle dripparticle = new DripParticle.HoneyFallAndLandParticle(p_106175_, p_106176_, p_106177_, p_106178_, Fluids.f_76191_, ParticleTypes.f_123781_);
         dripparticle.f_107226_ = 0.01F;
         dripparticle.m_107253_(0.582F, 0.448F, 0.082F);
         dripparticle.m_108335_(this.f_106161_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class HoneyHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106182_;

      public HoneyHangProvider(SpriteSet p_106184_) {
         this.f_106182_ = p_106184_;
      }

      public Particle m_6966_(SimpleParticleType p_106195_, ClientLevel p_106196_, double p_106197_, double p_106198_, double p_106199_, double p_106200_, double p_106201_, double p_106202_) {
         DripParticle.DripHangParticle dripparticle$driphangparticle = new DripParticle.DripHangParticle(p_106196_, p_106197_, p_106198_, p_106199_, Fluids.f_76191_, ParticleTypes.f_123780_);
         dripparticle$driphangparticle.f_107226_ *= 0.01F;
         dripparticle$driphangparticle.f_107225_ = 100;
         dripparticle$driphangparticle.m_107253_(0.622F, 0.508F, 0.082F);
         dripparticle$driphangparticle.m_108335_(this.f_106182_);
         return dripparticle$driphangparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class HoneyLandProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106203_;

      public HoneyLandProvider(SpriteSet p_106205_) {
         this.f_106203_ = p_106205_;
      }

      public Particle m_6966_(SimpleParticleType p_106216_, ClientLevel p_106217_, double p_106218_, double p_106219_, double p_106220_, double p_106221_, double p_106222_, double p_106223_) {
         DripParticle dripparticle = new DripParticle.DripLandParticle(p_106217_, p_106218_, p_106219_, p_106220_, Fluids.f_76191_);
         dripparticle.f_107225_ = (int)(128.0D / (Math.random() * 0.8D + 0.2D));
         dripparticle.m_107253_(0.522F, 0.408F, 0.082F);
         dripparticle.m_108335_(this.f_106203_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LavaFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106224_;

      public LavaFallProvider(SpriteSet p_106226_) {
         this.f_106224_ = p_106226_;
      }

      public Particle m_6966_(SimpleParticleType p_106237_, ClientLevel p_106238_, double p_106239_, double p_106240_, double p_106241_, double p_106242_, double p_106243_, double p_106244_) {
         DripParticle dripparticle = new DripParticle.FallAndLandParticle(p_106238_, p_106239_, p_106240_, p_106241_, Fluids.f_76195_, ParticleTypes.f_123802_);
         dripparticle.m_107253_(1.0F, 0.2857143F, 0.083333336F);
         dripparticle.m_108335_(this.f_106224_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LavaHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106245_;

      public LavaHangProvider(SpriteSet p_106247_) {
         this.f_106245_ = p_106247_;
      }

      public Particle m_6966_(SimpleParticleType p_106258_, ClientLevel p_106259_, double p_106260_, double p_106261_, double p_106262_, double p_106263_, double p_106264_, double p_106265_) {
         DripParticle.CoolingDripHangParticle dripparticle$coolingdriphangparticle = new DripParticle.CoolingDripHangParticle(p_106259_, p_106260_, p_106261_, p_106262_, Fluids.f_76195_, ParticleTypes.f_123801_);
         dripparticle$coolingdriphangparticle.m_108335_(this.f_106245_);
         return dripparticle$coolingdriphangparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LavaLandProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106266_;

      public LavaLandProvider(SpriteSet p_106268_) {
         this.f_106266_ = p_106268_;
      }

      public Particle m_6966_(SimpleParticleType p_106279_, ClientLevel p_106280_, double p_106281_, double p_106282_, double p_106283_, double p_106284_, double p_106285_, double p_106286_) {
         DripParticle dripparticle = new DripParticle.DripLandParticle(p_106280_, p_106281_, p_106282_, p_106283_, Fluids.f_76195_);
         dripparticle.m_107253_(1.0F, 0.2857143F, 0.083333336F);
         dripparticle.m_108335_(this.f_106266_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class NectarFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106287_;

      public NectarFallProvider(SpriteSet p_106289_) {
         this.f_106287_ = p_106289_;
      }

      public Particle m_6966_(SimpleParticleType p_106300_, ClientLevel p_106301_, double p_106302_, double p_106303_, double p_106304_, double p_106305_, double p_106306_, double p_106307_) {
         DripParticle dripparticle = new DripParticle.FallingParticle(p_106301_, p_106302_, p_106303_, p_106304_, Fluids.f_76191_);
         dripparticle.f_107225_ = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
         dripparticle.f_107226_ = 0.007F;
         dripparticle.m_107253_(0.92F, 0.782F, 0.72F);
         dripparticle.m_108335_(this.f_106287_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class ObsidianTearFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106308_;

      public ObsidianTearFallProvider(SpriteSet p_106310_) {
         this.f_106308_ = p_106310_;
      }

      public Particle m_6966_(SimpleParticleType p_106321_, ClientLevel p_106322_, double p_106323_, double p_106324_, double p_106325_, double p_106326_, double p_106327_, double p_106328_) {
         DripParticle dripparticle = new DripParticle.FallAndLandParticle(p_106322_, p_106323_, p_106324_, p_106325_, Fluids.f_76191_, ParticleTypes.f_123788_);
         dripparticle.f_106048_ = true;
         dripparticle.f_107226_ = 0.01F;
         dripparticle.m_107253_(0.51171875F, 0.03125F, 0.890625F);
         dripparticle.m_108335_(this.f_106308_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class ObsidianTearHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106329_;

      public ObsidianTearHangProvider(SpriteSet p_106331_) {
         this.f_106329_ = p_106331_;
      }

      public Particle m_6966_(SimpleParticleType p_106342_, ClientLevel p_106343_, double p_106344_, double p_106345_, double p_106346_, double p_106347_, double p_106348_, double p_106349_) {
         DripParticle.DripHangParticle dripparticle$driphangparticle = new DripParticle.DripHangParticle(p_106343_, p_106344_, p_106345_, p_106346_, Fluids.f_76191_, ParticleTypes.f_123787_);
         dripparticle$driphangparticle.f_106048_ = true;
         dripparticle$driphangparticle.f_107226_ *= 0.01F;
         dripparticle$driphangparticle.f_107225_ = 100;
         dripparticle$driphangparticle.m_107253_(0.51171875F, 0.03125F, 0.890625F);
         dripparticle$driphangparticle.m_108335_(this.f_106329_);
         return dripparticle$driphangparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class ObsidianTearLandProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106350_;

      public ObsidianTearLandProvider(SpriteSet p_106352_) {
         this.f_106350_ = p_106352_;
      }

      public Particle m_6966_(SimpleParticleType p_106363_, ClientLevel p_106364_, double p_106365_, double p_106366_, double p_106367_, double p_106368_, double p_106369_, double p_106370_) {
         DripParticle dripparticle = new DripParticle.DripLandParticle(p_106364_, p_106365_, p_106366_, p_106367_, Fluids.f_76191_);
         dripparticle.f_106048_ = true;
         dripparticle.f_107225_ = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
         dripparticle.m_107253_(0.51171875F, 0.03125F, 0.890625F);
         dripparticle.m_108335_(this.f_106350_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SporeBlossomFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_172028_;
      private final Random f_172029_;

      public SporeBlossomFallProvider(SpriteSet p_172031_) {
         this.f_172028_ = p_172031_;
         this.f_172029_ = new Random();
      }

      public Particle m_6966_(SimpleParticleType p_172042_, ClientLevel p_172043_, double p_172044_, double p_172045_, double p_172046_, double p_172047_, double p_172048_, double p_172049_) {
         int i = (int)(64.0F / Mth.m_144924_(this.f_172029_, 0.1F, 0.9F));
         DripParticle dripparticle = new DripParticle.FallingParticle(p_172043_, p_172044_, p_172045_, p_172046_, Fluids.f_76191_, i);
         dripparticle.f_107226_ = 0.005F;
         dripparticle.m_107253_(0.32F, 0.5F, 0.22F);
         dripparticle.m_108335_(this.f_172028_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WaterFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106371_;

      public WaterFallProvider(SpriteSet p_106373_) {
         this.f_106371_ = p_106373_;
      }

      public Particle m_6966_(SimpleParticleType p_106384_, ClientLevel p_106385_, double p_106386_, double p_106387_, double p_106388_, double p_106389_, double p_106390_, double p_106391_) {
         DripParticle dripparticle = new DripParticle.FallAndLandParticle(p_106385_, p_106386_, p_106387_, p_106388_, Fluids.f_76193_, ParticleTypes.f_123769_);
         dripparticle.m_107253_(0.2F, 0.3F, 1.0F);
         dripparticle.m_108335_(this.f_106371_);
         return dripparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WaterHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet f_106392_;

      public WaterHangProvider(SpriteSet p_106394_) {
         this.f_106392_ = p_106394_;
      }

      public Particle m_6966_(SimpleParticleType p_106405_, ClientLevel p_106406_, double p_106407_, double p_106408_, double p_106409_, double p_106410_, double p_106411_, double p_106412_) {
         DripParticle dripparticle = new DripParticle.DripHangParticle(p_106406_, p_106407_, p_106408_, p_106409_, Fluids.f_76193_, ParticleTypes.f_123804_);
         dripparticle.m_107253_(0.2F, 0.3F, 1.0F);
         dripparticle.m_108335_(this.f_106392_);
         return dripparticle;
      }
   }
}