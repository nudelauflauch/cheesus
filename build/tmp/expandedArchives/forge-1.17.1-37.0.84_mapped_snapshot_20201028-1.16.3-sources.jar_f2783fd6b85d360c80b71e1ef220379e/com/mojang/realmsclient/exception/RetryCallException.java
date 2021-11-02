package com.mojang.realmsclient.exception;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RetryCallException extends RealmsServiceException {
   public static final int f_167328_ = 5;
   public final int f_87787_;

   public RetryCallException(int p_87789_, int p_87790_) {
      super(p_87790_, "Retry operation", -1, "");
      if (p_87789_ >= 0 && p_87789_ <= 120) {
         this.f_87787_ = p_87789_;
      } else {
         this.f_87787_ = 5;
      }

   }
}