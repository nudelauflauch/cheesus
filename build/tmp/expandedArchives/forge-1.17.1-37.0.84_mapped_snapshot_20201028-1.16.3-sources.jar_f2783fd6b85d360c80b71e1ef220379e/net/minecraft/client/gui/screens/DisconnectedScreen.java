package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisconnectedScreen extends Screen {
   private final Component f_95988_;
   private MultiLineLabel f_95989_ = MultiLineLabel.f_94331_;
   private final Screen f_95990_;
   private int f_95991_;

   public DisconnectedScreen(Screen p_95993_, Component p_95994_, Component p_95995_) {
      super(p_95994_);
      this.f_95990_ = p_95993_;
      this.f_95988_ = p_95995_;
   }

   public boolean m_6913_() {
      return false;
   }

   protected void m_7856_() {
      this.f_95989_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_95988_, this.f_96543_ - 50);
      this.f_95991_ = this.f_95989_.m_5770_() * 9;
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, Math.min(this.f_96544_ / 2 + this.f_95991_ / 2 + 9, this.f_96544_ - 30), 200, 20, new TranslatableComponent("gui.toMenu"), (p_96002_) -> {
         this.f_96541_.m_91152_(this.f_95990_);
      }));
   }

   public void m_6305_(PoseStack p_95997_, int p_95998_, int p_95999_, float p_96000_) {
      this.m_7333_(p_95997_);
      m_93215_(p_95997_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96544_ / 2 - this.f_95991_ / 2 - 9 * 2, 11184810);
      this.f_95989_.m_6276_(p_95997_, this.f_96543_ / 2, this.f_96544_ / 2 - this.f_95991_ / 2);
      super.m_6305_(p_95997_, p_95998_, p_95999_, p_96000_);
   }
}