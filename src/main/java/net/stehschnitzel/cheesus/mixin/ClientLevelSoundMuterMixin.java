package net.stehschnitzel.cheesus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientLevel.class)
public abstract class ClientLevelSoundMuterMixin {

    @Shadow
    @Final
    private List<AbstractClientPlayer> players;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "playSound", at = @At("HEAD"), cancellable = true)
    private void muteSoundDefault(double x, double y, double z, SoundEvent soundEvent, SoundSource source, float volume, float pitch, boolean distanceDelay, long seed, CallbackInfo ci) {
        if (this.minecraft.player.hasEffect(CheesusEffectInit.SILENT_EFFECT) &&
                source.equals(SoundSource.PLAYERS)) {
            ci.cancel();
        } else if (this.minecraft.player.hasEffect(CheesusEffectInit.SILENT_EFFECT) &&
                (soundEvent.equals(SoundEvents.PLAYER_BURP)
                || soundEvent.equals(SoundEvents.AMBIENT_UNDERWATER_ENTER)
                || soundEvent.equals(SoundEvents.AMBIENT_UNDERWATER_EXIT))) {
            ci.cancel();
        } else {
            System.out.println(soundEvent);
            System.out.println(source);
        }
    }

    @Inject(method = "playLocalSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", at = @At("HEAD"), cancellable = true)
    private void muteLocalSound(Entity entity, SoundEvent sound, SoundSource category, float volume, float pitch, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(CheesusEffectInit.SILENT_EFFECT) &&
                category.equals(SoundSource.PLAYERS)) {
            ci.cancel();
        }
    }

    @Inject(method = "playSeededSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V", at = @At("HEAD"), cancellable = true)
    private void muteSeededSound(Player player, double x, double y, double z, Holder<SoundEvent> sound, SoundSource category, float volume, float pitch, long seed, CallbackInfo ci) {
        if (player != null && player.hasEffect(CheesusEffectInit.SILENT_EFFECT) &&
                category.equals(SoundSource.PLAYERS)) {
            ci.cancel();
        }
    }

    @Inject(method = "playSeededSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V", at = @At("HEAD"), cancellable = true)
    private void muteSeededSound(Player player, Entity entity, Holder<SoundEvent> sound, SoundSource category, float volume, float pitch, long seed, CallbackInfo ci) {
        if (player.hasEffect(CheesusEffectInit.SILENT_EFFECT) &&
                category.equals(SoundSource.PLAYERS)) {
            ci.cancel();
        }
    }
}
