package net.stehschnitzel.cheesus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockItem.class)
public class BlockItemMixin {


        @WrapOperation(method = "place", at = @At(
                        value = "INVOKE",
                        target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
        private void skipPlaceSound(
                Level instance, Player player,
                BlockPos pos, SoundEvent sound,
                SoundSource category, float volume,
                float pitch, Operation<Void> original) {
            if (player != null && player.hasEffect(CheesusEffectInit.SILENT_EFFECT) && sound != null) {
                Minecraft.getInstance().getSoundManager().play(new EntityBoundSoundInstance(sound, category, volume, pitch, player, RandomSource.create().nextLong()));
                return;
            }

            original.call(instance, player, pos, sound, category, volume, pitch);
    }
}
