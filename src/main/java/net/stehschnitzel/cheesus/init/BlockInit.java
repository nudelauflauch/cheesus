package net.stehschnitzel.cheesus.init;

import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.stehschnitzel.cheesus.common.blocks.*;
import com.google.common.base.Supplier;

import net.stehschnitzel.cheesus.Cheesus;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister
			.create(ForgeRegistries.ITEMS, Cheesus.MOD_ID);

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister
			.create(ForgeRegistries.BLOCKS, Cheesus.MOD_ID);

	//cheese
	public static final RegistryObject<BasicCheese> CHEESE = registerBlockWItem("cheese", () -> new BasicCheese(
			BlockBehaviour.Properties.of().strength(0.8f, 1.5f).sound(SoundType.CORAL_BLOCK)));

	public static final RegistryObject<EatableCheese> ALTITUDE_CHEESE = registerBlockWItem("altitude_cheese", () -> new EatableCheese(
			BlockBehaviour.Properties.copy(CHEESE.get()), MobEffects.DAMAGE_RESISTANCE));

	public static final RegistryObject<EatableCheese> BLUE_MOLD_CHEESE = registerBlockWItem("blue_mold_cheese", () -> new EatableCheese(
			BlockBehaviour.Properties.copy(CHEESE.get()), MobEffects.SATURATION));

	public static final RegistryObject<EatableCheese> DIABOLICAL_CHEESE = registerBlockWItem("diabolical_cheese",
			() -> new EatableCheese(
					BlockBehaviour.Properties.copy(CHEESE.get()), MobEffects.FIRE_RESISTANCE));

	public static final RegistryObject<EatableCheese> GREY_CHEESE = registerBlockWItem("grey_cheese", () -> new SmallCheese(
			BlockBehaviour.Properties.copy(CHEESE.get()), MobEffects.DAMAGE_BOOST));

	public static final RegistryObject<EatableCheese> WHITE_MOLD_CHEESE = registerBlockWItem("white_mold_cheese", () -> new SmallCheese(
			BlockBehaviour.Properties.copy(CHEESE.get()), MobEffects.REGENERATION));

	public static final RegistryObject<Block> CHEESE_CAKE = registerBlockWItem("cheese_cake", () -> new SmallCheese(
			BlockBehaviour.Properties.copy(CHEESE.get())));

	public static final RegistryObject<Block> CHEESE_COVER = registerBlock("cheese_cover",
			() -> new CheeseCover(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));

	//tools
	public static final RegistryObject<Block> CHEESE_STRAINER = registerBlockWItem(
			"cheese_strainer", () -> new CheeseStrainer(BlockBehaviour.Properties.copy(Blocks.STONE)));

	// Block
	private static <T extends Block> RegistryObject<T> registerBlockWItem(String name, Supplier<T> block) {
		RegistryObject<T> to_return = registerBlock(name, block);
		registerBlockItem(name, to_return);
		return to_return;
	}

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		return BLOCKS.register(name, block);
	}

	// Items
	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
		BLOCK_ITEMS.register(bus);
	}
}
