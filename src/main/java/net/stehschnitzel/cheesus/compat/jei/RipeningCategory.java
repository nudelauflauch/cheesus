package net.stehschnitzel.cheesus.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;

import java.util.List;

public class RipeningCategory implements IRecipeCategory<RipeningRecipe> {

    public static final RecipeType<RipeningRecipe> TYPE = RecipeType.create(Cheesus.MOD_ID, "ripening", RipeningRecipe.class);

    private static final int WIDTH = 160;
    private static final int HEIGHT = 62;

    //the top row is 116 wide and centered, the conditions go below it
    private static final int LEFT = (WIDTH - 116) / 2;
    private static final int SLOT_Y = 11;
    private static final int DIORAMA_X = LEFT + 26;
    private static final int DIORAMA_SIZE = 36;
    private static final int ARROW_X = LEFT + 68;
    private static final int ARROW_Y = 10;
    private static final int TEXT_Y = 40;

    private static final int TEXT_COLOR = 0xFF404040;

    private final IDrawable icon;

    public RipeningCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemLike(BlockInit.BLUE_MOLD_CHEESE.get());
    }

    @Override
    public RecipeType<RipeningRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei." + Cheesus.MOD_ID + ".category.ripening");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RipeningRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(LEFT + 1, SLOT_Y)
                .setStandardSlotBackground()
                .addItemStack(new ItemStack(BlockInit.CHEESE.get()));

        if (!recipe.tool().isEmpty()) {
            builder.addInputSlot(LEFT + 38, SLOT_Y)
                    .setStandardSlotBackground()
                    .addIngredients(recipe.tool());
        }

        builder.addOutputSlot(LEFT + 98, SLOT_Y)
                .setOutputSlotBackground()
                .addItemStack(recipe.output());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RipeningRecipe recipe, IFocusGroup focuses) {
        if (recipe.ground() != null) {
            builder.addTooltipArea(DIORAMA_X, 0, DIORAMA_SIZE, DIORAMA_SIZE)
                    .setTooltip(Component.translatable("jei." + Cheesus.MOD_ID + ".ripening.place"));
            builder.addAnimatedRecipeArrowWidget(100)
                    .setPosition(ARROW_X, ARROW_Y)
                    .setTooltip(Component.translatable("jei." + Cheesus.MOD_ID + ".ripening.time"));
        } else {
            builder.addRecipePlusSignWidget().setPosition(LEFT + 21, SLOT_Y + 2);
            builder.addRecipeArrowWidget().setPosition(ARROW_X, ARROW_Y);
        }

        builder.addText(List.<FormattedText>copyOf(recipe.conditions()), WIDTH, HEIGHT - TEXT_Y)
                .setPosition(0, TEXT_Y)
                .setTextAlignment(HorizontalAlignment.CENTER)
                .setColor(TEXT_COLOR);
    }

    @Override
    public void draw(RipeningRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (recipe.ground() != null) {
            BlockStateDrawable.drawColumn(guiGraphics,
                    List.of(recipe.ground(), BlockInit.CHEESE.get().defaultBlockState()), DIORAMA_X, 0, DIORAMA_SIZE);
        }
    }
}
