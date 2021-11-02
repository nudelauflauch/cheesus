package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnderwaterAmbientSoundHandler implements AmbientSoundHandler {
   public static final float f_174957_ = 0.01F;
   public static final float f_174958_ = 0.001F;
   public static final float f_174959_ = 1.0E-4F;
   private static final int f_174960_ = 0;
   private final LocalPlayer f_119852_;
   private final SoundManager f_119853_;
   private int f_119854_ = 0;

   public UnderwaterAmbientSoundHandler(LocalPlayer p_119856_, SoundManager p_119857_) {
      this.f_119852_ = p_119856_;
      this.f_119853_ = p_119857_;
   }

   public void m_7551_() {
      --this.f_119854_;
      if (this.f_119854_ <= 0 && this.f_119852_.m_5842_()) {
         float f = this.f_119852_.f_19853_.f_46441_.nextFloat();
         if (f < 1.0E-4F) {
            this.f_119854_ = 0;
            this.f_119853_.m_120367_(new UnderwaterAmbientSoundInstances.SubSound(this.f_119852_, SoundEvents.f_12650_));
         } else if (f < 0.001F) {
            this.f_119854_ = 0;
            this.f_119853_.m_120367_(new UnderwaterAmbientSoundInstances.SubSound(this.f_119852_, SoundEvents.f_12649_));
         } else if (f < 0.01F) {
            this.f_119854_ = 0;
            this.f_119853_.m_120367_(new UnderwaterAmbientSoundInstances.SubSound(this.f_119852_, SoundEvents.f_12648_));
         }
      }

   }
}