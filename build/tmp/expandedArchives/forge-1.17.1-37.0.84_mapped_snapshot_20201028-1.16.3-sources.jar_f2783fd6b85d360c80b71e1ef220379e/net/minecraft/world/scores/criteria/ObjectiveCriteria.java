package net.minecraft.world.scores.criteria;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatType;

public class ObjectiveCriteria {
   private static final Map<String, ObjectiveCriteria> f_166107_ = Maps.newHashMap();
   private static final Map<String, ObjectiveCriteria> f_166108_ = Maps.newHashMap();
   public static final ObjectiveCriteria f_83588_ = m_166113_("dummy");
   public static final ObjectiveCriteria f_83589_ = m_166113_("trigger");
   public static final ObjectiveCriteria f_83590_ = m_166113_("deathCount");
   public static final ObjectiveCriteria f_83591_ = m_166113_("playerKillCount");
   public static final ObjectiveCriteria f_83592_ = m_166113_("totalKillCount");
   public static final ObjectiveCriteria f_83593_ = m_166109_("health", true, ObjectiveCriteria.RenderType.HEARTS);
   public static final ObjectiveCriteria f_83594_ = m_166109_("food", true, ObjectiveCriteria.RenderType.INTEGER);
   public static final ObjectiveCriteria f_83595_ = m_166109_("air", true, ObjectiveCriteria.RenderType.INTEGER);
   public static final ObjectiveCriteria f_83596_ = m_166109_("armor", true, ObjectiveCriteria.RenderType.INTEGER);
   public static final ObjectiveCriteria f_83597_ = m_166109_("xp", true, ObjectiveCriteria.RenderType.INTEGER);
   public static final ObjectiveCriteria f_83598_ = m_166109_("level", true, ObjectiveCriteria.RenderType.INTEGER);
   public static final ObjectiveCriteria[] f_83599_ = new ObjectiveCriteria[]{m_166113_("teamkill." + ChatFormatting.BLACK.m_126666_()), m_166113_("teamkill." + ChatFormatting.DARK_BLUE.m_126666_()), m_166113_("teamkill." + ChatFormatting.DARK_GREEN.m_126666_()), m_166113_("teamkill." + ChatFormatting.DARK_AQUA.m_126666_()), m_166113_("teamkill." + ChatFormatting.DARK_RED.m_126666_()), m_166113_("teamkill." + ChatFormatting.DARK_PURPLE.m_126666_()), m_166113_("teamkill." + ChatFormatting.GOLD.m_126666_()), m_166113_("teamkill." + ChatFormatting.GRAY.m_126666_()), m_166113_("teamkill." + ChatFormatting.DARK_GRAY.m_126666_()), m_166113_("teamkill." + ChatFormatting.BLUE.m_126666_()), m_166113_("teamkill." + ChatFormatting.GREEN.m_126666_()), m_166113_("teamkill." + ChatFormatting.AQUA.m_126666_()), m_166113_("teamkill." + ChatFormatting.RED.m_126666_()), m_166113_("teamkill." + ChatFormatting.LIGHT_PURPLE.m_126666_()), m_166113_("teamkill." + ChatFormatting.YELLOW.m_126666_()), m_166113_("teamkill." + ChatFormatting.WHITE.m_126666_())};
   public static final ObjectiveCriteria[] f_83600_ = new ObjectiveCriteria[]{m_166113_("killedByTeam." + ChatFormatting.BLACK.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.DARK_BLUE.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.DARK_GREEN.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.DARK_AQUA.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.DARK_RED.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.DARK_PURPLE.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.GOLD.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.GRAY.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.DARK_GRAY.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.BLUE.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.GREEN.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.AQUA.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.RED.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.LIGHT_PURPLE.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.YELLOW.m_126666_()), m_166113_("killedByTeam." + ChatFormatting.WHITE.m_126666_())};
   private final String f_83601_;
   private final boolean f_83602_;
   private final ObjectiveCriteria.RenderType f_83603_;

   private static ObjectiveCriteria m_166109_(String p_166110_, boolean p_166111_, ObjectiveCriteria.RenderType p_166112_) {
      ObjectiveCriteria objectivecriteria = new ObjectiveCriteria(p_166110_, p_166111_, p_166112_);
      f_166107_.put(p_166110_, objectivecriteria);
      return objectivecriteria;
   }

   private static ObjectiveCriteria m_166113_(String p_166114_) {
      return m_166109_(p_166114_, false, ObjectiveCriteria.RenderType.INTEGER);
   }

   protected ObjectiveCriteria(String p_83606_) {
      this(p_83606_, false, ObjectiveCriteria.RenderType.INTEGER);
   }

   protected ObjectiveCriteria(String p_83608_, boolean p_83609_, ObjectiveCriteria.RenderType p_83610_) {
      this.f_83601_ = p_83608_;
      this.f_83602_ = p_83609_;
      this.f_83603_ = p_83610_;
      f_166108_.put(p_83608_, this);
   }

   public static Set<String> m_166115_() {
      return ImmutableSet.copyOf(f_166107_.keySet());
   }

   public static Optional<ObjectiveCriteria> m_83614_(String p_83615_) {
      ObjectiveCriteria objectivecriteria = f_166108_.get(p_83615_);
      if (objectivecriteria != null) {
         return Optional.of(objectivecriteria);
      } else {
         int i = p_83615_.indexOf(58);
         return i < 0 ? Optional.empty() : Registry.f_122867_.m_6612_(ResourceLocation.m_135822_(p_83615_.substring(0, i), '.')).flatMap((p_83619_) -> {
            return m_83611_(p_83619_, ResourceLocation.m_135822_(p_83615_.substring(i + 1), '.'));
         });
      }
   }

   private static <T> Optional<ObjectiveCriteria> m_83611_(StatType<T> p_83612_, ResourceLocation p_83613_) {
      return p_83612_.m_12893_().m_6612_(p_83613_).map(p_83612_::m_12902_);
   }

   public String m_83620_() {
      return this.f_83601_;
   }

   public boolean m_83621_() {
      return this.f_83602_;
   }

   public ObjectiveCriteria.RenderType m_83622_() {
      return this.f_83603_;
   }

   public static enum RenderType {
      INTEGER("integer"),
      HEARTS("hearts");

      private final String f_83625_;
      private static final Map<String, ObjectiveCriteria.RenderType> f_83626_;

      private RenderType(String p_83632_) {
         this.f_83625_ = p_83632_;
      }

      public String m_83633_() {
         return this.f_83625_;
      }

      public static ObjectiveCriteria.RenderType m_83634_(String p_83635_) {
         return f_83626_.getOrDefault(p_83635_, INTEGER);
      }

      static {
         Builder<String, ObjectiveCriteria.RenderType> builder = ImmutableMap.builder();

         for(ObjectiveCriteria.RenderType objectivecriteria$rendertype : values()) {
            builder.put(objectivecriteria$rendertype.f_83625_, objectivecriteria$rendertype);
         }

         f_83626_ = builder.build();
      }
   }
}