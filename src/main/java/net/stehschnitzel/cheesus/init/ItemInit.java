package net.stehschnitzel.cheesus.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.stehschnitzel.cheesus.Cheesus;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.stehschnitzel.cheesus.common.items.CheeseCoverItem;

public class ItemInit {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Cheesus.MOD_ID);

	//Cheese cover
	public static final DeferredItem<BlockItem> CHEESE_COVER = BlockInit.BLOCK_ITEMS.register("cheese_cover",
			() -> new CheeseCoverItem(BlockInit.CHEESE_COVER.get(), new Item.Properties()));

	// cheese_slices
	public static final DeferredItem<Item> CHEESE_SLICE = ITEMS.register("cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F)
							.build())));

	public static final DeferredItem<Item> ALTITUDE_CHEESE_SLICE = ITEMS.register("altitude_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build(),
                            Consumable.builder()
                            .onConsume(new ApplyStatusEffectsConsumeEffect(
                                    new MobEffectInstance(MobEffects.RESISTANCE, 200, 0),
                                    1.0F
                            )).build()
                    )));


	public static final DeferredItem<Item> BLUE_MOLD_CHEESE_SLICE = ITEMS.register("blue_mold_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build(),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                            new MobEffectInstance(MobEffects.SATURATION, 200, 0),
                            1.0F
                    )).build()
                    )));


	public static final DeferredItem<Item> DIABOLICAL_CHEESE_SLICE = ITEMS.register("diabolical_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build(),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0),
                                            1.0F
                                    )).build()
                    )));

	public static final DeferredItem<Item> GREY_CHEESE_SLICE = ITEMS.register("grey_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build(),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.STRENGTH, 200, 0),
                                            1.0F
                                    )).build()
                    )));


	public static final DeferredItem<Item> WHITE_MOLD_CHEESE_SLICE = ITEMS.register("white_mold_cheese_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F).build(),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.REGENERATION, 200, 0),
                                            1.0F
                                    )).build()
                    )));

    public static final DeferredItem<Item> CHEESECAKE_SLICE = ITEMS.register("cheesecake_slice",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(1.0F)
                            .build())));

	// things you can make with cheese
	public static final DeferredItem<Item> BAKED_CHEESE = ITEMS.register("baked_cheese",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.5f).build())));

//	public static final DeferredItem<Item> CHEESE_BREAD = ITEMS.register("cheese_bread",
//			() -> new Item(new Item.Properties()
//					.food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.9f).build())));

	public static final DeferredItem<Item> CHEESE_FONDUE = ITEMS.register("cheese_fondue",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(6).saturationModifier(1.2f).build())));

	public static final DeferredItem<Item> CHEESE_FROM_HELL = ITEMS.register("cheese_from_hell",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.7f).build(),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0),
                                            1.0F
                                    )).build()
                    )));

//	public static final DeferredItem<Item> GRAUKAS_KNEDL = ITEMS.register("graukas_knedl",
//			() -> new Item(new Item.Properties()
//					.food(new FoodProperties.Builder().nutrition(4).saturationModifier(1.5f).build())));

	public static final DeferredItem<Item> GRAUKAS_SOUP = ITEMS.register("graukas_soup",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(9).saturationModifier(0.8f).build(),
                            Consumable.builder()
                                    .onConsume(new ApplyStatusEffectsConsumeEffect(
                                            new MobEffectInstance(MobEffects.STRENGTH, 400, 0),
                                            1.0F
                                    )).build()
                    ).usingConvertsTo(Items.BOWL)
            ));

	public static final DeferredItem<Item> SCALLOPED_POTATO = ITEMS.register("scalloped_potato",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(7).saturationModifier(1.0f).build())));

//	public static final DeferredItem<Item> SALMON_HERB_CHEESE = ITEMS.register("salmon_herb_cheese",
//			() -> new Item(new Item.Properties()
//					.food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.8f).build())));

	public static final DeferredItem<Item> CHEESE_SUN = ITEMS
			.register("cheese_sun", () -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(7).saturationModifier(0.9f).build())));

    public static final DeferredItem<Item> SCRAMBLED_EGGS = ITEMS
            .register("scrambled_eggs", () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.2f).build())
                    .usingConvertsTo(Items.BOWL)
            ));

	//farmers delight items
	public static final DeferredItem<Item> SAVOURY_PASTA = ITEMS
			.register("savoury_pasta", () -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.7f).build())
                    .usingConvertsTo(Items.BOWL)
            ));

	public static final DeferredItem<Item> GOURMET_CHEESE = ITEMS
			.register("gourmet_cheese", () -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.3f).build())));

	public static final DeferredItem<Item> LASAGNA = ITEMS
			.register("lasagna", () -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.5f).build())));

	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}

}