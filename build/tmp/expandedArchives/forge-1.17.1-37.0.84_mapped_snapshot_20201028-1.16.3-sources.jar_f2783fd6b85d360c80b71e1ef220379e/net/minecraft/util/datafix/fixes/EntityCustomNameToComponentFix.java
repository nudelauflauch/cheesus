package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class EntityCustomNameToComponentFix extends DataFix {
   public EntityCustomNameToComponentFix(Schema p_15398_, boolean p_15399_) {
      super(p_15398_, p_15399_);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<String> opticfinder = DSL.fieldFinder("id", NamespacedSchema.m_17310_());
      return this.fixTypeEverywhereTyped("EntityCustomNameToComponentFix", this.getInputSchema().getType(References.f_16786_), (p_15402_) -> {
         return p_15402_.update(DSL.remainderFinder(), (p_145277_) -> {
            Optional<String> optional = p_15402_.getOptional(opticfinder);
            return optional.isPresent() && Objects.equals(optional.get(), "minecraft:commandblock_minecart") ? p_145277_ : m_15407_(p_145277_);
         });
      });
   }

   public static Dynamic<?> m_15407_(Dynamic<?> p_15408_) {
      String s = p_15408_.get("CustomName").asString("");
      return s.isEmpty() ? p_15408_.remove("CustomName") : p_15408_.set("CustomName", p_15408_.createString(Component.Serializer.m_130703_(new TextComponent(s))));
   }
}