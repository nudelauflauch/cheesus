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
					.icon(() -> new ItemStack(BlockInit.CHEESE.get()))
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
		 items.accept(BlockInit.WHITE_MOLD_CHEESE.get());
	}

	private static void registerCheese(CreativeModeTab.Output items) {
		items.accept(BlockInit.CHEESE.get());
		items.accept(BlockInit.ALTITUDE_CHEESE.get());
		items.accept(BlockInit.BLUE_MOLD_CHEESE.get());
		items.accept(BlockInit.DIABOLICAL_CHEESE.get());
		items.accept(BlockInit.GREY_CHEESE.get());
		items.accept(BlockInit.WHITE_MOLD_CHEESE.get());
	}

	private static void registerCheesePieces(CreativeModeTab.Output items) {
		items.accept(ItemInit.CHEESE_SLICE.get());
		items.accept(ItemInit.ALTITUDE_CHEESE_SLICE.get());
		items.accept(ItemInit.BLUE_MOLD_CHEESE_SLICE.get());
		items.accept(ItemInit.DIABOLICAL_CHEESE_SLICE.get());
		items.accept(ItemInit.GREY_CHEESE_SLICE.get());
		items.accept(ItemInit.WHITE_MOLD_CHEESE_SLICE.get());
	}

	private static void registerTools(CreativeModeTab.Output items) {
		items.accept(BlockInit.CHEESE_COVER.get());
		items.accept(BlockInit.CHEESE_STRAINER.get());
	}

	private static void registerFood(CreativeModeTab.Output items) {
		items.accept(BlockInit.CHEESE_CAKE.get());
		items.accept(ItemInit.BAKED_CHEESE.get());
		items.accept(ItemInit.CHEESE_FONDUE.get());
//		items.accept(ItemInit.CHEESE_BREAD.get());
		items.accept(ItemInit.CHEESE_FROM_HELL.get());
//		items.accept(ItemInit.GRAUKAS_KNEDL.get());
		items.accept(ItemInit.GRAUKAS_SOUP.get());
		items.accept(ItemInit.SCALLOPED_POTATO.get());
//		items.accept(ItemInit.SALMON_HERB_CHEESE.get());
		items.accept(ItemInit.CHEESE_SUN.get());
	}
}
