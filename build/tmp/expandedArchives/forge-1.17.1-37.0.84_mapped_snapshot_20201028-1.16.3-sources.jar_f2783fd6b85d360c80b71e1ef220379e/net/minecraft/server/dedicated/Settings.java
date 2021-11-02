package net.minecraft.server.dedicated;

import com.google.common.base.MoreObjects;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.core.RegistryAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Settings<T extends Settings<T>> {
   private static final Logger f_139797_ = LogManager.getLogger();
   protected final Properties f_139798_;

   public Settings(Properties p_139801_) {
      this.f_139798_ = p_139801_;
   }

   public static Properties m_139839_(Path p_139840_) {
      Properties properties = new Properties();

      try {
         InputStream inputstream = Files.newInputStream(p_139840_);

         try {
            properties.load(inputstream);
         } catch (Throwable throwable1) {
            if (inputstream != null) {
               try {
                  inputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (inputstream != null) {
            inputstream.close();
         }
      } catch (IOException ioexception) {
         f_139797_.error("Failed to load properties from file: {}", (Object)p_139840_);
      }

      return properties;
   }

   public void m_139876_(Path p_139877_) {
      try {
         OutputStream outputstream = Files.newOutputStream(p_139877_);

         try {
            net.minecraftforge.common.util.SortedProperties.store(f_139798_, outputstream, "Minecraft server properties");
         } catch (Throwable throwable1) {
            if (outputstream != null) {
               try {
                  outputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (outputstream != null) {
            outputstream.close();
         }
      } catch (IOException ioexception) {
         f_139797_.error("Failed to store properties to file: {}", (Object)p_139877_);
      }

   }

   private static <V extends Number> Function<String, V> m_139841_(Function<String, V> p_139842_) {
      return (p_139845_) -> {
         try {
            return p_139842_.apply(p_139845_);
         } catch (NumberFormatException numberformatexception) {
            return (V)null;
         }
      };
   }

   protected static <V> Function<String, V> m_139850_(IntFunction<V> p_139851_, Function<String, V> p_139852_) {
      return (p_139856_) -> {
         try {
            return p_139851_.apply(Integer.parseInt(p_139856_));
         } catch (NumberFormatException numberformatexception) {
            return p_139852_.apply(p_139856_);
         }
      };
   }

   @Nullable
   private String m_139878_(String p_139879_) {
      return (String)this.f_139798_.get(p_139879_);
   }

   @Nullable
   protected <V> V m_139814_(String p_139815_, Function<String, V> p_139816_) {
      String s = this.m_139878_(p_139815_);
      if (s == null) {
         return (V)null;
      } else {
         this.f_139798_.remove(p_139815_);
         return p_139816_.apply(s);
      }
   }

   protected <V> V m_139821_(String p_139822_, Function<String, V> p_139823_, Function<V, String> p_139824_, V p_139825_) {
      String s = this.m_139878_(p_139822_);
      V v = MoreObjects.firstNonNull((V)(s != null ? p_139823_.apply(s) : null), p_139825_);
      this.f_139798_.put(p_139822_, p_139824_.apply(v));
      return v;
   }

   protected <V> Settings<T>.MutableValue<V> m_139868_(String p_139869_, Function<String, V> p_139870_, Function<V, String> p_139871_, V p_139872_) {
      String s = this.m_139878_(p_139869_);
      V v = MoreObjects.firstNonNull((V)(s != null ? p_139870_.apply(s) : null), p_139872_);
      this.f_139798_.put(p_139869_, p_139871_.apply(v));
      return new Settings.MutableValue(p_139869_, v, p_139871_);
   }

   protected <V> V m_139826_(String p_139827_, Function<String, V> p_139828_, UnaryOperator<V> p_139829_, Function<V, String> p_139830_, V p_139831_) {
      return this.m_139821_(p_139827_, (p_139849_) -> {
         V v = p_139828_.apply(p_139849_);
         return (V)(v != null ? p_139829_.apply(v) : null);
      }, p_139830_, p_139831_);
   }

   protected <V> V m_139817_(String p_139818_, Function<String, V> p_139819_, V p_139820_) {
      return this.m_139821_(p_139818_, p_139819_, Objects::toString, p_139820_);
   }

   protected <V> Settings<T>.MutableValue<V> m_139864_(String p_139865_, Function<String, V> p_139866_, V p_139867_) {
      return this.m_139868_(p_139865_, p_139866_, Objects::toString, p_139867_);
   }

   protected String m_139811_(String p_139812_, String p_139813_) {
      return this.m_139821_(p_139812_, Function.identity(), Function.identity(), p_139813_);
   }

   @Nullable
   protected String m_139803_(String p_139804_) {
      return this.m_139814_(p_139804_, Function.identity());
   }

   protected int m_139805_(String p_139806_, int p_139807_) {
      return this.m_139817_(p_139806_, m_139841_(Integer::parseInt), p_139807_);
   }

   protected Settings<T>.MutableValue<Integer> m_139861_(String p_139862_, int p_139863_) {
      return this.m_139864_(p_139862_, m_139841_(Integer::parseInt), p_139863_);
   }

   protected int m_139832_(String p_139833_, UnaryOperator<Integer> p_139834_, int p_139835_) {
      return this.m_139826_(p_139833_, m_139841_(Integer::parseInt), p_139834_, Objects::toString, p_139835_);
   }

   protected long m_139808_(String p_139809_, long p_139810_) {
      return this.m_139817_(p_139809_, m_139841_(Long::parseLong), p_139810_);
   }

   protected boolean m_139836_(String p_139837_, boolean p_139838_) {
      return this.m_139817_(p_139837_, Boolean::valueOf, p_139838_);
   }

   protected Settings<T>.MutableValue<Boolean> m_139873_(String p_139874_, boolean p_139875_) {
      return this.m_139864_(p_139874_, Boolean::valueOf, p_139875_);
   }

   @Nullable
   protected Boolean m_139859_(String p_139860_) {
      return this.m_139814_(p_139860_, Boolean::valueOf);
   }

   protected Properties m_139802_() {
      Properties properties = new Properties();
      properties.putAll(this.f_139798_);
      return properties;
   }

   protected abstract T m_6764_(RegistryAccess p_139857_, Properties p_139858_);

   public class MutableValue<V> implements Supplier<V> {
      private final String f_139881_;
      private final V f_139882_;
      private final Function<V, String> f_139883_;

      MutableValue(String p_139886_, V p_139887_, Function<V, String> p_139888_) {
         this.f_139881_ = p_139886_;
         this.f_139882_ = p_139887_;
         this.f_139883_ = p_139888_;
      }

      public V get() {
         return this.f_139882_;
      }

      public T m_139895_(RegistryAccess p_139896_, V p_139897_) {
         Properties properties = Settings.this.m_139802_();
         properties.put(this.f_139881_, this.f_139883_.apply(p_139897_));
         return Settings.this.m_6764_(p_139896_, properties);
      }
   }
}
