package net.stehschnitzel.cheesus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Shadow
    private ClientLevel level;

    @WrapOperation(method = "handleTakeItemEntity", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
    private void muteItemAOrbPickupSounds(ClientLevel instance, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, boolean distanceDelay, Operation<Void> original,
    ClientboundTakeItemEntityPacket packet) {
        if (level.getEntity(packet.getPlayerId()) instanceof LivingEntity entity
                && entity.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            return;
        }
        original.call(instance, x, y, z, sound, category, volume, pitch, distanceDelay);
    }
}
