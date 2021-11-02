package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;

public class EntityCodSalmonFix extends SimplestEntityRenameFix {
   public static final Map<String, String> f_15389_ = ImmutableMap.<String, String>builder().put("minecraft:salmon_mob", "minecraft:salmon").put("minecraft:cod_mob", "minecraft:cod").build();
   public static final Map<String, String> f_15390_ = ImmutableMap.<String, String>builder().put("minecraft:salmon_mob_spawn_egg", "minecraft:salmon_spawn_egg").put("minecraft:cod_mob_spawn_egg", "minecraft:cod_spawn_egg").build();

   public EntityCodSalmonFix(Schema p_15393_, boolean p_15394_) {
      super("EntityCodSalmonFix", p_15393_, p_15394_);
   }

   protected String m_7476_(String p_15396_) {
      return f_15389_.getOrDefault(p_15396_, p_15396_);
   }
}