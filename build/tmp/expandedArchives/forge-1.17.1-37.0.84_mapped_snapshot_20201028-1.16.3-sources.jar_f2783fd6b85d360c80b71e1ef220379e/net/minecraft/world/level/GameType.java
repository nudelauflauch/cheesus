package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Abilities;

public enum GameType {
   SURVIVAL(0, "survival"),
   CREATIVE(1, "creative"),
   ADVENTURE(2, "adventure"),
   SPECTATOR(3, "spectator");

   public static final GameType f_151492_ = SURVIVAL;
   private static final int f_46378_ = -1;
   private final int f_46383_;
   private final String f_46384_;
   private final Component f_151493_;
   private final Component f_151494_;

   private GameType(int p_46390_, String p_46391_) {
      this.f_46383_ = p_46390_;
      this.f_46384_ = p_46391_;
      this.f_151493_ = new TranslatableComponent("selectWorld.gameMode." + p_46391_);
      this.f_151494_ = new TranslatableComponent("gameMode." + p_46391_);
   }

   public int m_46392_() {
      return this.f_46383_;
   }

   public String m_46405_() {
      return this.f_46384_;
   }

   public Component m_151499_() {
      return this.f_151494_;
   }

   public Component m_151500_() {
      return this.f_151493_;
   }

   public void m_46398_(Abilities p_46399_) {
      if (this == CREATIVE) {
         p_46399_.f_35936_ = true;
         p_46399_.f_35937_ = true;
         p_46399_.f_35934_ = true;
      } else if (this == SPECTATOR) {
         p_46399_.f_35936_ = true;
         p_46399_.f_35937_ = false;
         p_46399_.f_35934_ = true;
         p_46399_.f_35935_ = true;
      } else {
         p_46399_.f_35936_ = false;
         p_46399_.f_35937_ = false;
         p_46399_.f_35934_ = false;
         p_46399_.f_35935_ = false;
      }

      p_46399_.f_35938_ = !this.m_46407_();
   }

   public boolean m_46407_() {
      return this == ADVENTURE || this == SPECTATOR;
   }

   public boolean m_46408_() {
      return this == CREATIVE;
   }

   public boolean m_46409_() {
      return this == SURVIVAL || this == ADVENTURE;
   }

   public static GameType m_46393_(int p_46394_) {
      return m_46395_(p_46394_, f_151492_);
   }

   public static GameType m_46395_(int p_46396_, GameType p_46397_) {
      for(GameType gametype : values()) {
         if (gametype.f_46383_ == p_46396_) {
            return gametype;
         }
      }

      return p_46397_;
   }

   public static GameType m_46400_(String p_46401_) {
      return m_46402_(p_46401_, SURVIVAL);
   }

   public static GameType m_46402_(String p_46403_, GameType p_46404_) {
      for(GameType gametype : values()) {
         if (gametype.f_46384_.equals(p_46403_)) {
            return gametype;
         }
      }

      return p_46404_;
   }

   public static int m_151495_(@Nullable GameType p_151496_) {
      return p_151496_ != null ? p_151496_.f_46383_ : -1;
   }

   @Nullable
   public static GameType m_151497_(int p_151498_) {
      return p_151498_ == -1 ? null : m_46393_(p_151498_);
   }
}