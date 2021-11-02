package at.akunatur.cheesus.core.init;

import at.akunatur.cheesus.Cheesus;
import at.akunatur.cheesus.common.te.CheeseStrainerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, Cheesus.MOD_ID);

	public static final RegistryObject<TileEntityType<CheeseStrainerTileEntity>> CHEESE_STRAINER_TILE_ENTITY = TILE_ENTITY_TYPE
			.register("cheese_strainer_tile_entity", () -> TileEntityType.Builder
					.create(CheeseStrainerTileEntity::new, BlockInit.CHEESE_STRAINER.get()).build(null));
}
