package net.stehschnitzel.cheesus.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseCoverBlockEntity;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseStrainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BlockEntityInit {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister
			.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Cheesus.MOD_ID);

    public static final Supplier<BlockEntityType<CheeseStrainerBlockEntity>> CHEESE_STRAINER =
            BLOCK_ENTITY.register("cheese_strainer",
                    () -> BlockEntityType.Builder.of(
                            CheeseStrainerBlockEntity::new,
                            BlockInit.CHEESE_STRAINER.get()
                    ).build(null)
            );

    public static final Supplier<BlockEntityType<CheeseCoverBlockEntity>> CHEESE_COVER =
            BLOCK_ENTITY.register("cheese_cover",
                    () -> BlockEntityType.Builder.of(
                            CheeseCoverBlockEntity::new,
                            BlockInit.CHEESE_COVER.get()
                    ).build(null)
            );

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITY.register(eventBus);
	}

}