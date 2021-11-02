package net.minecraft.client.sounds;

import java.util.concurrent.locks.LockSupport;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoundEngineExecutor extends BlockableEventLoop<Runnable> {
   private Thread f_120329_ = this.m_120334_();
   private volatile boolean f_120330_;

   public SoundEngineExecutor() {
      super("Sound executor");
   }

   private Thread m_120334_() {
      Thread thread = new Thread(this::m_120336_);
      thread.setDaemon(true);
      thread.setName("Sound engine");
      thread.start();
      return thread;
   }

   protected Runnable m_6681_(Runnable p_120341_) {
      return p_120341_;
   }

   protected boolean m_6362_(Runnable p_120339_) {
      return !this.f_120330_;
   }

   protected Thread m_6304_() {
      return this.f_120329_;
   }

   private void m_120336_() {
      while(!this.f_120330_) {
         this.m_18701_(() -> {
            return this.f_120330_;
         });
      }

   }

   protected void m_5667_() {
      LockSupport.park("waiting for tasks");
   }

   public void m_120332_() {
      this.f_120330_ = true;
      this.f_120329_.interrupt();

      try {
         this.f_120329_.join();
      } catch (InterruptedException interruptedexception) {
         Thread.currentThread().interrupt();
      }

      this.m_18698_();
      this.f_120330_ = false;
      this.f_120329_ = this.m_120334_();
   }
}