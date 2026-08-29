package net.stehschnitzel.cheesus.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CheesusAdvancementProvider extends AdvancementProvider {
   public CheesusAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new CheesusAdvancementGenerator()));
   }
}
