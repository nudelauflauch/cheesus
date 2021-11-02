package net.minecraft.client.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfirmScreen extends Screen {
   private static final int f_169250_ = 90;
   private final Component f_95650_;
   private MultiLineLabel f_95651_ = MultiLineLabel.f_94331_;
   protected Component f_95647_;
   protected Component f_95648_;
   private int f_95652_;
   protected final BooleanConsumer f_95649_;
   private final List<Button> f_169251_ = Lists.newArrayList();

   public ConfirmScreen(BooleanConsumer p_95654_, Component p_95655_, Component p_95656_) {
      this(p_95654_, p_95655_, p_95656_, CommonComponents.f_130657_, CommonComponents.f_130658_);
   }

   public ConfirmScreen(BooleanConsumer p_95658_, Component p_95659_, Component p_95660_, Component p_95661_, Component p_95662_) {
      super(p_95659_);
      this.f_95649_ = p_95658_;
      this.f_95650_ = p_95660_;
      this.f_95647_ = p_95661_;
      this.f_95648_ = p_95662_;
   }

   public Component m_142562_() {
      return CommonComponents.m_178398_(super.m_142562_(), this.f_95650_);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_95651_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_95650_, this.f_96543_ - 50);
      int i = this.f_95651_.m_5770_() * 9;
      int j = Mth.m_14045_(90 + i + 12, this.f_96544_ / 6 + 96, this.f_96544_ - 24);
      this.f_169251_.clear();
      this.m_141972_(j);
   }

   protected void m_141972_(int p_169252_) {
      this.m_169253_(new Button(this.f_96543_ / 2 - 155, p_169252_, 150, 20, this.f_95647_, (p_169259_) -> {
         this.f_95649_.accept(true);
      }));
      this.m_169253_(new Button(this.f_96543_ / 2 - 155 + 160, p_169252_, 150, 20, this.f_95648_, (p_169257_) -> {
         this.f_95649_.accept(false);
      }));
   }

   protected void m_169253_(Button p_169254_) {
      this.f_169251_.add(this.m_142416_(p_169254_));
   }

   public void m_6305_(PoseStack p_95670_, int p_95671_, int p_95672_, float p_95673_) {
      this.m_7333_(p_95670_);
      m_93215_(p_95670_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 70, 16777215);
      this.f_95651_.m_6276_(p_95670_, this.f_96543_ / 2, 90);
      super.m_6305_(p_95670_, p_95671_, p_95672_, p_95673_);
   }

   public void m_95663_(int p_95664_) {
      this.f_95652_ = p_95664_;

      for(Button button : this.f_169251_) {
         button.f_93623_ = false;
      }

   }

   public void m_96624_() {
      super.m_96624_();
      if (--this.f_95652_ == 0) {
         for(Button button : this.f_169251_) {
            button.f_93623_ = true;
         }
      }

   }

   public boolean m_6913_() {
      return false;
   }

   public boolean m_7933_(int p_95666_, int p_95667_, int p_95668_) {
      if (p_95666_ == 256) {
         this.f_95649_.accept(false);
         return true;
      } else {
         return super.m_7933_(p_95666_, p_95667_, p_95668_);
      }
   }
}