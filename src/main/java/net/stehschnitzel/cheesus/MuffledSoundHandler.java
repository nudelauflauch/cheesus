package net.stehschnitzel.cheesus;

import com.mojang.blaze3d.audio.Channel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundSourceEvent;
import net.neoforged.neoforge.client.event.sound.PlayStreamingSourceEvent;
import net.neoforged.neoforge.client.event.sound.SoundEngineLoadEvent;
import net.stehschnitzel.cheesus.init.CheesusEffectInit;
import net.stehschnitzel.cheesus.mixin.ChannelAccessor;
import net.stehschnitzel.cheesus.mixin.SoundEngineMixin;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

@EventBusSubscriber(modid = Cheesus.MOD_ID, value = Dist.CLIENT)
public class MuffledSoundHandler {

    private static volatile boolean hasSilentEffect = false;

    private static SoundEngine soundEngine;
    private static int lowPassFilter = 0;
    private static int reverbSlot = 0;

    @SubscribeEvent
    public static void onSoundEngineLoad(SoundEngineLoadEvent event) {
        soundEngine = event.getEngine();
        lowPassFilter = 0;
    }

    @SubscribeEvent
    public static void onSound(PlaySoundSourceEvent event) {
        applyFilter(event.getSound(), event.getChannel(), hasSilentEffect);
    }

    @SubscribeEvent
    public static void onStreamingSound(PlayStreamingSourceEvent event) {
        applyFilter(event.getSound(), event.getChannel(), hasSilentEffect);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null || minecraft.level == null || soundEngine == null) {
            hasSilentEffect = false;
            return;
        }

        hasSilentEffect =
                minecraft.player.hasEffect(CheesusEffectInit.SILENT_EFFECT);

        ChannelAccess access = ((SoundEngineMixin) soundEngine).getChannelAccess();

        access.executeOnChannels(channels ->
                channels.forEach(channel -> applyFilter(null, channel, hasSilentEffect))
        );
    }

    private static void applyFilter(SoundInstance sound, Channel channel, boolean enabled) {
        if (!AL.getCapabilities().ALC_EXT_EFX) {
            return;
        }

        if (sound != null && sound.getSource().equals(SoundSource.MASTER)) {
            return;
        }

        int source = ((ChannelAccessor) channel).getSourceId();

        if (!enabled) {
            AL10.alSourcei(
                    source,
                    EXTEfx.AL_DIRECT_FILTER,
                    EXTEfx.AL_FILTER_NULL
            );

            AL11.alSource3i(
                    source,
                    EXTEfx.AL_AUXILIARY_SEND_FILTER,
                    EXTEfx.AL_EFFECTSLOT_NULL,
                    0,
                    EXTEfx.AL_FILTER_NULL
            );

            return;
        }

        if (lowPassFilter == 0) {
            lowPassFilter = EXTEfx.alGenFilters();

            EXTEfx.alFilteri(
                    lowPassFilter,
                    EXTEfx.AL_FILTER_TYPE,
                    EXTEfx.AL_FILTER_LOWPASS
            );

            EXTEfx.alFilterf(
                    lowPassFilter,
                    EXTEfx.AL_LOWPASS_GAIN,
                    0.65F
            );

            EXTEfx.alFilterf(
                    lowPassFilter,
                    EXTEfx.AL_LOWPASS_GAINHF,
                    0.03F
            );
        }

        AL10.alSourcei(
                source,
                EXTEfx.AL_DIRECT_FILTER,
                lowPassFilter
        );

        if (reverbSlot == 0) {
            int reverbEffect = EXTEfx.alGenEffects();

            EXTEfx.alEffecti(
                    reverbEffect,
                    EXTEfx.AL_EFFECT_TYPE,
                    EXTEfx.AL_EFFECT_REVERB
            );

            EXTEfx.alEffectf(
                    reverbEffect,
                    EXTEfx.AL_REVERB_DECAY_TIME,
                    4F
            );

            EXTEfx.alEffectf(
                    reverbEffect,
                    EXTEfx.AL_REVERB_GAIN,
                    0.25F
            );

            EXTEfx.alEffectf(
                    reverbEffect,
                    EXTEfx.AL_REVERB_GAINHF,
                    0.10F
            );

            reverbSlot = EXTEfx.alGenAuxiliaryEffectSlots();

            EXTEfx.alAuxiliaryEffectSloti(
                    reverbSlot,
                    EXTEfx.AL_EFFECTSLOT_EFFECT,
                    reverbEffect
            );
        }

        AL11.alSource3i(
                source,
                EXTEfx.AL_AUXILIARY_SEND_FILTER,
                reverbSlot,
                0,
                EXTEfx.AL_FILTER_NULL
        );

    }
}