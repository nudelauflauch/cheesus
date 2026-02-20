package net.stehschnitzel.cheesus.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusTags;
import net.stehschnitzel.cheesus.init.ItemInit;

import java.util.concurrent.CompletableFuture;

public class CheesusItemTagProvider extends ItemTagsProvider {
    public CheesusItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture, Cheesus.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(CheesusTags.Items.CHEESE)
                .add(BlockInit.CHEESE.get().asItem())
                .add(BlockInit.ALTITUDE_CHEESE.get().asItem())
                .add(BlockInit.BLUE_MOLD_CHEESE.get().asItem())
                .add(BlockInit.DIABOLICAL_CHEESE.get().asItem())
                .add(BlockInit.GREY_CHEESE.get().asItem())
                .add(BlockInit.WHITE_MOLD_CHEESE.get().asItem());

        tag(CheesusTags.Items.CHEESE_SLICE)
                .add(ItemInit.CHEESE_SLICE.get().asItem())
                .add(ItemInit.ALTITUDE_CHEESE_SLICE.get().asItem())
                .add(ItemInit.BLUE_MOLD_CHEESE_SLICE.get().asItem())
                .add(ItemInit.DIABOLICAL_CHEESE_SLICE.get().asItem())
                .add(ItemInit.GREY_CHEESE_SLICE.get().asItem())
                .add(ItemInit.WHITE_MOLD_CHEESE_SLICE.get().asItem());
    }
}
