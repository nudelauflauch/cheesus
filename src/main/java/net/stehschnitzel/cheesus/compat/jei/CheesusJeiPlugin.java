package net.stehschnitzel.cheesus.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.ItemInit;

//only loaded by JEI, so nothing in here is touched when JEI isn't installed
@JeiPlugin
public class CheesusJeiPlugin implements IModPlugin {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        addInfo(registration, BlockInit.CHEESE_STRAINER.get(), "cheese_strainer");
        addInfo(registration, ItemInit.CHEESE_COVER.get(), "cheese_cover");
        addInfo(registration, BlockInit.CHEESE.get(), "cheese");
    }

    private static void addInfo(IRecipeRegistration registration, ItemLike item, String key) {
        registration.addIngredientInfo(item, Component.translatable("jei." + Cheesus.MOD_ID + ".info." + key));
    }
}
