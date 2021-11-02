package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrindstoneScreen extends AbstractContainerScreen<GrindstoneMenu> {
   private static final ResourceLocation f_98779_ = new ResourceLocation("textures/gui/container/grindstone.png");

   public GrindstoneScreen(GrindstoneMenu p_98782_, Inventory p_98783_, Component p_98784_) {
      super(p_98782_, p_98783_, p_98784_);
   }

   public void m_6305_(PoseStack p_98791_, int p_98792_, int p_98793_, float p_98794_) {
      this.m_7333_(p_98791_);
      this.m_7286_(p_98791_, p_98794_, p_98792_, p_98793_);
      super.m_6305_(p_98791_, p_98792_, p_98793_, p_98794_);
      this.m_7025_(p_98791_, p_98792_, p_98793_);
   }

   protected void m_7286_(PoseStack p_98786_, float p_98787_, int p_98788_, int p_98789_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_98779_);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(p_98786_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      if ((this.f_97732_.m_38853_(0).m_6657_() || this.f_97732_.m_38853_(1).m_6657_()) && !this.f_97732_.m_38853_(2).m_6657_()) {
         this.m_93228_(p_98786_, i + 92, j + 31, this.f_97726_, 0, 28, 21);
      }

   }
}