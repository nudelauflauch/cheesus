package net.stehschnitzel.cheesus.init;

import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.stehschnitzel.cheesus.common.blocks.*;
import com.google.common.base.Supplier;

import net.stehschnitzel.cheesus.Cheesus;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockInit {
	public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister
			.createItems(Cheesus.MOD_ID);

	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister
			.createBlocks(Cheesus.MOD_ID);

	//cheese
	public static final DeferredBlock<BasicCheese> CHEESE = registerBlockWItem("cheese", () -> new BasicCheese(
			BlockBehaviour.Properties.of().strength(0.8f, 1.5f).sound(SoundType.CORAL_BLOCK)));

	public static final DeferredBlock<EatableCheese> ALTITUDE_CHEESE = registerBlockWItem("altitude_cheese", () -> new EatableCheese(
			BlockBehaviour.Properties.ofFullCopy(CHEESE.get()), MobEffects.ABSORPTION));

	public static final DeferredBlock<EatableCheese> BLUE_MOLD_CHEESE = registerBlockWItem("blue_mold_cheese", () -> new EatableCheese(
			BlockBehaviour.Properties.ofFullCopy(CHEESE.get()), MobEffects.SATURATION));

	public static final DeferredBlock<EatableCheese> DIABOLICAL_CHEESE = registerBlockWItem("diabolical_cheese",
			() -> new EatableCheese(
					BlockBehaviour.Properties.ofFullCopy(CHEESE.value()), MobEffects.FIRE_RESISTANCE));

	public static final DeferredBlock<EatableCheese> GREY_CHEESE = registerBlockWItem("grey_cheese", () -> new SmallCheese(
			BlockBehaviour.Properties.ofFullCopy(CHEESE.get()), MobEffects.DAMAGE_BOOST));

	public static final DeferredBlock<EatableCheese> WHITE_MOLD_CHEESE = registerBlockWItem("white_mold_cheese", () -> new SmallCheese(
			BlockBehaviour.Properties.ofFullCopy(CHEESE.get()), MobEffects.REGENERATION));

	public static final DeferredBlock<Block> CHEESECAKE = registerBlockWItem("cheesecake", () -> new SmallCheese(
			BlockBehaviour.Properties.ofFullCopy(CHEESE.get())));

	public static final DeferredBlock<Block> CHEESE_COVER = registerBlock("cheese_cover",
			() -> new CheeseCover(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion()));

	//tools
	public static final DeferredBlock<Block> CHEESE_STRAINER = registerBlockWItem(
			"cheese_strainer", () -> new CheeseStrainer(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

	// Block
	private static <T extends Block> DeferredBlock<T> registerBlockWItem(String name, Supplier<T> block) {
		DeferredBlock<T> to_return = registerBlock(name, block);
		registerBlockItem(name, to_return);
		return to_return;
	}

	private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
		return BLOCKS.register(name, block);
	}

	// Items
	private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<T> block) {
		return BLOCK_ITEMS.registerSimpleBlockItem(name, block);
	}

	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
		BLOCK_ITEMS.register(bus);
	}
}
