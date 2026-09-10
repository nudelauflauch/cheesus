package net.stehschnitzel.cheesus.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.common.effect.SilentEffect;

public class CheesusEffectInit {

    public static final DeferredRegister<MobEffect> MOB_EFFECT =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Cheesus.MOD_ID);

    public static final Holder<MobEffect> SILENT_EFFECT = MOB_EFFECT.register("silent",
            () -> new SilentEffect(MobEffectCategory.BENEFICIAL, 0x009295));

    public static void register(IEventBus bus) {
        MOB_EFFECT.register(bus);
    }
}
