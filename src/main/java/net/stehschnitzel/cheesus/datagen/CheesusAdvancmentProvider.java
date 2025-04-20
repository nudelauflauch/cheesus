package net.stehschnitzel.cheesus.datagen;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.datagen.triggers.PlacedAltitudeCheeseTrigger;
import net.stehschnitzel.cheesus.datagen.triggers.PlacedDiabolicalCheeseInNetherTrigger;
import net.stehschnitzel.cheesus.datagen.triggers.PlacedMoldCheeseInDarkTrigger;
import net.stehschnitzel.cheesus.datagen.triggers.RightClickedBlueMoldCheeseTrigger;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.ItemInit;

import java.util.function.Consumer;

public class CheesusAdvancmentProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
        Advancement cheesus_root = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.CHEESE.get()),
                        Component.translatable("advancements.husbandry.cheese_root.title"),
                        Component.translatable("advancements.husbandry.cheese_root.description"),
                        null, FrameType.TASK,
                        true, true, false))
                .parent(ResourceLocation.parse("husbandry/plant_seed"))
                .addCriterion("has_cheese", InventoryChangeTrigger.TriggerInstance.hasItems(BlockInit.CHEESE.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "cheese_root"), existingFileHelper);

        Advancement white_moldy_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.WHITE_MOLD_CHEESE.get()),
                        Component.translatable("advancements.husbandry.white_moldy.title"),
                        Component.translatable("advancements.husbandry.white_moldy.description"),
                        null, FrameType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .requirements(RequirementsStrategy.AND)
                .addCriterion("placed_mold_cheese_dark", PlacedMoldCheeseInDarkTrigger.TriggerInstance.placedMoldCheeseInDark())
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "white_moldy_cheese"), existingFileHelper);

        Advancement blue_moldy_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.BLUE_MOLD_CHEESE.get()),
                        Component.translatable("advancements.husbandry.blue_moldy.title"),
                        Component.translatable("advancements.husbandry.blue_moldy.description"),
                        null, FrameType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .addCriterion("sword_click", RightClickedBlueMoldCheeseTrigger.TriggerInstance.rightClickedBlueMoldCheese())
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "blue_white_cheese"), existingFileHelper);

        Advancement diabolical_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.DIABOLICAL_CHEESE.get()),
                        Component.translatable("advancements.husbandry.diabolical_cheese.title"),
                        Component.translatable("advancements.husbandry.diabolical_cheese.description"),
                        null, FrameType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .requirements(RequirementsStrategy.AND)
                .addCriterion("placed_diabolical_cheese_nether", PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance.placedDiabolicalCheeseInNether())
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "diabolical_cheese"), existingFileHelper);

        Advancement altitude_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.ALTITUDE_CHEESE.get()),
                        Component.translatable("advancements.husbandry.altitude_cheese.title"),
                        Component.translatable("advancements.husbandry.altitude_cheese.description"),
                        null, FrameType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .requirements(RequirementsStrategy.AND)
                .addCriterion("placed_altitude_cheese_nether", PlacedAltitudeCheeseTrigger.TriggerInstance.placedAltitudeCheese())
                .save(consumer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "altitude_cheese"), existingFileHelper);

        Advancement grey_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.GREY_CHEESE.get()),
                        Component.translatable("advancements.husbandry.grey_cheese.title"),
                        Component.translatable("advancements.husbandry.grey_cheese.description"),
                        null, FrameType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .addCriterion(
                        "cheese_click", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(BlockInit.CHEESE_STRAINER.get()).build()),
                                ItemPredicate.Builder.item().of(BlockInit.CHEESE.get())
                        )
                ).save(consumer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "grey_cheese"), existingFileHelper);
    }
}