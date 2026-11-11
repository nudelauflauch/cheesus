package net.stehschnitzel.cheesus.events;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusPotionInit;

@EventBusSubscriber(modid = Cheesus.MOD_ID)
public class CheesusEvents {

    @SubscribeEvent
    public static void onBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity() instanceof ServerPlayer player &&
                event.getLevel().getBlockState(event.getPos()).is(BlockInit.CHEESE.get()) &&
                event.getItemStack().is(ItemTags.SWORDS)) {
            AdvancementHolder advancement = player.getServer()
                    .getAdvancements()
                    .get(ResourceLocation.fromNamespaceAndPath(
                            Cheesus.MOD_ID, "husbandry/blue_mold_cheese"));

            if (advancement != null) {
                player.getAdvancements().award(advancement, "sword_click");
            }
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, BlockInit.SILENT_CHEESE.asItem(), CheesusPotionInit.SILENT_POTION);
        builder.addMix(CheesusPotionInit.SILENT_POTION, Items.REDSTONE, CheesusPotionInit.LONG_SILENT_POTION);
    }
}
