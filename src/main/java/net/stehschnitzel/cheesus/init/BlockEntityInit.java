package net.stehschnitzel.cheesus.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseCoverBlockEntity;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseStrainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Cheesus.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<CheeseStrainerBlockEntity>> CHEESE_STRAINER = BLOCK_ENTITY
			.register("cheese_strainer", () -> BlockEntityType.Builder
					.of(CheeseStrainerBlockEntity::new, BlockInit.CHEESE_STRAINER.get()).build(null));

	public static final RegistryObject<BlockEntityType<CheeseCoverBlockEntity>> CHEESE_COVER = BLOCK_ENTITY
			.register("cheese_cover", () -> BlockEntityType.Builder
					.of(CheeseCoverBlockEntity::new, BlockInit.CHEESE_COVER.get()).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITY.register(eventBus);
	}

}