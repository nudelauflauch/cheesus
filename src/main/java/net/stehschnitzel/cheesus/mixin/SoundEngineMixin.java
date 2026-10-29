package net.stehschnitzel.cheesus.mixin;

import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundEngine.class)
public interface SoundEngineMixin {

    @Accessor("channelAccess")
    ChannelAccess getChannelAccess();
}
