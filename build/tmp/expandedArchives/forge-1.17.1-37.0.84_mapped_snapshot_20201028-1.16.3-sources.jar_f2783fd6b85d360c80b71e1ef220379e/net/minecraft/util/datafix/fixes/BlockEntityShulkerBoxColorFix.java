package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

public class BlockEntityShulkerBoxColorFix extends NamedEntityFix {
   public BlockEntityShulkerBoxColorFix(Schema p_14855_, boolean p_14856_) {
      super(p_14855_, p_14856_, "BlockEntityShulkerBoxColorFix", References.f_16781_, "minecraft:shulker_box");
   }

   protected Typed<?> m_7504_(Typed<?> p_14858_) {
      return p_14858_.update(DSL.remainderFinder(), (p_14860_) -> {
         return p_14860_.remove("Color");
      });
   }
}