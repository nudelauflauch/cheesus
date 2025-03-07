package at.akunatur.cheesus.core.init;

import at.akunatur.cheesus.Cheesus;
import at.akunatur.cheesus.common.blocks.entities.CheeseStrainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITIES, Cheesus.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<CheeseStrainerBlockEntity>> CHEESE_STRAINER = BLOCK_ENTITY
			.register("cheese_strainer", () -> BlockEntityType.Builder
					.of(CheeseStrainerBlockEntity::new, BlockInit.CHEESE_STRAINER.get()).build(null));

}