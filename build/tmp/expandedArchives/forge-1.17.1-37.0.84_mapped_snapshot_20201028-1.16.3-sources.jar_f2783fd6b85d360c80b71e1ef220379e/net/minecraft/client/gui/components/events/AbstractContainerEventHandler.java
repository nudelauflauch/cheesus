package net.minecraft.client.gui.components.events;

import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractContainerEventHandler extends GuiComponent implements ContainerEventHandler {
   @Nullable
   private GuiEventListener f_94673_;
   private boolean f_94674_;

   public final boolean m_7282_() {
      return this.f_94674_;
   }

   public final void m_7897_(boolean p_94681_) {
      this.f_94674_ = p_94681_;
   }

   @Nullable
   public GuiEventListener m_7222_() {
      return this.f_94673_;
   }

   public void m_7522_(@Nullable GuiEventListener p_94677_) {
      this.f_94673_ = p_94677_;
   }
}