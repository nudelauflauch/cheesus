package net.stehschnitzel.cheesus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin {

    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    private static void stepOnCancel(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            ci.cancel();
        }
    }
}
