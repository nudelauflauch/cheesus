package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractFurnaceScreen<T extends AbstractFurnaceMenu> extends AbstractContainerScreen<T> implements RecipeUpdateListener {
   private static final ResourceLocation f_97820_ = new ResourceLocation("textures/gui/recipe_button.png");
   public final AbstractFurnaceRecipeBookComponent f_97819_;
   private boolean f_97821_;
   private final ResourceLocation f_97822_;

   public AbstractFurnaceScreen(T p_97825_, AbstractFurnaceRecipeBookComponent p_97826_, Inventory p_97827_, Component p_97828_, ResourceLocation p_97829_) {
      super(p_97825_, p_97827_, p_97828_);
      this.f_97819_ = p_97826_;
      this.f_97822_ = p_97829_;
   }

   public void m_7856_() {
      super.m_7856_();
      this.f_97821_ = this.f_96543_ < 379;
      this.f_97819_.m_100309_(this.f_96543_, this.f_96544_, this.f_96541_, this.f_97821_, this.f_97732_);
      this.f_97735_ = this.f_97819_.m_181401_(this.f_96543_, this.f_97726_);
      this.m_142416_(new ImageButton(this.f_97735_ + 20, this.f_96544_ / 2 - 49, 20, 18, 0, 0, 19, f_97820_, (p_97863_) -> {
         this.f_97819_.m_100384_();
         this.f_97735_ = this.f_97819_.m_181401_(this.f_96543_, this.f_97726_);
         ((ImageButton)p_97863_).m_94278_(this.f_97735_ + 20, this.f_96544_ / 2 - 49);
      }));
      this.f_97728_ = (this.f_97726_ - this.f_96547_.m_92852_(this.f_96539_)) / 2;
   }

   public void m_181908_() {
      super.m_181908_();
      this.f_97819_.m_100386_();
   }

   public void m_6305_(PoseStack p_97858_, int p_97859_, int p_97860_, float p_97861_) {
      this.m_7333_(p_97858_);
      if (this.f_97819_.m_100385_() && this.f_97821_) {
         this.m_7286_(p_97858_, p_97861_, p_97859_, p_97860_);
         this.f_97819_.m_6305_(p_97858_, p_97859_, p_97860_, p_97861_);
      } else {
         this.f_97819_.m_6305_(p_97858_, p_97859_, p_97860_, p_97861_);
         super.m_6305_(p_97858_, p_97859_, p_97860_, p_97861_);
         this.f_97819_.m_6545_(p_97858_, this.f_97735_, this.f_97736_, true, p_97861_);
      }

      this.m_7025_(p_97858_, p_97859_, p_97860_);
      this.f_97819_.m_100361_(p_97858_, this.f_97735_, this.f_97736_, p_97859_, p_97860_);
   }

   protected void m_7286_(PoseStack p_97853_, float p_97854_, int p_97855_, int p_97856_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, this.f_97822_);
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_93228_(p_97853_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      if (this.f_97732_.m_38997_()) {
         int k = this.f_97732_.m_38996_();
         this.m_93228_(p_97853_, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
      }

      int l = this.f_97732_.m_38995_();
      this.m_93228_(p_97853_, i + 79, j + 34, 176, 14, l + 1, 16);
   }

   public boolean m_6375_(double p_97834_, double p_97835_, int p_97836_) {
      if (this.f_97819_.m_6375_(p_97834_, p_97835_, p_97836_)) {
         return true;
      } else {
         return this.f_97821_ && this.f_97819_.m_100385_() ? true : super.m_6375_(p_97834_, p_97835_, p_97836_);
      }
   }

   protected void m_6597_(Slot p_97848_, int p_97849_, int p_97850_, ClickType p_97851_) {
      super.m_6597_(p_97848_, p_97849_, p_97850_, p_97851_);
      this.f_97819_.m_6904_(p_97848_);
   }

   public boolean m_7933_(int p_97844_, int p_97845_, int p_97846_) {
      return this.f_97819_.m_7933_(p_97844_, p_97845_, p_97846_) ? false : super.m_7933_(p_97844_, p_97845_, p_97846_);
   }

   protected boolean m_7467_(double p_97838_, double p_97839_, int p_97840_, int p_97841_, int p_97842_) {
      boolean flag = p_97838_ < (double)p_97840_ || p_97839_ < (double)p_97841_ || p_97838_ >= (double)(p_97840_ + this.f_97726_) || p_97839_ >= (double)(p_97841_ + this.f_97727_);
      return this.f_97819_.m_100297_(p_97838_, p_97839_, this.f_97735_, this.f_97736_, this.f_97726_, this.f_97727_, p_97842_) && flag;
   }

   public boolean m_5534_(char p_97831_, int p_97832_) {
      return this.f_97819_.m_5534_(p_97831_, p_97832_) ? true : super.m_5534_(p_97831_, p_97832_);
   }

   public void m_6916_() {
      this.f_97819_.m_100387_();
   }

   public RecipeBookComponent m_5564_() {
      return this.f_97819_;
   }

   public void m_7861_() {
      this.f_97819_.m_100373_();
      super.m_7861_();
   }
}