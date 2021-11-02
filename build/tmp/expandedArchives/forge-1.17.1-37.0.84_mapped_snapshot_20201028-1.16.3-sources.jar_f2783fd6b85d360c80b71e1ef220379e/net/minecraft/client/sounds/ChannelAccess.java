package net.minecraft.client.sounds;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChannelAccess {
   private final Set<ChannelAccess.ChannelHandle> f_120121_ = Sets.newIdentityHashSet();
   final Library f_120122_;
   final Executor f_120123_;

   public ChannelAccess(Library p_120125_, Executor p_120126_) {
      this.f_120122_ = p_120125_;
      this.f_120123_ = p_120126_;
   }

   public CompletableFuture<ChannelAccess.ChannelHandle> m_120128_(Library.Pool p_120129_) {
      CompletableFuture<ChannelAccess.ChannelHandle> completablefuture = new CompletableFuture<>();
      this.f_120123_.execute(() -> {
         Channel channel = this.f_120122_.m_83697_(p_120129_);
         if (channel != null) {
            ChannelAccess.ChannelHandle channelaccess$channelhandle = new ChannelAccess.ChannelHandle(channel);
            this.f_120121_.add(channelaccess$channelhandle);
            completablefuture.complete(channelaccess$channelhandle);
         } else {
            completablefuture.complete((ChannelAccess.ChannelHandle)null);
         }

      });
      return completablefuture;
   }

   public void m_120137_(Consumer<Stream<Channel>> p_120138_) {
      this.f_120123_.execute(() -> {
         p_120138_.accept(this.f_120121_.stream().map((p_174978_) -> {
            return p_174978_.f_120146_;
         }).filter(Objects::nonNull));
      });
   }

   public void m_120127_() {
      this.f_120123_.execute(() -> {
         Iterator<ChannelAccess.ChannelHandle> iterator = this.f_120121_.iterator();

         while(iterator.hasNext()) {
            ChannelAccess.ChannelHandle channelaccess$channelhandle = iterator.next();
            channelaccess$channelhandle.f_120146_.m_83682_();
            if (channelaccess$channelhandle.f_120146_.m_83680_()) {
               channelaccess$channelhandle.m_120156_();
               iterator.remove();
            }
         }

      });
   }

   public void m_120139_() {
      this.f_120121_.forEach(ChannelAccess.ChannelHandle::m_120156_);
      this.f_120121_.clear();
   }

   @OnlyIn(Dist.CLIENT)
   public class ChannelHandle {
      @Nullable
      Channel f_120146_;
      private boolean f_120147_;

      public boolean m_120151_() {
         return this.f_120147_;
      }

      public ChannelHandle(Channel p_120150_) {
         this.f_120146_ = p_120150_;
      }

      public void m_120154_(Consumer<Channel> p_120155_) {
         ChannelAccess.this.f_120123_.execute(() -> {
            if (this.f_120146_ != null) {
               p_120155_.accept(this.f_120146_);
            }

         });
      }

      public void m_120156_() {
         this.f_120147_ = true;
         ChannelAccess.this.f_120122_.m_83695_(this.f_120146_);
         this.f_120146_ = null;
      }
   }
}