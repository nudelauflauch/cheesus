package net.stehschnitzel.cheesus.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CheesusBlockTagProvider extends BlockTagsProvider {
    public CheesusBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Cheesus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockInit.CHEESE_STRAINER.get());
    }
}
