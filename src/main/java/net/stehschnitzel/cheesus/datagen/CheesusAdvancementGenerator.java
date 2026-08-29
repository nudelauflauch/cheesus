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

import java.util.function.Consumer;

public class CheesusAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(
            HolderLookup.Provider registries,
            Consumer<AdvancementHolder> writer,
            ExistingFileHelper existingFileHelper
    ) {

        AdvancementHolder cheeseRoot = Advancement.Builder.advancement()
                .display(display(
                        BlockInit.CHEESE,
                        "cheese_root",
                        AdvancementType.GOAL
                ))
                .parent(AdvancementSubProvider.createPlaceholder(
                        "minecraft:husbandry/plant_seed"
                ))
                .addCriterion(
                        "has_cheese",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                BlockInit.CHEESE
                        )
                )
                .save(
                        writer,
                        id("cheese_root"),
                        existingFileHelper
                );


        Advancement.Builder.advancement()
                .display(display(
                        BlockInit.WHITE_MOLD_CHEESE,
                        "white_moldy",
                        AdvancementType.TASK
                ))
                .parent(cheeseRoot)
                .addCriterion(
                        "has_white_mold_cheese",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                BlockInit.WHITE_MOLD_CHEESE
                        )
                )
                .save(
                        writer,
                        id("white_moldy_cheese"),
                        existingFileHelper
                );


        Advancement.Builder.advancement()
                .display(display(
                        BlockInit.BLUE_MOLD_CHEESE,
                        "blue_moldy",
                        AdvancementType.TASK
                ))
                .parent(cheeseRoot)
                .addCriterion(
                        "has_blue_mold_cheese",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                BlockInit.BLUE_MOLD_CHEESE
                        )
                )
                .save(
                        writer,
                        id("blue_moldy_cheese"),
                        existingFileHelper
                );


        Advancement.Builder.advancement()
                .display(display(
                        BlockInit.DIABOLICAL_CHEESE,
                        "diabolical_cheese",
                        AdvancementType.TASK
                ))
                .parent(cheeseRoot)
                .addCriterion(
                        "use_cheese_in_nether",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.inDimension(Level.NETHER),
                                ItemPredicate.Builder.item()
                                        .of(BlockInit.CHEESE)
                        )
                )
                .save(
                        writer,
                        id("diabolical_cheese"),
                        existingFileHelper
                );


        Advancement.Builder.advancement()
                .display(display(
                        BlockInit.ALTITUDE_CHEESE,
                        "altitude_cheese",
                        AdvancementType.TASK
                ))
                .parent(cheeseRoot)
                .addCriterion(
                        "use_cheese_above_140",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.atYLocation(
                                        MinMaxBounds.Doubles.atLeast(140.0)
                                ),
                                ItemPredicate.Builder.item()
                                        .of(BlockInit.CHEESE)
                        )
                )
                .save(
                        writer,
                        id("altitude_cheese"),
                        existingFileHelper
                );


        Advancement.Builder.advancement()
                .display(display(
                        BlockInit.GREY_CHEESE,
                        "grey_cheese",
                        AdvancementType.TASK
                ))
                .parent(cheeseRoot)
                .addCriterion(
                        "use_sword_on_cheese",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location()
                                        .setBlock(
                                                BlockPredicate.Builder.block()
                                                        .of(BlockInit.CHEESE.get())
                                        ),
                                ItemPredicate.Builder.item()
                                        .of(ItemTags.SWORDS)
                        )
                )
                .save(
                        writer,
                        id("grey_cheese"),
                        existingFileHelper
                );
    }


    private static DisplayInfo display(
            ItemLike icon,
            String name,
            AdvancementType type
    ) {
        return new DisplayInfo(
                new ItemStack(icon),
                Component.translatable(
                        "advancements.husbandry." + name + ".title"
                ),
                Component.translatable(
                        "advancements.husbandry." + name + ".description"
                ),
                null,
                type,
                true,
                true,
                false
        );
    }


    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(
                Cheesus.MOD_ID,
                path
        );
    }
}