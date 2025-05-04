package net.stehschnitzel.cheesus.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.common.blocks.CheeseStrainer;

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

	public static void register(IEventBus bus) {
		TABS.register(bus);
	}

	public static void fillItemList(CreativeModeTab.Output items) {
		 registerTools(items);
		 registerCheese(items);
		 registerCheesePieces(items);
		 registerFood(items);


		 registerDispensable();
	}


	//TODO has to be registered here because if i would register it normaly CHEESE would be none
	// because it gets called to early so idk how to fix it and make it look good
	public static void registerDispensable() {
		DispenserBlock.registerBehavior(BlockInit.CHEESE_STRAINER.get(), CheeseStrainer.DISPENSE_CHEESE_STRAINER_BEHAVIOR);
		DispenserBlock.registerBehavior(Items.MILK_BUCKET, CheeseStrainer.DISPENSE_INTO_CHEESE_STRAINER_BEHAVIOR);
		DispenserBlock.registerBehavior(Items.WATER_BUCKET, CheeseStrainer.DISPENSE_INTO_CHEESE_STRAINER_BEHAVIOR);
		DispenserBlock.registerBehavior(BlockInit.CHEESE.get(), CheeseStrainer.DISPENSE_INTO_CHEESE_STRAINER_BEHAVIOR);
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
		items.accept(BlockInit.CHEESE_STRAINER.get());
		items.accept(ItemInit.CHEESE_COVER.get());
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
