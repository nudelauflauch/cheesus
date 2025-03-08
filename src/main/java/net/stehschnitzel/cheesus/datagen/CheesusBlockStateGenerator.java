package net.stehschnitzel.cheesus.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.common.blocks.BasicCheese;
import net.stehschnitzel.cheesus.init.BlockInit;

public class CheesusBlockStateGenerator extends BlockStateProvider {
    public CheesusBlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Cheesus.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cheeseBuilder(BlockInit.WHITE_MOLD_CHEESE);
//        cheeseBuilder(BlockInit.REFINED_CHEESE);
        cheeseBuilder(BlockInit.CAMEMBERT);
        cheeseBuilder(BlockInit.CHEDDAR);
        cheeseBuilder(BlockInit.DIABOLICAL_CHEESE);
        cheeseBuilder(BlockInit.ALPINE_CHEESE);
        cheeseBuilder(BlockInit.BLUE_CHEESE, "template_blue_cheese");
        cheeseBuilder(BlockInit.HERB_CHEESE);
    }

    private void cheeseBuilder(RegistryObject<? extends Block> block) {
        cheeseBuilder(block, "cheese_template");
    }

    private void cheeseBuilder(RegistryObject<? extends Block> block, String template_name) {
        String name = block.getId().getPath();
        ModelFile f0 = cheese(name, 0, template_name);
        ModelFile f1 = cheese(name, 1, template_name);
        ModelFile f2 = cheese(name, 2, template_name);
        ModelFile f3 = cheese(name, 3, template_name);

        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            int bit = state.getValue(BasicCheese.BITES);
            ModelFile model;

            switch (bit) {
                case 1: {model = f1; break;}
                case 2: {model = f2; break;}
                case 3: {model = f3; break;}
                default: model = f0;
            }

            return ConfiguredModel.builder().modelFile(model)
                    .build();
        });
    }

    public ModelBuilder cheese(String name, int cnt, String template_name) {
        ResourceLocation cheese_texture = new ResourceLocation(Cheesus.MOD_ID, "block/" + name);

        ModelBuilder model =  models().withExistingParent(name + "_" + cnt,
                        new ResourceLocation(Cheesus.MOD_ID, "block/" + template_name + "_" + cnt))
                .texture("texture", cheese_texture);
        return model;
    }
}
