package net.stehschnitzel.cheesus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class LevelBreakingSoundMuter {

    @Shadow
    @Nullable
    private ClientLevel level;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "levelEvent", at = @At("HEAD"))
    private void readEventToMute(int type, BlockPos pos, int data, CallbackInfo ci) {
        if (type == 20010 && minecraft.player.hasEffect(CheesusEffectInit.SILENT_EFFECT)) {
            BlockState blockstate1 = Block.stateById(data);
            if (!blockstate1.isAir() && !net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions.of(blockstate1).playBreakSound(blockstate1, this.level, pos)) {
                SoundType soundtype = blockstate1.getSoundType(this.level, pos, null);
                Minecraft.getInstance().getSoundManager().play(
                        new SimpleSoundInstance(
                                soundtype.getBreakSound(),
                                SoundSource.BLOCKS,
                                (soundtype.getVolume() + 1.0F) / 2.0F,
                                soundtype.getPitch() * 0.8F,
                                SoundInstance.createUnseededRandom(), pos));
                return;
            }

            assert this.level != null;
            this.level.addDestroyBlockEffect(pos, blockstate1);
        }
    }

}
