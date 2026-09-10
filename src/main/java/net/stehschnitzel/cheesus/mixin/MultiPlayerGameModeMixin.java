package net.stehschnitzel.cheesus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.level.GameType;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Shadow
    private GameType localPlayerMode;

    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapOperation(method = "continueDestroyBlock", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/sounds/SoundManager;play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V"))
    private void muteBreakingBlock(SoundManager instance, SoundInstance sound, Operation<Void> original) {
        if (this.minecraft.player.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, sound);
    }
}
