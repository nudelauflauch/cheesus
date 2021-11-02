package net.minecraft.world.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractArrow extends Projectile {
   private static final double f_150120_ = 2.0D;
   private static final EntityDataAccessor<Byte> f_36707_ = SynchedEntityData.m_135353_(AbstractArrow.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Byte> f_36708_ = SynchedEntityData.m_135353_(AbstractArrow.class, EntityDataSerializers.f_135027_);
   private static final int f_150117_ = 1;
   private static final int f_150118_ = 2;
   private static final int f_150119_ = 4;
   @Nullable
   private BlockState f_36696_;
   protected boolean f_36703_;
   protected int f_36704_;
   public AbstractArrow.Pickup f_36705_ = AbstractArrow.Pickup.DISALLOWED;
   public int f_36706_;
   private int f_36697_;
   private double f_36698_ = 2.0D;
   private int f_36699_;
   private SoundEvent f_36700_ = this.m_7239_();
   @Nullable
   private IntOpenHashSet f_36701_;
   @Nullable
   private List<Entity> f_36702_;

   protected AbstractArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
      super(p_36721_, p_36722_);
   }

   protected AbstractArrow(EntityType<? extends AbstractArrow> p_36711_, double p_36712_, double p_36713_, double p_36714_, Level p_36715_) {
      this(p_36711_, p_36715_);
      this.m_6034_(p_36712_, p_36713_, p_36714_);
   }

   protected AbstractArrow(EntityType<? extends AbstractArrow> p_36717_, LivingEntity p_36718_, Level p_36719_) {
      this(p_36717_, p_36718_.m_20185_(), p_36718_.m_20188_() - (double)0.1F, p_36718_.m_20189_(), p_36719_);
      this.m_5602_(p_36718_);
      if (p_36718_ instanceof Player) {
         this.f_36705_ = AbstractArrow.Pickup.ALLOWED;
      }

   }

   public void m_36740_(SoundEvent p_36741_) {
      this.f_36700_ = p_36741_;
   }

   public boolean m_6783_(double p_36726_) {
      double d0 = this.m_142469_().m_82309_() * 10.0D;
      if (Double.isNaN(d0)) {
         d0 = 1.0D;
      }

      d0 = d0 * 64.0D * m_20150_();
      return p_36726_ < d0 * d0;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(f_36707_, (byte)0);
      this.f_19804_.m_135372_(f_36708_, (byte)0);
   }

   public void m_6686_(double p_36775_, double p_36776_, double p_36777_, float p_36778_, float p_36779_) {
      super.m_6686_(p_36775_, p_36776_, p_36777_, p_36778_, p_36779_);
      this.f_36697_ = 0;
   }

   public void m_6453_(double p_36728_, double p_36729_, double p_36730_, float p_36731_, float p_36732_, int p_36733_, boolean p_36734_) {
      this.m_6034_(p_36728_, p_36729_, p_36730_);
      this.m_19915_(p_36731_, p_36732_);
   }

   public void m_6001_(double p_36786_, double p_36787_, double p_36788_) {
      super.m_6001_(p_36786_, p_36787_, p_36788_);
      this.f_36697_ = 0;
   }

   public void m_8119_() {
      super.m_8119_();
      boolean flag = this.m_36797_();
      Vec3 vec3 = this.m_20184_();
      if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
         double d0 = vec3.m_165924_();
         this.m_146922_((float)(Mth.m_14136_(vec3.f_82479_, vec3.f_82481_) * (double)(180F / (float)Math.PI)));
         this.m_146926_((float)(Mth.m_14136_(vec3.f_82480_, d0) * (double)(180F / (float)Math.PI)));
         this.f_19859_ = this.m_146908_();
         this.f_19860_ = this.m_146909_();
      }

      BlockPos blockpos = this.m_142538_();
      BlockState blockstate = this.f_19853_.m_8055_(blockpos);
      if (!blockstate.m_60795_() && !flag) {
         VoxelShape voxelshape = blockstate.m_60812_(this.f_19853_, blockpos);
         if (!voxelshape.m_83281_()) {
            Vec3 vec31 = this.m_20182_();

            for(AABB aabb : voxelshape.m_83299_()) {
               if (aabb.m_82338_(blockpos).m_82390_(vec31)) {
                  this.f_36703_ = true;
                  break;
               }
            }
         }
      }

      if (this.f_36706_ > 0) {
         --this.f_36706_;
      }

      if (this.m_20070_() || blockstate.m_60713_(Blocks.f_152499_)) {
         this.m_20095_();
      }

      if (this.f_36703_ && !flag) {
         if (this.f_36696_ != blockstate && this.m_36798_()) {
            this.m_36799_();
         } else if (!this.f_19853_.f_46443_) {
            this.m_6901_();
         }

         ++this.f_36704_;
      } else {
         this.f_36704_ = 0;
         Vec3 vec32 = this.m_20182_();
         Vec3 vec33 = vec32.m_82549_(vec3);
         HitResult hitresult = this.f_19853_.m_45547_(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
         if (hitresult.m_6662_() != HitResult.Type.MISS) {
            vec33 = hitresult.m_82450_();
         }

         while(!this.m_146910_()) {
            EntityHitResult entityhitresult = this.m_6351_(vec32, vec33);
            if (entityhitresult != null) {
               hitresult = entityhitresult;
            }

            if (hitresult != null && hitresult.m_6662_() == HitResult.Type.ENTITY) {
               Entity entity = ((EntityHitResult)hitresult).m_82443_();
               Entity entity1 = this.m_37282_();
               if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).m_7099_((Player)entity)) {
                  hitresult = null;
                  entityhitresult = null;
               }
            }

            if (hitresult != null && hitresult.m_6662_() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
               this.m_6532_(hitresult);
               this.f_19812_ = true;
            }

            if (entityhitresult == null || this.m_36796_() <= 0) {
               break;
            }

            hitresult = null;
         }

         vec3 = this.m_20184_();
         double d5 = vec3.f_82479_;
         double d6 = vec3.f_82480_;
         double d1 = vec3.f_82481_;
         if (this.m_36792_()) {
            for(int i = 0; i < 4; ++i) {
               this.f_19853_.m_7106_(ParticleTypes.f_123797_, this.m_20185_() + d5 * (double)i / 4.0D, this.m_20186_() + d6 * (double)i / 4.0D, this.m_20189_() + d1 * (double)i / 4.0D, -d5, -d6 + 0.2D, -d1);
            }
         }

         double d7 = this.m_20185_() + d5;
         double d2 = this.m_20186_() + d6;
         double d3 = this.m_20189_() + d1;
         double d4 = vec3.m_165924_();
         if (flag) {
            this.m_146922_((float)(Mth.m_14136_(-d5, -d1) * (double)(180F / (float)Math.PI)));
         } else {
            this.m_146922_((float)(Mth.m_14136_(d5, d1) * (double)(180F / (float)Math.PI)));
         }

         this.m_146926_((float)(Mth.m_14136_(d6, d4) * (double)(180F / (float)Math.PI)));
         this.m_146926_(m_37273_(this.f_19860_, this.m_146909_()));
         this.m_146922_(m_37273_(this.f_19859_, this.m_146908_()));
         float f = 0.99F;
         float f1 = 0.05F;
         if (this.m_20069_()) {
            for(int j = 0; j < 4; ++j) {
               float f2 = 0.25F;
               this.f_19853_.m_7106_(ParticleTypes.f_123795_, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
            }

            f = this.m_6882_();
         }

         this.m_20256_(vec3.m_82490_((double)f));
         if (!this.m_20068_() && !flag) {
            Vec3 vec34 = this.m_20184_();
            this.m_20334_(vec34.f_82479_, vec34.f_82480_ - (double)0.05F, vec34.f_82481_);
         }

         this.m_6034_(d7, d2, d3);
         this.m_20101_();
      }
   }

   private boolean m_36798_() {
      return this.f_36703_ && this.f_19853_.m_45772_((new AABB(this.m_20182_(), this.m_20182_())).m_82400_(0.06D));
   }

   private void m_36799_() {
      this.f_36703_ = false;
      Vec3 vec3 = this.m_20184_();
      this.m_20256_(vec3.m_82542_((double)(this.f_19796_.nextFloat() * 0.2F), (double)(this.f_19796_.nextFloat() * 0.2F), (double)(this.f_19796_.nextFloat() * 0.2F)));
      this.f_36697_ = 0;
   }

   public void m_6478_(MoverType p_36749_, Vec3 p_36750_) {
      super.m_6478_(p_36749_, p_36750_);
      if (p_36749_ != MoverType.SELF && this.m_36798_()) {
         this.m_36799_();
      }

   }

   protected void m_6901_() {
      ++this.f_36697_;
      if (this.f_36697_ >= 1200) {
         this.m_146870_();
      }

   }

   private void m_36723_() {
      if (this.f_36702_ != null) {
         this.f_36702_.clear();
      }

      if (this.f_36701_ != null) {
         this.f_36701_.clear();
      }

   }

   protected void m_5790_(EntityHitResult p_36757_) {
      super.m_5790_(p_36757_);
      Entity entity = p_36757_.m_82443_();
      float f = (float)this.m_20184_().m_82553_();
      int i = Mth.m_14165_(Mth.m_14008_((double)f * this.f_36698_, 0.0D, 2.147483647E9D));
      if (this.m_36796_() > 0) {
         if (this.f_36701_ == null) {
            this.f_36701_ = new IntOpenHashSet(5);
         }

         if (this.f_36702_ == null) {
            this.f_36702_ = Lists.newArrayListWithCapacity(5);
         }

         if (this.f_36701_.size() >= this.m_36796_() + 1) {
            this.m_146870_();
            return;
         }

         this.f_36701_.add(entity.m_142049_());
      }

      if (this.m_36792_()) {
         long j = (long)this.f_19796_.nextInt(i / 2 + 2);
         i = (int)Math.min(j + (long)i, 2147483647L);
      }

      Entity entity1 = this.m_37282_();
      DamageSource damagesource;
      if (entity1 == null) {
         damagesource = DamageSource.m_19346_(this, this);
      } else {
         damagesource = DamageSource.m_19346_(this, entity1);
         if (entity1 instanceof LivingEntity) {
            ((LivingEntity)entity1).m_21335_(entity);
         }
      }

      boolean flag = entity.m_6095_() == EntityType.f_20566_;
      int k = entity.m_20094_();
      if (this.m_6060_() && !flag) {
         entity.m_20254_(5);
      }

      if (entity.m_6469_(damagesource, (float)i)) {
         if (flag) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            if (!this.f_19853_.f_46443_ && this.m_36796_() <= 0) {
               livingentity.m_21317_(livingentity.m_21234_() + 1);
            }

            if (this.f_36699_ > 0) {
               Vec3 vec3 = this.m_20184_().m_82542_(1.0D, 0.0D, 1.0D).m_82541_().m_82490_((double)this.f_36699_ * 0.6D);
               if (vec3.m_82556_() > 0.0D) {
                  livingentity.m_5997_(vec3.f_82479_, 0.1D, vec3.f_82481_);
               }
            }

            if (!this.f_19853_.f_46443_ && entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity);
            }

            this.m_7761_(livingentity);
            if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.m_20067_()) {
               ((ServerPlayer)entity1).f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132159_, 0.0F));
            }

            if (!entity.m_6084_() && this.f_36702_ != null) {
               this.f_36702_.add(livingentity);
            }

            if (!this.f_19853_.f_46443_ && entity1 instanceof ServerPlayer) {
               ServerPlayer serverplayer = (ServerPlayer)entity1;
               if (this.f_36702_ != null && this.m_36795_()) {
                  CriteriaTriggers.f_10556_.m_46871_(serverplayer, this.f_36702_);
               } else if (!entity.m_6084_() && this.m_36795_()) {
                  CriteriaTriggers.f_10556_.m_46871_(serverplayer, Arrays.asList(entity));
               }
            }
         }

         this.m_5496_(this.f_36700_, 1.0F, 1.2F / (this.f_19796_.nextFloat() * 0.2F + 0.9F));
         if (this.m_36796_() <= 0) {
            this.m_146870_();
         }
      } else {
         entity.m_7311_(k);
         this.m_20256_(this.m_20184_().m_82490_(-0.1D));
         this.m_146922_(this.m_146908_() + 180.0F);
         this.f_19859_ += 180.0F;
         if (!this.f_19853_.f_46443_ && this.m_20184_().m_82556_() < 1.0E-7D) {
            if (this.f_36705_ == AbstractArrow.Pickup.ALLOWED) {
               this.m_5552_(this.m_7941_(), 0.1F);
            }

            this.m_146870_();
         }
      }

   }

   protected void m_8060_(BlockHitResult p_36755_) {
      this.f_36696_ = this.f_19853_.m_8055_(p_36755_.m_82425_());
      super.m_8060_(p_36755_);
      Vec3 vec3 = p_36755_.m_82450_().m_82492_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      this.m_20256_(vec3);
      Vec3 vec31 = vec3.m_82541_().m_82490_((double)0.05F);
      this.m_20343_(this.m_20185_() - vec31.f_82479_, this.m_20186_() - vec31.f_82480_, this.m_20189_() - vec31.f_82481_);
      this.m_5496_(this.m_36784_(), 1.0F, 1.2F / (this.f_19796_.nextFloat() * 0.2F + 0.9F));
      this.f_36703_ = true;
      this.f_36706_ = 7;
      this.m_36762_(false);
      this.m_36767_((byte)0);
      this.m_36740_(SoundEvents.f_11685_);
      this.m_36793_(false);
      this.m_36723_();
   }

   protected SoundEvent m_7239_() {
      return SoundEvents.f_11685_;
   }

   protected final SoundEvent m_36784_() {
      return this.f_36700_;
   }

   protected void m_7761_(LivingEntity p_36744_) {
   }

   @Nullable
   protected EntityHitResult m_6351_(Vec3 p_36758_, Vec3 p_36759_) {
      return ProjectileUtil.m_37304_(this.f_19853_, this, p_36758_, p_36759_, this.m_142469_().m_82369_(this.m_20184_()).m_82400_(1.0D), this::m_5603_);
   }

   protected boolean m_5603_(Entity p_36743_) {
      return super.m_5603_(p_36743_) && (this.f_36701_ == null || !this.f_36701_.contains(p_36743_.m_142049_()));
   }

   public void m_7380_(CompoundTag p_36772_) {
      super.m_7380_(p_36772_);
      p_36772_.m_128376_("life", (short)this.f_36697_);
      if (this.f_36696_ != null) {
         p_36772_.m_128365_("inBlockState", NbtUtils.m_129202_(this.f_36696_));
      }

      p_36772_.m_128344_("shake", (byte)this.f_36706_);
      p_36772_.m_128379_("inGround", this.f_36703_);
      p_36772_.m_128344_("pickup", (byte)this.f_36705_.ordinal());
      p_36772_.m_128347_("damage", this.f_36698_);
      p_36772_.m_128379_("crit", this.m_36792_());
      p_36772_.m_128344_("PierceLevel", this.m_36796_());
      p_36772_.m_128359_("SoundEvent", Registry.f_122821_.m_7981_(this.f_36700_).toString());
      p_36772_.m_128379_("ShotFromCrossbow", this.m_36795_());
   }

   public void m_7378_(CompoundTag p_36761_) {
      super.m_7378_(p_36761_);
      this.f_36697_ = p_36761_.m_128448_("life");
      if (p_36761_.m_128425_("inBlockState", 10)) {
         this.f_36696_ = NbtUtils.m_129241_(p_36761_.m_128469_("inBlockState"));
      }

      this.f_36706_ = p_36761_.m_128445_("shake") & 255;
      this.f_36703_ = p_36761_.m_128471_("inGround");
      if (p_36761_.m_128425_("damage", 99)) {
         this.f_36698_ = p_36761_.m_128459_("damage");
      }

      this.f_36705_ = AbstractArrow.Pickup.m_36808_(p_36761_.m_128445_("pickup"));
      this.m_36762_(p_36761_.m_128471_("crit"));
      this.m_36767_(p_36761_.m_128445_("PierceLevel"));
      if (p_36761_.m_128425_("SoundEvent", 8)) {
         this.f_36700_ = Registry.f_122821_.m_6612_(new ResourceLocation(p_36761_.m_128461_("SoundEvent"))).orElse(this.m_7239_());
      }

      this.m_36793_(p_36761_.m_128471_("ShotFromCrossbow"));
   }

   public void m_5602_(@Nullable Entity p_36770_) {
      super.m_5602_(p_36770_);
      if (p_36770_ instanceof Player) {
         this.f_36705_ = ((Player)p_36770_).m_150110_().f_35937_ ? AbstractArrow.Pickup.CREATIVE_ONLY : AbstractArrow.Pickup.ALLOWED;
      }

   }

   public void m_6123_(Player p_36766_) {
      if (!this.f_19853_.f_46443_ && (this.f_36703_ || this.m_36797_()) && this.f_36706_ <= 0) {
         if (this.m_142470_(p_36766_)) {
            p_36766_.m_7938_(this, 1);
            this.m_146870_();
         }

      }
   }

   protected boolean m_142470_(Player p_150121_) {
      switch(this.f_36705_) {
      case ALLOWED:
         return p_150121_.m_150109_().m_36054_(this.m_7941_());
      case CREATIVE_ONLY:
         return p_150121_.m_150110_().f_35937_;
      default:
         return false;
      }
   }

   protected abstract ItemStack m_7941_();

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   public void m_36781_(double p_36782_) {
      this.f_36698_ = p_36782_;
   }

   public double m_36789_() {
      return this.f_36698_;
   }

   public void m_36735_(int p_36736_) {
      this.f_36699_ = p_36736_;
   }

   public int m_150123_() {
      return this.f_36699_;
   }

   public boolean m_6097_() {
      return false;
   }

   protected float m_6380_(Pose p_36752_, EntityDimensions p_36753_) {
      return 0.13F;
   }

   public void m_36762_(boolean p_36763_) {
      this.m_36737_(1, p_36763_);
   }

   public void m_36767_(byte p_36768_) {
      this.f_19804_.m_135381_(f_36708_, p_36768_);
   }

   private void m_36737_(int p_36738_, boolean p_36739_) {
      byte b0 = this.f_19804_.m_135370_(f_36707_);
      if (p_36739_) {
         this.f_19804_.m_135381_(f_36707_, (byte)(b0 | p_36738_));
      } else {
         this.f_19804_.m_135381_(f_36707_, (byte)(b0 & ~p_36738_));
      }

   }

   public boolean m_36792_() {
      byte b0 = this.f_19804_.m_135370_(f_36707_);
      return (b0 & 1) != 0;
   }

   public boolean m_36795_() {
      byte b0 = this.f_19804_.m_135370_(f_36707_);
      return (b0 & 4) != 0;
   }

   public byte m_36796_() {
      return this.f_19804_.m_135370_(f_36708_);
   }

   public void m_36745_(LivingEntity p_36746_, float p_36747_) {
      int i = EnchantmentHelper.m_44836_(Enchantments.f_44988_, p_36746_);
      int j = EnchantmentHelper.m_44836_(Enchantments.f_44989_, p_36746_);
      this.m_36781_((double)(p_36747_ * 2.0F) + this.f_19796_.nextGaussian() * 0.25D + (double)((float)this.f_19853_.m_46791_().m_19028_() * 0.11F));
      if (i > 0) {
         this.m_36781_(this.m_36789_() + (double)i * 0.5D + 0.5D);
      }

      if (j > 0) {
         this.m_36735_(j);
      }

      if (EnchantmentHelper.m_44836_(Enchantments.f_44990_, p_36746_) > 0) {
         this.m_20254_(100);
      }

   }

   protected float m_6882_() {
      return 0.6F;
   }

   public void m_36790_(boolean p_36791_) {
      this.f_19794_ = p_36791_;
      this.m_36737_(2, p_36791_);
   }

   public boolean m_36797_() {
      if (!this.f_19853_.f_46443_) {
         return this.f_19794_;
      } else {
         return (this.f_19804_.m_135370_(f_36707_) & 2) != 0;
      }
   }

   public void m_36793_(boolean p_36794_) {
      this.m_36737_(4, p_36794_);
   }

   public static enum Pickup {
      DISALLOWED,
      ALLOWED,
      CREATIVE_ONLY;

      public static AbstractArrow.Pickup m_36808_(int p_36809_) {
         if (p_36809_ < 0 || p_36809_ > values().length) {
            p_36809_ = 0;
         }

         return values()[p_36809_];
      }
   }
}
