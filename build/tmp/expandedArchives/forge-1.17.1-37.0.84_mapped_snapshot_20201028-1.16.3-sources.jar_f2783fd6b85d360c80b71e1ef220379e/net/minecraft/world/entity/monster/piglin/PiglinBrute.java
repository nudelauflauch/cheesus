package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class PiglinBrute extends AbstractPiglin {
   private static final int f_149974_ = 50;
   private static final float f_149975_ = 0.35F;
   private static final int f_149976_ = 7;
   protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinBrute>>> f_35045_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26810_, SensorType.f_26814_, SensorType.f_26820_);
   protected static final ImmutableList<MemoryModuleType<?>> f_35044_ = ImmutableList.of(MemoryModuleType.f_26371_, MemoryModuleType.f_26379_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26347_, MemoryModuleType.f_26346_, MemoryModuleType.f_26381_, MemoryModuleType.f_26382_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_, MemoryModuleType.f_26374_, MemoryModuleType.f_26377_, MemoryModuleType.f_26334_, MemoryModuleType.f_26333_, MemoryModuleType.f_26359_);

   public PiglinBrute(EntityType<? extends PiglinBrute> p_35048_, Level p_35049_) {
      super(p_35048_, p_35049_);
      this.f_21364_ = 20;
   }

   public static AttributeSupplier.Builder m_35075_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 50.0D).m_22268_(Attributes.f_22279_, (double)0.35F).m_22268_(Attributes.f_22281_, 7.0D);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_35058_, DifficultyInstance p_35059_, MobSpawnType p_35060_, @Nullable SpawnGroupData p_35061_, @Nullable CompoundTag p_35062_) {
      PiglinBruteAi.m_35094_(this);
      this.m_6851_(p_35059_);
      return super.m_6518_(p_35058_, p_35059_, p_35060_, p_35061_, p_35062_);
   }

   protected void m_6851_(DifficultyInstance p_35053_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42433_));
   }

   protected Brain.Provider<PiglinBrute> m_5490_() {
      return Brain.m_21923_(f_35044_, f_35045_);
   }

   protected Brain<?> m_8075_(Dynamic<?> p_35064_) {
      return PiglinBruteAi.m_35099_(this, this.m_5490_().m_22073_(p_35064_));
   }

   public Brain<PiglinBrute> m_6274_() {
      return (Brain<PiglinBrute>)super.m_6274_();
   }

   public boolean m_7121_() {
      return false;
   }

   public boolean m_7243_(ItemStack p_35078_) {
      return p_35078_.m_150930_(Items.f_42433_) ? super.m_7243_(p_35078_) : false;
   }

   protected void m_8024_() {
      this.f_19853_.m_46473_().m_6180_("piglinBruteBrain");
      this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
      this.f_19853_.m_46473_().m_7238_();
      PiglinBruteAi.m_35109_(this);
      PiglinBruteAi.m_35114_(this);
      super.m_8024_();
   }

   public PiglinArmPose m_6389_() {
      return this.m_5912_() && this.m_34668_() ? PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON : PiglinArmPose.DEFAULT;
   }

   public boolean m_6469_(DamageSource p_35055_, float p_35056_) {
      boolean flag = super.m_6469_(p_35055_, p_35056_);
      if (this.f_19853_.f_46443_) {
         return false;
      } else {
         if (flag && p_35055_.m_7639_() instanceof LivingEntity) {
            PiglinBruteAi.m_35096_(this, (LivingEntity)p_35055_.m_7639_());
         }

         return flag;
      }
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12301_;
   }

   protected SoundEvent m_7975_(DamageSource p_35072_) {
      return SoundEvents.f_12304_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12303_;
   }

   protected void m_7355_(BlockPos p_35066_, BlockState p_35067_) {
      this.m_5496_(SoundEvents.f_12305_, 0.15F, 1.0F);
   }

   protected void m_35076_() {
      this.m_5496_(SoundEvents.f_12302_, 1.0F, this.m_6100_());
   }

   protected void m_7580_() {
      this.m_5496_(SoundEvents.f_12306_, 1.0F, this.m_6100_());
   }
}