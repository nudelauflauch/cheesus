package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class AmbientMoodSettings {
   public static final Codec<AmbientMoodSettings> f_47386_ = RecordCodecBuilder.create((p_47402_) -> {
      return p_47402_.group(SoundEvent.f_11655_.fieldOf("sound").forGetter((p_151650_) -> {
         return p_151650_.f_47388_;
      }), Codec.INT.fieldOf("tick_delay").forGetter((p_151648_) -> {
         return p_151648_.f_47389_;
      }), Codec.INT.fieldOf("block_search_extent").forGetter((p_151646_) -> {
         return p_151646_.f_47390_;
      }), Codec.DOUBLE.fieldOf("offset").forGetter((p_151644_) -> {
         return p_151644_.f_47391_;
      })).apply(p_47402_, AmbientMoodSettings::new);
   });
   public static final AmbientMoodSettings f_47387_ = new AmbientMoodSettings(SoundEvents.f_11689_, 6000, 8, 2.0D);
   private final SoundEvent f_47388_;
   private final int f_47389_;
   private final int f_47390_;
   private final double f_47391_;

   public AmbientMoodSettings(SoundEvent p_47394_, int p_47395_, int p_47396_, double p_47397_) {
      this.f_47388_ = p_47394_;
      this.f_47389_ = p_47395_;
      this.f_47390_ = p_47396_;
      this.f_47391_ = p_47397_;
   }

   public SoundEvent m_47398_() {
      return this.f_47388_;
   }

   public int m_47403_() {
      return this.f_47389_;
   }

   public int m_47406_() {
      return this.f_47390_;
   }

   public double m_47409_() {
      return this.f_47391_;
   }
}