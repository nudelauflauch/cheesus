package net.minecraft.world.inventory;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AbstractContainerMenu {
   public static final int f_150385_ = -999;
   public static final int f_150386_ = 0;
   public static final int f_150387_ = 1;
   public static final int f_150388_ = 2;
   public static final int f_150389_ = 0;
   public static final int f_150390_ = 1;
   public static final int f_150391_ = 2;
   public static final int f_150392_ = Integer.MAX_VALUE;
   private final NonNullList<ItemStack> f_38841_ = NonNullList.m_122779_();
   public final NonNullList<Slot> f_38839_ = NonNullList.m_122779_();
   private final List<DataSlot> f_38842_ = Lists.newArrayList();
   private ItemStack f_150393_ = ItemStack.f_41583_;
   private final NonNullList<ItemStack> f_150394_ = NonNullList.m_122779_();
   private final IntList f_150395_ = new IntArrayList();
   private ItemStack f_150396_ = ItemStack.f_41583_;
   private int f_182405_;
   @Nullable
   private final MenuType<?> f_38843_;
   public final int f_38840_;
   private int f_38845_ = -1;
   private int f_38846_;
   private final Set<Slot> f_38847_ = Sets.newHashSet();
   private final List<ContainerListener> f_38848_ = Lists.newArrayList();
   @Nullable
   private ContainerSynchronizer f_150397_;
   private boolean f_150398_;

   protected AbstractContainerMenu(@Nullable MenuType<?> p_38851_, int p_38852_) {
      this.f_38843_ = p_38851_;
      this.f_38840_ = p_38852_;
   }

   protected static boolean m_38889_(ContainerLevelAccess p_38890_, Player p_38891_, Block p_38892_) {
      return p_38890_.m_39299_((p_38916_, p_38917_) -> {
         return !p_38916_.m_8055_(p_38917_).m_60713_(p_38892_) ? false : p_38891_.m_20275_((double)p_38917_.m_123341_() + 0.5D, (double)p_38917_.m_123342_() + 0.5D, (double)p_38917_.m_123343_() + 0.5D) <= 64.0D;
      }, true);
   }

   public MenuType<?> m_6772_() {
      if (this.f_38843_ == null) {
         throw new UnsupportedOperationException("Unable to construct this menu by type");
      } else {
         return this.f_38843_;
      }
   }

   protected static void m_38869_(Container p_38870_, int p_38871_) {
      int i = p_38870_.m_6643_();
      if (i < p_38871_) {
         throw new IllegalArgumentException("Container size " + i + " is smaller than expected " + p_38871_);
      }
   }

   protected static void m_38886_(ContainerData p_38887_, int p_38888_) {
      int i = p_38887_.m_6499_();
      if (i < p_38888_) {
         throw new IllegalArgumentException("Container data count " + i + " is smaller than expected " + p_38888_);
      }
   }

   protected Slot m_38897_(Slot p_38898_) {
      p_38898_.f_40219_ = this.f_38839_.size();
      this.f_38839_.add(p_38898_);
      this.f_38841_.add(ItemStack.f_41583_);
      this.f_150394_.add(ItemStack.f_41583_);
      return p_38898_;
   }

   protected DataSlot m_38895_(DataSlot p_38896_) {
      this.f_38842_.add(p_38896_);
      this.f_150395_.add(0);
      return p_38896_;
   }

   protected void m_38884_(ContainerData p_38885_) {
      for(int i = 0; i < p_38885_.m_6499_(); ++i) {
         this.m_38895_(DataSlot.m_39403_(p_38885_, i));
      }

   }

   public void m_38893_(ContainerListener p_38894_) {
      if (!this.f_38848_.contains(p_38894_)) {
         this.f_38848_.add(p_38894_);
         this.m_38946_();
      }
   }

   public void m_150416_(ContainerSynchronizer p_150417_) {
      this.f_150397_ = p_150417_;
      this.m_150429_();
   }

   public void m_150429_() {
      int i = 0;

      for(int j = this.f_38839_.size(); i < j; ++i) {
         this.f_150394_.set(i, this.f_38839_.get(i).m_7993_().m_41777_());
      }

      this.f_150396_ = this.m_142621_().m_41777_();
      i = 0;

      for(int k = this.f_38842_.size(); i < k; ++i) {
         this.f_150395_.set(i, this.f_38842_.get(i).m_6501_());
      }

      if (this.f_150397_ != null) {
         this.f_150397_.m_142589_(this, this.f_150394_, this.f_150396_, this.f_150395_.toIntArray());
      }

   }

   public void m_38943_(ContainerListener p_38944_) {
      this.f_38848_.remove(p_38944_);
   }

   public NonNullList<ItemStack> m_38927_() {
      NonNullList<ItemStack> nonnulllist = NonNullList.m_122779_();

      for(Slot slot : this.f_38839_) {
         nonnulllist.add(slot.m_7993_());
      }

      return nonnulllist;
   }

   public void m_38946_() {
      for(int i = 0; i < this.f_38839_.size(); ++i) {
         ItemStack itemstack = this.f_38839_.get(i).m_7993_();
         Supplier<ItemStack> supplier = Suppliers.memoize(itemstack::m_41777_);
         this.m_150407_(i, itemstack, supplier);
         this.m_150435_(i, itemstack, supplier);
      }

      this.m_150445_();

      for(int j = 0; j < this.f_38842_.size(); ++j) {
         DataSlot dataslot = this.f_38842_.get(j);
         int k = dataslot.m_6501_();
         if (dataslot.m_39409_()) {
            this.m_182420_(j, k);
         }

         this.m_150440_(j, k);
      }

   }

   public void m_182423_() {
      for(int i = 0; i < this.f_38839_.size(); ++i) {
         ItemStack itemstack = this.f_38839_.get(i).m_7993_();
         this.m_150407_(i, itemstack, itemstack::m_41777_);
      }

      for(int j = 0; j < this.f_38842_.size(); ++j) {
         DataSlot dataslot = this.f_38842_.get(j);
         if (dataslot.m_39409_()) {
            this.m_182420_(j, dataslot.m_6501_());
         }
      }

      this.m_150429_();
   }

   private void m_182420_(int p_182421_, int p_182422_) {
      for(ContainerListener containerlistener : this.f_38848_) {
         containerlistener.m_142153_(this, p_182421_, p_182422_);
      }

   }

   private void m_150407_(int p_150408_, ItemStack p_150409_, Supplier<ItemStack> p_150410_) {
      ItemStack itemstack = this.f_38841_.get(p_150408_);
      if (!ItemStack.m_41728_(itemstack, p_150409_)) {
         boolean clientStackChanged = !p_150409_.equals(itemstack, true);
         ItemStack itemstack1 = p_150410_.get();
         this.f_38841_.set(p_150408_, itemstack1);

         if(clientStackChanged)
         for(ContainerListener containerlistener : this.f_38848_) {
            containerlistener.m_7934_(this, p_150408_, itemstack1);
         }
      }

   }

   private void m_150435_(int p_150436_, ItemStack p_150437_, Supplier<ItemStack> p_150438_) {
      if (!this.f_150398_) {
         ItemStack itemstack = this.f_150394_.get(p_150436_);
         if (!ItemStack.m_41728_(itemstack, p_150437_)) {
            ItemStack itemstack1 = p_150438_.get();
            this.f_150394_.set(p_150436_, itemstack1);
            if (this.f_150397_ != null) {
               this.f_150397_.m_142074_(this, p_150436_, itemstack1);
            }
         }

      }
   }

   private void m_150440_(int p_150441_, int p_150442_) {
      if (!this.f_150398_) {
         int i = this.f_150395_.getInt(p_150441_);
         if (i != p_150442_) {
            this.f_150395_.set(p_150441_, p_150442_);
            if (this.f_150397_ != null) {
               this.f_150397_.m_142145_(this, p_150441_, p_150442_);
            }
         }

      }
   }

   private void m_150445_() {
      if (!this.f_150398_) {
         if (!ItemStack.m_41728_(this.m_142621_(), this.f_150396_)) {
            this.f_150396_ = this.m_142621_().m_41777_();
            if (this.f_150397_ != null) {
               this.f_150397_.m_142529_(this, this.f_150396_);
            }
         }

      }
   }

   public void m_150404_(int p_150405_, ItemStack p_150406_) {
      this.f_150394_.set(p_150405_, p_150406_.m_41777_());
   }

   public void m_182414_(int p_182415_, ItemStack p_182416_) {
      this.f_150394_.set(p_182415_, p_182416_);
   }

   public void m_150422_(ItemStack p_150423_) {
      this.f_150396_ = p_150423_.m_41777_();
   }

   public boolean m_6366_(Player p_38875_, int p_38876_) {
      return false;
   }

   public Slot m_38853_(int p_38854_) {
      return this.f_38839_.get(p_38854_);
   }

   public ItemStack m_7648_(Player p_38941_, int p_38942_) {
      return this.f_38839_.get(p_38942_).m_7993_();
   }

   public void m_150399_(int p_150400_, int p_150401_, ClickType p_150402_, Player p_150403_) {
      try {
         this.m_150430_(p_150400_, p_150401_, p_150402_, p_150403_);
      } catch (Exception exception) {
         CrashReport crashreport = CrashReport.m_127521_(exception, "Container click");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Click info");
         crashreportcategory.m_128165_("Menu Type", () -> {
            return this.f_38843_ != null ? Registry.f_122863_.m_7981_(this.f_38843_).toString() : "<no type>";
         });
         crashreportcategory.m_128165_("Menu Class", () -> {
            return this.getClass().getCanonicalName();
         });
         crashreportcategory.m_128159_("Slot Count", this.f_38839_.size());
         crashreportcategory.m_128159_("Slot", p_150400_);
         crashreportcategory.m_128159_("Button", p_150401_);
         crashreportcategory.m_128159_("Type", p_150402_);
         throw new ReportedException(crashreport);
      }
   }

   private void m_150430_(int p_150431_, int p_150432_, ClickType p_150433_, Player p_150434_) {
      Inventory inventory = p_150434_.m_150109_();
      if (p_150433_ == ClickType.QUICK_CRAFT) {
         int i = this.f_38846_;
         this.f_38846_ = m_38947_(p_150432_);
         if ((i != 1 || this.f_38846_ != 2) && i != this.f_38846_) {
            this.m_38951_();
         } else if (this.m_142621_().m_41619_()) {
            this.m_38951_();
         } else if (this.f_38846_ == 0) {
            this.f_38845_ = m_38928_(p_150432_);
            if (m_38862_(this.f_38845_, p_150434_)) {
               this.f_38846_ = 1;
               this.f_38847_.clear();
            } else {
               this.m_38951_();
            }
         } else if (this.f_38846_ == 1) {
            Slot slot = this.f_38839_.get(p_150431_);
            ItemStack itemstack = this.m_142621_();
            if (m_38899_(slot, itemstack, true) && slot.m_5857_(itemstack) && (this.f_38845_ == 2 || itemstack.m_41613_() > this.f_38847_.size()) && this.m_5622_(slot)) {
               this.f_38847_.add(slot);
            }
         } else if (this.f_38846_ == 2) {
            if (!this.f_38847_.isEmpty()) {
               if (this.f_38847_.size() == 1) {
                  int l = (this.f_38847_.iterator().next()).f_40219_;
                  this.m_38951_();
                  this.m_150430_(l, this.f_38845_, ClickType.PICKUP, p_150434_);
                  return;
               }

               ItemStack itemstack3 = this.m_142621_().m_41777_();
               int j1 = this.m_142621_().m_41613_();

               for(Slot slot1 : this.f_38847_) {
                  ItemStack itemstack1 = this.m_142621_();
                  if (slot1 != null && m_38899_(slot1, itemstack1, true) && slot1.m_5857_(itemstack1) && (this.f_38845_ == 2 || itemstack1.m_41613_() >= this.f_38847_.size()) && this.m_5622_(slot1)) {
                     ItemStack itemstack2 = itemstack3.m_41777_();
                     int j = slot1.m_6657_() ? slot1.m_7993_().m_41613_() : 0;
                     m_38922_(this.f_38847_, this.f_38845_, itemstack2, j);
                     int k = Math.min(itemstack2.m_41741_(), slot1.m_5866_(itemstack2));
                     if (itemstack2.m_41613_() > k) {
                        itemstack2.m_41764_(k);
                     }

                     j1 -= itemstack2.m_41613_() - j;
                     slot1.m_5852_(itemstack2);
                  }
               }

               itemstack3.m_41764_(j1);
               this.m_142503_(itemstack3);
            }

            this.m_38951_();
         } else {
            this.m_38951_();
         }
      } else if (this.f_38846_ != 0) {
         this.m_38951_();
      } else if ((p_150433_ == ClickType.PICKUP || p_150433_ == ClickType.QUICK_MOVE) && (p_150432_ == 0 || p_150432_ == 1)) {
         ClickAction clickaction = p_150432_ == 0 ? ClickAction.PRIMARY : ClickAction.SECONDARY;
         if (p_150431_ == -999) {
            if (!this.m_142621_().m_41619_()) {
               if (clickaction == ClickAction.PRIMARY) {
                  p_150434_.m_36176_(this.m_142621_(), true);
                  this.m_142503_(ItemStack.f_41583_);
               } else {
                  p_150434_.m_36176_(this.m_142621_().m_41620_(1), true);
               }
            }
         } else if (p_150433_ == ClickType.QUICK_MOVE) {
            if (p_150431_ < 0) {
               return;
            }

            Slot slot6 = this.f_38839_.get(p_150431_);
            if (!slot6.m_8010_(p_150434_)) {
               return;
            }

            for(ItemStack itemstack9 = this.m_7648_(p_150434_, p_150431_); !itemstack9.m_41619_() && ItemStack.m_41746_(slot6.m_7993_(), itemstack9); itemstack9 = this.m_7648_(p_150434_, p_150431_)) {
            }
         } else {
            if (p_150431_ < 0) {
               return;
            }

            Slot slot7 = this.f_38839_.get(p_150431_);
            ItemStack itemstack10 = slot7.m_7993_();
            ItemStack itemstack11 = this.m_142621_();
            p_150434_.m_141945_(itemstack11, slot7.m_7993_(), clickaction);
            if (!itemstack11.m_150926_(slot7, clickaction, p_150434_) && !itemstack10.m_150932_(itemstack11, slot7, clickaction, p_150434_, this.m_150446_())) {
               if (itemstack10.m_41619_()) {
                  if (!itemstack11.m_41619_()) {
                     int l2 = clickaction == ClickAction.PRIMARY ? itemstack11.m_41613_() : 1;
                     this.m_142503_(slot7.m_150656_(itemstack11, l2));
                  }
               } else if (slot7.m_8010_(p_150434_)) {
                  if (itemstack11.m_41619_()) {
                     int i3 = clickaction == ClickAction.PRIMARY ? itemstack10.m_41613_() : (itemstack10.m_41613_() + 1) / 2;
                     Optional<ItemStack> optional1 = slot7.m_150641_(i3, Integer.MAX_VALUE, p_150434_);
                     optional1.ifPresent((p_150421_) -> {
                        this.m_142503_(p_150421_);
                        slot7.m_142406_(p_150434_, p_150421_);
                     });
                  } else if (slot7.m_5857_(itemstack11)) {
                     if (ItemStack.m_150942_(itemstack10, itemstack11)) {
                        int j3 = clickaction == ClickAction.PRIMARY ? itemstack11.m_41613_() : 1;
                        this.m_142503_(slot7.m_150656_(itemstack11, j3));
                     } else if (itemstack11.m_41613_() <= slot7.m_5866_(itemstack11)) {
                        slot7.m_5852_(itemstack11);
                        this.m_142503_(itemstack10);
                     }
                  } else if (ItemStack.m_150942_(itemstack10, itemstack11)) {
                     Optional<ItemStack> optional = slot7.m_150641_(itemstack10.m_41613_(), itemstack11.m_41741_() - itemstack11.m_41613_(), p_150434_);
                     optional.ifPresent((p_150428_) -> {
                        itemstack11.m_41769_(p_150428_.m_41613_());
                        slot7.m_142406_(p_150434_, p_150428_);
                     });
                  }
               }
            }

            slot7.m_6654_();
         }
      } else if (p_150433_ == ClickType.SWAP) {
         Slot slot2 = this.f_38839_.get(p_150431_);
         ItemStack itemstack4 = inventory.m_8020_(p_150432_);
         ItemStack itemstack7 = slot2.m_7993_();
         if (!itemstack4.m_41619_() || !itemstack7.m_41619_()) {
            if (itemstack4.m_41619_()) {
               if (slot2.m_8010_(p_150434_)) {
                  inventory.m_6836_(p_150432_, itemstack7);
                  slot2.m_6405_(itemstack7.m_41613_());
                  slot2.m_5852_(ItemStack.f_41583_);
                  slot2.m_142406_(p_150434_, itemstack7);
               }
            } else if (itemstack7.m_41619_()) {
               if (slot2.m_5857_(itemstack4)) {
                  int l1 = slot2.m_5866_(itemstack4);
                  if (itemstack4.m_41613_() > l1) {
                     slot2.m_5852_(itemstack4.m_41620_(l1));
                  } else {
                     slot2.m_5852_(itemstack4);
                     inventory.m_6836_(p_150432_, ItemStack.f_41583_);
                  }
               }
            } else if (slot2.m_8010_(p_150434_) && slot2.m_5857_(itemstack4)) {
               int i2 = slot2.m_5866_(itemstack4);
               if (itemstack4.m_41613_() > i2) {
                  slot2.m_5852_(itemstack4.m_41620_(i2));
                  slot2.m_142406_(p_150434_, itemstack7);
                  if (!inventory.m_36054_(itemstack7)) {
                     p_150434_.m_36176_(itemstack7, true);
                  }
               } else {
                  slot2.m_5852_(itemstack4);
                  inventory.m_6836_(p_150432_, itemstack7);
                  slot2.m_142406_(p_150434_, itemstack7);
               }
            }
         }
      } else if (p_150433_ == ClickType.CLONE && p_150434_.m_150110_().f_35937_ && this.m_142621_().m_41619_() && p_150431_ >= 0) {
         Slot slot5 = this.f_38839_.get(p_150431_);
         if (slot5.m_6657_()) {
            ItemStack itemstack6 = slot5.m_7993_().m_41777_();
            itemstack6.m_41764_(itemstack6.m_41741_());
            this.m_142503_(itemstack6);
         }
      } else if (p_150433_ == ClickType.THROW && this.m_142621_().m_41619_() && p_150431_ >= 0) {
         Slot slot4 = this.f_38839_.get(p_150431_);
         int i1 = p_150432_ == 0 ? 1 : slot4.m_7993_().m_41613_();
         ItemStack itemstack8 = slot4.m_150647_(i1, Integer.MAX_VALUE, p_150434_);
         p_150434_.m_36176_(itemstack8, true);
      } else if (p_150433_ == ClickType.PICKUP_ALL && p_150431_ >= 0) {
         Slot slot3 = this.f_38839_.get(p_150431_);
         ItemStack itemstack5 = this.m_142621_();
         if (!itemstack5.m_41619_() && (!slot3.m_6657_() || !slot3.m_8010_(p_150434_))) {
            int k1 = p_150432_ == 0 ? 0 : this.f_38839_.size() - 1;
            int j2 = p_150432_ == 0 ? 1 : -1;

            for(int k2 = 0; k2 < 2; ++k2) {
               for(int k3 = k1; k3 >= 0 && k3 < this.f_38839_.size() && itemstack5.m_41613_() < itemstack5.m_41741_(); k3 += j2) {
                  Slot slot8 = this.f_38839_.get(k3);
                  if (slot8.m_6657_() && m_38899_(slot8, itemstack5, true) && slot8.m_8010_(p_150434_) && this.m_5882_(itemstack5, slot8)) {
                     ItemStack itemstack12 = slot8.m_7993_();
                     if (k2 != 0 || itemstack12.m_41613_() != itemstack12.m_41741_()) {
                        ItemStack itemstack13 = slot8.m_150647_(itemstack12.m_41613_(), itemstack5.m_41741_() - itemstack5.m_41613_(), p_150434_);
                        itemstack5.m_41769_(itemstack13.m_41613_());
                     }
                  }
               }
            }
         }
      }

   }

   private SlotAccess m_150446_() {
      return new SlotAccess() {
         public ItemStack m_142196_() {
            return AbstractContainerMenu.this.m_142621_();
         }

         public boolean m_142104_(ItemStack p_150452_) {
            AbstractContainerMenu.this.m_142503_(p_150452_);
            return true;
         }
      };
   }

   public boolean m_5882_(ItemStack p_38908_, Slot p_38909_) {
      return true;
   }

   public void m_6877_(Player p_38940_) {
      if (p_38940_ instanceof ServerPlayer) {
         ItemStack itemstack = this.m_142621_();
         if (!itemstack.m_41619_()) {
            if (p_38940_.m_6084_() && !((ServerPlayer)p_38940_).m_9232_()) {
               p_38940_.m_150109_().m_150079_(itemstack);
            } else {
               p_38940_.m_36176_(itemstack, false);
            }

            this.m_142503_(ItemStack.f_41583_);
         }
      }

   }

   protected void m_150411_(Player p_150412_, Container p_150413_) {
      if (!p_150412_.m_6084_() || p_150412_ instanceof ServerPlayer && ((ServerPlayer)p_150412_).m_9232_()) {
         for(int j = 0; j < p_150413_.m_6643_(); ++j) {
            p_150412_.m_36176_(p_150413_.m_8016_(j), false);
         }

      } else {
         for(int i = 0; i < p_150413_.m_6643_(); ++i) {
            Inventory inventory = p_150412_.m_150109_();
            if (inventory.f_35978_ instanceof ServerPlayer) {
               inventory.m_150079_(p_150413_.m_8016_(i));
            }
         }

      }
   }

   public void m_6199_(Container p_38868_) {
      this.m_38946_();
   }

   public void m_182406_(int p_182407_, int p_182408_, ItemStack p_182409_) {
      this.m_38853_(p_182407_).m_5852_(p_182409_);
      this.f_182405_ = p_182408_;
   }

   public void m_182410_(int p_182411_, List<ItemStack> p_182412_, ItemStack p_182413_) {
      for(int i = 0; i < p_182412_.size(); ++i) {
         this.m_38853_(i).m_5852_(p_182412_.get(i));
      }

      this.f_150393_ = p_182413_;
      this.f_182405_ = p_182411_;
   }

   public void m_7511_(int p_38855_, int p_38856_) {
      this.f_38842_.get(p_38855_).m_6422_(p_38856_);
   }

   public abstract boolean m_6875_(Player p_38874_);

   protected boolean m_38903_(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
      boolean flag = false;
      int i = p_38905_;
      if (p_38907_) {
         i = p_38906_ - 1;
      }

      if (p_38904_.m_41753_()) {
         while(!p_38904_.m_41619_()) {
            if (p_38907_) {
               if (i < p_38905_) {
                  break;
               }
            } else if (i >= p_38906_) {
               break;
            }

            Slot slot = this.f_38839_.get(i);
            ItemStack itemstack = slot.m_7993_();
            if (!itemstack.m_41619_() && ItemStack.m_150942_(p_38904_, itemstack)) {
               int j = itemstack.m_41613_() + p_38904_.m_41613_();
               int maxSize = Math.min(slot.m_6641_(), p_38904_.m_41741_());
               if (j <= maxSize) {
                  p_38904_.m_41764_(0);
                  itemstack.m_41764_(j);
                  slot.m_6654_();
                  flag = true;
               } else if (itemstack.m_41613_() < maxSize) {
                  p_38904_.m_41774_(maxSize - itemstack.m_41613_());
                  itemstack.m_41764_(maxSize);
                  slot.m_6654_();
                  flag = true;
               }
            }

            if (p_38907_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      if (!p_38904_.m_41619_()) {
         if (p_38907_) {
            i = p_38906_ - 1;
         } else {
            i = p_38905_;
         }

         while(true) {
            if (p_38907_) {
               if (i < p_38905_) {
                  break;
               }
            } else if (i >= p_38906_) {
               break;
            }

            Slot slot1 = this.f_38839_.get(i);
            ItemStack itemstack1 = slot1.m_7993_();
            if (itemstack1.m_41619_() && slot1.m_5857_(p_38904_)) {
               if (p_38904_.m_41613_() > slot1.m_6641_()) {
                  slot1.m_5852_(p_38904_.m_41620_(slot1.m_6641_()));
               } else {
                  slot1.m_5852_(p_38904_.m_41620_(p_38904_.m_41613_()));
               }

               slot1.m_6654_();
               flag = true;
               break;
            }

            if (p_38907_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      return flag;
   }

   public static int m_38928_(int p_38929_) {
      return p_38929_ >> 2 & 3;
   }

   public static int m_38947_(int p_38948_) {
      return p_38948_ & 3;
   }

   public static int m_38930_(int p_38931_, int p_38932_) {
      return p_38931_ & 3 | (p_38932_ & 3) << 2;
   }

   public static boolean m_38862_(int p_38863_, Player p_38864_) {
      if (p_38863_ == 0) {
         return true;
      } else if (p_38863_ == 1) {
         return true;
      } else {
         return p_38863_ == 2 && p_38864_.m_150110_().f_35937_;
      }
   }

   protected void m_38951_() {
      this.f_38846_ = 0;
      this.f_38847_.clear();
   }

   public static boolean m_38899_(@Nullable Slot p_38900_, ItemStack p_38901_, boolean p_38902_) {
      boolean flag = p_38900_ == null || !p_38900_.m_6657_();
      if (!flag && ItemStack.m_150942_(p_38901_, p_38900_.m_7993_())) {
         return p_38900_.m_7993_().m_41613_() + (p_38902_ ? 0 : p_38901_.m_41613_()) <= p_38901_.m_41741_();
      } else {
         return flag;
      }
   }

   public static void m_38922_(Set<Slot> p_38923_, int p_38924_, ItemStack p_38925_, int p_38926_) {
      switch(p_38924_) {
      case 0:
         p_38925_.m_41764_(Mth.m_14143_((float)p_38925_.m_41613_() / (float)p_38923_.size()));
         break;
      case 1:
         p_38925_.m_41764_(1);
         break;
      case 2:
         p_38925_.m_41764_(p_38925_.m_41741_());
      }

      p_38925_.m_41769_(p_38926_);
   }

   public boolean m_5622_(Slot p_38945_) {
      return true;
   }

   public static int m_38918_(@Nullable BlockEntity p_38919_) {
      return p_38919_ instanceof Container ? m_38938_((Container)p_38919_) : 0;
   }

   public static int m_38938_(@Nullable Container p_38939_) {
      if (p_38939_ == null) {
         return 0;
      } else {
         int i = 0;
         float f = 0.0F;

         for(int j = 0; j < p_38939_.m_6643_(); ++j) {
            ItemStack itemstack = p_38939_.m_8020_(j);
            if (!itemstack.m_41619_()) {
               f += (float)itemstack.m_41613_() / (float)Math.min(p_38939_.m_6893_(), itemstack.m_41741_());
               ++i;
            }
         }

         f = f / (float)p_38939_.m_6643_();
         return Mth.m_14143_(f * 14.0F) + (i > 0 ? 1 : 0);
      }
   }

   public void m_142503_(ItemStack p_150439_) {
      this.f_150393_ = p_150439_;
   }

   public ItemStack m_142621_() {
      return this.f_150393_;
   }

   public void m_150443_() {
      this.f_150398_ = true;
   }

   public void m_150444_() {
      this.f_150398_ = false;
   }

   public void m_150414_(AbstractContainerMenu p_150415_) {
      Table<Container, Integer, Integer> table = HashBasedTable.create();

      for(int i = 0; i < p_150415_.f_38839_.size(); ++i) {
         Slot slot = p_150415_.f_38839_.get(i);
         table.put(slot.f_40218_, slot.m_150661_(), i);
      }

      for(int j = 0; j < this.f_38839_.size(); ++j) {
         Slot slot1 = this.f_38839_.get(j);
         Integer integer = table.get(slot1.f_40218_, slot1.m_150661_());
         if (integer != null) {
            this.f_38841_.set(j, p_150415_.f_38841_.get(integer));
            this.f_150394_.set(j, p_150415_.f_150394_.get(integer));
         }
      }

   }

   public OptionalInt m_182417_(Container p_182418_, int p_182419_) {
      for(int i = 0; i < this.f_38839_.size(); ++i) {
         Slot slot = this.f_38839_.get(i);
         if (slot.f_40218_ == p_182418_ && p_182419_ == slot.m_150661_()) {
            return OptionalInt.of(i);
         }
      }

      return OptionalInt.empty();
   }

   public int m_182424_() {
      return this.f_182405_;
   }

   public int m_182425_() {
      this.f_182405_ = this.f_182405_ + 1 & 32767;
      return this.f_182405_;
   }
}
