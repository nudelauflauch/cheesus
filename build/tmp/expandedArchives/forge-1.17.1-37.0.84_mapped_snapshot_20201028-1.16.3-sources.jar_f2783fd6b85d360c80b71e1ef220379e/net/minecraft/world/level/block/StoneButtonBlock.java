package net.minecraft.world.level.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StoneButtonBlock extends ButtonBlock {
   public StoneButtonBlock(BlockBehaviour.Properties p_57060_) {
      super(false, p_57060_);
   }

   protected SoundEvent m_5722_(boolean p_57062_) {
      return p_57062_ ? SoundEvents.f_12444_ : SoundEvents.f_12443_;
   }
}