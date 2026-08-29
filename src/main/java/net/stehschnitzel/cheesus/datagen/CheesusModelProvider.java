package net.stehschnitzel.cheesus.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.common.blocks.EatableCheese;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.ItemInit;


public class CheesusModelProvider extends BlockStateProvider {

    public CheesusModelProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, Cheesus.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerStatesAndModels() {

        // Normal cheese blocks
        normalCheeseBuilder(BlockInit.CHEESE);
        normalCheeseBuilder(BlockInit.ALTITUDE_CHEESE);
        normalCheeseBuilder(BlockInit.BLUE_MOLD_CHEESE);
        normalCheeseBuilder(BlockInit.DIABOLICAL_CHEESE);

        // Small cheese blocks
        smallCheeseBuilder(BlockInit.GREY_CHEESE);
        smallCheeseBuilder(BlockInit.WHITE_MOLD_CHEESE);
        smallCheeseBuilder(BlockInit.CHEESECAKE);


        // Items
        generateSimpleItem(ItemInit.CHEESE_SLICE);
        generateSimpleItem(ItemInit.ALTITUDE_CHEESE_SLICE);
        generateSimpleItem(ItemInit.BLUE_MOLD_CHEESE_SLICE);
        generateSimpleItem(ItemInit.DIABOLICAL_CHEESE_SLICE);
        generateSimpleItem(ItemInit.GREY_CHEESE_SLICE);
        generateSimpleItem(ItemInit.WHITE_MOLD_CHEESE_SLICE);
        generateSimpleItem(ItemInit.CHEESECAKE_SLICE);

        generateSimpleItem(ItemInit.BAKED_CHEESE);
        generateSimpleItem(ItemInit.CHEESE_FONDUE);
//        generateSimpleItem(ItemInit.CHEESE_BREAD);
        generateSimpleItem(ItemInit.CHEESE_FROM_HELL);
//        generateSimpleItem(ItemInit.GRAUKAS_KNEDL);
        generateSimpleItem(ItemInit.GRAUKAS_SOUP);
        generateSimpleItem(ItemInit.SCALLOPED_POTATO);
//        generateSimpleItem(ItemInit.SALMON_HERB_CHEESE);
        generateSimpleItem(ItemInit.CHEESE_SUN);
        generateSimpleItem(ItemInit.SCRAMBLED_EGGS);
        generateSimpleItem(ItemInit.SAVOURY_PASTA);
        generateSimpleItem(ItemInit.GOURMET_CHEESE);
        generateSimpleItem(ItemInit.LASAGNA);


        // Flat block items
        itemModels().withExistingParent(
                BlockInit.CHEESE_COVER.getId().getPath(),
                modLoc("block/cheese_cover")
        );
        itemModels().withExistingParent(
                BlockInit.CHEESE_STRAINER.getId().getPath(),
                modLoc("block/cheese_strainer_0")
        );
    }


    private void generateSimpleItem(DeferredItem<? extends Item> item) {
        itemModels().basicItem(item.get());
    }


    private void normalCheeseBuilder(DeferredBlock<? extends Block> block) {
        cheeseBuilder(block, "normal_cheese");
    }


    private void smallCheeseBuilder(DeferredBlock<? extends Block> block) {
        cheeseBuilder(block, "small_cheese");
    }


    private void cheeseBuilder(
            DeferredBlock<? extends Block> deferredBlock,
            String parentName
    ) {
        Block block = deferredBlock.get();

        String blockName = deferredBlock.getId().getPath();

        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                deferredBlock.getId().getNamespace(),
                "block/" + blockName
        );


        ModelFile model0 = createCheeseModel(
                blockName + "_0",
                parentName + "_0",
                texture
        );

        ModelFile model1 = createCheeseModel(
                blockName + "_1",
                parentName + "_1",
                texture
        );

        ModelFile model2 = createCheeseModel(
                blockName + "_2",
                parentName + "_2",
                texture
        );

        ModelFile model3 = createCheeseModel(
                blockName + "_3",
                parentName + "_3",
                texture
        );


        // Select model based on EatableCheese.BITES.
        getVariantBuilder(block).forAllStates(state -> {

            int bites = state.getValue(EatableCheese.BITES);

            ModelFile model = switch (bites) {
                case 0 -> model0;
                case 1 -> model1;
                case 2 -> model2;
                default -> model3;
            };

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

        itemModels().basicItem(block.asItem());
    }


    private ModelFile createCheeseModel(
            String modelName,
            String parentName,
            ResourceLocation texture
    ) {
        return models()
                .withExistingParent(
                        modelName,
                        modLoc("block/" + parentName)
                )
                .texture("base", texture)
                .texture("particle", texture);
    }
}