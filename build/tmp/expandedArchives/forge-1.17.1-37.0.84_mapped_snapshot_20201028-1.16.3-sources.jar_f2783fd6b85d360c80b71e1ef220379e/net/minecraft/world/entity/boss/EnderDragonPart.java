package net.minecraft.world.entity.boss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

public class EnderDragonPart extends net.minecraftforge.entity.PartEntity<EnderDragon> {
   public final EnderDragon f_31010_;
   public final String f_31011_;
   private final EntityDimensions f_31012_;

   public EnderDragonPart(EnderDragon p_31014_, String p_31015_, float p_31016_, float p_31017_) {
      super(p_31014_);
      this.f_31012_ = EntityDimensions.m_20395_(p_31016_, p_31017_);
      this.m_6210_();
      this.f_31010_ = p_31014_;
      this.f_31011_ = p_31015_;
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag p_31025_) {
   }

   protected void m_7380_(CompoundTag p_31028_) {
   }

   public boolean m_6087_() {
      return true;
   }

   public boolean m_6469_(DamageSource p_31020_, float p_31021_) {
      return this.m_6673_(p_31020_) ? false : this.f_31010_.m_31120_(this, p_31020_, p_31021_);
   }

   public boolean m_7306_(Entity p_31031_) {
      return this == p_31031_ || this.f_31010_ == p_31031_;
   }

   public Packet<?> m_5654_() {
      throw new UnsupportedOperationException();
   }

   public EntityDimensions m_6972_(Pose p_31023_) {
      return this.f_31012_;
   }

   public boolean m_142391_() {
      return false;
   }
}
