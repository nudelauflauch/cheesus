package net.minecraft.world.entity.monster;

import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;

public class ZombifiedPiglin extends Zombie implements NeutralMob {
   private static final UUID f_34416_ = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
   private static final AttributeModifier f_34423_ = new AttributeModifier(f_34416_, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION);
   private static final UniformInt f_34424_ = TimeUtil.m_145020_(0, 1);
   private int f_34417_;
   private static final UniformInt f_34418_ = TimeUtil.m_145020_(20, 39);
   private int f_34419_;
   private UUID f_34420_;
   private static final int f_149890_ = 10;
   private static final UniformInt f_34421_ = TimeUtil.m_145020_(4, 6);
   private int f_34422_;

   public ZombifiedPiglin(EntityType<? extends ZombifiedPiglin> p_34427_, Level p_34428_) {
      super(p_34427_, p_34428_);
      this.m_21441_(BlockPathTypes.LAVA, 8.0F);
   }

   public void m_6925_(@Nullable UUID p_34444_) {
      this.f_34420_ = p_34444_;
   }

   public double m_6049_() {
      return this.m_6162_() ? -0.05D : -0.45D;
   }

   protected void m_6878_() {
      this.f_21345_.m_25352_(2, new ZombieAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this)).m_26044_());
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::m_21674_));
      this.f_21346_.m_25352_(3, new ResetUniversalAngerTargetGoal<>(this, true));
   }

   public static AttributeSupplier.Builder m_34470_() {
      return Zombie.m_34328_().m_22268_(Attributes.f_22287_, 0.0D).m_22268_(Attributes.f_22279_, (double)0.23F).m_22268_(Attributes.f_22281_, 5.0D);
   }

   protected boolean m_7593_() {
      return false;
   }

   protected void m_8024_() {
      AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
      if (this.m_21660_()) {
         if (!this.m_6162_() && !attributeinstance.m_22109_(f_34423_)) {
            attributeinstance.m_22118_(f_34423_);
         }

         this.m_34471_();
      } else if (attributeinstance.m_22109_(f_34423_)) {
         attributeinstance.m_22130_(f_34423_);
      }

      this.m_21666_((ServerLevel)this.f_19853_, true);
      if (this.m_5448_() != null) {
         this.m_34472_();
      }

      if (this.m_21660_()) {
         this.f_20889_ = this.f_19797_;
      }

      super.m_8024_();
   }

   private void m_34471_() {
      if (this.f_34417_ > 0) {
         --this.f_34417_;
         if (this.f_34417_ == 0) {
            this.m_34476_();
         }
      }

   }

   private void m_34472_() {
      if (this.f_34422_ > 0) {
         --this.f_34422_;
      } else {
         if (this.m_21574_().m_148306_(this.m_5448_())) {
            this.m_34473_();
         }

         this.f_34422_ = f_34421_.m_142270_(this.f_19796_);
      }
   }

   private void m_34473_() {
      double d0 = this.m_21133_(Attributes.f_22277_);
      AABB aabb = AABB.m_82333_(this.m_20182_()).m_82377_(d0, 10.0D, d0);
      this.f_19853_.m_6443_(ZombifiedPiglin.class, aabb, EntitySelector.f_20408_).stream().filter((p_34463_) -> {
         return p_34463_ != this;
      }).filter((p_34461_) -> {
         return p_34461_.m_5448_() == null;
      }).filter((p_34456_) -> {
         return !p_34456_.m_7307_(this.m_5448_());
      }).forEach((p_34440_) -> {
         p_34440_.m_6710_(this.m_5448_());
      });
   }

   private void m_34476_() {
      this.m_5496_(SoundEvents.f_12611_, this.m_6121_() * 2.0F, this.m_6100_() * 1.8F);
   }

   public void m_6710_(@Nullable LivingEntity p_34478_) {
      if (this.m_5448_() == null && p_34478_ != null) {
         this.f_34417_ = f_34424_.m_142270_(this.f_19796_);
         this.f_34422_ = f_34421_.m_142270_(this.f_19796_);
      }

      if (p_34478_ instanceof Player) {
         this.m_6598_((Player)p_34478_);
      }

      super.m_6710_(p_34478_);
   }

   public void m_6825_() {
      this.m_7870_(f_34418_.m_142270_(this.f_19796_));
   }

   public static boolean m_34449_(EntityType<ZombifiedPiglin> p_34450_, LevelAccessor p_34451_, MobSpawnType p_34452_, BlockPos p_34453_, Random p_34454_) {
      return p_34451_.m_46791_() != Difficulty.PEACEFUL && !p_34451_.m_8055_(p_34453_.m_7495_()).m_60713_(Blocks.f_50451_);
   }

   public boolean m_6914_(LevelReader p_34442_) {
      return p_34442_.m_45784_(this) && !p_34442_.m_46855_(this.m_142469_());
   }

   public void m_7380_(CompoundTag p_34458_) {
      super.m_7380_(p_34458_);
      this.m_21678_(p_34458_);
   }

   public void m_7378_(CompoundTag p_34446_) {
      super.m_7378_(p_34446_);
      this.m_147285_(this.f_19853_, p_34446_);
   }

   public void m_7870_(int p_34448_) {
      this.f_34419_ = p_34448_;
   }

   public int m_6784_() {
      return this.f_34419_;
   }

   protected SoundEvent m_7515_() {
      return this.m_21660_() ? SoundEvents.f_12611_ : SoundEvents.f_12610_;
   }

   protected SoundEvent m_7975_(DamageSource p_34466_) {
      return SoundEvents.f_12613_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12612_;
   }

   protected void m_6851_(DifficultyInstance p_34435_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42430_));
   }

   protected ItemStack m_5728_() {
      return ItemStack.f_41583_;
   }

   protected void m_7572_() {
      this.m_21051_(Attributes.f_22287_).m_22100_(0.0D);
   }

   public UUID m_6120_() {
      return this.f_34420_;
   }

   public boolean m_6935_(Player p_34475_) {
      return this.m_21674_(p_34475_);
   }

   public boolean m_7243_(ItemStack p_182402_) {
      return this.m_7252_(p_182402_);
   }
}