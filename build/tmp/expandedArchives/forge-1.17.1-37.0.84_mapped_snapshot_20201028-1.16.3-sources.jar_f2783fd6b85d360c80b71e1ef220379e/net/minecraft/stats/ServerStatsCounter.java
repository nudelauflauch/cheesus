package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStatsCounter extends StatsCounter {
   private static final Logger f_12809_ = LogManager.getLogger();
   private final MinecraftServer f_12810_;
   private final File f_12811_;
   private final Set<Stat<?>> f_12812_ = Sets.newHashSet();

   public ServerStatsCounter(MinecraftServer p_12816_, File p_12817_) {
      this.f_12810_ = p_12816_;
      this.f_12811_ = p_12817_;
      if (p_12817_.isFile()) {
         try {
            this.m_12832_(p_12816_.m_129933_(), FileUtils.readFileToString(p_12817_));
         } catch (IOException ioexception) {
            f_12809_.error("Couldn't read statistics file {}", p_12817_, ioexception);
         } catch (JsonParseException jsonparseexception) {
            f_12809_.error("Couldn't parse statistics file {}", p_12817_, jsonparseexception);
         }
      }

   }

   public void m_12818_() {
      try {
         FileUtils.writeStringToFile(this.f_12811_, this.m_12845_());
      } catch (IOException ioexception) {
         f_12809_.error("Couldn't save stats", (Throwable)ioexception);
      }

   }

   public void m_6085_(Player p_12827_, Stat<?> p_12828_, int p_12829_) {
      super.m_6085_(p_12827_, p_12828_, p_12829_);
      this.f_12812_.add(p_12828_);
   }

   private Set<Stat<?>> m_12851_() {
      Set<Stat<?>> set = Sets.newHashSet(this.f_12812_);
      this.f_12812_.clear();
      return set;
   }

   public void m_12832_(DataFixer p_12833_, String p_12834_) {
      try {
         JsonReader jsonreader = new JsonReader(new StringReader(p_12834_));

         label51: {
            try {
               jsonreader.setLenient(false);
               JsonElement jsonelement = Streams.parse(jsonreader);
               if (!jsonelement.isJsonNull()) {
                  CompoundTag compoundtag = m_12830_(jsonelement.getAsJsonObject());
                  if (!compoundtag.m_128425_("DataVersion", 99)) {
                     compoundtag.m_128405_("DataVersion", 1343);
                  }

                  compoundtag = NbtUtils.m_129213_(p_12833_, DataFixTypes.STATS, compoundtag, compoundtag.m_128451_("DataVersion"));
                  if (!compoundtag.m_128425_("stats", 10)) {
                     break label51;
                  }

                  CompoundTag compoundtag1 = compoundtag.m_128469_("stats");
                  Iterator iterator = compoundtag1.m_128431_().iterator();

                  while(true) {
                     if (!iterator.hasNext()) {
                        break label51;
                     }

                     String s = (String)iterator.next();
                     if (compoundtag1.m_128425_(s, 10)) {
                        Util.m_137521_(Registry.f_122867_.m_6612_(new ResourceLocation(s)), (p_12844_) -> {
                           CompoundTag compoundtag2 = compoundtag1.m_128469_(s);

                           for(String s1 : compoundtag2.m_128431_()) {
                              if (compoundtag2.m_128425_(s1, 99)) {
                                 Util.m_137521_(this.m_12823_(p_12844_, s1), (p_144252_) -> {
                                    this.f_13013_.put(p_144252_, compoundtag2.m_128451_(s1));
                                 }, () -> {
                                    f_12809_.warn("Invalid statistic in {}: Don't know what {} is", this.f_12811_, s1);
                                 });
                              } else {
                                 f_12809_.warn("Invalid statistic value in {}: Don't know what {} is for key {}", this.f_12811_, compoundtag2.m_128423_(s1), s1);
                              }
                           }

                        }, () -> {
                           f_12809_.warn("Invalid statistic type in {}: Don't know what {} is", this.f_12811_, s);
                        });
                     }
                  }
               }

               f_12809_.error("Unable to parse Stat data from {}", (Object)this.f_12811_);
            } catch (Throwable throwable1) {
               try {
                  jsonreader.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }

               throw throwable1;
            }

            jsonreader.close();
            return;
         }

         jsonreader.close();
      } catch (IOException | JsonParseException jsonparseexception) {
         f_12809_.error("Unable to parse Stat data from {}", this.f_12811_, jsonparseexception);
      }

   }

   private <T> Optional<Stat<T>> m_12823_(StatType<T> p_12824_, String p_12825_) {
      return Optional.ofNullable(ResourceLocation.m_135820_(p_12825_)).flatMap(p_12824_.m_12893_()::m_6612_).map(p_12824_::m_12902_);
   }

   private static CompoundTag m_12830_(JsonObject p_12831_) {
      CompoundTag compoundtag = new CompoundTag();

      for(Entry<String, JsonElement> entry : p_12831_.entrySet()) {
         JsonElement jsonelement = entry.getValue();
         if (jsonelement.isJsonObject()) {
            compoundtag.m_128365_(entry.getKey(), m_12830_(jsonelement.getAsJsonObject()));
         } else if (jsonelement.isJsonPrimitive()) {
            JsonPrimitive jsonprimitive = jsonelement.getAsJsonPrimitive();
            if (jsonprimitive.isNumber()) {
               compoundtag.m_128405_(entry.getKey(), jsonprimitive.getAsInt());
            }
         }
      }

      return compoundtag;
   }

   protected String m_12845_() {
      Map<StatType<?>, JsonObject> map = Maps.newHashMap();

      for(it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<Stat<?>> entry : this.f_13013_.object2IntEntrySet()) {
         Stat<?> stat = entry.getKey();
         map.computeIfAbsent(stat.m_12859_(), (p_12822_) -> {
            return new JsonObject();
         }).addProperty(m_12846_(stat).toString(), entry.getIntValue());
      }

      JsonObject jsonobject = new JsonObject();

      for(Entry<StatType<?>, JsonObject> entry1 : map.entrySet()) {
         jsonobject.add(Registry.f_122867_.m_7981_(entry1.getKey()).toString(), entry1.getValue());
      }

      JsonObject jsonobject1 = new JsonObject();
      jsonobject1.add("stats", jsonobject);
      jsonobject1.addProperty("DataVersion", SharedConstants.m_136187_().getWorldVersion());
      return jsonobject1.toString();
   }

   private static <T> ResourceLocation m_12846_(Stat<T> p_12847_) {
      return p_12847_.m_12859_().m_12893_().m_7981_(p_12847_.m_12867_());
   }

   public void m_12850_() {
      this.f_12812_.addAll(this.f_13013_.keySet());
   }

   public void m_12819_(ServerPlayer p_12820_) {
      Object2IntMap<Stat<?>> object2intmap = new Object2IntOpenHashMap<>();

      for(Stat<?> stat : this.m_12851_()) {
         object2intmap.put(stat, this.m_13015_(stat));
      }

      p_12820_.f_8906_.m_141995_(new ClientboundAwardStatsPacket(object2intmap));
   }
}