package at.akunatur.cheesus.core.init;

import at.akunatur.cheesus.Cheesus;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cheesus.MOD_ID);

	public static final RegistryObject<BlockItem> CHEESE_COVER = ITEMS.register("cheese_cover",
			() -> new BlockItem(BlockInit.CHEESE_COVER.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)));

	public static final RegistryObject<BlockItem> CHEESE_STRAINER = ITEMS.register("cheese_strainer",
			() -> new BlockItem(BlockInit.CHEESE_STRAINER.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)));

	public static final RegistryObject<BlockItem> ALPINE_CHEESE = ITEMS.register("alpine_cheese",
			() -> new BlockItem(BlockInit.ALPINE_CHEESE.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.7f).build())));

	public static final RegistryObject<BlockItem> CAMAEMBERT = ITEMS.register("camembert",
			() -> new BlockItem(BlockInit.CAMEMBERT.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.7f).build())));

	public static final RegistryObject<BlockItem> CHEDDAR = ITEMS.register("cheddar",
			() -> new BlockItem(BlockInit.CHEDDAR.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.7f).build())));

	public static final RegistryObject<BlockItem> DIABOLICAL_CHEESE = ITEMS.register("diabolical_cheese",
			() -> new BlockItem(BlockInit.DIABOLICAL_CHEESE.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.7f).build())));

	public static final RegistryObject<BlockItem> GRAUKAS = ITEMS.register("graukas",
			() -> new BlockItem(BlockInit.GRAUKAS.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.7f).build())));

	public static final RegistryObject<BlockItem> HERB_CHEESE = ITEMS.register("herb_cheese",
			() -> new BlockItem(BlockInit.HERB_CHEESE.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.7f).build())));
}
