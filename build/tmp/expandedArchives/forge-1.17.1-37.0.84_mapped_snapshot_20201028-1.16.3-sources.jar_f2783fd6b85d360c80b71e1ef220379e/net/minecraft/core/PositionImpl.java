package net.minecraft.core;

public class PositionImpl implements Position {
   protected final double f_122798_;
   protected final double f_122799_;
   protected final double f_122800_;

   public PositionImpl(double p_122802_, double p_122803_, double p_122804_) {
      this.f_122798_ = p_122802_;
      this.f_122799_ = p_122803_;
      this.f_122800_ = p_122804_;
   }

   public double m_7096_() {
      return this.f_122798_;
   }

   public double m_7098_() {
      return this.f_122799_;
   }

   public double m_7094_() {
      return this.f_122800_;
   }
}