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
import net.stehschnitzel.cheesus.common.blocks.EatableCheese;
import net.stehschnitzel.cheesus.init.BlockInit;

public class CheesusBlockStateProvider extends BlockStateProvider {
    public CheesusBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Cheesus.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        normalCheeseBuilder(BlockInit.CHEESE);
        normalCheeseBuilder(BlockInit.ALTITUDE_CHEESE);
        normalCheeseBuilder(BlockInit.BLUE_MOLD_CHEESE);
        normalCheeseBuilder(BlockInit.DIABOLICAL_CHEESE);
        smallCheeseBuilder(BlockInit.GREY_CHEESE);
        smallCheeseBuilder(BlockInit.WHITE_MOLD_CHEESE);
        smallCheeseBuilder(BlockInit.CHEESE_CAKE);
    }

    public void smallCheeseBuilder(RegistryObject<? extends Block> block) {
        cheeseBuilder(block, "small_cheese");
    }

    public void normalCheeseBuilder(RegistryObject<? extends Block> block) {
        cheeseBuilder(block, "normal_cheese");
    }

    public void cheeseBuilder(RegistryObject<? extends Block> block, String template_name) {
        String name = block.getId().getPath();
        ModelFile f0 = cheese(name, 0, template_name);
        ModelFile f1 = cheese(name, 1, template_name);
        ModelFile f2 = cheese(name, 2, template_name);
        ModelFile f3 = cheese(name, 3, template_name);

        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            int bit = state.getValue(EatableCheese.BITES);
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
        ResourceLocation cheese_texture = ResourceLocation.tryBuild(Cheesus.MOD_ID, "block/" + name);
        ResourceLocation cheese_particle = ResourceLocation.tryBuild(Cheesus.MOD_ID, "item/" + name);

        ModelBuilder model =  models().withExistingParent(name + "_" + cnt,
                        ResourceLocation.tryBuild(Cheesus.MOD_ID, "block/" + template_name + "_" + cnt))
                .texture("texture", cheese_texture)
                .texture("particle", cheese_particle);
        return model;
    }
}
