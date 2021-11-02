package net.minecraft.data.advancements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementProvider implements DataProvider {
   private static final Logger f_123960_ = LogManager.getLogger();
   private static final Gson f_123961_ = (new GsonBuilder()).setPrettyPrinting().create();
   private final DataGenerator f_123962_;
   private final List<Consumer<Consumer<Advancement>>> f_123963_ = ImmutableList.of(new TheEndAdvancements(), new HusbandryAdvancements(), new AdventureAdvancements(), new NetherAdvancements(), new StoryAdvancements());

   public AdvancementProvider(DataGenerator p_123966_) {
      this.f_123962_ = p_123966_;
   }

   public void m_6865_(HashCache p_123969_) {
      Path path = this.f_123962_.m_123916_();
      Set<ResourceLocation> set = Sets.newHashSet();
      Consumer<Advancement> consumer = (p_123977_) -> {
         if (!set.add(p_123977_.m_138327_())) {
            throw new IllegalStateException("Duplicate advancement " + p_123977_.m_138327_());
         } else {
            Path path1 = m_123970_(path, p_123977_);

            try {
               DataProvider.m_123920_(f_123961_, p_123969_, p_123977_.m_138313_().m_138400_(), path1);
            } catch (IOException ioexception) {
               f_123960_.error("Couldn't save advancement {}", path1, ioexception);
            }

         }
      };

      for(Consumer<Consumer<Advancement>> consumer1 : this.f_123963_) {
         consumer1.accept(consumer);
      }

   }

   private static Path m_123970_(Path p_123971_, Advancement p_123972_) {
      return p_123971_.resolve("data/" + p_123972_.m_138327_().m_135827_() + "/advancements/" + p_123972_.m_138327_().m_135815_() + ".json");
   }

   public String m_6055_() {
      return "Advancements";
   }
}