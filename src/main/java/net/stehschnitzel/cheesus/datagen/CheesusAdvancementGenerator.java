package net.stehschnitzel.cheesus.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;

import java.util.Optional;
import java.util.function.Consumer;

public class CheesusAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer, ExistingFileHelper existingFileHelper) {

        AdvancementHolder cheesus_root = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.CHEESE.get()),
                        Component.translatable("advancements.husbandry.cheese_root.title"),
                        Component.translatable("advancements.husbandry.cheese_root.description"),
                        Optional.empty(),
                        AdvancementType.GOAL,
                        true, true, false))
                .parent(Advancement.Builder.advancement().build(ResourceLocation.parse("minecraft:husbandry/plant_seed")))
                .addCriterion("has_cheese", InventoryChangeTrigger.TriggerInstance.hasItems(BlockInit.CHEESE.get()))
                .save(writer, Cheesus.MOD_ID + ":advancement/cheese_root");


        AdvancementHolder white_moldy_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.WHITE_MOLD_CHEESE.get()),
                        Component.translatable("advancements.husbandry.white_moldy.title"),
                        Component.translatable("advancements.husbandry.white_moldy.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .addCriterion("placed_mold_cheese_dark", InventoryChangeTrigger.TriggerInstance.hasItems(BlockInit.WHITE_MOLD_CHEESE.get()))
                .save(writer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "white_moldy_cheese").getPath());

        AdvancementHolder blue_moldy_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.BLUE_MOLD_CHEESE.get()),
                        Component.translatable("advancements.husbandry.blue_moldy.title"),
                        Component.translatable("advancements.husbandry.blue_moldy.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true,
                        true,
                        false))
                .parent(cheesus_root)
                .addCriterion("sword_click", InventoryChangeTrigger.TriggerInstance.hasItems(BlockInit.BLUE_MOLD_CHEESE.get()))
                .save(writer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "blue_white_cheese").getPath());

        AdvancementHolder diabolical_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.DIABOLICAL_CHEESE.get()),
                        Component.translatable("advancements.husbandry.diabolical_cheese.title"),
                        Component.translatable("advancements.husbandry.diabolical_cheese.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true,
                        true,
                        false))
                .parent(cheesus_root)
                .addCriterion("placed_diabolical_cheese_nether", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.inDimension(Level.NETHER),
                        ItemPredicate.Builder.item().of(BlockInit.CHEESE.asItem())))
                .save(writer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "diabolical_cheese").getPath());

        AdvancementHolder altitude_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.ALTITUDE_CHEESE.get()),
                        Component.translatable("advancements.husbandry.altitude_cheese.title"),
                        Component.translatable("advancements.husbandry.altitude_cheese.description"),
                        Optional.empty(), AdvancementType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .addCriterion("placed_altitude_cheese_nether", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.atYLocation(MinMaxBounds.Doubles.atLeast(140.0)),
                        ItemPredicate.Builder.item().of(BlockInit.CHEESE.asItem())))
                .save(writer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "altitude_cheese").getPath());

        AdvancementHolder grey_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.GREY_CHEESE.get()),
                        Component.translatable("advancements.husbandry.grey_cheese.title"),
                        Component.translatable("advancements.husbandry.grey_cheese.description"),
                        Optional.empty(), AdvancementType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .addCriterion("cheese_click", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(BlockInit.CHEESE.get())) ,
                        ItemPredicate.Builder.item().of(ItemTags.SWORDS)
                ))
                .save(writer, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "grey_cheese").getPath());

    }
}