package net.stehschnitzel.cheesus;

import com.mojang.logging.LogUtils;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.stehschnitzel.cheesus.init.*;
import net.stehschnitzel.cheesus.renderer.CheeseCoverEntityRenderer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Cheesus.MOD_ID)
public class Cheesus {

	public static final String MOD_ID = "cheesus";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Cheesus() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
//		bus.addListener(this::setup);
//		bus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);

		BlockInit.register(bus);
		ItemInit.register(bus);
		BlockEntityInit.register(bus);
		CheesusItemTabInit.register(bus);
//		CheesusCriteriaInit.register(bus);
    
	}

	private void setup(final FMLClientSetupEvent event) {
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}

//	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {

//		@SubscribeEvent
		public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(BlockEntityInit.CHEESE_COVER.get(), CheeseCoverEntityRenderer::new);

		}
	}
}