package net.stehschnitzel.cheesus.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import net.stehschnitzel.cheesus.common.blocks.CheeseStrainer;
import net.stehschnitzel.cheesus.common.blocks.EatableCheese;
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
        this.add(BlockInit.CHEESE_STRAINER.get(), createCheeseStrainerDispatchTable());
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
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EatableCheese.BITES, 0));

        this.add(pBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(lootitemcondition0)
                        .add(LootItem.lootTableItem(pBlock))));
    }

    public LootTable.Builder createCheeseStrainerDispatchTable() {
        // Always drop cheese_strainer
        LootPool.Builder alwaysDropStrainer = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BlockInit.CHEESE_STRAINER.get()));

        // Drop cheese if level = 4
        LootPool.Builder cheeseIfLevel4 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BlockInit.CHEESE.get())
                        .when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(BlockInit.CHEESE_STRAINER.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(CheeseStrainer.LEVEL, 4))));

        // Drop cheese if level = 5
        LootPool.Builder cheeseIfLevel5 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BlockInit.CHEESE.get())
                        .when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(BlockInit.CHEESE_STRAINER.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(CheeseStrainer.LEVEL, 5))));

        // Drop grey_cheese if level = 6
        LootPool.Builder greyCheeseIfLevel6 = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BlockInit.GREY_CHEESE.get())
                        .when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(BlockInit.CHEESE_STRAINER.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(CheeseStrainer.LEVEL, 6))));

        return LootTable.lootTable()
                .withPool(alwaysDropStrainer)
                .withPool(cheeseIfLevel4)
                .withPool(cheeseIfLevel5)
                .withPool(greyCheeseIfLevel6);
    }

    public LootTable.Builder createCheeseItemDispatchTable(Block pBlock, Item pItem) {
        LootItemCondition.Builder lootItemCondition0 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EatableCheese.BITES, 0));

        LootItemCondition.Builder lootItemCondition1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EatableCheese.BITES, 1));

        LootItemCondition.Builder lootItemCondition2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EatableCheese.BITES, 2));

        LootItemCondition.Builder lootItemCondition3 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EatableCheese.BITES, 3));

        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(lootItemCondition0)
                        .add(LootItem.lootTableItem(pBlock)))

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3.0F))
                        .when(lootItemCondition1)
                        .add(LootItem.lootTableItem(pItem)))  // Drops 3 pieces if 1 bite taken

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2.0F))
                        .when(lootItemCondition2)
                        .add(LootItem.lootTableItem(pItem)))  // Drops 2 pieces if 2 bites taken

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(lootItemCondition3)
                        .add(LootItem.lootTableItem(pItem))); // Drops 1 piece if 3 bites taken
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
