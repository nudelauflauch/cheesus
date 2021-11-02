package net.minecraft.server.level.progress;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerChunkProgressListener implements ChunkProgressListener {
   private static final Logger f_9622_ = LogManager.getLogger();
   private final int f_9623_;
   private int f_9624_;
   private long f_9625_;
   private long f_9626_ = Long.MAX_VALUE;

   public LoggerChunkProgressListener(int p_9629_) {
      int i = p_9629_ * 2 + 1;
      this.f_9623_ = i * i;
   }

   public void m_7647_(ChunkPos p_9631_) {
      this.f_9626_ = Util.m_137550_();
      this.f_9625_ = this.f_9626_;
   }

   public void m_5511_(ChunkPos p_9633_, @Nullable ChunkStatus p_9634_) {
      if (p_9634_ == ChunkStatus.f_62326_) {
         ++this.f_9624_;
      }

      int i = this.m_9636_();
      if (Util.m_137550_() > this.f_9626_) {
         this.f_9626_ += 500L;
         f_9622_.info((new TranslatableComponent("menu.preparingSpawn", Mth.m_14045_(i, 0, 100))).getString());
      }

   }

   public void m_142611_() {
   }

   public void m_7646_() {
      f_9622_.info("Time elapsed: {} ms", (long)(Util.m_137550_() - this.f_9625_));
      this.f_9626_ = Long.MAX_VALUE;
   }

   public int m_9636_() {
      return Mth.m_14143_((float)this.f_9624_ * 100.0F / (float)this.f_9623_);
   }
}