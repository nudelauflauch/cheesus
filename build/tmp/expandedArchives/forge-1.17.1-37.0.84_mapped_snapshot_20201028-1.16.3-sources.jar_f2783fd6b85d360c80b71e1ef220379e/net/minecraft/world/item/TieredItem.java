package net.minecraft.world.item;

public class TieredItem extends Item {
   private final Tier f_43306_;

   public TieredItem(Tier p_43308_, Item.Properties p_43309_) {
      super(p_43309_.m_41499_(p_43308_.m_6609_()));
      this.f_43306_ = p_43308_;
   }

   public Tier m_43314_() {
      return this.f_43306_;
   }

   public int m_6473_() {
      return this.f_43306_.m_6601_();
   }

   public boolean m_6832_(ItemStack p_43311_, ItemStack p_43312_) {
      return this.f_43306_.m_6282_().test(p_43312_) || super.m_6832_(p_43311_, p_43312_);
   }
}