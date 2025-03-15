package net.stehschnitzel.cheesus.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.ItemInit;

public class CheesusItemModelProvider extends ItemModelProvider {
    public CheesusItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Cheesus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(BlockInit.CHEESE);
        simpleItem(BlockInit.ALTITUDE_CHEESE);
        simpleItem(BlockInit.BLUE_MOLD_CHEESE);
        simpleItem(BlockInit.DIABOLICAL_CHEESE);
        simpleItem(BlockInit.GREY_CHEESE);
        simpleItem(BlockInit.WHITE_MOLD_CHEESE);
        simpleItem(BlockInit.CHEESE_CAKE);

        simpleItem(ItemInit.CHEESE_SLICE);
        simpleItem(ItemInit.ALTITUDE_CHEESE_SLICE);
        simpleItem(ItemInit.BLUE_MOLD_CHEESE_SLICE);
        simpleItem(ItemInit.DIABOLICAL_CHEESE_SLICE);
        simpleItem(ItemInit.GREY_CHEESE_SLICE);
        simpleItem(ItemInit.WHITE_MOLD_CHEESE_SLICE);

        simpleItem(ItemInit.BAKED_CHEESE);
        simpleItem(ItemInit.CHEESE_FONDUE);
//        simpleItem(ItemInit.CHEESE_BREAD);
        simpleItem(ItemInit.CHEESE_FROM_HELL);
//        simpleItem(ItemInit.GRAUKAS_KNEDL);
        simpleItem(ItemInit.GRAUKAS_SOUP);
        simpleItem(ItemInit.SCALLOPED_POTATO);
//        simpleItem(ItemInit.SALMON_HERB_CHEESE);
        simpleItem(ItemInit.CHEESE_SUN);
        withExistingParent("cheese_cover", "cheesus:block/cheese_cover");
        withExistingParent("cheese_strainer", "cheesus:block/cheese_strainer_0");

    }

    private ItemModelBuilder simpleItem(RegistryObject<?> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Cheesus.MOD_ID,"item/" + item.getId().getPath()));
    }
}
