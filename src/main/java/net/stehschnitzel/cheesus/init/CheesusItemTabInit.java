package net.stehschnitzel.cheesus.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.stehschnitzel.cheesus.Cheesus;

public class CheesusItemTabInit {

	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cheesus.MOD_ID);

	public static final RegistryObject<CreativeModeTab> CHEESUS_TAB = TABS.register("cheesus_tab",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.cheesus_tab"))
					.icon(() -> new ItemStack(BlockInit.CHEDDAR.get()))
					.displayItems((pParameters, pOutput) -> {
						CheesusItemTabInit.fillItemList(pOutput);
					})
					.build()
	);

	public static void fillItemList(CreativeModeTab.Output items) {
		 registerTools(items);
		 registerCheese(items);
		 registerCheesePieces(items);
		 registerFood(items);
		 items.accept(BlockInit.REFINED_CHEESE.get());
		 items.accept(BlockInit.WHITE_MOLD_CHEESE.get());
	}

	private static void registerCheese(CreativeModeTab.Output items) {
		items.accept(BlockInit.ALPINE_CHEESE.get());
		items.accept(BlockInit.CAMEMBERT.get());
		items.accept(BlockInit.CHEDDAR.get());
		items.accept(BlockInit.DIABOLICAL_CHEESE.get());
		items.accept(BlockInit.BLUE_CHEESE.get());
		items.accept(BlockInit.HERB_CHEESE.get());
	}

	private static void registerCheesePieces(CreativeModeTab.Output items) {
		items.accept(ItemInit.ALPINE_CHEESE_SLICE.get());
		items.accept(ItemInit.CAMEMBERT_SLICE.get());
		items.accept(ItemInit.CHEDDAR_SLICE.get());
		items.accept(ItemInit.DIABOLICAL_CHEESE_SLICE.get());
		items.accept(ItemInit.BLUE_CHEESE_SLICE.get());
		items.accept(ItemInit.HERB_CHEESE_SLICE.get());
	}

	private static void registerTools(CreativeModeTab.Output items) {
		items.accept(BlockInit.CHEESE_COVER.get());
		items.accept(BlockInit.CHEESE_STRAINER.get());
	}

	private static void registerFood(CreativeModeTab.Output items) {
		items.accept(ItemInit.BREADED_CAMBERT.get());
		items.accept(ItemInit.CHEESE_BREAD.get());
		items.accept(ItemInit.CHEESE_FROM_HELL.get());
		items.accept(ItemInit.GRAUKAS_KNEDL.get());
		items.accept(ItemInit.GRAUKAS_SOUP.get());
		items.accept(ItemInit.SCOLLOPED_POTATO.get());
		items.accept(ItemInit.SALMON_HERB_CHEESE.get());
		items.accept(ItemInit.TARTE_DE_SOLEIL_AU_CAMEMBER.get());
	}
}
