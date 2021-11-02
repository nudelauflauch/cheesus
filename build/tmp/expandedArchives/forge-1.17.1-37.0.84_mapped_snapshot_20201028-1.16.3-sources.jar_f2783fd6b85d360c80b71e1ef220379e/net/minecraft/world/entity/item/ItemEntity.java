package net.minecraft.world.entity.item;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ItemEntity extends Entity {
   private static final EntityDataAccessor<ItemStack> f_31984_ = SynchedEntityData.m_135353_(ItemEntity.class, EntityDataSerializers.f_135033_);
   private static final int f_149659_ = 6000;
   private static final int f_149660_ = 32767;
   private static final int f_149661_ = -32768;
   private int f_31985_;
   private int f_31986_;
   private int f_31987_ = 5;
   private UUID f_31988_;
   private UUID f_31982_;
   public final float f_31983_;
   /**
    * The maximum age of this EntityItem.  The item is expired once this is reached.
    */
   public int lifespan = 6000;

   public ItemEntity(EntityType<? extends ItemEntity> p_31991_, Level p_31992_) {
      super(p_31991_, p_31992_);
      this.f_31983_ = this.f_19796_.nextFloat() * (float)Math.PI * 2.0F;
      this.m_146922_(this.f_19796_.nextFloat() * 360.0F);
   }

   public ItemEntity(Level p_32001_, double p_32002_, double p_32003_, double p_32004_, ItemStack p_32005_) {
      this(p_32001_, p_32002_, p_32003_, p_32004_, p_32005_, p_32001_.f_46441_.nextDouble() * 0.2D - 0.1D, 0.2D, p_32001_.f_46441_.nextDouble() * 0.2D - 0.1D);
   }

   public ItemEntity(Level p_149663_, double p_149664_, double p_149665_, double p_149666_, ItemStack p_149667_, double p_149668_, double p_149669_, double p_149670_) {
      this(EntityType.f_20461_, p_149663_);
      this.m_6034_(p_149664_, p_149665_, p_149666_);
      this.m_20334_(p_149668_, p_149669_, p_149670_);
      this.m_32045_(p_149667_);
      this.lifespan = (p_149667_.m_41720_() == null ? 6000 : p_149667_.getEntityLifespan(p_149663_));
   }

   private ItemEntity(ItemEntity p_31994_) {
      super(p_31994_.m_6095_(), p_31994_.f_19853_);
      this.m_32045_(p_31994_.m_32055_().m_41777_());
      this.m_20359_(p_31994_);
      this.f_31985_ = p_31994_.f_31985_;
      this.f_31983_ = p_31994_.f_31983_;
   }

   public boolean m_142050_() {
      return ItemTags.f_144322_.m_8110_(this.m_32055_().m_41720_());
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(f_31984_, ItemStack.f_41583_);
   }

   public void m_8119_() {
      if (m_32055_().onEntityItemUpdate(this)) return;
      if (this.m_32055_().m_41619_()) {
         this.m_146870_();
      } else {
         super.m_8119_();
         if (this.f_31986_ > 0 && this.f_31986_ != 32767) {
            --this.f_31986_;
         }

         this.f_19854_ = this.m_20185_();
         this.f_19855_ = this.m_20186_();
         this.f_19856_ = this.m_20189_();
         Vec3 vec3 = this.m_20184_();
         float f = this.m_20192_() - 0.11111111F;
         if (this.m_20069_() && this.m_20120_(FluidTags.f_13131_) > (double)f) {
            this.m_32067_();
         } else if (this.m_20077_() && this.m_20120_(FluidTags.f_13132_) > (double)f) {
            this.m_32068_();
         } else if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.04D, 0.0D));
         }

         if (this.f_19853_.f_46443_) {
            this.f_19794_ = false;
         } else {
            this.f_19794_ = !this.f_19853_.m_45768_(this, this.m_142469_().m_82406_(1.0E-7D), (p_149672_) -> {
               return true;
            });
            if (this.f_19794_) {
               this.m_20314_(this.m_20185_(), (this.m_142469_().f_82289_ + this.m_142469_().f_82292_) / 2.0D, this.m_20189_());
            }
         }

         if (!this.f_19861_ || this.m_20184_().m_165925_() > (double)1.0E-5F || (this.f_19797_ + this.m_142049_()) % 4 == 0) {
            this.m_6478_(MoverType.SELF, this.m_20184_());
            float f1 = 0.98F;
            if (this.f_19861_) {
               f1 = this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20186_() - 1.0D, this.m_20189_())).getFriction(f_19853_, new BlockPos(this.m_20185_(), this.m_20186_() - 1.0D, this.m_20189_()), this) * 0.98F;
            }

            this.m_20256_(this.m_20184_().m_82542_((double)f1, 0.98D, (double)f1));
            if (this.f_19861_) {
               Vec3 vec31 = this.m_20184_();
               if (vec31.f_82480_ < 0.0D) {
                  this.m_20256_(vec31.m_82542_(1.0D, -0.5D, 1.0D));
               }
            }
         }

         boolean flag = Mth.m_14107_(this.f_19854_) != Mth.m_14107_(this.m_20185_()) || Mth.m_14107_(this.f_19855_) != Mth.m_14107_(this.m_20186_()) || Mth.m_14107_(this.f_19856_) != Mth.m_14107_(this.m_20189_());
         int i = flag ? 2 : 40;
         if (this.f_19797_ % i == 0 && !this.f_19853_.f_46443_ && this.m_32070_()) {
            this.m_32069_();
         }

         if (this.f_31985_ != -32768) {
            ++this.f_31985_;
         }

         this.f_19812_ |= this.m_20073_();
         if (!this.f_19853_.f_46443_) {
            double d0 = this.m_20184_().m_82546_(vec3).m_82556_();
            if (d0 > 0.01D) {
               this.f_19812_ = true;
            }
         }

         ItemStack item = this.m_32055_();
         if (!this.f_19853_.f_46443_ && this.f_31985_ >= lifespan) {
             int hook = net.minecraftforge.event.ForgeEventFactory.onItemExpire(this, item);
             if (hook < 0) this.m_146870_();
             else          this.lifespan += hook;
         }

         if (item.m_41619_()) {
            this.m_146870_();
         }

      }
   }

   private void m_32067_() {
      Vec3 vec3 = this.m_20184_();
      this.m_20334_(vec3.f_82479_ * (double)0.99F, vec3.f_82480_ + (double)(vec3.f_82480_ < (double)0.06F ? 5.0E-4F : 0.0F), vec3.f_82481_ * (double)0.99F);
   }

   private void m_32068_() {
      Vec3 vec3 = this.m_20184_();
      this.m_20334_(vec3.f_82479_ * (double)0.95F, vec3.f_82480_ + (double)(vec3.f_82480_ < (double)0.06F ? 5.0E-4F : 0.0F), vec3.f_82481_ * (double)0.95F);
   }

   private void m_32069_() {
      if (this.m_32070_()) {
         for(ItemEntity itementity : this.f_19853_.m_6443_(ItemEntity.class, this.m_142469_().m_82377_(0.5D, 0.0D, 0.5D), (p_149676_) -> {
            return p_149676_ != this && p_149676_.m_32070_();
         })) {
            if (itementity.m_32070_()) {
               this.m_32015_(itementity);
               if (this.m_146910_()) {
                  break;
               }
            }
         }

      }
   }

   private boolean m_32070_() {
      ItemStack itemstack = this.m_32055_();
      return this.m_6084_() && this.f_31986_ != 32767 && this.f_31985_ != -32768 && this.f_31985_ < 6000 && itemstack.m_41613_() < itemstack.m_41741_();
   }

   private void m_32015_(ItemEntity p_32016_) {
      ItemStack itemstack = this.m_32055_();
      ItemStack itemstack1 = p_32016_.m_32055_();
      if (Objects.equals(this.m_32056_(), p_32016_.m_32056_()) && m_32026_(itemstack, itemstack1)) {
         if (itemstack1.m_41613_() < itemstack.m_41613_()) {
            m_32017_(this, itemstack, p_32016_, itemstack1);
         } else {
            m_32017_(p_32016_, itemstack1, this, itemstack);
         }

      }
   }

   public static boolean m_32026_(ItemStack p_32027_, ItemStack p_32028_) {
      if (!p_32028_.m_150930_(p_32027_.m_41720_())) {
         return false;
      } else if (p_32028_.m_41613_() + p_32027_.m_41613_() > p_32028_.m_41741_()) {
         return false;
      } else if (p_32028_.m_41782_() ^ p_32027_.m_41782_()) {
         return false;
      } else if (!p_32027_.areCapsCompatible(p_32028_)) {
         return false;
      } else {
         return !p_32028_.m_41782_() || p_32028_.m_41783_().equals(p_32027_.m_41783_());
      }
   }

   public static ItemStack m_32029_(ItemStack p_32030_, ItemStack p_32031_, int p_32032_) {
      int i = Math.min(Math.min(p_32030_.m_41741_(), p_32032_) - p_32030_.m_41613_(), p_32031_.m_41613_());
      ItemStack itemstack = p_32030_.m_41777_();
      itemstack.m_41769_(i);
      p_32031_.m_41774_(i);
      return itemstack;
   }

   private static void m_32022_(ItemEntity p_32023_, ItemStack p_32024_, ItemStack p_32025_) {
      ItemStack itemstack = m_32029_(p_32024_, p_32025_, 64);
      p_32023_.m_32045_(itemstack);
   }

   private static void m_32017_(ItemEntity p_32018_, ItemStack p_32019_, ItemEntity p_32020_, ItemStack p_32021_) {
      m_32022_(p_32018_, p_32019_, p_32021_);
      p_32018_.f_31986_ = Math.max(p_32018_.f_31986_, p_32020_.f_31986_);
      p_32018_.f_31985_ = Math.min(p_32018_.f_31985_, p_32020_.f_31985_);
      if (p_32021_.m_41619_()) {
         p_32020_.m_146870_();
      }

   }

   public boolean m_5825_() {
      return this.m_32055_().m_41720_().m_41475_() || super.m_5825_();
   }

   public boolean m_6469_(DamageSource p_32013_, float p_32014_) {
      if (this.f_19853_.f_46443_ || this.m_146910_()) return false; //Forge: Fixes MC-53850
      if (this.m_6673_(p_32013_)) {
         return false;
      } else if (!this.m_32055_().m_41619_() && this.m_32055_().m_150930_(Items.f_42686_) && p_32013_.m_19372_()) {
         return false;
      } else if (!this.m_32055_().m_41720_().m_41386_(p_32013_)) {
         return false;
      } else {
         this.m_5834_();
         this.f_31987_ = (int)((float)this.f_31987_ - p_32014_);
         this.m_146852_(GameEvent.f_157808_, p_32013_.m_7639_());
         if (this.f_31987_ <= 0) {
            this.m_32055_().m_150924_(this);
            this.m_146870_();
         }

         return true;
      }
   }

   public void m_7380_(CompoundTag p_32050_) {
      p_32050_.m_128376_("Health", (short)this.f_31987_);
      p_32050_.m_128376_("Age", (short)this.f_31985_);
      p_32050_.m_128376_("PickupDelay", (short)this.f_31986_);
      p_32050_.m_128405_("Lifespan", lifespan);
      if (this.m_32057_() != null) {
         p_32050_.m_128362_("Thrower", this.m_32057_());
      }

      if (this.m_32056_() != null) {
         p_32050_.m_128362_("Owner", this.m_32056_());
      }

      if (!this.m_32055_().m_41619_()) {
         p_32050_.m_128365_("Item", this.m_32055_().m_41739_(new CompoundTag()));
      }

   }

   public void m_7378_(CompoundTag p_32034_) {
      this.f_31987_ = p_32034_.m_128448_("Health");
      this.f_31985_ = p_32034_.m_128448_("Age");
      if (p_32034_.m_128441_("PickupDelay")) {
         this.f_31986_ = p_32034_.m_128448_("PickupDelay");
      }
      if (p_32034_.m_128441_("Lifespan")) lifespan = p_32034_.m_128451_("Lifespan");

      if (p_32034_.m_128403_("Owner")) {
         this.f_31982_ = p_32034_.m_128342_("Owner");
      }

      if (p_32034_.m_128403_("Thrower")) {
         this.f_31988_ = p_32034_.m_128342_("Thrower");
      }

      CompoundTag compoundtag = p_32034_.m_128469_("Item");
      this.m_32045_(ItemStack.m_41712_(compoundtag));
      if (this.m_32055_().m_41619_()) {
         this.m_146870_();
      }

   }

   public void m_6123_(Player p_32040_) {
      if (!this.f_19853_.f_46443_) {
         if (this.f_31986_ > 0) return;
         ItemStack itemstack = this.m_32055_();
         Item item = itemstack.m_41720_();
         int i = itemstack.m_41613_();

         int hook = net.minecraftforge.event.ForgeEventFactory.onItemPickup(this, p_32040_);
         if (hook < 0) return;

         ItemStack copy = itemstack.m_41777_();
         if (this.f_31986_ == 0 && (this.f_31982_ == null || lifespan - this.f_31985_ <= 200 || this.f_31982_.equals(p_32040_.m_142081_())) && (hook == 1 || i <= 0 || p_32040_.m_150109_().m_36054_(itemstack))) {
            copy.m_41764_(copy.m_41613_() - m_32055_().m_41613_());
            net.minecraftforge.fmllegacy.hooks.BasicEventHooks.firePlayerItemPickupEvent(p_32040_, this, copy);
            p_32040_.m_7938_(this, i);
            if (itemstack.m_41619_()) {
               this.m_146870_();
               itemstack.m_41764_(i);
            }

            p_32040_.m_6278_(Stats.f_12984_.m_12902_(item), i);
            p_32040_.m_21053_(this);
         }

      }
   }

   public Component m_7755_() {
      Component component = this.m_7770_();
      return (Component)(component != null ? component : new TranslatableComponent(this.m_32055_().m_41778_()));
   }

   public boolean m_6097_() {
      return false;
   }

   @Nullable
   public Entity changeDimension(ServerLevel p_32042_, net.minecraftforge.common.util.ITeleporter teleporter) {
      Entity entity = super.changeDimension(p_32042_, teleporter);
      if (!this.f_19853_.f_46443_ && entity instanceof ItemEntity) {
         ((ItemEntity)entity).m_32069_();
      }

      return entity;
   }

   public ItemStack m_32055_() {
      return this.m_20088_().m_135370_(f_31984_);
   }

   public void m_32045_(ItemStack p_32046_) {
      this.m_20088_().m_135381_(f_31984_, p_32046_);
   }

   public void m_7350_(EntityDataAccessor<?> p_32036_) {
      super.m_7350_(p_32036_);
      if (f_31984_.equals(p_32036_)) {
         this.m_32055_().m_41636_(this);
      }

   }

   @Nullable
   public UUID m_32056_() {
      return this.f_31982_;
   }

   public void m_32047_(@Nullable UUID p_32048_) {
      this.f_31982_ = p_32048_;
   }

   @Nullable
   public UUID m_32057_() {
      return this.f_31988_;
   }

   public void m_32052_(@Nullable UUID p_32053_) {
      this.f_31988_ = p_32053_;
   }

   public int m_32059_() {
      return this.f_31985_;
   }

   public void m_32060_() {
      this.f_31986_ = 10;
   }

   public void m_32061_() {
      this.f_31986_ = 0;
   }

   public void m_32062_() {
      this.f_31986_ = 32767;
   }

   public void m_32010_(int p_32011_) {
      this.f_31986_ = p_32011_;
   }

   public boolean m_32063_() {
      return this.f_31986_ > 0;
   }

   public void m_149678_() {
      this.f_31985_ = -32768;
   }

   public void m_32064_() {
      this.f_31985_ = -6000;
   }

   public void m_32065_() {
      this.m_32062_();
      this.f_31985_ = m_32055_().getEntityLifespan(f_19853_) - 1;
   }

   public float m_32008_(float p_32009_) {
      return ((float)this.m_32059_() + p_32009_) / 20.0F + this.f_31983_;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public ItemEntity m_32066_() {
      return new ItemEntity(this);
   }

   public SoundSource m_5720_() {
      return SoundSource.AMBIENT;
   }
}
