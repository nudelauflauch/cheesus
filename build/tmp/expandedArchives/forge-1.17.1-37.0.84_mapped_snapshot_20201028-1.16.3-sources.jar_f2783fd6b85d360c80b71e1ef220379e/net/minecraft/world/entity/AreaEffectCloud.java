package net.minecraft.world.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AreaEffectCloud extends Entity {
   private static final Logger f_19696_ = LogManager.getLogger();
   private static final int f_146782_ = 5;
   private static final EntityDataAccessor<Float> f_19697_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> f_19698_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_19699_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<ParticleOptions> f_19700_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135036_);
   private static final float f_146781_ = 32.0F;
   private Potion f_19701_ = Potions.f_43598_;
   private final List<MobEffectInstance> f_19685_ = Lists.newArrayList();
   private final Map<Entity, Integer> f_19686_ = Maps.newHashMap();
   private int f_19687_ = 600;
   private int f_19688_ = 20;
   private int f_19689_ = 20;
   private boolean f_19690_;
   private int f_19691_;
   private float f_19692_;
   private float f_19693_;
   @Nullable
   private LivingEntity f_19694_;
   @Nullable
   private UUID f_19695_;

   public AreaEffectCloud(EntityType<? extends AreaEffectCloud> p_19704_, Level p_19705_) {
      super(p_19704_, p_19705_);
      this.f_19794_ = true;
      this.m_19712_(3.0F);
   }

   public AreaEffectCloud(Level p_19707_, double p_19708_, double p_19709_, double p_19710_) {
      this(EntityType.f_20476_, p_19707_);
      this.m_6034_(p_19708_, p_19709_, p_19710_);
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(f_19698_, 0);
      this.m_20088_().m_135372_(f_19697_, 0.5F);
      this.m_20088_().m_135372_(f_19699_, false);
      this.m_20088_().m_135372_(f_19700_, ParticleTypes.f_123811_);
   }

   public void m_19712_(float p_19713_) {
      if (!this.f_19853_.f_46443_) {
         this.m_20088_().m_135381_(f_19697_, Mth.m_14036_(p_19713_, 0.0F, 32.0F));
      }

   }

   public void m_6210_() {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      super.m_6210_();
      this.m_6034_(d0, d1, d2);
   }

   public float m_19743_() {
      return this.m_20088_().m_135370_(f_19697_);
   }

   public void m_19722_(Potion p_19723_) {
      this.f_19701_ = p_19723_;
      if (!this.f_19690_) {
         this.m_19750_();
      }

   }

   private void m_19750_() {
      if (this.f_19701_ == Potions.f_43598_ && this.f_19685_.isEmpty()) {
         this.m_20088_().m_135381_(f_19698_, 0);
      } else {
         this.m_20088_().m_135381_(f_19698_, PotionUtils.m_43564_(PotionUtils.m_43561_(this.f_19701_, this.f_19685_)));
      }

   }

   public void m_19716_(MobEffectInstance p_19717_) {
      this.f_19685_.add(p_19717_);
      if (!this.f_19690_) {
         this.m_19750_();
      }

   }

   public int m_19744_() {
      return this.m_20088_().m_135370_(f_19698_);
   }

   public void m_19714_(int p_19715_) {
      this.f_19690_ = true;
      this.m_20088_().m_135381_(f_19698_, p_19715_);
   }

   public ParticleOptions m_19745_() {
      return this.m_20088_().m_135370_(f_19700_);
   }

   public void m_19724_(ParticleOptions p_19725_) {
      this.m_20088_().m_135381_(f_19700_, p_19725_);
   }

   protected void m_19730_(boolean p_19731_) {
      this.m_20088_().m_135381_(f_19699_, p_19731_);
   }

   public boolean m_19747_() {
      return this.m_20088_().m_135370_(f_19699_);
   }

   public int m_19748_() {
      return this.f_19687_;
   }

   public void m_19734_(int p_19735_) {
      this.f_19687_ = p_19735_;
   }

   public void m_8119_() {
      super.m_8119_();
      boolean flag = this.m_19747_();
      float f = this.m_19743_();
      if (this.f_19853_.f_46443_) {
         if (flag && this.f_19796_.nextBoolean()) {
            return;
         }

         ParticleOptions particleoptions = this.m_19745_();
         int i;
         float f1;
         if (flag) {
            i = 2;
            f1 = 0.2F;
         } else {
            i = Mth.m_14167_((float)Math.PI * f * f);
            f1 = f;
         }

         for(int j = 0; j < i; ++j) {
            float f2 = this.f_19796_.nextFloat() * ((float)Math.PI * 2F);
            float f3 = Mth.m_14116_(this.f_19796_.nextFloat()) * f1;
            double d0 = this.m_20185_() + (double)(Mth.m_14089_(f2) * f3);
            double d2 = this.m_20186_();
            double d4 = this.m_20189_() + (double)(Mth.m_14031_(f2) * f3);
            double d5;
            double d6;
            double d7;
            if (particleoptions.m_6012_() != ParticleTypes.f_123811_) {
               if (flag) {
                  d5 = 0.0D;
                  d6 = 0.0D;
                  d7 = 0.0D;
               } else {
                  d5 = (0.5D - this.f_19796_.nextDouble()) * 0.15D;
                  d6 = (double)0.01F;
                  d7 = (0.5D - this.f_19796_.nextDouble()) * 0.15D;
               }
            } else {
               int k = flag && this.f_19796_.nextBoolean() ? 16777215 : this.m_19744_();
               d5 = (double)((float)(k >> 16 & 255) / 255.0F);
               d6 = (double)((float)(k >> 8 & 255) / 255.0F);
               d7 = (double)((float)(k & 255) / 255.0F);
            }

            this.f_19853_.m_7107_(particleoptions, d0, d2, d4, d5, d6, d7);
         }
      } else {
         if (this.f_19797_ >= this.f_19688_ + this.f_19687_) {
            this.m_146870_();
            return;
         }

         boolean flag1 = this.f_19797_ < this.f_19688_;
         if (flag != flag1) {
            this.m_19730_(flag1);
         }

         if (flag1) {
            return;
         }

         if (this.f_19693_ != 0.0F) {
            f += this.f_19693_;
            if (f < 0.5F) {
               this.m_146870_();
               return;
            }

            this.m_19712_(f);
         }

         if (this.f_19797_ % 5 == 0) {
            this.f_19686_.entrySet().removeIf((p_146784_) -> {
               return this.f_19797_ >= p_146784_.getValue();
            });
            List<MobEffectInstance> list = Lists.newArrayList();

            for(MobEffectInstance mobeffectinstance : this.f_19701_.m_43488_()) {
               list.add(new MobEffectInstance(mobeffectinstance.m_19544_(), mobeffectinstance.m_19557_() / 4, mobeffectinstance.m_19564_(), mobeffectinstance.m_19571_(), mobeffectinstance.m_19572_()));
            }

            list.addAll(this.f_19685_);
            if (list.isEmpty()) {
               this.f_19686_.clear();
            } else {
               List<LivingEntity> list1 = this.f_19853_.m_45976_(LivingEntity.class, this.m_142469_());
               if (!list1.isEmpty()) {
                  for(LivingEntity livingentity : list1) {
                     if (!this.f_19686_.containsKey(livingentity) && livingentity.m_5801_()) {
                        double d8 = livingentity.m_20185_() - this.m_20185_();
                        double d1 = livingentity.m_20189_() - this.m_20189_();
                        double d3 = d8 * d8 + d1 * d1;
                        if (d3 <= (double)(f * f)) {
                           this.f_19686_.put(livingentity, this.f_19797_ + this.f_19689_);

                           for(MobEffectInstance mobeffectinstance1 : list) {
                              if (mobeffectinstance1.m_19544_().m_8093_()) {
                                 mobeffectinstance1.m_19544_().m_19461_(this, this.m_19749_(), livingentity, mobeffectinstance1.m_19564_(), 0.5D);
                              } else {
                                 livingentity.m_147207_(new MobEffectInstance(mobeffectinstance1), this);
                              }
                           }

                           if (this.f_19692_ != 0.0F) {
                              f += this.f_19692_;
                              if (f < 0.5F) {
                                 this.m_146870_();
                                 return;
                              }

                              this.m_19712_(f);
                           }

                           if (this.f_19691_ != 0) {
                              this.f_19687_ += this.f_19691_;
                              if (this.f_19687_ <= 0) {
                                 this.m_146870_();
                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public float m_146787_() {
      return this.f_19692_;
   }

   public void m_19732_(float p_19733_) {
      this.f_19692_ = p_19733_;
   }

   public float m_146788_() {
      return this.f_19693_;
   }

   public void m_19738_(float p_19739_) {
      this.f_19693_ = p_19739_;
   }

   public int m_146789_() {
      return this.f_19691_;
   }

   public void m_146785_(int p_146786_) {
      this.f_19691_ = p_146786_;
   }

   public int m_146790_() {
      return this.f_19688_;
   }

   public void m_19740_(int p_19741_) {
      this.f_19688_ = p_19741_;
   }

   public void m_19718_(@Nullable LivingEntity p_19719_) {
      this.f_19694_ = p_19719_;
      this.f_19695_ = p_19719_ == null ? null : p_19719_.m_142081_();
   }

   @Nullable
   public LivingEntity m_19749_() {
      if (this.f_19694_ == null && this.f_19695_ != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.f_19695_);
         if (entity instanceof LivingEntity) {
            this.f_19694_ = (LivingEntity)entity;
         }
      }

      return this.f_19694_;
   }

   protected void m_7378_(CompoundTag p_19727_) {
      this.f_19797_ = p_19727_.m_128451_("Age");
      this.f_19687_ = p_19727_.m_128451_("Duration");
      this.f_19688_ = p_19727_.m_128451_("WaitTime");
      this.f_19689_ = p_19727_.m_128451_("ReapplicationDelay");
      this.f_19691_ = p_19727_.m_128451_("DurationOnUse");
      this.f_19692_ = p_19727_.m_128457_("RadiusOnUse");
      this.f_19693_ = p_19727_.m_128457_("RadiusPerTick");
      this.m_19712_(p_19727_.m_128457_("Radius"));
      if (p_19727_.m_128403_("Owner")) {
         this.f_19695_ = p_19727_.m_128342_("Owner");
      }

      if (p_19727_.m_128425_("Particle", 8)) {
         try {
            this.m_19724_(ParticleArgument.m_103944_(new StringReader(p_19727_.m_128461_("Particle"))));
         } catch (CommandSyntaxException commandsyntaxexception) {
            f_19696_.warn("Couldn't load custom particle {}", p_19727_.m_128461_("Particle"), commandsyntaxexception);
         }
      }

      if (p_19727_.m_128425_("Color", 99)) {
         this.m_19714_(p_19727_.m_128451_("Color"));
      }

      if (p_19727_.m_128425_("Potion", 8)) {
         this.m_19722_(PotionUtils.m_43577_(p_19727_));
      }

      if (p_19727_.m_128425_("Effects", 9)) {
         ListTag listtag = p_19727_.m_128437_("Effects", 10);
         this.f_19685_.clear();

         for(int i = 0; i < listtag.size(); ++i) {
            MobEffectInstance mobeffectinstance = MobEffectInstance.m_19560_(listtag.m_128728_(i));
            if (mobeffectinstance != null) {
               this.m_19716_(mobeffectinstance);
            }
         }
      }

   }

   protected void m_7380_(CompoundTag p_19737_) {
      p_19737_.m_128405_("Age", this.f_19797_);
      p_19737_.m_128405_("Duration", this.f_19687_);
      p_19737_.m_128405_("WaitTime", this.f_19688_);
      p_19737_.m_128405_("ReapplicationDelay", this.f_19689_);
      p_19737_.m_128405_("DurationOnUse", this.f_19691_);
      p_19737_.m_128350_("RadiusOnUse", this.f_19692_);
      p_19737_.m_128350_("RadiusPerTick", this.f_19693_);
      p_19737_.m_128350_("Radius", this.m_19743_());
      p_19737_.m_128359_("Particle", this.m_19745_().m_5942_());
      if (this.f_19695_ != null) {
         p_19737_.m_128362_("Owner", this.f_19695_);
      }

      if (this.f_19690_) {
         p_19737_.m_128405_("Color", this.m_19744_());
      }

      if (this.f_19701_ != Potions.f_43598_) {
         p_19737_.m_128359_("Potion", Registry.f_122828_.m_7981_(this.f_19701_).toString());
      }

      if (!this.f_19685_.isEmpty()) {
         ListTag listtag = new ListTag();

         for(MobEffectInstance mobeffectinstance : this.f_19685_) {
            listtag.add(mobeffectinstance.m_19555_(new CompoundTag()));
         }

         p_19737_.m_128365_("Effects", listtag);
      }

   }

   public void m_7350_(EntityDataAccessor<?> p_19729_) {
      if (f_19697_.equals(p_19729_)) {
         this.m_6210_();
      }

      super.m_7350_(p_19729_);
   }

   public Potion m_146791_() {
      return this.f_19701_;
   }

   public PushReaction m_7752_() {
      return PushReaction.IGNORE;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public EntityDimensions m_6972_(Pose p_19721_) {
      return EntityDimensions.m_20395_(this.m_19743_() * 2.0F, 0.5F);
   }
}