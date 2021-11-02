package net.minecraft.client.player;

import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Input {
   public float f_108566_;
   public float f_108567_;
   public boolean f_108568_;
   public boolean f_108569_;
   public boolean f_108570_;
   public boolean f_108571_;
   public boolean f_108572_;
   public boolean f_108573_;

   public void m_7606_(boolean p_108576_) {
   }

   public Vec2 m_108575_() {
      return new Vec2(this.f_108566_, this.f_108567_);
   }

   public boolean m_108577_() {
      return this.f_108567_ > 1.0E-5F;
   }
}