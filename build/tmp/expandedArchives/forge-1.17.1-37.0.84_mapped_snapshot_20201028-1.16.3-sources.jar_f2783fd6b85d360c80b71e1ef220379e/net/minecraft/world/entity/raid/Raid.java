package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class Raid {
   private static final int f_150204_ = 2;
   private static final int f_150205_ = 0;
   private static final int f_150206_ = 1;
   private static final int f_150207_ = 2;
   private static final int f_150208_ = 32;
   private static final int f_150209_ = 48000;
   private static final int f_150210_ = 3;
   private static final String f_150211_ = "block.minecraft.ominous_banner";
   private static final String f_150212_ = "event.minecraft.raid.raiders_remaining";
   public static final int f_150197_ = 16;
   private static final int f_150213_ = 40;
   private static final int f_150214_ = 300;
   public static final int f_150198_ = 2400;
   public static final int f_150199_ = 600;
   private static final int f_150215_ = 30;
   public static final int f_150200_ = 24000;
   public static final int f_150201_ = 5;
   private static final int f_150216_ = 2;
   private static final Component f_37665_ = new TranslatableComponent("event.minecraft.raid");
   private static final Component f_37666_ = new TranslatableComponent("event.minecraft.raid.victory");
   private static final Component f_37667_ = new TranslatableComponent("event.minecraft.raid.defeat");
   private static final Component f_37668_ = f_37665_.m_6881_().m_130946_(" - ").m_7220_(f_37666_);
   private static final Component f_37669_ = f_37665_.m_6881_().m_130946_(" - ").m_7220_(f_37667_);
   private static final int f_150217_ = 48000;
   public static final int f_150202_ = 9216;
   public static final int f_150203_ = 12544;
   private final Map<Integer, Raider> f_37670_ = Maps.newHashMap();
   private final Map<Integer, Set<Raider>> f_37671_ = Maps.newHashMap();
   private final Set<UUID> f_37672_ = Sets.newHashSet();
   private long f_37673_;
   private BlockPos f_37674_;
   private final ServerLevel f_37675_;
   private boolean f_37676_;
   private final int f_37677_;
   private float f_37678_;
   private int f_37679_;
   private boolean f_37680_;
   private int f_37681_;
   private final ServerBossEvent f_37682_ = new ServerBossEvent(f_37665_, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10);
   private int f_37683_;
   private int f_37684_;
   private final Random f_37685_ = new Random();
   private final int f_37686_;
   private Raid.RaidStatus f_37687_;
   private int f_37688_;
   private Optional<BlockPos> f_37689_ = Optional.empty();

   public Raid(int p_37692_, ServerLevel p_37693_, BlockPos p_37694_) {
      this.f_37677_ = p_37692_;
      this.f_37675_ = p_37693_;
      this.f_37680_ = true;
      this.f_37684_ = 300;
      this.f_37682_.m_142711_(0.0F);
      this.f_37674_ = p_37694_;
      this.f_37686_ = this.m_37724_(p_37693_.m_46791_());
      this.f_37687_ = Raid.RaidStatus.ONGOING;
   }

   public Raid(ServerLevel p_37696_, CompoundTag p_37697_) {
      this.f_37675_ = p_37696_;
      this.f_37677_ = p_37697_.m_128451_("Id");
      this.f_37676_ = p_37697_.m_128471_("Started");
      this.f_37680_ = p_37697_.m_128471_("Active");
      this.f_37673_ = p_37697_.m_128454_("TicksActive");
      this.f_37679_ = p_37697_.m_128451_("BadOmenLevel");
      this.f_37681_ = p_37697_.m_128451_("GroupsSpawned");
      this.f_37684_ = p_37697_.m_128451_("PreRaidTicks");
      this.f_37683_ = p_37697_.m_128451_("PostRaidTicks");
      this.f_37678_ = p_37697_.m_128457_("TotalHealth");
      this.f_37674_ = new BlockPos(p_37697_.m_128451_("CX"), p_37697_.m_128451_("CY"), p_37697_.m_128451_("CZ"));
      this.f_37686_ = p_37697_.m_128451_("NumGroups");
      this.f_37687_ = Raid.RaidStatus.m_37803_(p_37697_.m_128461_("Status"));
      this.f_37672_.clear();
      if (p_37697_.m_128425_("HeroesOfTheVillage", 9)) {
         ListTag listtag = p_37697_.m_128437_("HeroesOfTheVillage", 11);

         for(int i = 0; i < listtag.size(); ++i) {
            this.f_37672_.add(NbtUtils.m_129233_(listtag.get(i)));
         }
      }

   }

   public boolean m_37706_() {
      return this.m_37767_() || this.m_37768_();
   }

   public boolean m_37749_() {
      return this.m_37757_() && this.m_37778_() == 0 && this.f_37684_ > 0;
   }

   public boolean m_37757_() {
      return this.f_37681_ > 0;
   }

   public boolean m_37762_() {
      return this.f_37687_ == Raid.RaidStatus.STOPPED;
   }

   public boolean m_37767_() {
      return this.f_37687_ == Raid.RaidStatus.VICTORY;
   }

   public boolean m_37768_() {
      return this.f_37687_ == Raid.RaidStatus.LOSS;
   }

   public float m_150220_() {
      return this.f_37678_;
   }

   public Set<Raider> m_150221_() {
      Set<Raider> set = Sets.newHashSet();

      for(Set<Raider> set1 : this.f_37671_.values()) {
         set.addAll(set1);
      }

      return set;
   }

   public Level m_37769_() {
      return this.f_37675_;
   }

   public boolean m_37770_() {
      return this.f_37676_;
   }

   public int m_37771_() {
      return this.f_37681_;
   }

   private Predicate<ServerPlayer> m_37784_() {
      return (p_37723_) -> {
         BlockPos blockpos = p_37723_.m_142538_();
         return p_37723_.m_6084_() && this.f_37675_.m_8832_(blockpos) == this;
      };
   }

   private void m_37785_() {
      Set<ServerPlayer> set = Sets.newHashSet(this.f_37682_.m_8324_());
      List<ServerPlayer> list = this.f_37675_.m_8795_(this.m_37784_());

      for(ServerPlayer serverplayer : list) {
         if (!set.contains(serverplayer)) {
            this.f_37682_.m_6543_(serverplayer);
         }
      }

      for(ServerPlayer serverplayer1 : set) {
         if (!list.contains(serverplayer1)) {
            this.f_37682_.m_6539_(serverplayer1);
         }
      }

   }

   public int m_37772_() {
      return 5;
   }

   public int m_37773_() {
      return this.f_37679_;
   }

   public void m_150218_(int p_150219_) {
      this.f_37679_ = p_150219_;
   }

   public void m_37728_(Player p_37729_) {
      if (p_37729_.m_21023_(MobEffects.f_19594_)) {
         this.f_37679_ += p_37729_.m_21124_(MobEffects.f_19594_).m_19564_() + 1;
         this.f_37679_ = Mth.m_14045_(this.f_37679_, 0, this.m_37772_());
      }

      p_37729_.m_21195_(MobEffects.f_19594_);
   }

   public void m_37774_() {
      this.f_37680_ = false;
      this.f_37682_.m_7706_();
      this.f_37687_ = Raid.RaidStatus.STOPPED;
   }

   public void m_37775_() {
      if (!this.m_37762_()) {
         if (this.f_37687_ == Raid.RaidStatus.ONGOING) {
            boolean flag = this.f_37680_;
            this.f_37680_ = this.f_37675_.m_46805_(this.f_37674_);
            if (this.f_37675_.m_46791_() == Difficulty.PEACEFUL) {
               this.m_37774_();
               return;
            }

            if (flag != this.f_37680_) {
               this.f_37682_.m_8321_(this.f_37680_);
            }

            if (!this.f_37680_) {
               return;
            }

            if (!this.f_37675_.m_8802_(this.f_37674_)) {
               this.m_37786_();
            }

            if (!this.f_37675_.m_8802_(this.f_37674_)) {
               if (this.f_37681_ > 0) {
                  this.f_37687_ = Raid.RaidStatus.LOSS;
               } else {
                  this.m_37774_();
               }
            }

            ++this.f_37673_;
            if (this.f_37673_ >= 48000L) {
               this.m_37774_();
               return;
            }

            int i = this.m_37778_();
            if (i == 0 && this.m_37698_()) {
               if (this.f_37684_ <= 0) {
                  if (this.f_37684_ == 0 && this.f_37681_ > 0) {
                     this.f_37684_ = 300;
                     this.f_37682_.m_6456_(f_37665_);
                     return;
                  }
               } else {
                  boolean flag1 = this.f_37689_.isPresent();
                  boolean flag2 = !flag1 && this.f_37684_ % 5 == 0;
                  if (flag1 && !this.f_37675_.m_143340_(this.f_37689_.get())) {
                     flag2 = true;
                  }

                  if (flag2) {
                     int j = 0;
                     if (this.f_37684_ < 100) {
                        j = 1;
                     } else if (this.f_37684_ < 40) {
                        j = 2;
                     }

                     this.f_37689_ = this.m_37763_(j);
                  }

                  if (this.f_37684_ == 300 || this.f_37684_ % 20 == 0) {
                     this.m_37785_();
                  }

                  --this.f_37684_;
                  this.f_37682_.m_142711_(Mth.m_14036_((float)(300 - this.f_37684_) / 300.0F, 0.0F, 1.0F));
               }
            }

            if (this.f_37673_ % 20L == 0L) {
               this.m_37785_();
               this.m_37703_();
               if (i > 0) {
                  if (i <= 2) {
                     this.f_37682_.m_6456_(f_37665_.m_6881_().m_130946_(" - ").m_7220_(new TranslatableComponent("event.minecraft.raid.raiders_remaining", i)));
                  } else {
                     this.f_37682_.m_6456_(f_37665_);
                  }
               } else {
                  this.f_37682_.m_6456_(f_37665_);
               }
            }

            boolean flag3 = false;
            int k = 0;

            while(this.m_37704_()) {
               BlockPos blockpos = this.f_37689_.isPresent() ? this.f_37689_.get() : this.m_37707_(k, 20);
               if (blockpos != null) {
                  this.f_37676_ = true;
                  this.m_37755_(blockpos);
                  if (!flag3) {
                     this.m_37743_(blockpos);
                     flag3 = true;
                  }
               } else {
                  ++k;
               }

               if (k > 3) {
                  this.m_37774_();
                  break;
               }
            }

            if (this.m_37770_() && !this.m_37698_() && i == 0) {
               if (this.f_37683_ < 40) {
                  ++this.f_37683_;
               } else {
                  this.f_37687_ = Raid.RaidStatus.VICTORY;

                  for(UUID uuid : this.f_37672_) {
                     Entity entity = this.f_37675_.m_8791_(uuid);
                     if (entity instanceof LivingEntity && !entity.m_5833_()) {
                        LivingEntity livingentity = (LivingEntity)entity;
                        livingentity.m_7292_(new MobEffectInstance(MobEffects.f_19595_, 48000, this.f_37679_ - 1, false, false, true));
                        if (livingentity instanceof ServerPlayer) {
                           ServerPlayer serverplayer = (ServerPlayer)livingentity;
                           serverplayer.m_36220_(Stats.f_12950_);
                           CriteriaTriggers.f_10557_.m_53645_(serverplayer);
                        }
                     }
                  }
               }
            }

            this.m_37705_();
         } else if (this.m_37706_()) {
            ++this.f_37688_;
            if (this.f_37688_ >= 600) {
               this.m_37774_();
               return;
            }

            if (this.f_37688_ % 20 == 0) {
               this.m_37785_();
               this.f_37682_.m_8321_(true);
               if (this.m_37767_()) {
                  this.f_37682_.m_142711_(0.0F);
                  this.f_37682_.m_6456_(f_37668_);
               } else {
                  this.f_37682_.m_6456_(f_37669_);
               }
            }
         }

      }
   }

   private void m_37786_() {
      Stream<SectionPos> stream = SectionPos.m_123201_(SectionPos.m_123199_(this.f_37674_), 2);
      stream.filter(this.f_37675_::m_8762_).map(SectionPos::m_123250_).min(Comparator.comparingDouble((p_37766_) -> {
         return p_37766_.m_123331_(this.f_37674_);
      })).ifPresent(this::m_37760_);
   }

   private Optional<BlockPos> m_37763_(int p_37764_) {
      for(int i = 0; i < 3; ++i) {
         BlockPos blockpos = this.m_37707_(p_37764_, 1);
         if (blockpos != null) {
            return Optional.of(blockpos);
         }
      }

      return Optional.empty();
   }

   private boolean m_37698_() {
      if (this.m_37700_()) {
         return !this.m_37701_();
      } else {
         return !this.m_37699_();
      }
   }

   private boolean m_37699_() {
      return this.m_37771_() == this.f_37686_;
   }

   private boolean m_37700_() {
      return this.f_37679_ > 1;
   }

   private boolean m_37701_() {
      return this.m_37771_() > this.f_37686_;
   }

   private boolean m_37702_() {
      return this.m_37699_() && this.m_37778_() == 0 && this.m_37700_();
   }

   private void m_37703_() {
      Iterator<Set<Raider>> iterator = this.f_37671_.values().iterator();
      Set<Raider> set = Sets.newHashSet();

      while(iterator.hasNext()) {
         Set<Raider> set1 = iterator.next();

         for(Raider raider : set1) {
            BlockPos blockpos = raider.m_142538_();
            if (!raider.m_146910_() && raider.f_19853_.m_46472_() == this.f_37675_.m_46472_() && !(this.f_37674_.m_123331_(blockpos) >= 12544.0D)) {
               if (raider.f_19797_ > 600) {
                  if (this.f_37675_.m_8791_(raider.m_142081_()) == null) {
                     set.add(raider);
                  }

                  if (!this.f_37675_.m_8802_(blockpos) && raider.m_21216_() > 2400) {
                     raider.m_37863_(raider.m_37889_() + 1);
                  }

                  if (raider.m_37889_() >= 30) {
                     set.add(raider);
                  }
               }
            } else {
               set.add(raider);
            }
         }
      }

      for(Raider raider1 : set) {
         this.m_37740_(raider1, true);
      }

   }

   private void m_37743_(BlockPos p_37744_) {
      float f = 13.0F;
      int i = 64;
      Collection<ServerPlayer> collection = this.f_37682_.m_8324_();

      for(ServerPlayer serverplayer : this.f_37675_.m_6907_()) {
         Vec3 vec3 = serverplayer.m_20182_();
         Vec3 vec31 = Vec3.m_82512_(p_37744_);
         double d0 = Math.sqrt((vec31.f_82479_ - vec3.f_82479_) * (vec31.f_82479_ - vec3.f_82479_) + (vec31.f_82481_ - vec3.f_82481_) * (vec31.f_82481_ - vec3.f_82481_));
         double d1 = vec3.f_82479_ + 13.0D / d0 * (vec31.f_82479_ - vec3.f_82479_);
         double d2 = vec3.f_82481_ + 13.0D / d0 * (vec31.f_82481_ - vec3.f_82481_);
         if (d0 <= 64.0D || collection.contains(serverplayer)) {
            serverplayer.f_8906_.m_141995_(new ClientboundSoundPacket(SoundEvents.f_12355_, SoundSource.NEUTRAL, d1, serverplayer.m_20186_(), d2, 64.0F, 1.0F));
         }
      }

   }

   private void m_37755_(BlockPos p_37756_) {
      boolean flag = false;
      int i = this.f_37681_ + 1;
      this.f_37678_ = 0.0F;
      DifficultyInstance difficultyinstance = this.f_37675_.m_6436_(p_37756_);
      boolean flag1 = this.m_37702_();

      for(Raid.RaiderType raid$raidertype : Raid.RaiderType.f_37813_) {
         int j = this.m_37730_(raid$raidertype, i, flag1) + this.m_37734_(raid$raidertype, this.f_37685_, i, difficultyinstance, flag1);
         int k = 0;

         for(int l = 0; l < j; ++l) {
            Raider raider = raid$raidertype.f_37814_.m_20615_(this.f_37675_);
            if (!flag && raider.m_7490_()) {
               raider.m_33075_(true);
               this.m_37710_(i, raider);
               flag = true;
            }

            this.m_37713_(i, raider, p_37756_, false);
            if (raid$raidertype.f_37814_ == EntityType.f_20518_) {
               Raider raider1 = null;
               if (i == this.m_37724_(Difficulty.NORMAL)) {
                  raider1 = EntityType.f_20513_.m_20615_(this.f_37675_);
               } else if (i >= this.m_37724_(Difficulty.HARD)) {
                  if (k == 0) {
                     raider1 = EntityType.f_20568_.m_20615_(this.f_37675_);
                  } else {
                     raider1 = EntityType.f_20493_.m_20615_(this.f_37675_);
                  }
               }

               ++k;
               if (raider1 != null) {
                  this.m_37713_(i, raider1, p_37756_, false);
                  raider1.m_20035_(p_37756_, 0.0F, 0.0F);
                  raider1.m_20329_(raider);
               }
            }
         }
      }

      this.f_37689_ = Optional.empty();
      ++this.f_37681_;
      this.m_37776_();
      this.m_37705_();
   }

   public void m_37713_(int p_37714_, Raider p_37715_, @Nullable BlockPos p_37716_, boolean p_37717_) {
      boolean flag = this.m_37752_(p_37714_, p_37715_);
      if (flag) {
         p_37715_.m_37851_(this);
         p_37715_.m_37842_(p_37714_);
         p_37715_.m_37897_(true);
         p_37715_.m_37863_(0);
         if (!p_37717_ && p_37716_ != null) {
            p_37715_.m_6034_((double)p_37716_.m_123341_() + 0.5D, (double)p_37716_.m_123342_() + 1.0D, (double)p_37716_.m_123343_() + 0.5D);
            p_37715_.m_6518_(this.f_37675_, this.f_37675_.m_6436_(p_37716_), MobSpawnType.EVENT, (SpawnGroupData)null, (CompoundTag)null);
            p_37715_.m_7895_(p_37714_, false);
            p_37715_.m_6853_(true);
            this.f_37675_.m_47205_(p_37715_);
         }
      }

   }

   public void m_37776_() {
      this.f_37682_.m_142711_(Mth.m_14036_(this.m_37777_() / this.f_37678_, 0.0F, 1.0F));
   }

   public float m_37777_() {
      float f = 0.0F;

      for(Set<Raider> set : this.f_37671_.values()) {
         for(Raider raider : set) {
            f += raider.m_21223_();
         }
      }

      return f;
   }

   private boolean m_37704_() {
      return this.f_37684_ == 0 && (this.f_37681_ < this.f_37686_ || this.m_37702_()) && this.m_37778_() == 0;
   }

   public int m_37778_() {
      return this.f_37671_.values().stream().mapToInt(Set::size).sum();
   }

   public void m_37740_(Raider p_37741_, boolean p_37742_) {
      Set<Raider> set = this.f_37671_.get(p_37741_.m_37887_());
      if (set != null) {
         boolean flag = set.remove(p_37741_);
         if (flag) {
            if (p_37742_) {
               this.f_37678_ -= p_37741_.m_21223_();
            }

            p_37741_.m_37851_((Raid)null);
            this.m_37776_();
            this.m_37705_();
         }
      }

   }

   private void m_37705_() {
      this.f_37675_.m_8905_().m_77762_();
   }

   public static ItemStack m_37779_() {
      ItemStack itemstack = new ItemStack(Items.f_42660_);
      CompoundTag compoundtag = itemstack.m_41698_("BlockEntityTag");
      ListTag listtag = (new BannerPattern.Builder()).m_58588_(BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN).m_58588_(BannerPattern.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY).m_58588_(BannerPattern.STRIPE_CENTER, DyeColor.GRAY).m_58588_(BannerPattern.BORDER, DyeColor.LIGHT_GRAY).m_58588_(BannerPattern.STRIPE_MIDDLE, DyeColor.BLACK).m_58588_(BannerPattern.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY).m_58588_(BannerPattern.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY).m_58588_(BannerPattern.BORDER, DyeColor.BLACK).m_58587_();
      compoundtag.m_128365_("Patterns", listtag);
      itemstack.m_41654_(ItemStack.TooltipPart.ADDITIONAL);
      itemstack.m_41714_((new TranslatableComponent("block.minecraft.ominous_banner")).m_130940_(ChatFormatting.GOLD));
      return itemstack;
   }

   @Nullable
   public Raider m_37750_(int p_37751_) {
      return this.f_37670_.get(p_37751_);
   }

   @Nullable
   private BlockPos m_37707_(int p_37708_, int p_37709_) {
      int i = p_37708_ == 0 ? 2 : 2 - p_37708_;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i1 = 0; i1 < p_37709_; ++i1) {
         float f = this.f_37675_.f_46441_.nextFloat() * ((float)Math.PI * 2F);
         int j = this.f_37674_.m_123341_() + Mth.m_14143_(Mth.m_14089_(f) * 32.0F * (float)i) + this.f_37675_.f_46441_.nextInt(5);
         int l = this.f_37674_.m_123343_() + Mth.m_14143_(Mth.m_14031_(f) * 32.0F * (float)i) + this.f_37675_.f_46441_.nextInt(5);
         int k = this.f_37675_.m_6924_(Heightmap.Types.WORLD_SURFACE, j, l);
         blockpos$mutableblockpos.m_122178_(j, k, l);
         if (!this.f_37675_.m_8802_(blockpos$mutableblockpos) || p_37708_ >= 2) {
            int j1 = 10;
            if (this.f_37675_.m_151572_(blockpos$mutableblockpos.m_123341_() - 10, blockpos$mutableblockpos.m_123343_() - 10, blockpos$mutableblockpos.m_123341_() + 10, blockpos$mutableblockpos.m_123343_() + 10) && this.f_37675_.m_143340_(blockpos$mutableblockpos) && (NaturalSpawner.m_47051_(SpawnPlacements.Type.ON_GROUND, this.f_37675_, blockpos$mutableblockpos, EntityType.f_20518_) || this.f_37675_.m_8055_(blockpos$mutableblockpos.m_7495_()).m_60713_(Blocks.f_50125_) && this.f_37675_.m_8055_(blockpos$mutableblockpos).m_60795_())) {
               return blockpos$mutableblockpos;
            }
         }
      }

      return null;
   }

   private boolean m_37752_(int p_37753_, Raider p_37754_) {
      return this.m_37718_(p_37753_, p_37754_, true);
   }

   public boolean m_37718_(int p_37719_, Raider p_37720_, boolean p_37721_) {
      this.f_37671_.computeIfAbsent(p_37719_, (p_37746_) -> {
         return Sets.newHashSet();
      });
      Set<Raider> set = this.f_37671_.get(p_37719_);
      Raider raider = null;

      for(Raider raider1 : set) {
         if (raider1.m_142081_().equals(p_37720_.m_142081_())) {
            raider = raider1;
            break;
         }
      }

      if (raider != null) {
         set.remove(raider);
         set.add(p_37720_);
      }

      set.add(p_37720_);
      if (p_37721_) {
         this.f_37678_ += p_37720_.m_21223_();
      }

      this.m_37776_();
      this.m_37705_();
      return true;
   }

   public void m_37710_(int p_37711_, Raider p_37712_) {
      this.f_37670_.put(p_37711_, p_37712_);
      p_37712_.m_8061_(EquipmentSlot.HEAD, m_37779_());
      p_37712_.m_21409_(EquipmentSlot.HEAD, 2.0F);
   }

   public void m_37758_(int p_37759_) {
      this.f_37670_.remove(p_37759_);
   }

   public BlockPos m_37780_() {
      return this.f_37674_;
   }

   private void m_37760_(BlockPos p_37761_) {
      this.f_37674_ = p_37761_;
   }

   public int m_37781_() {
      return this.f_37677_;
   }

   private int m_37730_(Raid.RaiderType p_37731_, int p_37732_, boolean p_37733_) {
      return p_37733_ ? p_37731_.f_37815_[this.f_37686_] : p_37731_.f_37815_[p_37732_];
   }

   private int m_37734_(Raid.RaiderType p_37735_, Random p_37736_, int p_37737_, DifficultyInstance p_37738_, boolean p_37739_) {
      Difficulty difficulty = p_37738_.m_19048_();
      boolean flag = difficulty == Difficulty.EASY;
      boolean flag1 = difficulty == Difficulty.NORMAL;
      int i;
      switch(p_37735_) {
      case WITCH:
         if (flag || p_37737_ <= 2 || p_37737_ == 4) {
            return 0;
         }

         i = 1;
         break;
      case PILLAGER:
      case VINDICATOR:
         if (flag) {
            i = p_37736_.nextInt(2);
         } else if (flag1) {
            i = 1;
         } else {
            i = 2;
         }
         break;
      case RAVAGER:
         i = !flag && p_37739_ ? 1 : 0;
         break;
      default:
         return 0;
      }

      return i > 0 ? p_37736_.nextInt(i + 1) : 0;
   }

   public boolean m_37782_() {
      return this.f_37680_;
   }

   public CompoundTag m_37747_(CompoundTag p_37748_) {
      p_37748_.m_128405_("Id", this.f_37677_);
      p_37748_.m_128379_("Started", this.f_37676_);
      p_37748_.m_128379_("Active", this.f_37680_);
      p_37748_.m_128356_("TicksActive", this.f_37673_);
      p_37748_.m_128405_("BadOmenLevel", this.f_37679_);
      p_37748_.m_128405_("GroupsSpawned", this.f_37681_);
      p_37748_.m_128405_("PreRaidTicks", this.f_37684_);
      p_37748_.m_128405_("PostRaidTicks", this.f_37683_);
      p_37748_.m_128350_("TotalHealth", this.f_37678_);
      p_37748_.m_128405_("NumGroups", this.f_37686_);
      p_37748_.m_128359_("Status", this.f_37687_.m_37800_());
      p_37748_.m_128405_("CX", this.f_37674_.m_123341_());
      p_37748_.m_128405_("CY", this.f_37674_.m_123342_());
      p_37748_.m_128405_("CZ", this.f_37674_.m_123343_());
      ListTag listtag = new ListTag();

      for(UUID uuid : this.f_37672_) {
         listtag.add(NbtUtils.m_129226_(uuid));
      }

      p_37748_.m_128365_("HeroesOfTheVillage", listtag);
      return p_37748_;
   }

   public int m_37724_(Difficulty p_37725_) {
      switch(p_37725_) {
      case EASY:
         return 3;
      case NORMAL:
         return 5;
      case HARD:
         return 7;
      default:
         return 0;
      }
   }

   public float m_37783_() {
      int i = this.m_37773_();
      if (i == 2) {
         return 0.1F;
      } else if (i == 3) {
         return 0.25F;
      } else if (i == 4) {
         return 0.5F;
      } else {
         return i == 5 ? 0.75F : 0.0F;
      }
   }

   public void m_37726_(Entity p_37727_) {
      this.f_37672_.add(p_37727_.m_142081_());
   }

   static enum RaidStatus {
      ONGOING,
      VICTORY,
      LOSS,
      STOPPED;

      private static final Raid.RaidStatus[] f_37794_ = values();

      static Raid.RaidStatus m_37803_(String p_37804_) {
         for(Raid.RaidStatus raid$raidstatus : f_37794_) {
            if (p_37804_.equalsIgnoreCase(raid$raidstatus.name())) {
               return raid$raidstatus;
            }
         }

         return ONGOING;
      }

      public String m_37800_() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public static enum RaiderType implements net.minecraftforge.common.IExtensibleEnum {
      VINDICATOR(EntityType.f_20493_, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
      EVOKER(EntityType.f_20568_, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
      PILLAGER(EntityType.f_20513_, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
      WITCH(EntityType.f_20495_, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
      RAVAGER(EntityType.f_20518_, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

      static Raid.RaiderType[] f_37813_ = values();
      final EntityType<? extends Raider> f_37814_;
      final int[] f_37815_;

      private RaiderType(EntityType<? extends Raider> p_37821_, int[] p_37822_) {
         this.f_37814_ = p_37821_;
         this.f_37815_ = p_37822_;
      }
      
      /**
       * The waveCountsIn integer decides how many entities of the EntityType defined in typeIn will spawn in each wave.
       * For example, one ravager will always spawn in wave 3.
       */
      public static RaiderType create(String name, EntityType<? extends Raider> typeIn, int[] waveCountsIn) {
         throw new IllegalStateException("Enum not extended");
      }
      
      @Override
      @Deprecated
      public void init() {
         f_37813_ = values();
      }
   }
}
