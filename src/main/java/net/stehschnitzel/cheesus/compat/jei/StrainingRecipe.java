package net.stehschnitzel.cheesus.compat.jei;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.common.blocks.CheeseStrainer;
import net.stehschnitzel.cheesus.init.BlockInit;

import java.util.ArrayList;
import java.util.List;

//inputs holds one list of alternatives per slot, stages are the strainer states shown while the recipe is displayed
public record StrainingRecipe(List<List<ItemStack>> inputs, List<BlockState> stages, ItemStack output) {

    public static List<StrainingRecipe> all() {
        List<ItemStack> milk = milkSources();

        return List.of(
                new StrainingRecipe(List.of(milk, milk, milk), strainerStages(0, 1, 2, 3, 4),
                        new ItemStack(BlockInit.CHEESE.get())),
                new StrainingRecipe(List.of(List.of(new ItemStack(BlockInit.CHEESE.get()))), strainerStages(5, 6),
                        new ItemStack(BlockInit.GREY_CHEESE.get()))
        );
    }

    //the milk cup and golden milk bucket only exist when their mods are installed
    private static List<ItemStack> milkSources() {
        List<ItemStack> milk = new ArrayList<>();
        milk.add(new ItemStack(Items.MILK_BUCKET));
        addIfPresent(milk, "minersdelight", "milk_cup");
        addIfPresent(milk, "caverns_and_chasms", "golden_milk_bucket");
        return milk;
    }

    private static void addIfPresent(List<ItemStack> stacks, String namespace, String path) {
        BuiltInRegistries.ITEM.getOptional(ResourceLocation.fromNamespaceAndPath(namespace, path))
                .ifPresent(item -> stacks.add(new ItemStack(item)));
    }

    private static List<BlockState> strainerStages(int... levels) {
        List<BlockState> stages = new ArrayList<>();
        for (int level : levels) {
            stages.add(BlockInit.CHEESE_STRAINER.get().defaultBlockState().setValue(CheeseStrainer.LEVEL, level));
        }
        return stages;
    }
}
