package net.minecraft.world.level.storage.loot;

public class SerializerType<T> {
   private final Serializer<? extends T> f_79328_;

   public SerializerType(Serializer<? extends T> p_79330_) {
      this.f_79328_ = p_79330_;
   }

   public Serializer<? extends T> m_79331_() {
      return this.f_79328_;
   }
}