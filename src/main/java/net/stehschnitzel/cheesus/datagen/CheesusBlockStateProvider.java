package net.stehschnitzel.cheesus.datagen;

import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.common.blocks.EatableCheese;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.ItemInit;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CheesusBlockStateProvider extends ModelProvider {
    public CheesusBlockStateProvider(PackOutput output) {
        super(output, Cheesus.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        CheesusBlockModelGenerator cheesusGenerator = new CheesusBlockModelGenerator(
                blockModels.blockStateOutput, blockModels.itemModelOutput, blockModels.modelOutput
        );

        cheesusGenerator.normalCheeseBuilder(BlockInit.CHEESE);
        cheesusGenerator.normalCheeseBuilder(BlockInit.ALTITUDE_CHEESE);
        cheesusGenerator.normalCheeseBuilder(BlockInit.BLUE_MOLD_CHEESE);
        cheesusGenerator.normalCheeseBuilder(BlockInit.DIABOLICAL_CHEESE);
        cheesusGenerator.smallCheeseBuilder(BlockInit.GREY_CHEESE);
        cheesusGenerator.smallCheeseBuilder(BlockInit.WHITE_MOLD_CHEESE);
        cheesusGenerator.smallCheeseBuilder(BlockInit.CHEESECAKE);


        //items
        generatesimpleItem(itemModels, ItemInit.CHEESE_SLICE);
        generatesimpleItem(itemModels, ItemInit.ALTITUDE_CHEESE_SLICE);
        generatesimpleItem(itemModels, ItemInit.BLUE_MOLD_CHEESE_SLICE);
        generatesimpleItem(itemModels, ItemInit.DIABOLICAL_CHEESE_SLICE);
        generatesimpleItem(itemModels, ItemInit.GREY_CHEESE_SLICE);
        generatesimpleItem(itemModels, ItemInit.WHITE_MOLD_CHEESE_SLICE);
        generatesimpleItem(itemModels, ItemInit.CHEESECAKE_SLICE);

        generatesimpleItem(itemModels, ItemInit.BAKED_CHEESE);
        generatesimpleItem(itemModels, ItemInit.CHEESE_FONDUE);
//        generatesimpleItem(itemModels, ItemInit.CHEESE_BREAD);
        generatesimpleItem(itemModels, ItemInit.CHEESE_FROM_HELL);
//        generatesimpleItem(itemModels, ItemInit.GRAUKAS_KNEDL);
        generatesimpleItem(itemModels, ItemInit.GRAUKAS_SOUP);
        generatesimpleItem(itemModels, ItemInit.SCALLOPED_POTATO);
//        generatesimpleItem(itemModels, ItemInit.SALMON_HERB_CHEESE);
        generatesimpleItem(itemModels, ItemInit.CHEESE_SUN);
        generatesimpleItem(itemModels, ItemInit.SCRAMBLED_EGGS);
        generatesimpleItem(itemModels, ItemInit.SAVOURY_PASTA);
        generatesimpleItem(itemModels, ItemInit.GOURMET_CHEESE);
        generatesimpleItem(itemModels, ItemInit.LASAGNA);
        blockModels.createFlatItemModel(BlockInit.CHEESE_COVER.get().asItem());
        blockModels.createFlatItemModel(BlockInit.CHEESE_STRAINER.get().asItem());

    }

    private void generatesimpleItem(ItemModelGenerators itemModels, DeferredItem<Item> item) {
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    class CheesusBlockModelGenerator extends BlockModelGenerators {

        public static final TextureSlot BASE = TextureSlot.create("base", TextureSlot.ALL);

        public static final ModelTemplate NORMAL_CHEESE_0 = createTemplate(
                "normal_cheese_0", "");

        public static final ModelTemplate NORMAL_CHEESE_1 = createTemplate(
                "normal_cheese_1", "");

        public static final ModelTemplate NORMAL_CHEESE_2 = createTemplate(
                "normal_cheese_2", "");

        public static final ModelTemplate NORMAL_CHEESE_3 = createTemplate(
                "normal_cheese_3", "");

        public static final ModelTemplate SMALL_CHEESE_0 = createTemplate(
                "small_cheese_0", "");

        public static final ModelTemplate SMALL_CHEESE_1 = createTemplate(
                "small_cheese_1", "");

        public static final ModelTemplate SMALL_CHEESE_2 = createTemplate(
                "small_cheese_2", "");

        public static final ModelTemplate SMALL_CHEESE_3 = createTemplate(
                "small_cheese_3", "");

        public CheesusBlockModelGenerator(Consumer<BlockModelDefinitionGenerator> blockStateOutput,
                                          ItemModelOutput itemModelOutput,
                                          BiConsumer<Identifier, ModelInstance> modelOutput) {
            super(blockStateOutput, itemModelOutput, modelOutput);
        }

        public static ModelTemplate createTemplate(String name, String suffix) {
            return new ModelTemplate(
                    Optional.of(ModelLocationUtils.decorateBlockModelLocation("cheesus:" + name)),
                    Optional.of(suffix),
                    TextureSlot.PARTICLE, BASE
            );
        }

        public void normalCheeseBuilder(DeferredBlock<?> block) {
            TextureMapping textureMapping = new TextureMapping()
                    .put(BASE, TextureMapping.getBlockTexture(block.get()))
                    .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block.get()));

            MultiVariant f0 = plainVariant(NORMAL_CHEESE_0.create(block.get(), textureMapping, modelOutput));
            MultiVariant f1 = plainVariant(NORMAL_CHEESE_1.create(block.get(), textureMapping, modelOutput));
            MultiVariant f2 = plainVariant(NORMAL_CHEESE_2.create(block.get(), textureMapping, modelOutput));
            MultiVariant f3 = plainVariant(NORMAL_CHEESE_3.create(block.get(), textureMapping, modelOutput));
            this.blockStateOutput.accept(
                    createCheese(block.get(), f0, f1, f2, f3)
            );

            this.registerSimpleItemModel(block.get(),
                    createFlatItemModel(block.get().asItem()));
        }

        public void smallCheeseBuilder(DeferredBlock<?> block) {
            TextureMapping textureMapping = new TextureMapping()
                    .put(BASE, TextureMapping.getBlockTexture(block.get()))
                    .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block.get()));

            MultiVariant f0 = plainVariant(SMALL_CHEESE_0.create(block.get(), textureMapping, modelOutput));
            MultiVariant f1 = plainVariant(SMALL_CHEESE_1.create(block.get(), textureMapping, modelOutput));
            MultiVariant f2 = plainVariant(SMALL_CHEESE_2.create(block.get(), textureMapping, modelOutput));
            MultiVariant f3 = plainVariant(SMALL_CHEESE_3.create(block.get(), textureMapping, modelOutput));
            this.blockStateOutput.accept(
                    createCheese(block.get(), f0, f1, f2, f3)
            );

            this.registerSimpleItemModel(block.get(),
                    createFlatItemModel(block.get().asItem()));
        }

        public static BlockModelDefinitionGenerator createCheese(
                Block block,
                MultiVariant f0,
                MultiVariant f1,
                MultiVariant f2,
                MultiVariant f3
        ) {
            return MultiVariantGenerator.dispatch(block)
                    .with(PropertyDispatch.initial(EatableCheese.BITES)
                            .generate(
                                    (bites) -> {
                                        MultiVariant model = null;
                                        switch (bites) {
                                            case 0:
                                                return f0;
                                            case 1:
                                                return f1;
                                            case 2:
                                                return f2;
                                            default:
                                                return f3;
                                        }
                                    }
                            )
                    );
        }
    }
}
