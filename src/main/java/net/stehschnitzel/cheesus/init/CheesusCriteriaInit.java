package net.stehschnitzel.cheesus.init;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.stehschnitzel.cheesus.datagen.triggers.PlacedAltitudeCheeseTrigger;
import net.stehschnitzel.cheesus.datagen.triggers.PlacedDiabolicalCheeseInNetherTrigger;
import net.stehschnitzel.cheesus.datagen.triggers.PlacedMoldCheeseInDarkTrigger;
import net.stehschnitzel.cheesus.datagen.triggers.RightClickedBlueMoldCheeseTrigger;

public class CheesusCriteriaInit {
    public static final PlacedMoldCheeseInDarkTrigger PLACED_MOLD_CHEESE_DARK = CriteriaTriggers.register(
            new PlacedMoldCheeseInDarkTrigger()
    );

    public static final PlacedDiabolicalCheeseInNetherTrigger PLACED_DIABOLICAL_CHEESE_NETHER = CriteriaTriggers.register(
            new PlacedDiabolicalCheeseInNetherTrigger()
    );

    public static final PlacedAltitudeCheeseTrigger PLACED_ALTITUDE_CHEESE = CriteriaTriggers.register(
            new PlacedAltitudeCheeseTrigger()
    );

    public static final RightClickedBlueMoldCheeseTrigger RIGHT_CLICKED_BLUE_MOLD_CHEESE = CriteriaTriggers.register(
            new RightClickedBlueMoldCheeseTrigger()
    );

    public static void register(IEventBus bus) {
    }

}
