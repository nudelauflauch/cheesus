package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ErrorScreen extends Screen {
   private final Component f_96047_;

   public ErrorScreen(Component p_96049_, Component p_96050_) {
      super(p_96049_);
      this.f_96047_ = p_96050_;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, 140, 200, 20, CommonComponents.f_130656_, (p_96057_) -> {
         this.f_96541_.m_91152_((Screen)null);
      }));
   }

   public void m_6305_(PoseStack p_96052_, int p_96053_, int p_96054_, float p_96055_) {
      this.m_93179_(p_96052_, 0, 0, this.f_96543_, this.f_96544_, -12574688, -11530224);
      m_93215_(p_96052_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 90, 16777215);
      m_93215_(p_96052_, this.f_96547_, this.f_96047_, this.f_96543_ / 2, 110, 16777215);
      super.m_6305_(p_96052_, p_96053_, p_96054_, p_96055_);
   }

   public boolean m_6913_() {
      return false;
   }
}