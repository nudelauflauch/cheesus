package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StoredUserList<K, V extends StoredUserEntry<K>> {
   protected static final Logger f_11374_ = LogManager.getLogger();
   private static final Gson f_11375_ = (new GsonBuilder()).setPrettyPrinting().create();
   private final File f_11376_;
   private final Map<String, V> f_11377_ = Maps.newHashMap();

   public StoredUserList(File p_11380_) {
      this.f_11376_ = p_11380_;
   }

   public File m_11385_() {
      return this.f_11376_;
   }

   public void m_11381_(V p_11382_) {
      this.f_11377_.put(this.m_5981_(p_11382_.m_11373_()), p_11382_);

      try {
         this.m_11398_();
      } catch (IOException ioexception) {
         f_11374_.warn("Could not save the list after adding a user.", (Throwable)ioexception);
      }

   }

   @Nullable
   public V m_11388_(K p_11389_) {
      this.m_11400_();
      return this.f_11377_.get(this.m_5981_(p_11389_));
   }

   public void m_11393_(K p_11394_) {
      this.f_11377_.remove(this.m_5981_(p_11394_));

      try {
         this.m_11398_();
      } catch (IOException ioexception) {
         f_11374_.warn("Could not save the list after removing a user.", (Throwable)ioexception);
      }

   }

   public void m_11386_(StoredUserEntry<K> p_11387_) {
      this.m_11393_(p_11387_.m_11373_());
   }

   public String[] m_5875_() {
      return this.f_11377_.keySet().toArray(new String[0]);
   }

   public boolean m_11390_() {
      return this.f_11377_.size() < 1;
   }

   protected String m_5981_(K p_11384_) {
      return p_11384_.toString();
   }

   protected boolean m_11396_(K p_11397_) {
      return this.f_11377_.containsKey(this.m_5981_(p_11397_));
   }

   private void m_11400_() {
      List<K> list = Lists.newArrayList();

      for(V v : this.f_11377_.values()) {
         if (v.m_7524_()) {
            list.add(v.m_11373_());
         }
      }

      for(K k : list) {
         this.f_11377_.remove(this.m_5981_(k));
      }

   }

   protected abstract StoredUserEntry<K> m_6666_(JsonObject p_11383_);

   public Collection<V> m_11395_() {
      return this.f_11377_.values();
   }

   public void m_11398_() throws IOException {
      JsonArray jsonarray = new JsonArray();
      this.f_11377_.values().stream().map((p_11392_) -> {
         return Util.m_137469_(new JsonObject(), p_11392_::m_6009_);
      }).forEach(jsonarray::add);
      BufferedWriter bufferedwriter = Files.newWriter(this.f_11376_, StandardCharsets.UTF_8);

      try {
         f_11375_.toJson((JsonElement)jsonarray, bufferedwriter);
      } catch (Throwable throwable1) {
         if (bufferedwriter != null) {
            try {
               bufferedwriter.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (bufferedwriter != null) {
         bufferedwriter.close();
      }

   }

   public void m_11399_() throws IOException {
      if (this.f_11376_.exists()) {
         BufferedReader bufferedreader = Files.newReader(this.f_11376_, StandardCharsets.UTF_8);

         try {
            JsonArray jsonarray = f_11375_.fromJson(bufferedreader, JsonArray.class);
            this.f_11377_.clear();

            for(JsonElement jsonelement : jsonarray) {
               JsonObject jsonobject = GsonHelper.m_13918_(jsonelement, "entry");
               StoredUserEntry<K> storeduserentry = this.m_6666_(jsonobject);
               if (storeduserentry.m_11373_() != null) {
                  this.f_11377_.put(this.m_5981_(storeduserentry.m_11373_()), (V)storeduserentry);
               }
            }
         } catch (Throwable throwable1) {
            if (bufferedreader != null) {
               try {
                  bufferedreader.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (bufferedreader != null) {
            bufferedreader.close();
         }

      }
   }
}