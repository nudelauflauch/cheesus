package net.minecraft.world.level.block.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class SignBlockEntity extends BlockEntity {
   public static final int f_155692_ = 4;
   private static final String[] f_155693_ = new String[]{"Text1", "Text2", "Text3", "Text4"};
   private static final String[] f_155694_ = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
   private final Component[] f_59720_ = new Component[]{TextComponent.f_131282_, TextComponent.f_131282_, TextComponent.f_131282_, TextComponent.f_131282_};
   private final Component[] f_155695_ = new Component[]{TextComponent.f_131282_, TextComponent.f_131282_, TextComponent.f_131282_, TextComponent.f_131282_};
   private boolean f_59721_ = true;
   @Nullable
   private UUID f_59722_;
   @Nullable
   private FormattedCharSequence[] f_59723_;
   private boolean f_155696_;
   private DyeColor f_59724_ = DyeColor.BLACK;
   private boolean f_155697_;

   public SignBlockEntity(BlockPos p_155700_, BlockState p_155701_) {
      super(BlockEntityType.f_58924_, p_155700_, p_155701_);
   }

   public CompoundTag m_6945_(CompoundTag p_59745_) {
      super.m_6945_(p_59745_);

      for(int i = 0; i < 4; ++i) {
         Component component = this.f_59720_[i];
         String s = Component.Serializer.m_130703_(component);
         p_59745_.m_128359_(f_155693_[i], s);
         Component component1 = this.f_155695_[i];
         if (!component1.equals(component)) {
            p_59745_.m_128359_(f_155694_[i], Component.Serializer.m_130703_(component1));
         }
      }

      p_59745_.m_128359_("Color", this.f_59724_.m_41065_());
      p_59745_.m_128379_("GlowingText", this.f_155697_);
      return p_59745_;
   }

   public void m_142466_(CompoundTag p_155716_) {
      this.f_59721_ = false;
      super.m_142466_(p_155716_);
      this.f_59724_ = DyeColor.m_41057_(p_155716_.m_128461_("Color"), DyeColor.BLACK);

      for(int i = 0; i < 4; ++i) {
         String s = p_155716_.m_128461_(f_155693_[i]);
         Component component = this.m_155711_(s);
         this.f_59720_[i] = component;
         String s1 = f_155694_[i];
         if (p_155716_.m_128425_(s1, 8)) {
            this.f_155695_[i] = this.m_155711_(p_155716_.m_128461_(s1));
         } else {
            this.f_155695_[i] = component;
         }
      }

      this.f_59723_ = null;
      this.f_155697_ = p_155716_.m_128471_("GlowingText");
   }

   private Component m_155711_(String p_155712_) {
      Component component = this.m_155720_(p_155712_);
      if (this.f_58857_ instanceof ServerLevel) {
         try {
            return ComponentUtils.m_130731_(this.m_59735_((ServerPlayer)null), component, (Entity)null, 0);
         } catch (CommandSyntaxException commandsyntaxexception) {
         }
      }

      return component;
   }

   private Component m_155720_(String p_155721_) {
      try {
         Component component = Component.Serializer.m_130701_(p_155721_);
         if (component != null) {
            return component;
         }
      } catch (Exception exception) {
      }

      return TextComponent.f_131282_;
   }

   public Component m_155706_(int p_155707_, boolean p_155708_) {
      return this.m_155724_(p_155708_)[p_155707_];
   }

   public void m_59732_(int p_59733_, Component p_59734_) {
      this.m_155702_(p_59733_, p_59734_, p_59734_);
   }

   public void m_155702_(int p_155703_, Component p_155704_, Component p_155705_) {
      this.f_59720_[p_155703_] = p_155704_;
      this.f_155695_[p_155703_] = p_155705_;
      this.f_59723_ = null;
   }

   public FormattedCharSequence[] m_155717_(boolean p_155718_, Function<Component, FormattedCharSequence> p_155719_) {
      if (this.f_59723_ == null || this.f_155696_ != p_155718_) {
         this.f_155696_ = p_155718_;
         this.f_59723_ = new FormattedCharSequence[4];

         for(int i = 0; i < 4; ++i) {
            this.f_59723_[i] = p_155719_.apply(this.m_155706_(i, p_155718_));
         }
      }

      return this.f_59723_;
   }

   private Component[] m_155724_(boolean p_155725_) {
      return p_155725_ ? this.f_155695_ : this.f_59720_;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 9, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   public boolean m_6326_() {
      return true;
   }

   public boolean m_59751_() {
      return this.f_59721_;
   }

   public void m_59746_(boolean p_59747_) {
      this.f_59721_ = p_59747_;
      if (!p_59747_) {
         this.f_59722_ = null;
      }

   }

   public void m_155713_(UUID p_155714_) {
      this.f_59722_ = p_155714_;
   }

   @Nullable
   public UUID m_155726_() {
      return this.f_59722_;
   }

   public boolean m_155709_(ServerPlayer p_155710_) {
      for(Component component : this.m_155724_(p_155710_.m_143387_())) {
         Style style = component.m_7383_();
         ClickEvent clickevent = style.m_131182_();
         if (clickevent != null && clickevent.m_130622_() == ClickEvent.Action.RUN_COMMAND) {
            p_155710_.m_20194_().m_129892_().m_82117_(this.m_59735_(p_155710_), clickevent.m_130623_());
         }
      }

      return true;
   }

   public CommandSourceStack m_59735_(@Nullable ServerPlayer p_59736_) {
      String s = p_59736_ == null ? "Sign" : p_59736_.m_7755_().getString();
      Component component = (Component)(p_59736_ == null ? new TextComponent("Sign") : p_59736_.m_5446_());
      return new CommandSourceStack(CommandSource.f_80164_, Vec3.m_82512_(this.f_58858_), Vec2.f_82462_, (ServerLevel)this.f_58857_, 2, s, component, this.f_58857_.m_142572_(), p_59736_);
   }

   public DyeColor m_59753_() {
      return this.f_59724_;
   }

   public boolean m_59739_(DyeColor p_59740_) {
      if (p_59740_ != this.m_59753_()) {
         this.f_59724_ = p_59740_;
         this.m_155728_();
         return true;
      } else {
         return false;
      }
   }

   public boolean m_155727_() {
      return this.f_155697_;
   }

   public boolean m_155722_(boolean p_155723_) {
      if (this.f_155697_ != p_155723_) {
         this.f_155697_ = p_155723_;
         this.m_155728_();
         return true;
      } else {
         return false;
      }
   }

   private void m_155728_() {
      this.m_6596_();
      this.f_58857_.m_7260_(this.m_58899_(), this.m_58900_(), this.m_58900_(), 3);
   }
}