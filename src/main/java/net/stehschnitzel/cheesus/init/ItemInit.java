package net.stehschnitzel.cheesus.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.stehschnitzel.cheesus.Cheesus;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stehschnitzel.cheesus.common.items.CheeseCoverItem;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cheesus.MOD_ID);

	//Cheese cover
	public static final RegistryObject<BlockItem> CHEESE_COVER = BlockInit.BLOCK_ITEMS.register("cheese_cover",
			() -> new CheeseCoverItem(BlockInit.CHEESE_COVER.get(), new Item.Properties()));

	// cheese_slices
	public static final RegistryObject<Item> CHEESE_SLICE = ITEMS.register("cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F)
							.build())));

	public static final RegistryObject<Item> ALTITUDE_CHEESE_SLICE = ITEMS.register("altitude_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F)
							.effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1), 1)
							.build())));

	public static final RegistryObject<Item> BLUE_MOLD_CHEESE_SLICE = ITEMS.register("blue_mold_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F)
							.effect(new MobEffectInstance(MobEffects.SATURATION, 200, 1), 1)
							.build())));

	public static final RegistryObject<Item> DIABOLICAL_CHEESE_SLICE = ITEMS.register("diabolical_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F)
							.effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1), 1)
							.build())));

	public static final RegistryObject<Item> GREY_CHEESE_SLICE = ITEMS.register("grey_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F)
							.effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1), 1)
							.build())));


	public static final RegistryObject<Item> WHITE_MOLD_CHEESE_SLICE = ITEMS.register("white_mold_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6F)
							.effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1)
							.build())));

	// things you can make with cheese
	public static final RegistryObject<Item> BAKED_CHEESE = ITEMS.register("baked_cheese",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(12).saturationMod(1f).build())));

//	public static final RegistryObject<Item> CHEESE_BREAD = ITEMS.register("cheese_bread",
//			() -> new Item(new Item.Properties()
//					.food(new FoodProperties.Builder().nutrition(3).saturationMod(0.9f).build())));

	public static final RegistryObject<Item> CHEESE_FONDUE = ITEMS.register("cheese_fondue",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).build())));

	public static final RegistryObject<Item> CHEESE_FROM_HELL = ITEMS.register("cheese_from_hell",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(5).saturationMod(0.7f)
							.effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 1), 1)
							.build())));

//	public static final RegistryObject<Item> GRAUKAS_KNEDL = ITEMS.register("graukas_knedl",
//			() -> new Item(new Item.Properties()
//					.food(new FoodProperties.Builder().nutrition(4).saturationMod(1.5f).build())));

	public static final RegistryObject<Item> GRAUKAS_SOUP = ITEMS.register("graukas_soup",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(5).saturationMod(0.8f)
							.build()).stacksTo(16)));

	public static final RegistryObject<Item> SCALLOPED_POTATO = ITEMS.register("scalloped_potato",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.9f).build())));

//	public static final RegistryObject<Item> SALMON_HERB_CHEESE = ITEMS.register("salmon_herb_cheese",
//			() -> new Item(new Item.Properties()
//					.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.8f).build())));

	public static final RegistryObject<Item> CHEESE_SUN = ITEMS
			.register("cheese_sun", () -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(5).saturationMod(0.9f).build())));

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}

}
