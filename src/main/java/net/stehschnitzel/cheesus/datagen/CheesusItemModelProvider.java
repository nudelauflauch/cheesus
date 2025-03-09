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
        simpleItem(ItemInit.WHITE_MOLD_CHEESE_SLICE);
//        simpleItem(ItemInit.REFINED_CHEESE_SLICE);
        simpleItem(ItemInit.CAMEMBERT_SLICE);
        simpleItem(ItemInit.CHEDDAR_SLICE);
        simpleItem(ItemInit.DIABOLICAL_CHEESE_SLICE);
        simpleItem(ItemInit.ALPINE_CHEESE_SLICE);
        simpleItem(ItemInit.BLUE_CHEESE_SLICE);
        simpleItem(ItemInit.HERB_CHEESE_SLICE);
        simpleItem(ItemInit.BREADED_CAMBERT);
        simpleItem(ItemInit.CHEESE_BREAD);
        simpleItem(ItemInit.CHEESE_FROM_HELL);
        simpleItem(ItemInit.GRAUKAS_SOUP);
        simpleItem(ItemInit.SCOLLOPED_POTATO);
        simpleItem(ItemInit.SALMON_HERB_CHEESE);
        simpleItem(ItemInit.TARTE_DE_SOLEIL_AU_CAMEMBER);
        withExistingParent("cheese_cover", "cheesus:block/cheese_cover");
        withExistingParent("cheese_strainer", "cheesus:block/cheese_strainer_0");
        simpleItem(BlockInit.WHITE_MOLD_CHEESE);
//        simpleItem(BlockInit.REFINED_CHEESE);
        simpleItem(BlockInit.CAMEMBERT);
        simpleItem(BlockInit.CHEDDAR);
        simpleItem(BlockInit.DIABOLICAL_CHEESE);
        simpleItem(BlockInit.ALPINE_CHEESE);
        simpleItem(BlockInit.BLUE_CHEESE);
        simpleItem(BlockInit.HERB_CHEESE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<?> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Cheesus.MOD_ID,"item/" + item.getId().getPath()));
    }
}
