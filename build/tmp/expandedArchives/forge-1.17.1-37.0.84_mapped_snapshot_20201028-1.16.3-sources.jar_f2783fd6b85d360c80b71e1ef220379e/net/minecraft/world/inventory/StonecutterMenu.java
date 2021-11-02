package net.minecraft.world.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class StonecutterMenu extends AbstractContainerMenu {
   public static final int f_150665_ = 0;
   public static final int f_150666_ = 1;
   private static final int f_150667_ = 2;
   private static final int f_150668_ = 29;
   private static final int f_150669_ = 29;
   private static final int f_150670_ = 38;
   private final ContainerLevelAccess f_40285_;
   private final DataSlot f_40286_ = DataSlot.m_39401_();
   private final Level f_40287_;
   private List<StonecutterRecipe> f_40288_ = Lists.newArrayList();
   private ItemStack f_40289_ = ItemStack.f_41583_;
   long f_40290_;
   final Slot f_40282_;
   final Slot f_40283_;
   Runnable f_40291_ = () -> {
   };
   public final Container f_40284_ = new SimpleContainer(1) {
      public void m_6596_() {
         super.m_6596_();
         StonecutterMenu.this.m_6199_(this);
         StonecutterMenu.this.f_40291_.run();
      }
   };
   final ResultContainer f_40292_ = new ResultContainer();

   public StonecutterMenu(int p_40294_, Inventory p_40295_) {
      this(p_40294_, p_40295_, ContainerLevelAccess.f_39287_);
   }

   public StonecutterMenu(int p_40297_, Inventory p_40298_, final ContainerLevelAccess p_40299_) {
      super(MenuType.f_39980_, p_40297_);
      this.f_40285_ = p_40299_;
      this.f_40287_ = p_40298_.f_35978_.f_19853_;
      this.f_40282_ = this.m_38897_(new Slot(this.f_40284_, 0, 20, 33));
      this.f_40283_ = this.m_38897_(new Slot(this.f_40292_, 1, 143, 33) {
         public boolean m_5857_(ItemStack p_40362_) {
            return false;
         }

         public void m_142406_(Player p_150672_, ItemStack p_150673_) {
            p_150673_.m_41678_(p_150672_.f_19853_, p_150672_, p_150673_.m_41613_());
            StonecutterMenu.this.f_40292_.m_8015_(p_150672_);
            ItemStack itemstack = StonecutterMenu.this.f_40282_.m_6201_(1);
            if (!itemstack.m_41619_()) {
               StonecutterMenu.this.m_40342_();
            }

            p_40299_.m_39292_((p_40364_, p_40365_) -> {
               long l = p_40364_.m_46467_();
               if (StonecutterMenu.this.f_40290_ != l) {
                  p_40364_.m_5594_((Player)null, p_40365_, SoundEvents.f_12494_, SoundSource.BLOCKS, 1.0F, 1.0F);
                  StonecutterMenu.this.f_40290_ = l;
               }

            });
            super.m_142406_(p_150672_, p_150673_);
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_40298_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_40298_, k, 8 + k * 18, 142));
      }

      this.m_38895_(this.f_40286_);
   }

   public int m_40338_() {
      return this.f_40286_.m_6501_();
   }

   public List<StonecutterRecipe> m_40339_() {
      return this.f_40288_;
   }

   public int m_40340_() {
      return this.f_40288_.size();
   }

   public boolean m_40341_() {
      return this.f_40282_.m_6657_() && !this.f_40288_.isEmpty();
   }

   public boolean m_6875_(Player p_40307_) {
      return m_38889_(this.f_40285_, p_40307_, Blocks.f_50679_);
   }

   public boolean m_6366_(Player p_40309_, int p_40310_) {
      if (this.m_40334_(p_40310_)) {
         this.f_40286_.m_6422_(p_40310_);
         this.m_40342_();
      }

      return true;
   }

   private boolean m_40334_(int p_40335_) {
      return p_40335_ >= 0 && p_40335_ < this.f_40288_.size();
   }

   public void m_6199_(Container p_40302_) {
      ItemStack itemstack = this.f_40282_.m_7993_();
      if (!itemstack.m_150930_(this.f_40289_.m_41720_())) {
         this.f_40289_ = itemstack.m_41777_();
         this.m_40303_(p_40302_, itemstack);
      }

   }

   private void m_40303_(Container p_40304_, ItemStack p_40305_) {
      this.f_40288_.clear();
      this.f_40286_.m_6422_(-1);
      this.f_40283_.m_5852_(ItemStack.f_41583_);
      if (!p_40305_.m_41619_()) {
         this.f_40288_ = this.f_40287_.m_7465_().m_44056_(RecipeType.f_44112_, p_40304_, this.f_40287_);
      }

   }

   void m_40342_() {
      if (!this.f_40288_.isEmpty() && this.m_40334_(this.f_40286_.m_6501_())) {
         StonecutterRecipe stonecutterrecipe = this.f_40288_.get(this.f_40286_.m_6501_());
         this.f_40292_.m_6029_(stonecutterrecipe);
         this.f_40283_.m_5852_(stonecutterrecipe.m_5874_(this.f_40284_));
      } else {
         this.f_40283_.m_5852_(ItemStack.f_41583_);
      }

      this.m_38946_();
   }

   public MenuType<?> m_6772_() {
      return MenuType.f_39980_;
   }

   public void m_40323_(Runnable p_40324_) {
      this.f_40291_ = p_40324_;
   }

   public boolean m_5882_(ItemStack p_40321_, Slot p_40322_) {
      return p_40322_.f_40218_ != this.f_40292_ && super.m_5882_(p_40321_, p_40322_);
   }

   public ItemStack m_7648_(Player p_40328_, int p_40329_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_40329_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         Item item = itemstack1.m_41720_();
         itemstack = itemstack1.m_41777_();
         if (p_40329_ == 1) {
            item.m_7836_(itemstack1, p_40328_.f_19853_, p_40328_);
            if (!this.m_38903_(itemstack1, 2, 38, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_40329_ == 0) {
            if (!this.m_38903_(itemstack1, 2, 38, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.f_40287_.m_7465_().m_44015_(RecipeType.f_44112_, new SimpleContainer(itemstack1), this.f_40287_).isPresent()) {
            if (!this.m_38903_(itemstack1, 0, 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (p_40329_ >= 2 && p_40329_ < 29) {
            if (!this.m_38903_(itemstack1, 29, 38, false)) {
               return ItemStack.f_41583_;
            }
         } else if (p_40329_ >= 29 && p_40329_ < 38 && !this.m_38903_(itemstack1, 2, 29, false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         }

         slot.m_6654_();
         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(p_40328_, itemstack1);
         this.m_38946_();
      }

      return itemstack;
   }

   public void m_6877_(Player p_40326_) {
      super.m_6877_(p_40326_);
      this.f_40292_.m_8016_(1);
      this.f_40285_.m_39292_((p_40313_, p_40314_) -> {
         this.m_150411_(p_40326_, this.f_40284_);
      });
   }
}