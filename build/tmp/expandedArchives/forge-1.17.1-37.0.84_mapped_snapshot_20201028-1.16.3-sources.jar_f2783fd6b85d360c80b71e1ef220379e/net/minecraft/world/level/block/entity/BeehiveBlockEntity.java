package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BeehiveBlockEntity extends BlockEntity {
   public static final String f_155121_ = "FlowerPos";
   public static final String f_155122_ = "MinOccupationTicks";
   public static final String f_155123_ = "EntityData";
   public static final String f_155124_ = "TicksInHive";
   public static final String f_155125_ = "HasNectar";
   public static final String f_155126_ = "Bees";
   private static final List<String> f_155129_ = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "HivePos", "Passengers", "Leash", "UUID");
   public static final int f_155127_ = 3;
   private static final int f_155130_ = 400;
   private static final int f_155131_ = 2400;
   public static final int f_155128_ = 600;
   private final List<BeehiveBlockEntity.BeeData> f_58732_ = Lists.newArrayList();
   @Nullable
   private BlockPos f_58733_;

   public BeehiveBlockEntity(BlockPos p_155134_, BlockState p_155135_) {
      super(BlockEntityType.f_58912_, p_155134_, p_155135_);
   }

   public void m_6596_() {
      if (this.m_58773_()) {
         this.m_58748_((Player)null, this.f_58857_.m_8055_(this.m_58899_()), BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
      }

      super.m_6596_();
   }

   public boolean m_58773_() {
      if (this.f_58857_ == null) {
         return false;
      } else {
         for(BlockPos blockpos : BlockPos.m_121940_(this.f_58858_.m_142082_(-1, -1, -1), this.f_58858_.m_142082_(1, 1, 1))) {
            if (this.f_58857_.m_8055_(blockpos).m_60734_() instanceof FireBlock) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean m_58774_() {
      return this.f_58732_.isEmpty();
   }

   public boolean m_58775_() {
      return this.f_58732_.size() == 3;
   }

   public void m_58748_(@Nullable Player p_58749_, BlockState p_58750_, BeehiveBlockEntity.BeeReleaseStatus p_58751_) {
      List<Entity> list = this.m_58759_(p_58750_, p_58751_);
      if (p_58749_ != null) {
         for(Entity entity : list) {
            if (entity instanceof Bee) {
               Bee bee = (Bee)entity;
               if (p_58749_.m_20182_().m_82557_(entity.m_20182_()) <= 16.0D) {
                  if (!this.m_58777_()) {
                     bee.m_6710_(p_58749_);
                  } else {
                     bee.m_27915_(400);
                  }
               }
            }
         }
      }

   }

   private List<Entity> m_58759_(BlockState p_58760_, BeehiveBlockEntity.BeeReleaseStatus p_58761_) {
      List<Entity> list = Lists.newArrayList();
      this.f_58732_.removeIf((p_58766_) -> {
         return m_155136_(this.f_58857_, this.f_58858_, p_58760_, p_58766_, list, p_58761_, this.f_58733_);
      });
      return list;
   }

   public void m_58741_(Entity p_58742_, boolean p_58743_) {
      this.m_58744_(p_58742_, p_58743_, 0);
   }

   @VisibleForDebug
   public int m_58776_() {
      return this.f_58732_.size();
   }

   public static int m_58752_(BlockState p_58753_) {
      return p_58753_.m_61143_(BeehiveBlock.f_49564_);
   }

   @VisibleForDebug
   public boolean m_58777_() {
      return CampfireBlock.m_51248_(this.f_58857_, this.m_58899_());
   }

   public void m_58744_(Entity p_58745_, boolean p_58746_, int p_58747_) {
      if (this.f_58732_.size() < 3) {
         p_58745_.m_8127_();
         p_58745_.m_20153_();
         CompoundTag compoundtag = new CompoundTag();
         p_58745_.m_20223_(compoundtag);
         this.m_155157_(compoundtag, p_58747_, p_58746_);
         if (this.f_58857_ != null) {
            if (p_58745_ instanceof Bee) {
               Bee bee = (Bee)p_58745_;
               if (bee.m_27852_() && (!this.m_58780_() || this.f_58857_.f_46441_.nextBoolean())) {
                  this.f_58733_ = bee.m_27851_();
               }
            }

            BlockPos blockpos = this.m_58899_();
            this.f_58857_.m_6263_((Player)null, (double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), SoundEvents.f_11695_, SoundSource.BLOCKS, 1.0F, 1.0F);
         }

         p_58745_.m_146870_();
      }
   }

   public void m_155157_(CompoundTag p_155158_, int p_155159_, boolean p_155160_) {
      this.f_58732_.add(new BeehiveBlockEntity.BeeData(p_155158_, p_155159_, p_155160_ ? 2400 : 600));
   }

   private static boolean m_155136_(Level p_155137_, BlockPos p_155138_, BlockState p_155139_, BeehiveBlockEntity.BeeData p_155140_, @Nullable List<Entity> p_155141_, BeehiveBlockEntity.BeeReleaseStatus p_155142_, @Nullable BlockPos p_155143_) {
      if ((p_155137_.m_46462_() || p_155137_.m_46471_()) && p_155142_ != BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY) {
         return false;
      } else {
         CompoundTag compoundtag = p_155140_.f_58782_;
         m_155161_(compoundtag);
         compoundtag.m_128365_("HivePos", NbtUtils.m_129224_(p_155138_));
         compoundtag.m_128379_("NoGravity", true);
         Direction direction = p_155139_.m_61143_(BeehiveBlock.f_49563_);
         BlockPos blockpos = p_155138_.m_142300_(direction);
         boolean flag = !p_155137_.m_8055_(blockpos).m_60812_(p_155137_, blockpos).m_83281_();
         if (flag && p_155142_ != BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY) {
            return false;
         } else {
            Entity entity = EntityType.m_20645_(compoundtag, p_155137_, (p_58740_) -> {
               return p_58740_;
            });
            if (entity != null) {
               if (!entity.m_6095_().m_20609_(EntityTypeTags.f_13122_)) {
                  return false;
               } else {
                  if (entity instanceof Bee) {
                     Bee bee = (Bee)entity;
                     if (p_155143_ != null && !bee.m_27852_() && p_155137_.f_46441_.nextFloat() < 0.9F) {
                        bee.m_27876_(p_155143_);
                     }

                     if (p_155142_ == BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED) {
                        bee.m_27864_();
                        if (p_155139_.m_60620_(BlockTags.f_13072_)) {
                           int i = m_58752_(p_155139_);
                           if (i < 5) {
                              int j = p_155137_.f_46441_.nextInt(100) == 0 ? 2 : 1;
                              if (i + j > 5) {
                                 --j;
                              }

                              p_155137_.m_46597_(p_155138_, p_155139_.m_61124_(BeehiveBlock.f_49564_, Integer.valueOf(i + j)));
                           }
                        }
                     }

                     m_58736_(p_155140_.f_58783_, bee);
                     if (p_155141_ != null) {
                        p_155141_.add(bee);
                     }

                     float f = entity.m_20205_();
                     double d3 = flag ? 0.0D : 0.55D + (double)(f / 2.0F);
                     double d0 = (double)p_155138_.m_123341_() + 0.5D + d3 * (double)direction.m_122429_();
                     double d1 = (double)p_155138_.m_123342_() + 0.5D - (double)(entity.m_20206_() / 2.0F);
                     double d2 = (double)p_155138_.m_123343_() + 0.5D + d3 * (double)direction.m_122431_();
                     entity.m_7678_(d0, d1, d2, entity.m_146908_(), entity.m_146909_());
                  }

                  p_155137_.m_5594_((Player)null, p_155138_, SoundEvents.f_11696_, SoundSource.BLOCKS, 1.0F, 1.0F);
                  return p_155137_.m_7967_(entity);
               }
            } else {
               return false;
            }
         }
      }
   }

   static void m_155161_(CompoundTag p_155162_) {
      for(String s : f_155129_) {
         p_155162_.m_128473_(s);
      }

   }

   private static void m_58736_(int p_58737_, Bee p_58738_) {
      int i = p_58738_.m_146764_();
      if (i < 0) {
         p_58738_.m_146762_(Math.min(0, i + p_58737_));
      } else if (i > 0) {
         p_58738_.m_146762_(Math.max(0, i - p_58737_));
      }

      p_58738_.m_27601_(Math.max(0, p_58738_.m_27591_() - p_58737_));
   }

   private boolean m_58780_() {
      return this.f_58733_ != null;
   }

   private static void m_155149_(Level p_155150_, BlockPos p_155151_, BlockState p_155152_, List<BeehiveBlockEntity.BeeData> p_155153_, @Nullable BlockPos p_155154_) {
      BeehiveBlockEntity.BeeData beehiveblockentity$beedata;
      for(Iterator<BeehiveBlockEntity.BeeData> iterator = p_155153_.iterator(); iterator.hasNext(); ++beehiveblockentity$beedata.f_58783_) {
         beehiveblockentity$beedata = iterator.next();
         if (beehiveblockentity$beedata.f_58783_ > beehiveblockentity$beedata.f_58784_) {
            BeehiveBlockEntity.BeeReleaseStatus beehiveblockentity$beereleasestatus = beehiveblockentity$beedata.f_58782_.m_128471_("HasNectar") ? BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED : BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED;
            if (m_155136_(p_155150_, p_155151_, p_155152_, beehiveblockentity$beedata, (List<Entity>)null, beehiveblockentity$beereleasestatus, p_155154_)) {
               iterator.remove();
            }
         }
      }

   }

   public static void m_155144_(Level p_155145_, BlockPos p_155146_, BlockState p_155147_, BeehiveBlockEntity p_155148_) {
      m_155149_(p_155145_, p_155146_, p_155147_, p_155148_.f_58732_, p_155148_.f_58733_);
      if (!p_155148_.f_58732_.isEmpty() && p_155145_.m_5822_().nextDouble() < 0.005D) {
         double d0 = (double)p_155146_.m_123341_() + 0.5D;
         double d1 = (double)p_155146_.m_123342_();
         double d2 = (double)p_155146_.m_123343_() + 0.5D;
         p_155145_.m_6263_((Player)null, d0, d1, d2, SoundEvents.f_11698_, SoundSource.BLOCKS, 1.0F, 1.0F);
      }

      DebugPackets.m_179510_(p_155145_, p_155146_, p_155147_, p_155148_);
   }

   public void m_142466_(CompoundTag p_155156_) {
      super.m_142466_(p_155156_);
      this.f_58732_.clear();
      ListTag listtag = p_155156_.m_128437_("Bees", 10);

      for(int i = 0; i < listtag.size(); ++i) {
         CompoundTag compoundtag = listtag.m_128728_(i);
         BeehiveBlockEntity.BeeData beehiveblockentity$beedata = new BeehiveBlockEntity.BeeData(compoundtag.m_128469_("EntityData"), compoundtag.m_128451_("TicksInHive"), compoundtag.m_128451_("MinOccupationTicks"));
         this.f_58732_.add(beehiveblockentity$beedata);
      }

      this.f_58733_ = null;
      if (p_155156_.m_128441_("FlowerPos")) {
         this.f_58733_ = NbtUtils.m_129239_(p_155156_.m_128469_("FlowerPos"));
      }

   }

   public CompoundTag m_6945_(CompoundTag p_58771_) {
      super.m_6945_(p_58771_);
      p_58771_.m_128365_("Bees", this.m_58779_());
      if (this.m_58780_()) {
         p_58771_.m_128365_("FlowerPos", NbtUtils.m_129224_(this.f_58733_));
      }

      return p_58771_;
   }

   public ListTag m_58779_() {
      ListTag listtag = new ListTag();

      for(BeehiveBlockEntity.BeeData beehiveblockentity$beedata : this.f_58732_) {
         beehiveblockentity$beedata.f_58782_.m_128473_("UUID");
         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128365_("EntityData", beehiveblockentity$beedata.f_58782_);
         compoundtag.m_128405_("TicksInHive", beehiveblockentity$beedata.f_58783_);
         compoundtag.m_128405_("MinOccupationTicks", beehiveblockentity$beedata.f_58784_);
         listtag.add(compoundtag);
      }

      return listtag;
   }

   static class BeeData {
      final CompoundTag f_58782_;
      int f_58783_;
      final int f_58784_;

      BeeData(CompoundTag p_58786_, int p_58787_, int p_58788_) {
         BeehiveBlockEntity.m_155161_(p_58786_);
         this.f_58782_ = p_58786_;
         this.f_58783_ = p_58787_;
         this.f_58784_ = p_58788_;
      }
   }

   public static enum BeeReleaseStatus {
      HONEY_DELIVERED,
      BEE_RELEASED,
      EMERGENCY;
   }
}