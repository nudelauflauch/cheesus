package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSliderButton extends AbstractWidget {
   protected double f_93577_;

   public AbstractSliderButton(int p_93579_, int p_93580_, int p_93581_, int p_93582_, Component p_93583_, double p_93584_) {
      super(p_93579_, p_93580_, p_93581_, p_93582_, p_93583_);
      this.f_93577_ = p_93584_;
   }

   protected int m_7202_(boolean p_93607_) {
      return 0;
   }

   protected MutableComponent m_5646_() {
      return new TranslatableComponent("gui.narrate.slider", this.m_6035_());
   }

   public void m_142291_(NarrationElementOutput p_168798_) {
      p_168798_.m_169146_(NarratedElementType.TITLE, this.m_5646_());
      if (this.f_93623_) {
         if (this.m_93696_()) {
            p_168798_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.slider.usage.focused"));
         } else {
            p_168798_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.slider.usage.hovered"));
         }
      }

   }

   protected void m_7906_(PoseStack p_93600_, Minecraft p_93601_, int p_93602_, int p_93603_) {
      RenderSystem.m_157456_(0, f_93617_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.m_5702_() ? 2 : 1) * 20;
      this.m_93228_(p_93600_, this.f_93620_ + (int)(this.f_93577_ * (double)(this.f_93618_ - 8)), this.f_93621_, 0, 46 + i, 4, 20);
      this.m_93228_(p_93600_, this.f_93620_ + (int)(this.f_93577_ * (double)(this.f_93618_ - 8)) + 4, this.f_93621_, 196, 46 + i, 4, 20);
   }

   public void m_5716_(double p_93588_, double p_93589_) {
      this.m_93585_(p_93588_);
   }

   public boolean m_7933_(int p_93596_, int p_93597_, int p_93598_) {
      boolean flag = p_93596_ == 263;
      if (flag || p_93596_ == 262) {
         float f = flag ? -1.0F : 1.0F;
         this.m_93611_(this.f_93577_ + (double)(f / (float)(this.f_93618_ - 8)));
      }

      return false;
   }

   private void m_93585_(double p_93586_) {
      this.m_93611_((p_93586_ - (double)(this.f_93620_ + 4)) / (double)(this.f_93618_ - 8));
   }

   private void m_93611_(double p_93612_) {
      double d0 = this.f_93577_;
      this.f_93577_ = Mth.m_14008_(p_93612_, 0.0D, 1.0D);
      if (d0 != this.f_93577_) {
         this.m_5697_();
      }

      this.m_5695_();
   }

   protected void m_7212_(double p_93591_, double p_93592_, double p_93593_, double p_93594_) {
      this.m_93585_(p_93591_);
      super.m_7212_(p_93591_, p_93592_, p_93593_, p_93594_);
   }

   public void m_7435_(SoundManager p_93605_) {
   }

   public void m_7691_(double p_93609_, double p_93610_) {
      super.m_7435_(Minecraft.m_91087_().m_91106_());
   }

   protected abstract void m_5695_();

   protected abstract void m_5697_();
}