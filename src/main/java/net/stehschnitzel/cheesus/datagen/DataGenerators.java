package net.stehschnitzel.cheesus.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.stehschnitzel.cheesus.Cheesus;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Cheesus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) throws IOException {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new CheesusBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), LootTableProvider.create(packOutput));
        generator.addProvider(event.includeClient(), new CheesusItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new CheesusRecipeProvider(packOutput));

        BlockTagsProvider blockTagsProvider = new CheesusBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new CheesusItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
    }
}
