package net.minecraft.world.level.storage.loot.providers.number;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.Serializer;

public class NumberProviders {
   public static final LootNumberProviderType f_165731_ = m_165738_("constant", new ConstantValue.Serializer());
   public static final LootNumberProviderType f_165732_ = m_165738_("uniform", new UniformGenerator.Serializer());
   public static final LootNumberProviderType f_165733_ = m_165738_("binomial", new BinomialDistributionGenerator.Serializer());
   public static final LootNumberProviderType f_165734_ = m_165738_("score", new ScoreboardValue.Serializer());

   private static LootNumberProviderType m_165738_(String p_165739_, Serializer<? extends NumberProvider> p_165740_) {
      return Registry.m_122965_(Registry.f_175421_, new ResourceLocation(p_165739_), new LootNumberProviderType(p_165740_));
   }

   public static Object m_165737_() {
      return GsonAdapterFactory.m_78801_(Registry.f_175421_, "provider", "type", NumberProvider::m_142587_).m_164986_(f_165731_, new ConstantValue.InlineSerializer()).m_164984_(f_165732_).m_78822_();
   }
}