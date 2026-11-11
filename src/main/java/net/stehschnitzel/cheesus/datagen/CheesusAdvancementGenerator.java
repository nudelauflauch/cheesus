package net.stehschnitzel.cheesus.datagen;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
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
                .save(writer, Cheesus.MOD_ID + ":husbandry/cheese_root");

        AdvancementHolder white_moldy_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.WHITE_MOLD_CHEESE.get()),
                        Component.translatable("advancements.husbandry.white_moldy.title"),
                        Component.translatable("advancements.husbandry.white_moldy.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .addCriterion("placed_mold_cheese_dark", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(
                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockInit.CHEESE.get()),
                            LocationCheck.checkLocation(
                                    LocationPredicate.Builder.location().setLight(
                                            LightPredicate.Builder.light().setComposite(MinMaxBounds.Ints.between(0, 3))),
                                    new BlockPos(0, 1, 0))
                        )
                )
                .save(writer, Cheesus.MOD_ID + ":husbandry/white_moldy_cheese");

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
                .addCriterion("sword_click", CriteriaTriggers.IMPOSSIBLE.createCriterion(
                                new ImpossibleTrigger.TriggerInstance()))
                .save(writer, Cheesus.MOD_ID + ":husbandry/blue_mold_cheese");

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
                .save(writer, Cheesus.MOD_ID + ":husbandry/diabolical_cheese");

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
                .save(writer, Cheesus.MOD_ID + ":husbandry/altitude_cheese");

        AdvancementHolder grey_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.GREY_CHEESE.get()),
                        Component.translatable("advancements.husbandry.grey_cheese.title"),
                        Component.translatable("advancements.husbandry.grey_cheese.description"),
                        Optional.empty(), AdvancementType.TASK,
                        true, true, false))
                .parent(cheesus_root)
                .addCriterion("cheese_click", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(BlockInit.CHEESE_STRAINER.get())) ,
                        ItemPredicate.Builder.item().of(BlockInit.CHEESE.asItem())))
                .save(writer, Cheesus.MOD_ID + ":husbandry/grey_cheese");

        AdvancementHolder silent_cheese = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(BlockInit.SILENT_CHEESE.get()),
                        Component.translatable("advancements.husbandry.silent_cheese.title"),
                        Component.translatable("advancements.husbandry.silent_cheese.description"),
                        Optional.empty(), AdvancementType.TASK,
                        true, true, false))
                .parent(white_moldy_cheese)
                .addCriterion("placed_silent_cheese_deep_dark", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.inBiome(registries.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.DEEP_DARK)),
                        ItemPredicate.Builder.item().of(BlockInit.CHEESE.asItem())))
                .save(writer, Cheesus.MOD_ID + ":husbandry/silent_cheese");

    }
}