package net.minecraft.data.models.blockstates;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Variant implements Supplier<JsonElement> {
   private final Map<VariantProperty<?>, VariantProperty<?>.Value> f_125499_ = Maps.newLinkedHashMap();

   public <T> Variant m_125511_(VariantProperty<T> p_125512_, T p_125513_) {
      VariantProperty<?>.Value variantproperty = this.f_125499_.put(p_125512_, p_125512_.m_125553_(p_125513_));
      if (variantproperty != null) {
         throw new IllegalStateException("Replacing value of " + variantproperty + " with " + p_125513_);
      } else {
         return this;
      }
   }

   public static Variant m_125501_() {
      return new Variant();
   }

   public static Variant m_125508_(Variant p_125509_, Variant p_125510_) {
      Variant variant = new Variant();
      variant.f_125499_.putAll(p_125509_.f_125499_);
      variant.f_125499_.putAll(p_125510_.f_125499_);
      return variant;
   }

   public JsonElement get() {
      JsonObject jsonobject = new JsonObject();
      this.f_125499_.values().forEach((p_125507_) -> {
         p_125507_.m_125563_(jsonobject);
      });
      return jsonobject;
   }

   public static JsonElement m_125514_(List<Variant> p_125515_) {
      if (p_125515_.size() == 1) {
         return p_125515_.get(0).get();
      } else {
         JsonArray jsonarray = new JsonArray();
         p_125515_.forEach((p_125504_) -> {
            jsonarray.add(p_125504_.get());
         });
         return jsonarray;
      }
   }
}