package net.stehschnitzel.cheesus;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.stehschnitzel.cheesus.init.*;
import net.stehschnitzel.cheesus.renderer.CheeseCoverEntityRenderer;

import org.slf4j.Logger;

@Mod(Cheesus.MOD_ID)
public class Cheesus {

	public static final String MOD_ID = "cheesus";
	public static final Logger LOGGER = LogUtils.getLogger();

    public Cheesus(IEventBus bus, ModContainer modContainer) {
		bus.addListener(this::setup);
		bus.addListener(this::commonSetup);

		BlockInit.register(bus);
		ItemInit.register(bus);
		BlockEntityInit.register(bus);
		CheesusItemTabInit.register(bus);
		CheesusCriteriaInit.register(bus);
    
	}

	private void setup(final FMLClientSetupEvent event) {
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}

	@Mod(value = MOD_ID, dist = Dist.CLIENT)
	public static class ClientModEvents {

		@SubscribeEvent
		public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(BlockEntityInit.CHEESE_COVER.get(), CheeseCoverEntityRenderer::new);
		}
	}
}