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

public class CheesusLootTableProvider extends BlockLootSubProvider {
    protected CheesusLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlockInit.CHEESE_COVER.get());
        dropSelf(BlockInit.CHEESE_STRAINER.get());
        dropCheese(BlockInit.CHEESE.get(), ItemInit.CHEESE_SLICE.get());
        dropCheese(BlockInit.ALTITUDE_CHEESE.get(), ItemInit.ALTITUDE_CHEESE_SLICE.get());
        dropCheese(BlockInit.BLUE_MOLD_CHEESE.get(), ItemInit.BLUE_MOLD_CHEESE_SLICE.get());
        dropCheese(BlockInit.DIABOLICAL_CHEESE.get(), ItemInit.DIABOLICAL_CHEESE_SLICE.get());
        dropCheese(BlockInit.GREY_CHEESE.get(), ItemInit.GREY_CHEESE_SLICE.get());
        dropCheese(BlockInit.WHITE_MOLD_CHEESE.get(), ItemInit.WHITE_MOLD_CHEESE_SLICE.get());
        dropCheeseCake(BlockInit.CHEESE_CAKE.get());
    }

    protected void dropCheese(Block pBlock, Item pItem) {
        this.add(pBlock, createCheeseItemDispatchTable(pBlock, pItem));
    }

    protected void dropCheeseCake(Block pBlock) {
        LootItemCondition.Builder lootitemcondition0 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BasicCheese.BITES, 0));

        this.add(pBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(lootitemcondition0)
                        .add(LootItem.lootTableItem(pBlock))));
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
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(lootitemcondition0)
                        .when(HAS_SILK_TOUCH)
                        .add(LootItem.lootTableItem(pBlock)))

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(4.0F))
                        .when(lootitemcondition0.and(HAS_NO_SILK_TOUCH))
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
