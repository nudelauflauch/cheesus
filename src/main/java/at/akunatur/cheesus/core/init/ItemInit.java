package at.akunatur.cheesus.core.init;

import at.akunatur.cheesus.Cheesus;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cheesus.MOD_ID);

	public static final RegistryObject<BlockItem> CHEESE_COVER = ITEMS.register("cheese_cover",
			() -> new BlockItem(BlockInit.CHEESE_COVER.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)));

	public static final RegistryObject<BlockItem> CHEESE_STRAINER = ITEMS.register("cheese_strainer",
			() -> new BlockItem(BlockInit.CHEESE_STRAINER.get(), new Item.Properties().tab(Cheesus.CHEESUS_TAB)));

	// cheese Items
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

	// cheese_pieces
	public static final RegistryObject<Item> PIECE_ALPINE_CHEESE = ITEMS.register("piece_alpine_cheese",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(1).saturationMod(1.7f / 4).build())));

	public static final RegistryObject<Item> PIECE_CAMAEMBERT = ITEMS.register("piece_camembert",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(1).saturationMod(1.7f / 4).build())));

	public static final RegistryObject<Item> PIECE_CHEDDAR = ITEMS.register("piece_cheddar",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(1).saturationMod(1.7f / 4).build())));

	public static final RegistryObject<Item> PIECE_DIABOLICAL_CHEESE = ITEMS.register("piece_diabolical_cheese",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(1).saturationMod(1.7f / 4).build())));

	public static final RegistryObject<Item> PIECE_GRAUKAS = ITEMS.register("piece_graukas",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(1).saturationMod(1.7f / 4).build())));

	public static final RegistryObject<Item> PIECE_HERB_CHEESE = ITEMS.register("piece_herb_cheese",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(1).saturationMod(1.7f / 4).build())));

	// things you can make with cheese
	public static final RegistryObject<Item> BREADED_CAMBERT = ITEMS.register("breaded_camembert",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(5).saturationMod(0.9f).build())));

	public static final RegistryObject<Item> CHEESE_BREAD = ITEMS.register("cheese_bread",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.9f).build())));

	public static final RegistryObject<Item> CHEESE_FROM_HELL = ITEMS.register("cheese_from_hell",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(1.7f).build())));

	public static final RegistryObject<Item> GRAUKAS_KNEDL = ITEMS.register("graukas_knedl",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.5f).build())));

	public static final RegistryObject<Item> GRAUKAS_SOUP = ITEMS.register("graukas_soup",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(5).saturationMod(0.8f).build()).stacksTo(16)));

	public static final RegistryObject<Item> SCOLLOPED_POTATO = ITEMS.register("scollop_potato",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.9f).build())));

	public static final RegistryObject<Item> SALMON_HERB_CHEESE = ITEMS.register("salmon_herb_cheese",
			() -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.8f).build())));

	public static final RegistryObject<Item> TARTE_DE_SOLEIL_AU_CAMEMBER = ITEMS
			.register("tarte_de_soleil_au_camembert", () -> new Item(new Item.Properties().tab(Cheesus.CHEESUS_TAB)
					.food(new FoodProperties.Builder().nutrition(5).saturationMod(1.9f).build())));

}
