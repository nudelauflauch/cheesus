package net.stehschnitzel.cheesus.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.stehschnitzel.cheesus.Cheesus;

public class CheesusPotionInit {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, Cheesus.MOD_ID);

    public static final Holder<Potion> SILENT_POTION = POTIONS.register("silent_potion",
            ()-> new Potion(new MobEffectInstance(CheesusEffectInit.SILENT_EFFECT, 1200, 0)));

    public static final Holder<Potion> LONG_SILENT_POTION = POTIONS.register("long_silent_potion",
            ()-> new Potion(new MobEffectInstance(CheesusEffectInit.SILENT_EFFECT, 3600, 0)));


    public static void register(IEventBus bus) {
        POTIONS.register(bus);
    }

}
