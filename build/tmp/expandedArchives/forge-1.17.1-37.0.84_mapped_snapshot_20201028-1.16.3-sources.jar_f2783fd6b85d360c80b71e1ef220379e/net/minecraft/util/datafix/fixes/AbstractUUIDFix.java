package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractUUIDFix extends DataFix {
   protected static final Logger f_14568_ = LogManager.getLogger();
   protected TypeReference f_14569_;

   public AbstractUUIDFix(Schema p_14572_, TypeReference p_14573_) {
      super(p_14572_, false);
      this.f_14569_ = p_14573_;
   }

   protected Typed<?> m_14574_(Typed<?> p_14575_, String p_14576_, Function<Dynamic<?>, Dynamic<?>> p_14577_) {
      Type<?> type = this.getInputSchema().getChoiceType(this.f_14569_, p_14576_);
      Type<?> type1 = this.getOutputSchema().getChoiceType(this.f_14569_, p_14576_);
      return p_14575_.updateTyped(DSL.namedChoice(p_14576_, type), type1, (p_14607_) -> {
         return p_14607_.update(DSL.remainderFinder(), p_14577_);
      });
   }

   protected static Optional<Dynamic<?>> m_14590_(Dynamic<?> p_14591_, String p_14592_, String p_14593_) {
      return m_14587_(p_14591_, p_14592_).map((p_14616_) -> {
         return p_14591_.remove(p_14592_).set(p_14593_, p_14616_);
      });
   }

   protected static Optional<Dynamic<?>> m_14608_(Dynamic<?> p_14609_, String p_14610_, String p_14611_) {
      return p_14609_.get(p_14610_).result().flatMap(AbstractUUIDFix::m_14578_).map((p_14598_) -> {
         return p_14609_.remove(p_14610_).set(p_14611_, p_14598_);
      });
   }

   protected static Optional<Dynamic<?>> m_14617_(Dynamic<?> p_14618_, String p_14619_, String p_14620_) {
      String s = p_14619_ + "Most";
      String s1 = p_14619_ + "Least";
      return m_14621_(p_14618_, s, s1).map((p_14604_) -> {
         return p_14618_.remove(s).remove(s1).set(p_14620_, p_14604_);
      });
   }

   protected static Optional<Dynamic<?>> m_14587_(Dynamic<?> p_14588_, String p_14589_) {
      return p_14588_.get(p_14589_).result().flatMap((p_14586_) -> {
         String s = p_14586_.asString((String)null);
         if (s != null) {
            try {
               UUID uuid = UUID.fromString(s);
               return m_14580_(p_14588_, uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
            } catch (IllegalArgumentException illegalargumentexception) {
            }
         }

         return Optional.empty();
      });
   }

   protected static Optional<Dynamic<?>> m_14578_(Dynamic<?> p_14579_) {
      return m_14621_(p_14579_, "M", "L");
   }

   protected static Optional<Dynamic<?>> m_14621_(Dynamic<?> p_14622_, String p_14623_, String p_14624_) {
      long i = p_14622_.get(p_14623_).asLong(0L);
      long j = p_14622_.get(p_14624_).asLong(0L);
      return i != 0L && j != 0L ? m_14580_(p_14622_, i, j) : Optional.empty();
   }

   protected static Optional<Dynamic<?>> m_14580_(Dynamic<?> p_14581_, long p_14582_, long p_14583_) {
      return Optional.of(p_14581_.createIntList(Arrays.stream(new int[]{(int)(p_14582_ >> 32), (int)p_14582_, (int)(p_14583_ >> 32), (int)p_14583_})));
   }
}