package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultAttributes {
   private static final Logger f_22293_ = LogManager.getLogger();
   private static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> f_22294_ = ImmutableMap.<EntityType<? extends LivingEntity>, AttributeSupplier>builder().put(EntityType.f_20529_, LivingEntity.m_21183_().m_22265_()).put(EntityType.f_147039_, Axolotl.m_149176_().m_22265_()).put(EntityType.f_20549_, Bat.m_27455_().m_22265_()).put(EntityType.f_20550_, Bee.m_27858_().m_22265_()).put(EntityType.f_20551_, Blaze.m_32238_().m_22265_()).put(EntityType.f_20553_, Cat.m_28168_().m_22265_()).put(EntityType.f_20554_, CaveSpider.m_32267_().m_22265_()).put(EntityType.f_20555_, Chicken.m_28263_().m_22265_()).put(EntityType.f_20556_, AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20557_, Cow.m_28307_().m_22265_()).put(EntityType.f_20558_, Creeper.m_32318_().m_22265_()).put(EntityType.f_20559_, Dolphin.m_28379_().m_22265_()).put(EntityType.f_20560_, AbstractChestedHorse.m_30501_().m_22265_()).put(EntityType.f_20562_, Zombie.m_34328_().m_22265_()).put(EntityType.f_20563_, ElderGuardian.m_32471_().m_22265_()).put(EntityType.f_20566_, EnderMan.m_32541_().m_22265_()).put(EntityType.f_20567_, Endermite.m_32619_().m_22265_()).put(EntityType.f_20565_, EnderDragon.m_31167_().m_22265_()).put(EntityType.f_20568_, Evoker.m_32657_().m_22265_()).put(EntityType.f_20452_, Fox.m_28553_().m_22265_()).put(EntityType.f_20453_, Ghast.m_32752_().m_22265_()).put(EntityType.f_20454_, Giant.m_32796_().m_22265_()).put(EntityType.f_147034_, GlowSquid.m_29988_().m_22265_()).put(EntityType.f_147035_, Goat.m_149401_().m_22265_()).put(EntityType.f_20455_, Guardian.m_32853_().m_22265_()).put(EntityType.f_20456_, Hoglin.m_34551_().m_22265_()).put(EntityType.f_20457_, AbstractHorse.m_30627_().m_22265_()).put(EntityType.f_20458_, Zombie.m_34328_().m_22265_()).put(EntityType.f_20459_, Illusioner.m_32931_().m_22265_()).put(EntityType.f_20460_, IronGolem.m_28883_().m_22265_()).put(EntityType.f_20466_, Llama.m_30824_().m_22265_()).put(EntityType.f_20468_, MagmaCube.m_33000_().m_22265_()).put(EntityType.f_20504_, Cow.m_28307_().m_22265_()).put(EntityType.f_20503_, AbstractChestedHorse.m_30501_().m_22265_()).put(EntityType.f_20505_, Ocelot.m_29036_().m_22265_()).put(EntityType.f_20507_, Panda.m_29157_().m_22265_()).put(EntityType.f_20508_, Parrot.m_29438_().m_22265_()).put(EntityType.f_20509_, Monster.m_33035_().m_22265_()).put(EntityType.f_20510_, Pig.m_29503_().m_22265_()).put(EntityType.f_20511_, Piglin.m_34770_().m_22265_()).put(EntityType.f_20512_, PiglinBrute.m_35075_().m_22265_()).put(EntityType.f_20513_, Pillager.m_33307_().m_22265_()).put(EntityType.f_20532_, Player.m_36340_().m_22265_()).put(EntityType.f_20514_, PolarBear.m_29560_().m_22265_()).put(EntityType.f_20516_, AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20517_, Rabbit.m_29717_().m_22265_()).put(EntityType.f_20518_, Ravager.m_33371_().m_22265_()).put(EntityType.f_20519_, AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20520_, Sheep.m_29873_().m_22265_()).put(EntityType.f_20521_, Shulker.m_33477_().m_22265_()).put(EntityType.f_20523_, Silverfish.m_33551_().m_22265_()).put(EntityType.f_20524_, AbstractSkeleton.m_32166_().m_22265_()).put(EntityType.f_20525_, SkeletonHorse.m_30918_().m_22265_()).put(EntityType.f_20526_, Monster.m_33035_().m_22265_()).put(EntityType.f_20528_, SnowGolem.m_29934_().m_22265_()).put(EntityType.f_20479_, Spider.m_33815_().m_22265_()).put(EntityType.f_20480_, Squid.m_29988_().m_22265_()).put(EntityType.f_20481_, AbstractSkeleton.m_32166_().m_22265_()).put(EntityType.f_20482_, Strider.m_33937_().m_22265_()).put(EntityType.f_20488_, Llama.m_30824_().m_22265_()).put(EntityType.f_20489_, AbstractFish.m_27495_().m_22265_()).put(EntityType.f_20490_, Turtle.m_30207_().m_22265_()).put(EntityType.f_20491_, Vex.m_34040_().m_22265_()).put(EntityType.f_20492_, Villager.m_35503_().m_22265_()).put(EntityType.f_20493_, Vindicator.m_34104_().m_22265_()).put(EntityType.f_20494_, Mob.m_21552_().m_22265_()).put(EntityType.f_20495_, Witch.m_34155_().m_22265_()).put(EntityType.f_20496_, WitherBoss.m_31501_().m_22265_()).put(EntityType.f_20497_, AbstractSkeleton.m_32166_().m_22265_()).put(EntityType.f_20499_, Wolf.m_30425_().m_22265_()).put(EntityType.f_20500_, Zoglin.m_34257_().m_22265_()).put(EntityType.f_20501_, Zombie.m_34328_().m_22265_()).put(EntityType.f_20502_, ZombieHorse.m_31008_().m_22265_()).put(EntityType.f_20530_, Zombie.m_34328_().m_22265_()).put(EntityType.f_20531_, ZombifiedPiglin.m_34470_().m_22265_()).build();

   public static AttributeSupplier m_22297_(EntityType<? extends LivingEntity> p_22298_) {
      AttributeSupplier supplier = net.minecraftforge.common.ForgeHooks.getAttributesView().get(p_22298_);
      return supplier != null ? supplier : f_22294_.get(p_22298_);
   }

   public static boolean m_22301_(EntityType<?> p_22302_) {
      return f_22294_.containsKey(p_22302_) || net.minecraftforge.common.ForgeHooks.getAttributesView().containsKey(p_22302_);
   }

   public static void m_22296_() {
      Registry.f_122826_.m_123024_().filter((p_22306_) -> {
         return p_22306_.m_20674_() != MobCategory.MISC;
      }).filter((p_22304_) -> {
         return !m_22301_(p_22304_);
      }).map(Registry.f_122826_::m_7981_).forEach((p_22300_) -> {
         Util.m_143785_("Entity " + p_22300_ + " has no attributes");
      });
   }
}
