package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public abstract class HeightProvider {
   private static final Codec<Either<VerticalAnchor, HeightProvider>> f_161969_ = Codec.either(VerticalAnchor.f_158914_, Registry.f_175419_.dispatch(HeightProvider::m_142002_, HeightProviderType::m_161992_));
   public static final Codec<HeightProvider> f_161970_ = f_161969_.xmap((p_161974_) -> {
      return p_161974_.map(ConstantHeight::m_161956_, (p_161980_) -> {
         return p_161980_;
      });
   }, (p_161976_) -> {
      return p_161976_.m_142002_() == HeightProviderType.f_161981_ ? Either.left(((ConstantHeight)p_161976_).m_161963_()) : Either.right(p_161976_);
   });

   public abstract int m_142233_(Random p_161977_, WorldGenerationContext p_161978_);

   public abstract HeightProviderType<?> m_142002_();
}