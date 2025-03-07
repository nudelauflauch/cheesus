package at.akunatur.cheesus;

import at.akunatur.cheesus.core.init.CheesusItemGroup;
import at.akunatur.cheesus.core.init.ItemInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.akunatur.cheesus.core.init.BlockEntityInit;
import at.akunatur.cheesus.core.init.BlockInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cheesus.MOD_ID)
public class Cheesus {

	public static final String MOD_ID = "cheesus";
	public static final Logger LOGGER = LogManager.getLogger();

	public static final CheesusItemGroup CHEESUS_TAB = new CheesusItemGroup(
			"cheesus_tab");

	IEventBus forgeBus = MinecraftForge.EVENT_BUS;

	public Cheesus() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);
		bus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);

		 ItemInit.ITEMS.register(bus);
		// BlockInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		BlockInit.BLOCK_ITEMS.register(bus);
		BlockEntityInit.BLOCK_ENTITY.register(bus);
	}

	private void setup(final FMLClientSetupEvent event) {
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}
}