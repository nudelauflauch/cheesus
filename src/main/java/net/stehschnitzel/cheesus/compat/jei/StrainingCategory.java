package net.stehschnitzel.cheesus.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;

import java.util.List;

public class StrainingCategory implements IRecipeCategory<StrainingRecipe> {

    public static final RecipeType<StrainingRecipe> TYPE = RecipeType.create(Cheesus.MOD_ID, "straining", StrainingRecipe.class);

    private static final int WIDTH = 140;
    private static final int HEIGHT = 58;
    private static final int SLOT_SPACING = 19;

    private static final int STRAINER_X = 50;
    private static final int STRAINER_Y = 9;
    private static final int STRAINER_SIZE = 40;

    private static final int ARROW_X = 94;
    private static final int ARROW_Y = 20;
    private static final int OUTPUT_X = 122;

    //how long each strainer stage is shown for
    private static final int STAGE_MILLIS = 1000;

    private final IDrawable icon;

    public StrainingCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemLike(BlockInit.CHEESE_STRAINER.get());
    }

    @Override
    public RecipeType<StrainingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei." + Cheesus.MOD_ID + ".category.straining");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StrainingRecipe recipe, IFocusGroup focuses) {
        List<List<ItemStack>> inputs = recipe.inputs();
        int top = (HEIGHT - (inputs.size() * SLOT_SPACING - 1)) / 2 + 1;

        for (int i = 0; i < inputs.size(); i++) {
            builder.addInputSlot(1, top + i * SLOT_SPACING)
                    .setStandardSlotBackground()
                    .addItemStacks(inputs.get(i));
        }

        builder.addOutputSlot(OUTPUT_X, (HEIGHT - 16) / 2)
                .setOutputSlotBackground()
                .addItemStack(recipe.output());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, StrainingRecipe recipe, IFocusGroup focuses) {
        builder.addRecipeArrowWidget().setPosition(22, ARROW_Y);
        builder.addAnimatedRecipeArrowWidget(recipe.stages().size() * STAGE_MILLIS / 50)
                .setPosition(ARROW_X, ARROW_Y)
                .setTooltip(Component.translatable("jei." + Cheesus.MOD_ID + ".straining.time"));
        builder.addTooltipArea(STRAINER_X, STRAINER_Y, STRAINER_SIZE, STRAINER_SIZE)
                .setTooltip(List.of(
                        BlockInit.CHEESE_STRAINER.get().getName(),
                        Component.translatable("jei." + Cheesus.MOD_ID + ".straining.collect").withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public void draw(StrainingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        List<BlockState> stages = recipe.stages();
        BlockState stage = stages.get((int) (Util.getMillis() / STAGE_MILLIS % stages.size()));
        BlockStateDrawable.draw(guiGraphics, stage, STRAINER_X, STRAINER_Y, STRAINER_SIZE);
    }
}
