package net.stehschnitzel.cheesus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMuterMixin {

    @WrapOperation(method = "eat", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void muteBurpSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original) {
        Player eatingPlayer = (Player) (Object) this;
        if (eatingPlayer.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, player, x, y, z, sound, category, volume, pitch);
    }

    @WrapOperation(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void muteAttackSounds(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original) {
        Player attackingPlayer = (Player) (Object) this;
        if (attackingPlayer.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, player, x, y, z, sound, category, volume, pitch);
    }

    @WrapOperation(method = "giveExperienceLevels", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void muteXPLevelUpSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original) {
        Player attackingPlayer = (Player) (Object) this;
        if (attackingPlayer.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, player, x, y, z, sound, category, volume, pitch);
    }

    @WrapOperation(method = "hurtCurrentlyUsedShield", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    private void muteShildBreakSound(Player instance, SoundEvent sound, float volume, float pitch, Operation<Void> original) {
        Player attackingPlayer = (Player) (Object) this;
        if (attackingPlayer.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, sound, volume, pitch);
    }
}