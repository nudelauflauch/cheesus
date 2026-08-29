package net.stehschnitzel.cheesus.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.internal.NeoForgeAdvancementProvider;

import java.util.concurrent.CompletableFuture;

public class CheesusAdvancementProvider extends NeoForgeAdvancementProvider {
   public CheesusAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
   }
}
