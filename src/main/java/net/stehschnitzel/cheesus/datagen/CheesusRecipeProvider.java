package net.stehschnitzel.cheesus.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusTags;
import net.stehschnitzel.cheesus.init.ItemInit;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

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

        //with farmers delight
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(BlockInit.CHEESE.get().asItem()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ItemInit.CHEESE_SLICE.get(), 4)
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "farmers_cheese_slice"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(BlockInit.ALTITUDE_CHEESE.get().asItem()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ItemInit.ALTITUDE_CHEESE_SLICE.get(), 4)
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "farmers_altitude_cheese_slice"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(BlockInit.BLUE_MOLD_CHEESE.get().asItem()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ItemInit.BLUE_MOLD_CHEESE_SLICE.get(), 4)
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "farmers_blue_mold_cheese_slice"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(BlockInit.DIABOLICAL_CHEESE.get().asItem()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ItemInit.DIABOLICAL_CHEESE_SLICE.get(), 4)
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "farmers_diabolical_cheese_slice"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(BlockInit.GREY_CHEESE.get().asItem()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ItemInit.GREY_CHEESE_SLICE.get(), 4)
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "farmers_grey_cheese_slice"));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(BlockInit.WHITE_MOLD_CHEESE.get().asItem()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ItemInit.WHITE_MOLD_CHEESE_SLICE.get(), 4)
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "farmers_white_mold_cheese_slice"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, BlockInit.CHEESE_CAKE.get())
                .define('E', CheesusTags.Items.CHEESE)
                .define('B', Items.SUGAR)
                .define('C', Items.WHEAT)
                .define('A', Items.EGG)
                .pattern("AAA")
                .pattern("BEB")
                .pattern("CCC")
                .unlockedBy("has_egg", has(Items.EGG)).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemInit.CHEESE_SUN.get())
                .define('E', ItemInit.WHITE_MOLD_CHEESE_SLICE.get())
                .define('C', Items.WHEAT)
                .pattern(" C ")
                .pattern("CEC")
                .pattern(" C ")
                .unlockedBy("has_wheat", has(Items.WHEAT)).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemInit.SCRAMBLED_EGGS.get())
                .requires(Items.EGG)
                .requires(Items.BOWL)
                .requires(CheesusTags.Items.CHEESE_SLICE)
                .unlockedBy("has_cheese_slice", has(CheesusTags.Items.CHEESE_SLICE))
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .unlockedBy(getHasName(Items.BOWL), has(Items.BOWL))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemInit.CHEESE_FONDUE.get(), 4)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .requires(Items.BOWL)
                        .requires(ItemInit.BAKED_CHEESE.get())
                        .requires(Items.BREAD)
                        .unlockedBy(getHasName(ItemInit.BAKED_CHEESE.get()), has(ItemInit.BAKED_CHEESE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemInit.GRAUKAS_SOUP.get(), 1)
                .requires(Items.BOWL)
                .requires(ItemInit.GREY_CHEESE_SLICE.get())
                .unlockedBy(getHasName(BlockInit.GREY_CHEESE.get()), has(BlockInit.GREY_CHEESE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemInit.SCALLOPED_POTATO.get())
                .define('C', ItemInit.CHEESE_SLICE.get())
                .define('E', Items.BAKED_POTATO)
                .pattern("C")
                .pattern("E")
                .unlockedBy(getHasName(BlockInit.CHEESE.get()), has(BlockInit.CHEESE.get()))
                .save(pWriter);

        cheeseCooking(Ingredient.of(CheesusTags.Items.CHEESE), ItemInit.BAKED_CHEESE.get(), BlockInit.CHEESE.get(), pWriter);

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemInit.DIABOLICAL_CHEESE_SLICE.get()), RecipeCategory.FOOD, ItemInit.CHEESE_FROM_HELL.get(),0.35F, 300)
                .unlockedBy(getHasName(ItemInit.DIABOLICAL_CHEESE_SLICE.get()), has(ItemInit.DIABOLICAL_CHEESE_SLICE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.CHEESE_COVER.get())
                .define('#', Items.GLASS)
                .define('X', ItemTags.PLANKS)
                .pattern("###")
                .pattern("# #")
                .pattern("XXX")
                .unlockedBy(getHasName(BlockInit.CHEESE.get()), has(BlockInit.CHEESE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.CHEESE_STRAINER.get())
                .define('X', Items.BRICK)
                .pattern("X X")
                .pattern("X X")
                .pattern("XXX")
                .unlockedBy(getHasName(BlockInit.CHEESE.get()), has(BlockInit.CHEESE.get()))
                .save(pWriter);

        //farmers delight
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemInit.GOURMET_CHEESE.get(), 4)
                .requires(Items.SWEET_BERRIES)
                .requires(BlockInit.WHITE_MOLD_CHEESE.get())
                .requires(ModItems.TOMATO_SAUCE.get())
                .unlockedBy(getHasName(BlockInit.WHITE_MOLD_CHEESE.get()), has(BlockInit.WHITE_MOLD_CHEESE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.LASAGNA.get())
                .define('C', CheesusTags.Items.CHEESE_SLICE)
                .define('T', ModItems.TOMATO_SAUCE.get())
                .define('B', ForgeTags.COOKED_BEEF)
                .define('P', ModItems.RAW_PASTA.get())
                .pattern(" CC")
                .pattern(" TB")
                .pattern(" PP")
                .unlockedBy(getHasName(BlockInit.CHEESE.get()), has(BlockInit.CHEESE.get()))
                .save(pWriter);

        CookingPotRecipeBuilder.cookingPotRecipe(ItemInit.SAVOURY_PASTA.get(), 1, 200, 1.0F, Items.BOWL)
                .addIngredient(ModItems.TOMATO.get())
                .addIngredient(ModItems.RAW_PASTA.get())
                .addIngredient(ItemInit.BLUE_MOLD_CHEESE_SLICE.get())
                .addIngredient(CheesusTags.Items.CHEESE_SLICE)
                .unlockedBy(getHasName(ItemInit.BLUE_MOLD_CHEESE_SLICE.get()), has(ItemInit.BLUE_MOLD_CHEESE_SLICE.get()))
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "savoury_pasta"));
    }

    private void cheeseCooking(Ingredient inputItem, ItemLike outputItem, ItemLike unlockedItem, Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.smelting(inputItem, RecipeCategory.FOOD, outputItem, 0.35F, 200).unlockedBy(getHasName(unlockedItem), has(unlockedItem))
                .save(pWriter, Cheesus.MOD_ID + ":" + getItemName(outputItem) + "_from_smelting");
        SimpleCookingRecipeBuilder.campfireCooking(inputItem, RecipeCategory.FOOD, outputItem, 0.35F, 600).unlockedBy(getHasName(unlockedItem), has(unlockedItem))
                .save(pWriter, Cheesus.MOD_ID + ":" + getItemName(outputItem) + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(inputItem, RecipeCategory.FOOD, outputItem, 0.35F, 100).unlockedBy(getHasName(unlockedItem), has(unlockedItem))
                .save(pWriter, Cheesus.MOD_ID + ":" + getItemName(outputItem) + "_from_smoking");
    }

    public void cheeseRecipe(ItemLike cheeseItem, ItemLike cheesePieceItem, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, cheeseItem)
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

        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(cheeseItem), Ingredient.of(ForgeTags.TOOLS_KNIVES), cheesePieceItem, 4).build(pWriter);
    }
}
