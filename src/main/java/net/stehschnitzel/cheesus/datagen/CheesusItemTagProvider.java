package net.stehschnitzel.cheesus.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusTags;
import net.stehschnitzel.cheesus.init.ItemInit;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CheesusItemTagProvider extends ItemTagsProvider {
    public CheesusItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, Cheesus.MOD_ID, existingFileHelper);
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
