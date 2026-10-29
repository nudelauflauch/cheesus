package net.stehschnitzel.cheesus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public abstract class BowMuterMixin {

    @WrapOperation(method = "releaseUsing", at = @At(
                          value = "INVOKE",
                  target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void muteBow(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original, ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving != null && entityLiving.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, player, x, y, z, sound, category, volume, pitch);
    }
}
