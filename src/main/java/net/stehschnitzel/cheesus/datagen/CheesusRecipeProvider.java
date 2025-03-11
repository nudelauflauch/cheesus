package net.stehschnitzel.cheesus.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.ItemInit;

import java.util.function.Consumer;

public class CheesusRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public CheesusRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        cheeseRecipe(BlockInit.CHEESE.get(), ItemInit.CHEESE_SLICE.get(), pWriter);
        cheeseRecipe(BlockInit.ALTITUDE_CHEESE.get(), ItemInit.ALTITUDE_CHEESE_SLICE.get(), pWriter);
        cheeseRecipe(BlockInit.BLUE_MOLD_CHEESE.get(), ItemInit.BLUE_MOLD_CHEESE_SLICE.get(), pWriter);
        cheeseRecipe(BlockInit.DIABOLICAL_CHEESE.get(), ItemInit.DIABOLICAL_CHEESE_SLICE.get(), pWriter);
        cheeseRecipe(BlockInit.GREY_CHEESE.get(), ItemInit.GREY_CHEESE_SLICE.get(), pWriter);
        cheeseRecipe(BlockInit.WHITE_MOLD_CHEESE.get(), ItemInit.WHITE_MOLD_CHEESE_SLICE.get(), pWriter);
    }

    private void cheeseRecipe(ItemLike cheeseItem, ItemLike cheesePieceItem, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, cheeseItem, 2)
                .pattern("AA")
                .pattern("AA")
                .define('A', cheesePieceItem)
                .unlockedBy(getHasName(cheesePieceItem), has(cheesePieceItem))
                .unlockedBy(getHasName(cheeseItem), has(cheeseItem))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, cheesePieceItem, 4)
                .requires(cheeseItem)
                .unlockedBy(getHasName(cheeseItem), has(cheeseItem))
                .unlockedBy(getHasName(cheesePieceItem), has(cheesePieceItem))
                .save(pWriter);
    }

}
