package net.stehschnitzel.cheesus.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.stehschnitzel.cheesus.common.blocks.BasicCheese;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.ItemInit;

import java.util.Set;

public class CheesusLootTables extends BlockLootSubProvider {
    protected CheesusLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlockInit.CHEESE_COVER.get());
        dropSelf(BlockInit.CHEESE_STRAINER.get());
        dropCheese(BlockInit.WHITE_MOLD_CHEESE.get(), ItemInit.WHITE_MOLD_CHEESE_SLICE.get());
        dropCheese(BlockInit.REFINED_CHEESE.get(), ItemInit.REFINED_CHEESE_SLICE.get());
        dropCheese(BlockInit.CAMEMBERT.get(), ItemInit.CAMEMBERT_SLICE.get());
        dropCheese(BlockInit.CHEDDAR.get(), ItemInit.CHEDDAR_SLICE.get());
        dropCheese(BlockInit.DIABOLICAL_CHEESE.get(), ItemInit.DIABOLICAL_CHEESE_SLICE.get());
        dropCheese(BlockInit.ALPINE_CHEESE.get(), ItemInit.ALPINE_CHEESE_SLICE.get());
        dropCheese(BlockInit.BLUE_CHEESE.get(), ItemInit.BLUE_CHEESE_SLICE.get());
        dropCheese(BlockInit.HERB_CHEESE.get(), ItemInit.HERB_CHEESE_SLICE.get());
    }

    protected void dropCheese(Block pBlock, Item pItem) {
        this.add(pBlock, createCheeseItemDispatchTable(pBlock, pItem));
    }

    protected LootTable.Builder createCheeseItemDispatchTable(Block pBlock, Item pItem) {
        LootItemCondition.Builder lootitemcondition0 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BasicCheese.BITES, 0));

        LootItemCondition.Builder lootitemcondition1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BasicCheese.BITES, 1));

        LootItemCondition.Builder lootitemcondition2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BasicCheese.BITES, 2));

        LootItemCondition.Builder lootitemcondition3 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BasicCheese.BITES, 3));

        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(4.0F))
                        .when(lootitemcondition0)
                        .add(LootItem.lootTableItem(pItem)))  // Drops 4 pieces if uneaten

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3.0F))
                        .when(lootitemcondition1)
                        .add(LootItem.lootTableItem(pItem)))  // Drops 3 pieces if 1 bite taken

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2.0F))
                        .when(lootitemcondition2)
                        .add(LootItem.lootTableItem(pItem)))  // Drops 2 pieces if 2 bites taken

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(lootitemcondition3)
                        .add(LootItem.lootTableItem(pItem))); // Drops 1 piece if 3 bites taken
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
