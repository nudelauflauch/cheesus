package at.akunatur.cheesus.datagen;

import at.akunatur.cheesus.Cheesus;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Cheesus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

//    @SubscribeEvent
//    public static void gatherData(GatherDataEvent event) throws IOException {
//        DataGenerator generator = event.getGenerator();
//        PackOutput packOutput = generator.getPackOutput();
//        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
//        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
//
//    }
}
