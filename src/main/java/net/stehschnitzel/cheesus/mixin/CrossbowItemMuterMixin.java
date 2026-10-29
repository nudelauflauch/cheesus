package net.stehschnitzel.cheesus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(CrossbowItem.class)
public class CrossbowItemMuterMixin {


    @WrapOperation(method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem$ChargingSounds;end()Ljava/util/Optional;"))
    private Optional<Holder<SoundEvent>> muteFullyChargedSound(CrossbowItem.ChargingSounds instance,
                                                           Operation<Optional<Holder<SoundEvent>>> original,
                                                           ItemStack stack,
                                                           Level level,
                                                           LivingEntity entityLiving,
                                                           int timeLeft) {
        if (entityLiving != null &&
                entityLiving.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return Optional.empty();
        }
        return original.call(instance);
    }

    @WrapOperation(method = "onUseTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem$ChargingSounds;start()Ljava/util/Optional;"))
    private Optional<Holder<SoundEvent>> muteStartChargingSound(CrossbowItem.ChargingSounds instance,
                                                           Operation<Optional<Holder<SoundEvent>>> original,
                                                           Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (livingEntity != null && livingEntity.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return Optional.empty();
        }
        return original.call(instance);
    }

    @WrapOperation(method = "onUseTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem$ChargingSounds;start()Ljava/util/Optional;"))
    private Optional<Holder<SoundEvent>> muteMidChargingSound(CrossbowItem.ChargingSounds instance,
                                                                Operation<Optional<Holder<SoundEvent>>> original,
                                                                Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (livingEntity != null && livingEntity.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return Optional.empty();
        }
        return original.call(instance);
    }

    @WrapOperation(method = "shootProjectile",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void muteShootingSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original,
            LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        if (shooter != null && shooter.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, player, x, y, z, sound, category, volume, pitch);
    }
}
