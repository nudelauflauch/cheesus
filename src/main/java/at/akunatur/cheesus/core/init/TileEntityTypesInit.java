package at.akunatur.cheesus.core.init;

import at.akunatur.cheesus.Cheesus;
import at.akunatur.cheesus.common.te.CheeseStrainerTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {

	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITIES, Cheesus.MOD_ID);

	public static final RegistryObject<BlockEntityType<CheeseStrainerTileEntity>> CHEESE_STRAINER_TILE_ENTITY = TILE_ENTITY_TYPE
			.register("cheese_strainer_tile_entity", () -> BlockEntityType.Builder
					.of(CheeseStrainerTileEntity::new, BlockInit.CHEESE_STRAINER.get()).build(null));
}