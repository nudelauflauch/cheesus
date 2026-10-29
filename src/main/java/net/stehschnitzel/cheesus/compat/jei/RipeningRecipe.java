package net.stehschnitzel.cheesus.compat.jei;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

//ground is the block the cheese is shown on to hint at where it has to be placed, tool is used on the cheese instead
public record RipeningRecipe(ItemStack output, @Nullable BlockState ground, Ingredient tool, List<Component> conditions) {

    //mirrors BasicCheese#randomTick and BasicCheese#useItemOn
    public static List<RipeningRecipe> all() {
        return List.of(
                placed(BlockInit.ALTITUDE_CHEESE.get(), Blocks.SNOW_BLOCK, "altitude"),
                placed(BlockInit.WHITE_MOLD_CHEESE.get(), Blocks.COBBLESTONE, "white_mold", "white_mold.biome"),
                placed(BlockInit.SILENT_CHEESE.get(), Blocks.SCULK, "silent", "silent.light"),
                placed(BlockInit.DIABOLICAL_CHEESE.get(), Blocks.NETHERRACK, "diabolical"),
                new RipeningRecipe(new ItemStack(BlockInit.BLUE_MOLD_CHEESE.get()), null, Ingredient.of(ItemTags.SWORDS),
                        List.of(condition("blue_mold")))
        );
    }

    private static RipeningRecipe placed(Block output, Block ground, String... conditions) {
        return new RipeningRecipe(new ItemStack(output), ground.defaultBlockState(), Ingredient.EMPTY,
                Arrays.stream(conditions).map(RipeningRecipe::condition).toList());
    }

    private static Component condition(String key) {
        return Component.translatable("jei." + Cheesus.MOD_ID + ".ripening." + key);
    }
}
