package net.stehschnitzel.cheesus.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.Block.getId;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "spawnDestroyParticles", at = @At("HEAD"), cancellable = true)
    private void dontplaySound(Level level, Player player, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (player.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            level.levelEvent(player, 20010, pos, getId(state));
            ci.cancel();
        }
    }
}
