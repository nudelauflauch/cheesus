package net.minecraft.client;

import net.minecraft.obfuscate.DontObfuscate;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientBrandRetriever {
   public static final String f_177870_ = "vanilla";

   @DontObfuscate
   public static String m_129629_() {
      return net.minecraftforge.fmllegacy.BrandingControl.getClientBranding();
   }
}
