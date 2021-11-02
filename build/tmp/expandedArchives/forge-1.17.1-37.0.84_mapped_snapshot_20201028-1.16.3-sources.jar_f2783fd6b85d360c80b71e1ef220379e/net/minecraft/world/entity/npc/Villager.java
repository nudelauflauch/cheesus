package net.minecraft.world.entity.npc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.sensing.GolemSensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class Villager extends AbstractVillager implements ReputationEventHandler, VillagerDataHolder {
   private static final EntityDataAccessor<VillagerData> f_35371_ = SynchedEntityData.m_135353_(Villager.class, EntityDataSerializers.f_135043_);
   public static final int f_149997_ = 12;
   public static final Map<Item, Integer> f_35369_ = ImmutableMap.of(Items.f_42406_, 4, Items.f_42620_, 1, Items.f_42619_, 1, Items.f_42732_, 1);
   private static final int f_149999_ = 2;
   private static final Set<Item> f_35372_ = ImmutableSet.of(Items.f_42406_, Items.f_42620_, Items.f_42619_, Items.f_42405_, Items.f_42404_, Items.f_42732_, Items.f_42733_);
   private static final int f_150000_ = 10;
   private static final int f_150001_ = 1200;
   private static final int f_150002_ = 24000;
   private static final int f_150003_ = 25;
   private static final int f_150004_ = 10;
   private static final int f_150005_ = 5;
   private static final long f_150006_ = 24000L;
   @VisibleForTesting
   public static final float f_149998_ = 0.5F;
   private int f_35373_;
   private boolean f_35374_;
   @Nullable
   private Player f_35375_;
   private boolean f_150007_;
   private byte f_35376_;
   private final GossipContainer f_35377_ = new GossipContainer();
   private long f_35378_;
   private long f_35361_;
   private int f_35362_;
   private long f_35363_;
   private int f_35364_;
   private long f_35365_;
   private boolean f_35366_;
   private static final ImmutableList<MemoryModuleType<?>> f_35367_ = ImmutableList.of(MemoryModuleType.f_26359_, MemoryModuleType.f_26360_, MemoryModuleType.f_26361_, MemoryModuleType.f_26362_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26366_, MemoryModuleType.f_26367_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26332_, MemoryModuleType.f_26370_, MemoryModuleType.f_26371_, MemoryModuleType.f_26374_, MemoryModuleType.f_26375_, MemoryModuleType.f_26377_, MemoryModuleType.f_26379_, MemoryModuleType.f_26380_, MemoryModuleType.f_26381_, MemoryModuleType.f_26382_, MemoryModuleType.f_26323_, MemoryModuleType.f_26363_, MemoryModuleType.f_26324_, MemoryModuleType.f_26325_, MemoryModuleType.f_26326_, MemoryModuleType.f_26328_, MemoryModuleType.f_26329_, MemoryModuleType.f_26330_, MemoryModuleType.f_26327_);
   private static final ImmutableList<SensorType<? extends Sensor<? super Villager>>> f_35368_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26810_, SensorType.f_26813_, SensorType.f_26814_, SensorType.f_26815_, SensorType.f_26816_, SensorType.f_26817_, SensorType.f_26818_);
   public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<Villager, PoiType>> f_35370_ = ImmutableMap.of(MemoryModuleType.f_26359_, (p_35493_, p_35494_) -> {
      return p_35494_ == PoiType.f_27346_;
   }, MemoryModuleType.f_26360_, (p_35486_, p_35487_) -> {
      return p_35486_.m_7141_().m_35571_().m_35622_() == p_35487_;
   }, MemoryModuleType.f_26361_, (p_35469_, p_35470_) -> {
      return PoiType.f_27329_.test(p_35470_);
   }, MemoryModuleType.f_26362_, (p_35434_, p_35435_) -> {
      return p_35435_ == PoiType.f_27347_;
   });

   public Villager(EntityType<? extends Villager> p_35381_, Level p_35382_) {
      this(p_35381_, p_35382_, VillagerType.f_35821_);
   }

   public Villager(EntityType<? extends Villager> p_35384_, Level p_35385_, VillagerType p_35386_) {
      super(p_35384_, p_35385_);
      ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
      this.m_21573_().m_7008_(true);
      this.m_21553_(true);
      this.m_141967_(this.m_7141_().m_35567_(p_35386_).m_35565_(VillagerProfession.f_35585_));
   }

   public Brain<Villager> m_6274_() {
      return (Brain<Villager>)super.m_6274_();
   }

   protected Brain.Provider<Villager> m_5490_() {
      return Brain.m_21923_(f_35367_, f_35368_);
   }

   protected Brain<?> m_8075_(Dynamic<?> p_35445_) {
      Brain<Villager> brain = this.m_5490_().m_22073_(p_35445_);
      this.m_35424_(brain);
      return brain;
   }

   public void m_35483_(ServerLevel p_35484_) {
      Brain<Villager> brain = this.m_6274_();
      brain.m_21933_(p_35484_, this);
      this.f_20939_ = brain.m_21973_();
      this.m_35424_(this.m_6274_());
   }

   private void m_35424_(Brain<Villager> p_35425_) {
      VillagerProfession villagerprofession = this.m_7141_().m_35571_();
      if (this.m_6162_()) {
         p_35425_.m_21912_(Schedule.f_38014_);
         p_35425_.m_21900_(Activity.f_37981_, VillagerGoalPackages.m_24583_(0.5F));
      } else {
         p_35425_.m_21912_(Schedule.f_38015_);
         p_35425_.m_21903_(Activity.f_37980_, VillagerGoalPackages.m_24589_(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.f_26360_, MemoryStatus.VALUE_PRESENT)));
      }

      p_35425_.m_21900_(Activity.f_37978_, VillagerGoalPackages.m_24585_(villagerprofession, 0.5F));
      p_35425_.m_21903_(Activity.f_37983_, VillagerGoalPackages.m_24595_(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.f_26362_, MemoryStatus.VALUE_PRESENT)));
      p_35425_.m_21900_(Activity.f_37982_, VillagerGoalPackages.m_24592_(villagerprofession, 0.5F));
      p_35425_.m_21900_(Activity.f_37979_, VillagerGoalPackages.m_24598_(villagerprofession, 0.5F));
      p_35425_.m_21900_(Activity.f_37984_, VillagerGoalPackages.m_24601_(villagerprofession, 0.5F));
      p_35425_.m_21900_(Activity.f_37986_, VillagerGoalPackages.m_24604_(villagerprofession, 0.5F));
      p_35425_.m_21900_(Activity.f_37985_, VillagerGoalPackages.m_24607_(villagerprofession, 0.5F));
      p_35425_.m_21900_(Activity.f_37987_, VillagerGoalPackages.m_24610_(villagerprofession, 0.5F));
      p_35425_.m_21930_(ImmutableSet.of(Activity.f_37978_));
      p_35425_.m_21944_(Activity.f_37979_);
      p_35425_.m_21889_(Activity.f_37979_);
      p_35425_.m_21862_(this.f_19853_.m_46468_(), this.f_19853_.m_46467_());
   }

   protected void m_142669_() {
      super.m_142669_();
      if (this.f_19853_ instanceof ServerLevel) {
         this.m_35483_((ServerLevel)this.f_19853_);
      }

   }

   public static AttributeSupplier.Builder m_35503_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.5D).m_22268_(Attributes.f_22277_, 48.0D);
   }

   public boolean m_35504_() {
      return this.f_35366_;
   }

   protected void m_8024_() {
      this.f_19853_.m_46473_().m_6180_("villagerBrain");
      this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
      this.f_19853_.m_46473_().m_7238_();
      if (this.f_35366_) {
         this.f_35366_ = false;
      }

      if (!this.m_35306_() && this.f_35373_ > 0) {
         --this.f_35373_;
         if (this.f_35373_ <= 0) {
            if (this.f_35374_) {
               this.m_35528_();
               this.f_35374_ = false;
            }

            this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 200, 0));
         }
      }

      if (this.f_35375_ != null && this.f_19853_ instanceof ServerLevel) {
         ((ServerLevel)this.f_19853_).m_8670_(ReputationEventType.f_26989_, this.f_35375_, this);
         this.f_19853_.m_7605_(this, (byte)14);
         this.f_35375_ = null;
      }

      if (!this.m_21525_() && this.f_19796_.nextInt(100) == 0) {
         Raid raid = ((ServerLevel)this.f_19853_).m_8832_(this.m_142538_());
         if (raid != null && raid.m_37782_() && !raid.m_37706_()) {
            this.f_19853_.m_7605_(this, (byte)42);
         }
      }

      if (this.m_7141_().m_35571_() == VillagerProfession.f_35585_ && this.m_35306_()) {
         this.m_7996_();
      }

      super.m_8024_();
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_35303_() > 0) {
         this.m_35319_(this.m_35303_() - 1);
      }

      this.m_35530_();
   }

   public InteractionResult m_6071_(Player p_35472_, InteractionHand p_35473_) {
      ItemStack itemstack = p_35472_.m_21120_(p_35473_);
      if (itemstack.m_41720_() != Items.f_42601_ && this.m_6084_() && !this.m_35306_() && !this.m_5803_() && !p_35472_.m_36341_()) {
         if (this.m_6162_()) {
            this.m_35518_();
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         } else {
            boolean flag = this.m_6616_().isEmpty();
            if (p_35473_ == InteractionHand.MAIN_HAND) {
               if (flag && !this.f_19853_.f_46443_) {
                  this.m_35518_();
               }

               p_35472_.m_36220_(Stats.f_12940_);
            }

            if (flag) {
               return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            } else {
               if (!this.f_19853_.f_46443_ && !this.f_35261_.isEmpty()) {
                  this.m_35536_(p_35472_);
               }

               return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
         }
      } else {
         return super.m_6071_(p_35472_, p_35473_);
      }
   }

   private void m_35518_() {
      this.m_35319_(40);
      if (!this.f_19853_.m_5776_()) {
         this.m_5496_(SoundEvents.f_12507_, this.m_6121_(), this.m_6100_());
      }

   }

   private void m_35536_(Player p_35537_) {
      this.m_35540_(p_35537_);
      this.m_7189_(p_35537_);
      this.m_45301_(p_35537_, this.m_5446_(), this.m_7141_().m_35576_());
   }

   public void m_7189_(@Nullable Player p_35508_) {
      boolean flag = this.m_7962_() != null && p_35508_ == null;
      super.m_7189_(p_35508_);
      if (flag) {
         this.m_7996_();
      }

   }

   protected void m_7996_() {
      super.m_7996_();
      this.m_35519_();
   }

   private void m_35519_() {
      for(MerchantOffer merchantoffer : this.m_6616_()) {
         merchantoffer.m_45376_();
      }

   }

   public boolean m_7862_() {
      return true;
   }

   public void m_35510_() {
      this.m_35523_();

      for(MerchantOffer merchantoffer : this.m_6616_()) {
         merchantoffer.m_45372_();
      }

      this.f_35363_ = this.f_19853_.m_46467_();
      ++this.f_35364_;
   }

   private boolean m_35520_() {
      for(MerchantOffer merchantoffer : this.m_6616_()) {
         if (merchantoffer.m_45382_()) {
            return true;
         }
      }

      return false;
   }

   private boolean m_35521_() {
      return this.f_35364_ == 0 || this.f_35364_ < 2 && this.f_19853_.m_46467_() > this.f_35363_ + 2400L;
   }

   public boolean m_35511_() {
      long i = this.f_35363_ + 12000L;
      long j = this.f_19853_.m_46467_();
      boolean flag = j > i;
      long k = this.f_19853_.m_46468_();
      if (this.f_35365_ > 0L) {
         long l = this.f_35365_ / 24000L;
         long i1 = k / 24000L;
         flag |= i1 > l;
      }

      this.f_35365_ = k;
      if (flag) {
         this.f_35363_ = j;
         this.m_35531_();
      }

      return this.m_35521_() && this.m_35520_();
   }

   private void m_35522_() {
      int i = 2 - this.f_35364_;
      if (i > 0) {
         for(MerchantOffer merchantoffer : this.m_6616_()) {
            merchantoffer.m_45372_();
         }
      }

      for(int j = 0; j < i; ++j) {
         this.m_35523_();
      }

   }

   private void m_35523_() {
      for(MerchantOffer merchantoffer : this.m_6616_()) {
         merchantoffer.m_45369_();
      }

   }

   private void m_35540_(Player p_35541_) {
      int i = this.m_35532_(p_35541_);
      if (i != 0) {
         for(MerchantOffer merchantoffer : this.m_6616_()) {
            merchantoffer.m_45353_(-Mth.m_14143_((float)i * merchantoffer.m_45378_()));
         }
      }

      if (p_35541_.m_21023_(MobEffects.f_19595_)) {
         MobEffectInstance mobeffectinstance = p_35541_.m_21124_(MobEffects.f_19595_);
         int k = mobeffectinstance.m_19564_();

         for(MerchantOffer merchantoffer1 : this.m_6616_()) {
            double d0 = 0.3D + 0.0625D * (double)k;
            int j = (int)Math.floor(d0 * (double)merchantoffer1.m_45352_().m_41613_());
            merchantoffer1.m_45353_(-Math.max(j, 1));
         }
      }

   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_35371_, new VillagerData(VillagerType.f_35821_, VillagerProfession.f_35585_, 1));
   }

   public void m_7380_(CompoundTag p_35481_) {
      super.m_7380_(p_35481_);
      VillagerData.f_35550_.encodeStart(NbtOps.f_128958_, this.m_7141_()).resultOrPartial(f_19849_::error).ifPresent((p_35454_) -> {
         p_35481_.m_128365_("VillagerData", p_35454_);
      });
      p_35481_.m_128344_("FoodLevel", this.f_35376_);
      p_35481_.m_128365_("Gossips", this.f_35377_.m_26179_(NbtOps.f_128958_).getValue());
      p_35481_.m_128405_("Xp", this.f_35362_);
      p_35481_.m_128356_("LastRestock", this.f_35363_);
      p_35481_.m_128356_("LastGossipDecay", this.f_35361_);
      p_35481_.m_128405_("RestocksToday", this.f_35364_);
      if (this.f_35366_) {
         p_35481_.m_128379_("AssignProfessionWhenSpawned", true);
      }

   }

   public void m_7378_(CompoundTag p_35451_) {
      super.m_7378_(p_35451_);
      if (p_35451_.m_128425_("VillagerData", 10)) {
         DataResult<VillagerData> dataresult = VillagerData.f_35550_.parse(new Dynamic<>(NbtOps.f_128958_, p_35451_.m_128423_("VillagerData")));
         dataresult.resultOrPartial(f_19849_::error).ifPresent(this::m_141967_);
      }

      if (p_35451_.m_128425_("Offers", 10)) {
         this.f_35261_ = new MerchantOffers(p_35451_.m_128469_("Offers"));
      }

      if (p_35451_.m_128425_("FoodLevel", 1)) {
         this.f_35376_ = p_35451_.m_128445_("FoodLevel");
      }

      ListTag listtag = p_35451_.m_128437_("Gossips", 10);
      this.f_35377_.m_26177_(new Dynamic<>(NbtOps.f_128958_, listtag));
      if (p_35451_.m_128425_("Xp", 3)) {
         this.f_35362_ = p_35451_.m_128451_("Xp");
      }

      this.f_35363_ = p_35451_.m_128454_("LastRestock");
      this.f_35361_ = p_35451_.m_128454_("LastGossipDecay");
      this.m_21553_(true);
      if (this.f_19853_ instanceof ServerLevel) {
         this.m_35483_((ServerLevel)this.f_19853_);
      }

      this.f_35364_ = p_35451_.m_128451_("RestocksToday");
      if (p_35451_.m_128441_("AssignProfessionWhenSpawned")) {
         this.f_35366_ = p_35451_.m_128471_("AssignProfessionWhenSpawned");
      }

   }

   public boolean m_6785_(double p_35535_) {
      return false;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      if (this.m_5803_()) {
         return null;
      } else {
         return this.m_35306_() ? SoundEvents.f_12508_ : SoundEvents.f_12503_;
      }
   }

   protected SoundEvent m_7975_(DamageSource p_35498_) {
      return SoundEvents.f_12506_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12505_;
   }

   public void m_35512_() {
      SoundEvent soundevent = this.m_7141_().m_35571_().m_35625_();
      if (soundevent != null) {
         this.m_5496_(soundevent, this.m_6121_(), this.m_6100_());
      }

   }

   public void m_141967_(VillagerData p_35437_) {
      VillagerData villagerdata = this.m_7141_();
      if (villagerdata.m_35571_() != p_35437_.m_35571_()) {
         this.f_35261_ = null;
      }

      this.f_19804_.m_135381_(f_35371_, p_35437_);
   }

   public VillagerData m_7141_() {
      return this.f_19804_.m_135370_(f_35371_);
   }

   protected void m_8058_(MerchantOffer p_35475_) {
      int i = 3 + this.f_19796_.nextInt(4);
      this.f_35362_ += p_35475_.m_45379_();
      this.f_35375_ = this.m_7962_();
      if (this.m_35527_()) {
         this.f_35373_ = 40;
         this.f_35374_ = true;
         i += 5;
      }

      if (p_35475_.m_45383_()) {
         this.f_19853_.m_7967_(new ExperienceOrb(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5D, this.m_20189_(), i));
      }

   }

   public void m_150015_(boolean p_150016_) {
      this.f_150007_ = p_150016_;
   }

   public boolean m_150014_() {
      return this.f_150007_;
   }

   public void m_6703_(@Nullable LivingEntity p_35423_) {
      if (p_35423_ != null && this.f_19853_ instanceof ServerLevel) {
         ((ServerLevel)this.f_19853_).m_8670_(ReputationEventType.f_26987_, p_35423_, this);
         if (this.m_6084_() && p_35423_ instanceof Player) {
            this.f_19853_.m_7605_(this, (byte)13);
         }
      }

      super.m_6703_(p_35423_);
   }

   public void m_6667_(DamageSource p_35419_) {
      f_19849_.info("Villager {} died, message: '{}'", this, p_35419_.m_6157_(this).getString());
      Entity entity = p_35419_.m_7639_();
      if (entity != null) {
         this.m_35420_(entity);
      }

      this.m_35524_();
      super.m_6667_(p_35419_);
   }

   private void m_35524_() {
      this.m_35428_(MemoryModuleType.f_26359_);
      this.m_35428_(MemoryModuleType.f_26360_);
      this.m_35428_(MemoryModuleType.f_26361_);
      this.m_35428_(MemoryModuleType.f_26362_);
   }

   private void m_35420_(Entity p_35421_) {
      if (this.f_19853_ instanceof ServerLevel) {
         Optional<List<LivingEntity>> optional = this.f_20939_.m_21952_(MemoryModuleType.f_148205_);
         if (optional.isPresent()) {
            ServerLevel serverlevel = (ServerLevel)this.f_19853_;
            optional.get().stream().filter((p_35539_) -> {
               return p_35539_ instanceof ReputationEventHandler;
            }).forEach((p_35407_) -> {
               serverlevel.m_8670_(ReputationEventType.f_26988_, p_35421_, (ReputationEventHandler)p_35407_);
            });
         }
      }
   }

   public void m_35428_(MemoryModuleType<GlobalPos> p_35429_) {
      if (this.f_19853_ instanceof ServerLevel) {
         MinecraftServer minecraftserver = ((ServerLevel)this.f_19853_).m_142572_();
         this.f_20939_.m_21952_(p_35429_).ifPresent((p_35460_) -> {
            ServerLevel serverlevel = minecraftserver.m_129880_(p_35460_.m_122640_());
            if (serverlevel != null) {
               PoiManager poimanager = serverlevel.m_8904_();
               Optional<PoiType> optional = poimanager.m_27177_(p_35460_.m_122646_());
               BiPredicate<Villager, PoiType> bipredicate = f_35370_.get(p_35429_);
               if (optional.isPresent() && bipredicate.test(this, optional.get())) {
                  poimanager.m_27154_(p_35460_.m_122646_());
                  DebugPackets.m_133719_(serverlevel, p_35460_.m_122646_());
               }

            }
         });
      }
   }

   public boolean m_142668_() {
      return this.f_35376_ + this.m_35529_() >= 12 && this.m_146764_() == 0;
   }

   private boolean m_35525_() {
      return this.f_35376_ < 12;
   }

   private void m_35526_() {
      if (this.m_35525_() && this.m_35529_() != 0) {
         for(int i = 0; i < this.m_141944_().m_6643_(); ++i) {
            ItemStack itemstack = this.m_141944_().m_8020_(i);
            if (!itemstack.m_41619_()) {
               Integer integer = f_35369_.get(itemstack.m_41720_());
               if (integer != null) {
                  int j = itemstack.m_41613_();

                  for(int k = j; k > 0; --k) {
                     this.f_35376_ = (byte)(this.f_35376_ + integer);
                     this.m_141944_().m_7407_(i, 1);
                     if (!this.m_35525_()) {
                        return;
                     }
                  }
               }
            }
         }

      }
   }

   public int m_35532_(Player p_35533_) {
      return this.f_35377_.m_26195_(p_35533_.m_142081_(), (p_35427_) -> {
         return true;
      });
   }

   private void m_35548_(int p_35549_) {
      this.f_35376_ = (byte)(this.f_35376_ - p_35549_);
   }

   public void m_35513_() {
      this.m_35526_();
      this.m_35548_(12);
   }

   public void m_35476_(MerchantOffers p_35477_) {
      this.f_35261_ = p_35477_;
   }

   private boolean m_35527_() {
      int i = this.m_7141_().m_35576_();
      return VillagerData.m_35582_(i) && this.f_35362_ >= VillagerData.m_35577_(i);
   }

   private void m_35528_() {
      this.m_141967_(this.m_7141_().m_35561_(this.m_7141_().m_35576_() + 1));
      this.m_7604_();
   }

   protected Component m_5677_() {
      net.minecraft.resources.ResourceLocation profName = this.m_7141_().m_35571_().getRegistryName();
      return new TranslatableComponent(this.m_6095_().m_20675_() + '.' + (!"minecraft".equals(profName.m_135827_()) ? profName.m_135827_() + '.' : "") + profName.m_135815_());
   }

   public void m_7822_(byte p_35391_) {
      if (p_35391_ == 12) {
         this.m_35287_(ParticleTypes.f_123750_);
      } else if (p_35391_ == 13) {
         this.m_35287_(ParticleTypes.f_123792_);
      } else if (p_35391_ == 14) {
         this.m_35287_(ParticleTypes.f_123748_);
      } else if (p_35391_ == 42) {
         this.m_35287_(ParticleTypes.f_123769_);
      } else {
         super.m_7822_(p_35391_);
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_35439_, DifficultyInstance p_35440_, MobSpawnType p_35441_, @Nullable SpawnGroupData p_35442_, @Nullable CompoundTag p_35443_) {
      if (p_35441_ == MobSpawnType.BREEDING) {
         this.m_141967_(this.m_7141_().m_35565_(VillagerProfession.f_35585_));
      }

      if (p_35441_ == MobSpawnType.COMMAND || p_35441_ == MobSpawnType.SPAWN_EGG || p_35441_ == MobSpawnType.SPAWNER || p_35441_ == MobSpawnType.DISPENSER) {
         this.m_141967_(this.m_7141_().m_35567_(VillagerType.m_35835_(p_35439_.m_45837_(this.m_142538_()))));
      }

      if (p_35441_ == MobSpawnType.STRUCTURE) {
         this.f_35366_ = true;
      }

      return super.m_6518_(p_35439_, p_35440_, p_35441_, p_35442_, p_35443_);
   }

   public Villager m_142606_(ServerLevel p_150012_, AgeableMob p_150013_) {
      double d0 = this.f_19796_.nextDouble();
      VillagerType villagertype;
      if (d0 < 0.5D) {
         villagertype = VillagerType.m_35835_(p_150012_.m_45837_(this.m_142538_()));
      } else if (d0 < 0.75D) {
         villagertype = this.m_7141_().m_35560_();
      } else {
         villagertype = ((Villager)p_150013_).m_7141_().m_35560_();
      }

      Villager villager = new Villager(EntityType.f_20492_, p_150012_, villagertype);
      villager.m_6518_(p_150012_, p_150012_.m_6436_(villager.m_142538_()), MobSpawnType.BREEDING, (SpawnGroupData)null, (CompoundTag)null);
      return villager;
   }

   public void m_8038_(ServerLevel p_35409_, LightningBolt p_35410_) {
      if (p_35409_.m_46791_() != Difficulty.PEACEFUL && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, EntityType.f_20495_, (timer) -> {})) {
         f_19849_.info("Villager {} was struck by lightning {}.", this, p_35410_);
         Witch witch = EntityType.f_20495_.m_20615_(p_35409_);
         witch.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
         witch.m_6518_(p_35409_, p_35409_.m_6436_(witch.m_142538_()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
         witch.m_21557_(this.m_21525_());
         if (this.m_8077_()) {
            witch.m_6593_(this.m_7770_());
            witch.m_20340_(this.m_20151_());
         }

         witch.m_21530_();
         net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, witch);
         p_35409_.m_47205_(witch);
         this.m_35524_();
         this.m_146870_();
      } else {
         super.m_8038_(p_35409_, p_35410_);
      }

   }

   protected void m_7581_(ItemEntity p_35467_) {
      ItemStack itemstack = p_35467_.m_32055_();
      if (this.m_7243_(itemstack)) {
         SimpleContainer simplecontainer = this.m_141944_();
         boolean flag = simplecontainer.m_19183_(itemstack);
         if (!flag) {
            return;
         }

         this.m_21053_(p_35467_);
         this.m_7938_(p_35467_, itemstack.m_41613_());
         ItemStack itemstack1 = simplecontainer.m_19173_(itemstack);
         if (itemstack1.m_41619_()) {
            p_35467_.m_146870_();
         } else {
            itemstack.m_41764_(itemstack1.m_41613_());
         }
      }

   }

   public boolean m_7243_(ItemStack p_35543_) {
      Item item = p_35543_.m_41720_();
      return (f_35372_.contains(item) || this.m_7141_().m_35571_().m_35623_().contains(item)) && this.m_141944_().m_19183_(p_35543_);
   }

   public boolean m_35514_() {
      return this.m_35529_() >= 24;
   }

   public boolean m_35515_() {
      return this.m_35529_() < 12;
   }

   private int m_35529_() {
      SimpleContainer simplecontainer = this.m_141944_();
      return f_35369_.entrySet().stream().mapToInt((p_35417_) -> {
         return simplecontainer.m_18947_(p_35417_.getKey()) * p_35417_.getValue();
      }).sum();
   }

   public boolean m_35516_() {
      return this.m_141944_().m_18949_(ImmutableSet.of(Items.f_42404_, Items.f_42620_, Items.f_42619_, Items.f_42733_));
   }

   protected void m_7604_() {
      VillagerData villagerdata = this.m_7141_();
      Int2ObjectMap<VillagerTrades.ItemListing[]> int2objectmap = VillagerTrades.f_35627_.get(villagerdata.m_35571_());
      if (int2objectmap != null && !int2objectmap.isEmpty()) {
         VillagerTrades.ItemListing[] avillagertrades$itemlisting = int2objectmap.get(villagerdata.m_35576_());
         if (avillagertrades$itemlisting != null) {
            MerchantOffers merchantoffers = this.m_6616_();
            this.m_35277_(merchantoffers, avillagertrades$itemlisting, 2);
         }
      }
   }

   public void m_35411_(ServerLevel p_35412_, Villager p_35413_, long p_35414_) {
      if ((p_35414_ < this.f_35378_ || p_35414_ >= this.f_35378_ + 1200L) && (p_35414_ < p_35413_.f_35378_ || p_35414_ >= p_35413_.f_35378_ + 1200L)) {
         this.f_35377_.m_26163_(p_35413_.f_35377_, this.f_19796_, 10);
         this.f_35378_ = p_35414_;
         p_35413_.f_35378_ = p_35414_;
         this.m_35397_(p_35412_, p_35414_, 5);
      }
   }

   private void m_35530_() {
      long i = this.f_19853_.m_46467_();
      if (this.f_35361_ == 0L) {
         this.f_35361_ = i;
      } else if (i >= this.f_35361_ + 24000L) {
         this.f_35377_.m_26198_();
         this.f_35361_ = i;
      }
   }

   public void m_35397_(ServerLevel p_35398_, long p_35399_, int p_35400_) {
      if (this.m_35392_(p_35399_)) {
         AABB aabb = this.m_142469_().m_82377_(10.0D, 10.0D, 10.0D);
         List<Villager> list = p_35398_.m_45976_(Villager.class, aabb);
         List<Villager> list1 = list.stream().filter((p_35396_) -> {
            return p_35396_.m_35392_(p_35399_);
         }).limit(5L).collect(Collectors.toList());
         if (list1.size() >= p_35400_) {
            IronGolem irongolem = this.m_35490_(p_35398_);
            if (irongolem != null) {
               list.forEach(GolemSensor::m_26649_);
            }
         }
      }
   }

   public boolean m_35392_(long p_35393_) {
      if (!this.m_35461_(this.f_19853_.m_46467_())) {
         return false;
      } else {
         return !this.f_20939_.m_21874_(MemoryModuleType.f_26327_);
      }
   }

   @Nullable
   private IronGolem m_35490_(ServerLevel p_35491_) {
      BlockPos blockpos = this.m_142538_();

      for(int i = 0; i < 10; ++i) {
         double d0 = (double)(p_35491_.f_46441_.nextInt(16) - 8);
         double d1 = (double)(p_35491_.f_46441_.nextInt(16) - 8);
         BlockPos blockpos1 = this.m_35446_(blockpos, d0, d1);
         if (blockpos1 != null) {
            IronGolem irongolem = EntityType.f_20460_.m_20655_(p_35491_, (CompoundTag)null, (Component)null, (Player)null, blockpos1, MobSpawnType.MOB_SUMMONED, false, false);
            if (irongolem != null) {
               if (irongolem.m_5545_(p_35491_, MobSpawnType.MOB_SUMMONED) && irongolem.m_6914_(p_35491_)) {
                  p_35491_.m_47205_(irongolem);
                  return irongolem;
               }

               irongolem.m_146870_();
            }
         }
      }

      return null;
   }

   @Nullable
   private BlockPos m_35446_(BlockPos p_35447_, double p_35448_, double p_35449_) {
      int i = 6;
      BlockPos blockpos = p_35447_.m_142022_(p_35448_, 6.0D, p_35449_);
      BlockState blockstate = this.f_19853_.m_8055_(blockpos);

      for(int j = 6; j >= -6; --j) {
         BlockPos blockpos1 = blockpos;
         BlockState blockstate1 = blockstate;
         blockpos = blockpos.m_7495_();
         blockstate = this.f_19853_.m_8055_(blockpos);
         if ((blockstate1.m_60795_() || blockstate1.m_60767_().m_76332_()) && blockstate.m_60767_().m_76337_()) {
            return blockpos1;
         }
      }

      return null;
   }

   public void m_6814_(ReputationEventType p_35431_, Entity p_35432_) {
      if (p_35431_ == ReputationEventType.f_26985_) {
         this.f_35377_.m_26191_(p_35432_.m_142081_(), GossipType.MAJOR_POSITIVE, 20);
         this.f_35377_.m_26191_(p_35432_.m_142081_(), GossipType.MINOR_POSITIVE, 25);
      } else if (p_35431_ == ReputationEventType.f_26989_) {
         this.f_35377_.m_26191_(p_35432_.m_142081_(), GossipType.TRADING, 2);
      } else if (p_35431_ == ReputationEventType.f_26987_) {
         this.f_35377_.m_26191_(p_35432_.m_142081_(), GossipType.MINOR_NEGATIVE, 25);
      } else if (p_35431_ == ReputationEventType.f_26988_) {
         this.f_35377_.m_26191_(p_35432_.m_142081_(), GossipType.MAJOR_NEGATIVE, 25);
      }

   }

   public int m_7809_() {
      return this.f_35362_;
   }

   public void m_35546_(int p_35547_) {
      this.f_35362_ = p_35547_;
   }

   private void m_35531_() {
      this.m_35522_();
      this.f_35364_ = 0;
   }

   public GossipContainer m_35517_() {
      return this.f_35377_;
   }

   public void m_35455_(Tag p_35456_) {
      this.f_35377_.m_26177_(new Dynamic<>(NbtOps.f_128958_, p_35456_));
   }

   protected void m_8025_() {
      super.m_8025_();
      DebugPackets.m_133695_(this);
   }

   public void m_5802_(BlockPos p_35479_) {
      super.m_5802_(p_35479_);
      this.f_20939_.m_21879_(MemoryModuleType.f_26328_, this.f_19853_.m_46467_());
      this.f_20939_.m_21936_(MemoryModuleType.f_26370_);
      this.f_20939_.m_21936_(MemoryModuleType.f_26326_);
   }

   public void m_5796_() {
      super.m_5796_();
      this.f_20939_.m_21879_(MemoryModuleType.f_26329_, this.f_19853_.m_46467_());
   }

   private boolean m_35461_(long p_35462_) {
      Optional<Long> optional = this.f_20939_.m_21952_(MemoryModuleType.f_26328_);
      if (optional.isPresent()) {
         return p_35462_ - optional.get() < 24000L;
      } else {
         return false;
      }
   }
}
