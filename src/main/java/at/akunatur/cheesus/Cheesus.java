package at.akunatur.cheesus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.akunatur.cheesus.core.init.BlockEntityTypesInit;
import at.akunatur.cheesus.core.init.BlockInit;
import at.akunatur.cheesus.core.init.ContainerTypesInit;
import at.akunatur.cheesus.core.init.ItemInit;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cheesus.MOD_ID)
public class Cheesus {

	public static final String MOD_ID = "cheesus";
	public static final Logger LOGGER = LogManager.getLogger();

	public static final CheesusItemGroup CHEESUS_TAB = new CheesusItemGroup("cheesus_tab");

	IEventBus forgeBus = MinecraftForge.EVENT_BUS;

	public Cheesus() {

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		BlockEntityTypesInit.TILE_ENTITY_TYPE.register(bus);
		ContainerTypesInit.CONTAINER_TYPES.register(bus);
	}

	private void setup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(BlockInit.CHEESE_COVER.get(), RenderType.translucent());
	}

	public static class CheesusItemGroup extends CreativeModeTab {

		public CheesusItemGroup(String label) {
			super(label);

		}

		@Override
		public ItemStack makeIcon() {
			return ItemInit.CHEESE_STRAINER.get().getDefaultInstance();
		}

	}
}