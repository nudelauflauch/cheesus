package net.minecraft.client.renderer.texture;

import java.util.Collection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StitcherException extends RuntimeException {
   private final Collection<TextureAtlasSprite.Info> f_118254_;

   public StitcherException(TextureAtlasSprite.Info p_118256_, Collection<TextureAtlasSprite.Info> p_118257_) {
      super(String.format("Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?", p_118256_.m_118431_(), p_118256_.m_118434_(), p_118256_.m_118437_()));
      this.f_118254_ = p_118257_;
   }

   public Collection<TextureAtlasSprite.Info> m_118258_() {
      return this.f_118254_;
   }
}