package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BackUpIfTooClose;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CopyMemoryWithExpiry;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.entity.ai.behavior.DismountOrSkipMounting;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.GoToCelebrateLocation;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.Mount;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StartCelebratingIfTargetDead;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class PiglinAi {
   public static final int f_149940_ = 8;
   public static final int f_149941_ = 4;
   public static final Item f_34794_ = Items.f_42417_;
   private static final int f_149942_ = 16;
   private static final int f_149943_ = 600;
   private static final int f_149944_ = 120;
   private static final int f_149945_ = 9;
   private static final int f_149946_ = 200;
   private static final int f_149947_ = 200;
   private static final int f_149948_ = 300;
   private static final UniformInt f_34795_ = TimeUtil.m_145020_(30, 120);
   private static final int f_149949_ = 100;
   private static final int f_149950_ = 400;
   private static final int f_149951_ = 8;
   private static final UniformInt f_34796_ = TimeUtil.m_145020_(10, 40);
   private static final UniformInt f_34797_ = TimeUtil.m_145020_(10, 30);
   private static final UniformInt f_34798_ = TimeUtil.m_145020_(5, 20);
   private static final int f_149952_ = 20;
   private static final int f_149953_ = 200;
   private static final int f_149954_ = 12;
   private static final int f_149955_ = 8;
   private static final int f_149956_ = 14;
   private static final int f_149957_ = 8;
   private static final int f_149958_ = 5;
   private static final float f_149959_ = 0.75F;
   private static final int f_149960_ = 6;
   private static final UniformInt f_34799_ = TimeUtil.m_145020_(5, 7);
   private static final UniformInt f_34800_ = TimeUtil.m_145020_(5, 7);
   private static final float f_149932_ = 0.1F;
   private static final float f_149933_ = 1.0F;
   private static final float f_149934_ = 1.0F;
   private static final float f_149935_ = 0.8F;
   private static final float f_149936_ = 1.0F;
   private static final float f_149937_ = 1.0F;
   private static final float f_149938_ = 0.6F;
   private static final float f_149939_ = 0.6F;

   protected static Brain<?> m_34840_(Piglin p_34841_, Brain<Piglin> p_34842_) {
      m_34820_(p_34842_);
      m_34891_(p_34842_);
      m_34940_(p_34842_);
      m_34903_(p_34841_, p_34842_);
      m_34920_(p_34842_);
      m_34958_(p_34842_);
      m_34973_(p_34842_);
      p_34842_.m_21930_(ImmutableSet.of(Activity.f_37978_));
      p_34842_.m_21944_(Activity.f_37979_);
      p_34842_.m_21962_();
      return p_34842_;
   }

   protected static void m_34832_(Piglin p_34833_) {
      int i = f_34795_.m_142270_(p_34833_.f_19853_.f_46441_);
      p_34833_.m_6274_().m_21882_(MemoryModuleType.f_26340_, true, (long)i);
   }

   private static void m_34820_(Brain<Piglin> p_34821_) {
      p_34821_.m_21891_(Activity.f_37978_, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), new InteractWithDoor(), m_34937_(), m_34955_(), new StopHoldingItemIfNoLongerAdmiring<>(), new StartAdmiringItemIfSeen<>(120), new StartCelebratingIfTargetDead(300, PiglinAi::m_34810_), new StopBeingAngryIfTargetDead<>()));
   }

   private static void m_34891_(Brain<Piglin> p_34892_) {
      p_34892_.m_21891_(Activity.f_37979_, 10, ImmutableList.of(new SetEntityLookTarget(PiglinAi::m_34883_, 14.0F), new StartAttacking<>(AbstractPiglin::m_34667_, PiglinAi::m_35000_), new RunIf<>(Piglin::m_7121_, new StartHuntingHoglin<>()), m_34917_(), m_34970_(), m_34805_(), m_34882_(), new SetLookAndInteract(EntityType.f_20532_, 4)));
   }

   private static void m_34903_(Piglin p_34904_, Brain<Piglin> p_34905_) {
      p_34905_.m_21895_(Activity.f_37988_, 10, ImmutableList.<net.minecraft.world.entity.ai.behavior.Behavior<? super Piglin>>of(new StopAttackingIfTargetInvalid<Piglin>((p_34981_) -> {
         return !m_34900_(p_34904_, p_34981_);
      }), new RunIf<>(PiglinAi::m_34918_, new BackUpIfTooClose<>(5, 0.75F)), new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0F), new MeleeAttack(20), new CrossbowAttack(), new RememberIfHoglinWasKilled(), new EraseMemoryIf<>(PiglinAi::m_34998_, MemoryModuleType.f_26372_)), MemoryModuleType.f_26372_);
   }

   private static void m_34920_(Brain<Piglin> p_34921_) {
      p_34921_.m_21895_(Activity.f_37989_, 10, ImmutableList.of(m_34917_(), new SetEntityLookTarget(PiglinAi::m_34883_, 14.0F), new StartAttacking<Piglin>(AbstractPiglin::m_34667_, PiglinAi::m_35000_), new RunIf<Piglin>((p_34804_) -> {
         return !p_34804_.m_34771_();
      }, new GoToCelebrateLocation<>(2, 1.0F)), new RunIf<Piglin>(Piglin::m_34771_, new GoToCelebrateLocation<>(4, 0.6F)), new RunOne<>(ImmutableList.of(Pair.of(new SetEntityLookTarget(EntityType.f_20511_, 8.0F), 1), Pair.of(new RandomStroll(0.6F, 2, 1), 1), Pair.of(new DoNothing(10, 20), 1)))), MemoryModuleType.f_26341_);
   }

   private static void m_34940_(Brain<Piglin> p_34941_) {
      p_34941_.m_21895_(Activity.f_37990_, 10, ImmutableList.of(new GoToWantedItem<>(PiglinAi::m_35028_, 1.0F, true, 9), new StopAdmiringIfItemTooFarAway<>(9), new StopAdmiringIfTiredOfTryingToReachItem<>(200, 200)), MemoryModuleType.f_26336_);
   }

   private static void m_34958_(Brain<Piglin> p_34959_) {
      p_34959_.m_21895_(Activity.f_37991_, 10, ImmutableList.of(SetWalkTargetAwayFrom.m_24019_(MemoryModuleType.f_26383_, 1.0F, 12, true), m_34805_(), m_34882_(), new EraseMemoryIf<Piglin>(PiglinAi::m_35008_, MemoryModuleType.f_26383_)), MemoryModuleType.f_26383_);
   }

   private static void m_34973_(Brain<Piglin> p_34974_) {
      p_34974_.m_21895_(Activity.f_37992_, 10, ImmutableList.of(new Mount<>(0.8F), new SetEntityLookTarget(PiglinAi::m_34883_, 8.0F), new RunIf<>(Entity::m_20159_, m_34805_()), new DismountOrSkipMounting<>(8, PiglinAi::m_34834_)), MemoryModuleType.f_26376_);
   }

   private static RunOne<Piglin> m_34805_() {
      return new RunOne<>(ImmutableList.of(Pair.of(new SetEntityLookTarget(EntityType.f_20532_, 8.0F), 1), Pair.of(new SetEntityLookTarget(EntityType.f_20511_, 8.0F), 1), Pair.of(new SetEntityLookTarget(8.0F), 1), Pair.of(new DoNothing(30, 60), 1)));
   }

   private static RunOne<Piglin> m_34882_() {
      return new RunOne<>(ImmutableList.of(Pair.of(new RandomStroll(0.6F), 2), Pair.of(InteractWith.m_23260_(EntityType.f_20511_, 8, MemoryModuleType.f_26374_, 0.6F, 2), 2), Pair.of(new RunIf<>(PiglinAi::m_34982_, new SetWalkTargetFromLookTarget(0.6F, 3)), 2), Pair.of(new DoNothing(30, 60), 1)));
   }

   private static SetWalkTargetAwayFrom<BlockPos> m_34917_() {
      return SetWalkTargetAwayFrom.m_24012_(MemoryModuleType.f_26356_, 1.0F, 8, false);
   }

   private static CopyMemoryWithExpiry<Piglin, LivingEntity> m_34937_() {
      return new CopyMemoryWithExpiry<>(Piglin::m_6162_, MemoryModuleType.f_26333_, MemoryModuleType.f_26383_, f_34800_);
   }

   private static CopyMemoryWithExpiry<Piglin, LivingEntity> m_34955_() {
      return new CopyMemoryWithExpiry<>(PiglinAi::m_34998_, MemoryModuleType.f_26351_, MemoryModuleType.f_26383_, f_34799_);
   }

   protected static void m_34898_(Piglin p_34899_) {
      Brain<Piglin> brain = p_34899_.m_6274_();
      Activity activity = brain.m_21968_().orElse((Activity)null);
      brain.m_21926_(ImmutableList.of(Activity.f_37990_, Activity.f_37988_, Activity.f_37991_, Activity.f_37989_, Activity.f_37992_, Activity.f_37979_));
      Activity activity1 = brain.m_21968_().orElse((Activity)null);
      if (activity != activity1) {
         m_34947_(p_34899_).ifPresent(p_34899_::m_34689_);
      }

      p_34899_.m_21561_(brain.m_21874_(MemoryModuleType.f_26372_));
      if (!brain.m_21874_(MemoryModuleType.f_26376_) && m_34992_(p_34899_)) {
         p_34899_.m_8127_();
      }

      if (!brain.m_21874_(MemoryModuleType.f_26341_)) {
         brain.m_21936_(MemoryModuleType.f_26342_);
      }

      p_34899_.m_34789_(brain.m_21874_(MemoryModuleType.f_26342_));
   }

   private static boolean m_34992_(Piglin p_34993_) {
      if (!p_34993_.m_6162_()) {
         return false;
      } else {
         Entity entity = p_34993_.m_20202_();
         return entity instanceof Piglin && ((Piglin)entity).m_6162_() || entity instanceof Hoglin && ((Hoglin)entity).m_6162_();
      }
   }

   protected static void m_34843_(Piglin p_34844_, ItemEntity p_34845_) {
      m_35006_(p_34844_);
      ItemStack itemstack;
      if (p_34845_.m_32055_().m_150930_(Items.f_42587_)) {
         p_34844_.m_7938_(p_34845_, p_34845_.m_32055_().m_41613_());
         itemstack = p_34845_.m_32055_();
         p_34845_.m_146870_();
      } else {
         p_34844_.m_7938_(p_34845_, 1);
         itemstack = m_34822_(p_34845_);
      }

      if (m_149965_(itemstack)) {
         p_34844_.m_6274_().m_21936_(MemoryModuleType.f_26337_);
         m_34932_(p_34844_, itemstack);
         m_34938_(p_34844_);
      } else if (m_149969_(itemstack) && !m_35018_(p_34844_)) {
         m_35014_(p_34844_);
      } else {
         boolean flag = p_34844_.m_21540_(itemstack);
         if (!flag) {
            m_34952_(p_34844_, itemstack);
         }
      }
   }

   private static void m_34932_(Piglin p_34933_, ItemStack p_34934_) {
      if (m_35026_(p_34933_)) {
         p_34933_.m_19983_(p_34933_.m_21120_(InteractionHand.OFF_HAND));
      }

      p_34933_.m_34785_(p_34934_);
   }

   private static ItemStack m_34822_(ItemEntity p_34823_) {
      ItemStack itemstack = p_34823_.m_32055_();
      ItemStack itemstack1 = itemstack.m_41620_(1);
      if (itemstack.m_41619_()) {
         p_34823_.m_146870_();
      } else {
         p_34823_.m_32045_(itemstack);
      }

      return itemstack1;
   }

   protected static void m_34867_(Piglin p_34868_, boolean p_34869_) {
      ItemStack itemstack = p_34868_.m_21120_(InteractionHand.OFF_HAND);
      p_34868_.m_21008_(InteractionHand.OFF_HAND, ItemStack.f_41583_);
      if (p_34868_.m_34667_()) {
         boolean flag = itemstack.isPiglinCurrency();
         if (p_34869_ && flag) {
            m_34860_(p_34868_, m_34996_(p_34868_));
         } else if (!flag) {
            boolean flag1 = p_34868_.m_21540_(itemstack);
            if (!flag1) {
               m_34952_(p_34868_, itemstack);
            }
         }
      } else {
         boolean flag2 = p_34868_.m_21540_(itemstack);
         if (!flag2) {
            ItemStack itemstack1 = p_34868_.m_21205_();
            if (m_149965_(itemstack1)) {
               m_34952_(p_34868_, itemstack1);
            } else {
               m_34860_(p_34868_, Collections.singletonList(itemstack1));
            }

            p_34868_.m_34783_(itemstack);
         }
      }

   }

   protected static void m_34927_(Piglin p_34928_) {
      if (m_35020_(p_34928_) && !p_34928_.m_21206_().m_41619_()) {
         p_34928_.m_19983_(p_34928_.m_21206_());
         p_34928_.m_21008_(InteractionHand.OFF_HAND, ItemStack.f_41583_);
      }

   }

   private static void m_34952_(Piglin p_34953_, ItemStack p_34954_) {
      ItemStack itemstack = p_34953_.m_34778_(p_34954_);
      m_34912_(p_34953_, Collections.singletonList(itemstack));
   }

   private static void m_34860_(Piglin p_34861_, List<ItemStack> p_34862_) {
      Optional<Player> optional = p_34861_.m_6274_().m_21952_(MemoryModuleType.f_26368_);
      if (optional.isPresent()) {
         m_34850_(p_34861_, optional.get(), p_34862_);
      } else {
         m_34912_(p_34861_, p_34862_);
      }

   }

   private static void m_34912_(Piglin p_34913_, List<ItemStack> p_34914_) {
      m_34863_(p_34913_, p_34914_, m_35016_(p_34913_));
   }

   private static void m_34850_(Piglin p_34851_, Player p_34852_, List<ItemStack> p_34853_) {
      m_34863_(p_34851_, p_34853_, p_34852_.m_20182_());
   }

   private static void m_34863_(Piglin p_34864_, List<ItemStack> p_34865_, Vec3 p_34866_) {
      if (!p_34865_.isEmpty()) {
         p_34864_.m_6674_(InteractionHand.OFF_HAND);

         for(ItemStack itemstack : p_34865_) {
            BehaviorUtils.m_22613_(p_34864_, itemstack, p_34866_.m_82520_(0.0D, 1.0D, 0.0D));
         }
      }

   }

   private static List<ItemStack> m_34996_(Piglin p_34997_) {
      LootTable loottable = p_34997_.f_19853_.m_142572_().m_129898_().m_79217_(BuiltInLootTables.f_78738_);
      return loottable.m_79129_((new LootContext.Builder((ServerLevel)p_34997_.f_19853_)).m_78972_(LootContextParams.f_81455_, p_34997_).m_78977_(p_34997_.f_19853_.f_46441_).m_78975_(LootContextParamSets.f_81417_));
   }

   private static boolean m_34810_(LivingEntity p_34811_, LivingEntity p_34812_) {
      if (p_34812_.m_6095_() != EntityType.f_20456_) {
         return false;
      } else {
         return (new Random(p_34811_.f_19853_.m_46467_())).nextFloat() < 0.1F;
      }
   }

   protected static boolean m_34857_(Piglin p_34858_, ItemStack p_34859_) {
      if (p_34858_.m_6162_() && p_34859_.m_150922_(ItemTags.f_144309_)) {
         return false;
      } else if (p_34859_.m_150922_(ItemTags.f_13150_)) {
         return false;
      } else if (m_35024_(p_34858_) && p_34858_.m_6274_().m_21874_(MemoryModuleType.f_26372_)) {
         return false;
      } else if (p_34859_.isPiglinCurrency()) {
         return m_35028_(p_34858_);
      } else {
         boolean flag = p_34858_.m_34780_(p_34859_);
         if (p_34859_.m_150930_(Items.f_42587_)) {
            return flag;
         } else if (m_149969_(p_34859_)) {
            return !m_35018_(p_34858_) && flag;
         } else if (!m_149965_(p_34859_)) {
            return p_34858_.m_34787_(p_34859_);
         } else {
            return m_35028_(p_34858_) && flag;
         }
      }
   }

   protected static boolean m_149965_(ItemStack p_149966_) {
      return p_149966_.m_150922_(ItemTags.f_13151_);
   }

   private static boolean m_34834_(Piglin p_34835_, Entity p_34836_) {
      if (!(p_34836_ instanceof Mob)) {
         return false;
      } else {
         Mob mob = (Mob)p_34836_;
         return !mob.m_6162_() || !mob.m_6084_() || m_34988_(p_34835_) || m_34988_(mob) || mob instanceof Piglin && mob.m_20202_() == null;
      }
   }

   private static boolean m_34900_(Piglin p_34901_, LivingEntity p_34902_) {
      return m_35000_(p_34901_).filter((p_34887_) -> {
         return p_34887_ == p_34902_;
      }).isPresent();
   }

   private static boolean m_34998_(Piglin p_34999_) {
      Brain<Piglin> brain = p_34999_.m_6274_();
      if (brain.m_21874_(MemoryModuleType.f_26351_)) {
         LivingEntity livingentity = brain.m_21952_(MemoryModuleType.f_26351_).get();
         return p_34999_.m_19950_(livingentity, 6.0D);
      } else {
         return false;
      }
   }

   private static Optional<? extends LivingEntity> m_35000_(Piglin p_35001_) {
      Brain<Piglin> brain = p_35001_.m_6274_();
      if (m_34998_(p_35001_)) {
         return Optional.empty();
      } else {
         Optional<LivingEntity> optional = BehaviorUtils.m_22610_(p_35001_, MemoryModuleType.f_26334_);
         if (optional.isPresent() && Sensor.m_182377_(p_35001_, optional.get())) {
            return optional;
         } else {
            if (brain.m_21874_(MemoryModuleType.f_26335_)) {
               Optional<Player> optional1 = brain.m_21952_(MemoryModuleType.f_148206_);
               if (optional1.isPresent()) {
                  return optional1;
               }
            }

            Optional<Mob> optional3 = brain.m_21952_(MemoryModuleType.f_26333_);
            if (optional3.isPresent()) {
               return optional3;
            } else {
               Optional<Player> optional2 = brain.m_21952_(MemoryModuleType.f_26345_);
               return optional2.isPresent() && Sensor.m_148312_(p_35001_, optional2.get()) ? optional2 : Optional.empty();
            }
         }
      }
   }

   public static void m_34873_(Player p_34874_, boolean p_34875_) {
      List<Piglin> list = p_34874_.f_19853_.m_45976_(Piglin.class, p_34874_.m_142469_().m_82400_(16.0D));
      list.stream().filter(PiglinAi::m_34942_).filter((p_34881_) -> {
         return !p_34875_ || BehaviorUtils.m_22667_(p_34881_, p_34874_);
      }).forEach((p_34872_) -> {
         if (p_34872_.f_19853_.m_46469_().m_46207_(GameRules.f_46127_)) {
            m_34944_(p_34872_, p_34874_);
         } else {
            m_34924_(p_34872_, p_34874_);
         }

      });
   }

   public static InteractionResult m_34846_(Piglin p_34847_, Player p_34848_, InteractionHand p_34849_) {
      ItemStack itemstack = p_34848_.m_21120_(p_34849_);
      if (m_34909_(p_34847_, itemstack)) {
         ItemStack itemstack1 = itemstack.m_41620_(1);
         m_34932_(p_34847_, itemstack1);
         m_34938_(p_34847_);
         m_35006_(p_34847_);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   protected static boolean m_34909_(Piglin p_34910_, ItemStack p_34911_) {
      return !m_35024_(p_34910_) && !m_35020_(p_34910_) && p_34910_.m_34667_() && p_34911_.isPiglinCurrency();
   }

   protected static void m_34837_(Piglin p_34838_, LivingEntity p_34839_) {
      if (!(p_34839_ instanceof Piglin)) {
         if (m_35026_(p_34838_)) {
            m_34867_(p_34838_, false);
         }

         Brain<Piglin> brain = p_34838_.m_6274_();
         brain.m_21936_(MemoryModuleType.f_26341_);
         brain.m_21936_(MemoryModuleType.f_26342_);
         brain.m_21936_(MemoryModuleType.f_26336_);
         if (p_34839_ instanceof Player) {
            brain.m_21882_(MemoryModuleType.f_26339_, true, 400L);
         }

         m_34986_(p_34838_).ifPresent((p_34816_) -> {
            if (p_34816_.m_6095_() != p_34839_.m_6095_()) {
               brain.m_21936_(MemoryModuleType.f_26383_);
            }

         });
         if (p_34838_.m_6162_()) {
            brain.m_21882_(MemoryModuleType.f_26383_, p_34839_, 100L);
            if (Sensor.m_182377_(p_34838_, p_34839_)) {
               m_34895_(p_34838_, p_34839_);
            }

         } else if (p_34839_.m_6095_() == EntityType.f_20456_ && m_35012_(p_34838_)) {
            m_34967_(p_34838_, p_34839_);
            m_34929_(p_34838_, p_34839_);
         } else {
            m_34826_(p_34838_, p_34839_);
         }
      }
   }

   protected static void m_34826_(AbstractPiglin p_34827_, LivingEntity p_34828_) {
      if (!p_34827_.m_6274_().m_21954_(Activity.f_37991_)) {
         if (Sensor.m_182377_(p_34827_, p_34828_)) {
            if (!BehaviorUtils.m_22598_(p_34827_, p_34828_, 4.0D)) {
               if (p_34828_.m_6095_() == EntityType.f_20532_ && p_34827_.f_19853_.m_46469_().m_46207_(GameRules.f_46127_)) {
                  m_34944_(p_34827_, p_34828_);
                  m_34824_(p_34827_);
               } else {
                  m_34924_(p_34827_, p_34828_);
                  m_34895_(p_34827_, p_34828_);
               }

            }
         }
      }
   }

   public static Optional<SoundEvent> m_34947_(Piglin p_34948_) {
      return p_34948_.m_6274_().m_21968_().map((p_34908_) -> {
         return m_34854_(p_34948_, p_34908_);
      });
   }

   private static SoundEvent m_34854_(Piglin p_34855_, Activity p_34856_) {
      if (p_34856_ == Activity.f_37988_) {
         return SoundEvents.f_12240_;
      } else if (p_34855_.m_34666_()) {
         return SoundEvents.f_12245_;
      } else if (p_34856_ == Activity.f_37991_ && m_35002_(p_34855_)) {
         return SoundEvents.f_12245_;
      } else if (p_34856_ == Activity.f_37990_) {
         return SoundEvents.f_12238_;
      } else if (p_34856_ == Activity.f_37989_) {
         return SoundEvents.f_12241_;
      } else if (m_34971_(p_34855_)) {
         return SoundEvents.f_12243_;
      } else {
         return m_35022_(p_34855_) ? SoundEvents.f_12245_ : SoundEvents.f_12239_;
      }
   }

   private static boolean m_35002_(Piglin p_35003_) {
      Brain<Piglin> brain = p_35003_.m_6274_();
      return !brain.m_21874_(MemoryModuleType.f_26383_) ? false : brain.m_21952_(MemoryModuleType.f_26383_).get().m_19950_(p_35003_, 12.0D);
   }

   protected static boolean m_34965_(Piglin p_34966_) {
      return p_34966_.m_6274_().m_21874_(MemoryModuleType.f_26340_) || m_35004_(p_34966_).stream().anyMatch((p_34995_) -> {
         return p_34995_.m_6274_().m_21874_(MemoryModuleType.f_26340_);
      });
   }

   private static List<AbstractPiglin> m_35004_(Piglin p_35005_) {
      return p_35005_.m_6274_().m_21952_(MemoryModuleType.f_26347_).orElse(ImmutableList.of());
   }

   private static List<AbstractPiglin> m_34960_(AbstractPiglin p_34961_) {
      return p_34961_.m_6274_().m_21952_(MemoryModuleType.f_26346_).orElse(ImmutableList.of());
   }

   public static boolean m_34808_(LivingEntity p_34809_) {
      for(ItemStack itemstack : p_34809_.m_6168_()) {
         Item item = itemstack.m_41720_();
         if (itemstack.makesPiglinsNeutral(p_34809_)) {
            return true;
         }
      }

      return false;
   }

   private static void m_35006_(Piglin p_35007_) {
      p_35007_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
      p_35007_.m_21573_().m_26573_();
   }

   private static RunSometimes<Piglin> m_34970_() {
      return new RunSometimes<>(new CopyMemoryWithExpiry<>(Piglin::m_6162_, MemoryModuleType.f_26344_, MemoryModuleType.f_26376_, f_34797_), f_34796_);
   }

   protected static void m_34895_(AbstractPiglin p_34896_, LivingEntity p_34897_) {
      m_34960_(p_34896_).forEach((p_34890_) -> {
         if (p_34897_.m_6095_() != EntityType.f_20456_ || p_34890_.m_7121_() && ((Hoglin)p_34897_).m_34555_()) {
            m_34962_(p_34890_, p_34897_);
         }
      });
   }

   protected static void m_34824_(AbstractPiglin p_34825_) {
      m_34960_(p_34825_).forEach((p_34991_) -> {
         m_34893_(p_34991_).ifPresent((p_149964_) -> {
            m_34924_(p_34991_, p_149964_);
         });
      });
   }

   protected static void m_34977_(Piglin p_34978_) {
      m_35004_(p_34978_).forEach(PiglinAi::m_34922_);
   }

   protected static void m_34924_(AbstractPiglin p_34925_, LivingEntity p_34926_) {
      if (Sensor.m_182377_(p_34925_, p_34926_)) {
         p_34925_.m_6274_().m_21936_(MemoryModuleType.f_26326_);
         p_34925_.m_6274_().m_21882_(MemoryModuleType.f_26334_, p_34926_.m_142081_(), 600L);
         if (p_34926_.m_6095_() == EntityType.f_20456_ && p_34925_.m_7121_()) {
            m_34922_(p_34925_);
         }

         if (p_34926_.m_6095_() == EntityType.f_20532_ && p_34925_.f_19853_.m_46469_().m_46207_(GameRules.f_46127_)) {
            p_34925_.m_6274_().m_21882_(MemoryModuleType.f_26335_, true, 600L);
         }

      }
   }

   private static void m_34944_(AbstractPiglin p_34945_, LivingEntity p_34946_) {
      Optional<Player> optional = m_34893_(p_34945_);
      if (optional.isPresent()) {
         m_34924_(p_34945_, optional.get());
      } else {
         m_34924_(p_34945_, p_34946_);
      }

   }

   private static void m_34962_(AbstractPiglin p_34963_, LivingEntity p_34964_) {
      Optional<LivingEntity> optional = m_34975_(p_34963_);
      LivingEntity livingentity = BehaviorUtils.m_22625_(p_34963_, optional, p_34964_);
      if (!optional.isPresent() || optional.get() != livingentity) {
         m_34924_(p_34963_, livingentity);
      }
   }

   private static Optional<LivingEntity> m_34975_(AbstractPiglin p_34976_) {
      return BehaviorUtils.m_22610_(p_34976_, MemoryModuleType.f_26334_);
   }

   public static Optional<LivingEntity> m_34986_(Piglin p_34987_) {
      return p_34987_.m_6274_().m_21874_(MemoryModuleType.f_26383_) ? p_34987_.m_6274_().m_21952_(MemoryModuleType.f_26383_) : Optional.empty();
   }

   public static Optional<Player> m_34893_(AbstractPiglin p_34894_) {
      return p_34894_.m_6274_().m_21874_(MemoryModuleType.f_148206_) ? p_34894_.m_6274_().m_21952_(MemoryModuleType.f_148206_) : Optional.empty();
   }

   private static void m_34929_(Piglin p_34930_, LivingEntity p_34931_) {
      m_35004_(p_34930_).stream().filter((p_34985_) -> {
         return p_34985_ instanceof Piglin;
      }).forEach((p_34819_) -> {
         m_34949_((Piglin)p_34819_, p_34931_);
      });
   }

   private static void m_34949_(Piglin p_34950_, LivingEntity p_34951_) {
      Brain<Piglin> brain = p_34950_.m_6274_();
      LivingEntity livingentity = BehaviorUtils.m_22625_(p_34950_, brain.m_21952_(MemoryModuleType.f_26383_), p_34951_);
      livingentity = BehaviorUtils.m_22625_(p_34950_, brain.m_21952_(MemoryModuleType.f_26372_), livingentity);
      m_34967_(p_34950_, livingentity);
   }

   private static boolean m_35008_(Piglin p_35009_) {
      Brain<Piglin> brain = p_35009_.m_6274_();
      if (!brain.m_21874_(MemoryModuleType.f_26383_)) {
         return true;
      } else {
         LivingEntity livingentity = brain.m_21952_(MemoryModuleType.f_26383_).get();
         EntityType<?> entitytype = livingentity.m_6095_();
         if (entitytype == EntityType.f_20456_) {
            return m_35010_(p_35009_);
         } else if (m_34806_(entitytype)) {
            return !brain.m_21938_(MemoryModuleType.f_26351_, livingentity);
         } else {
            return false;
         }
      }
   }

   private static boolean m_35010_(Piglin p_35011_) {
      return !m_35012_(p_35011_);
   }

   private static boolean m_35012_(Piglin p_35013_) {
      int i = p_35013_.m_6274_().m_21952_(MemoryModuleType.f_26352_).orElse(0) + 1;
      int j = p_35013_.m_6274_().m_21952_(MemoryModuleType.f_26353_).orElse(0);
      return j > i;
   }

   private static void m_34967_(Piglin p_34968_, LivingEntity p_34969_) {
      p_34968_.m_6274_().m_21936_(MemoryModuleType.f_26334_);
      p_34968_.m_6274_().m_21936_(MemoryModuleType.f_26372_);
      p_34968_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
      p_34968_.m_6274_().m_21882_(MemoryModuleType.f_26383_, p_34969_, (long)f_34798_.m_142270_(p_34968_.f_19853_.f_46441_));
      m_34922_(p_34968_);
   }

   protected static void m_34922_(AbstractPiglin p_34923_) {
      p_34923_.m_6274_().m_21882_(MemoryModuleType.f_26340_, true, (long)f_34795_.m_142270_(p_34923_.f_19853_.f_46441_));
   }

   private static boolean m_149971_(Piglin p_149972_) {
      return p_149972_.m_6274_().m_21874_(MemoryModuleType.f_26354_);
   }

   private static void m_35014_(Piglin p_35015_) {
      p_35015_.m_6274_().m_21882_(MemoryModuleType.f_26355_, true, 200L);
   }

   private static Vec3 m_35016_(Piglin p_35017_) {
      Vec3 vec3 = LandRandomPos.m_148488_(p_35017_, 4, 2);
      return vec3 == null ? p_35017_.m_20182_() : vec3;
   }

   private static boolean m_35018_(Piglin p_35019_) {
      return p_35019_.m_6274_().m_21874_(MemoryModuleType.f_26355_);
   }

   protected static boolean m_34942_(AbstractPiglin p_34943_) {
      return p_34943_.m_6274_().m_21954_(Activity.f_37979_);
   }

   private static boolean m_34918_(LivingEntity p_34919_) {
      return p_34919_.m_21093_(is -> is.m_41720_() instanceof net.minecraft.world.item.CrossbowItem);
   }

   private static void m_34938_(LivingEntity p_34939_) {
      p_34939_.m_6274_().m_21882_(MemoryModuleType.f_26336_, true, 120L);
   }

   private static boolean m_35020_(Piglin p_35021_) {
      return p_35021_.m_6274_().m_21874_(MemoryModuleType.f_26336_);
   }

   private static boolean m_149967_(ItemStack p_149968_) {
      return p_149968_.m_150930_(f_34794_);
   }

   private static boolean m_149969_(ItemStack p_149970_) {
      return p_149970_.m_150922_(ItemTags.f_144310_);
   }

   private static boolean m_35022_(Piglin p_35023_) {
      return p_35023_.m_6274_().m_21874_(MemoryModuleType.f_26356_);
   }

   private static boolean m_34971_(LivingEntity p_34972_) {
      return p_34972_.m_6274_().m_21874_(MemoryModuleType.f_26354_);
   }

   private static boolean m_34982_(LivingEntity p_34983_) {
      return !m_34971_(p_34983_);
   }

   public static boolean m_34883_(LivingEntity p_34884_) {
      return p_34884_.m_6095_() == EntityType.f_20532_ && p_34884_.m_21093_(PiglinAi::m_149965_);
   }

   private static boolean m_35024_(Piglin p_35025_) {
      return p_35025_.m_6274_().m_21874_(MemoryModuleType.f_26339_);
   }

   private static boolean m_34988_(LivingEntity p_34989_) {
      return p_34989_.m_6274_().m_21874_(MemoryModuleType.f_26381_);
   }

   private static boolean m_35026_(Piglin p_35027_) {
      return !p_35027_.m_21206_().m_41619_();
   }

   private static boolean m_35028_(Piglin p_35029_) {
      return p_35029_.m_21206_().m_41619_() || !m_149965_(p_35029_.m_21206_());
   }

   public static boolean m_34806_(EntityType<?> p_34807_) {
      return p_34807_ == EntityType.f_20531_ || p_34807_ == EntityType.f_20500_;
   }
}
