package net.stehschnitzel.cheesus.mixin;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import net.stehschnitzel.cheesus.common.effect.SilentEffect;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VibrationSystem.Listener.class)
public class VibrationMixin {

    @Inject(method = "handleGameEvent", at = @At("HEAD"), cancellable = true)
    private static void receiveVibrationPlusEffect(
            ServerLevel level, Holder<GameEvent> gameEvent, GameEvent.Context context, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = context.sourceEntity();

        if (entity instanceof LivingEntity livingEntity &&
                livingEntity.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            cir.setReturnValue(false);
        }

        if (entity instanceof Projectile projectile &&
                projectile.getOwner() instanceof LivingEntity livingEntity &&
                livingEntity.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            cir.setReturnValue(false);
        }
    }

}
