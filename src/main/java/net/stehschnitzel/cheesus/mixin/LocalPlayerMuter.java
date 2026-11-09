package net.stehschnitzel.cheesus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Entity.class)
public class LocalPlayerMuter {

    @Shadow
    private Level level;

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void mutePlayer(BlockPos pos, BlockState state, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!entity.equals(Minecraft.getInstance().player)) {
            ci.cancel();
            return;
        }
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(CheesusEffectInit.MUTED_EFFECT)) {
            SoundType soundtype = state.getSoundType(level, pos, entity);
            Minecraft.getInstance().getSoundManager().play(
                    new SimpleSoundInstance(
                            soundtype.getStepSound(),
                            entity.getSoundSource(),
                            soundtype.getVolume() * 0.15F,
                            soundtype.getPitch(),
                            SoundInstance.createUnseededRandom(),
                            pos));
            ci.cancel();
        }
    }
}