package at.akunatur.cheesus.core.init;

import at.akunatur.cheesus.Cheesus;
import at.akunatur.cheesus.common.blocks.Cheese;
import at.akunatur.cheesus.common.blocks.CheeseCover;
import at.akunatur.cheesus.common.blocks.CheeseStrainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Cheesus.MOD_ID);

	public static final RegistryObject<CheeseStrainer> CHEESE_STRAINER = BLOCKS.register("cheese_strainer",
			() -> new CheeseStrainer(BlockBehaviour.Properties.of(Material.METAL).strength(0.8f, 1.5f)
					.sound(SoundType.STONE).requiresCorrectToolForDrops()));

	public static final RegistryObject<CheeseCover> CHEESE_COVER = BLOCKS.register("cheese_cover",
			() -> new CheeseCover(BlockBehaviour.Properties.copy(Blocks.GLASS)));

	public static final RegistryObject<Cheese> CAMEMBERT = BLOCKS.register("camembert", () -> new Cheese(
			BlockBehaviour.Properties.of(Material.PLANT).strength(0.8f, 1.5f).sound(SoundType.FUNGUS)));

	public static final RegistryObject<Cheese> CHEDDAR = BLOCKS.register("cheddar", () -> new Cheese(
			BlockBehaviour.Properties.of(Material.PLANT).strength(0.8f, 1.5f).sound(SoundType.FUNGUS)));

	public static final RegistryObject<Cheese> DIABOLICAL_CHEESE = BLOCKS.register("diabolical_cheese",
			() -> new Cheese(
					BlockBehaviour.Properties.of(Material.PLANT).strength(0.8f, 1.5f).sound(SoundType.FUNGUS)));

	public static final RegistryObject<Cheese> ALPINE_CHEESE = BLOCKS.register("alpine_cheese", () -> new Cheese(
			BlockBehaviour.Properties.of(Material.PLANT).strength(0.8f, 1.5f).sound(SoundType.FUNGUS)));

	public static final RegistryObject<Cheese> GRAUKAS = BLOCKS.register("graukas", () -> new Cheese(
			BlockBehaviour.Properties.of(Material.PLANT).strength(0.8f, 1.5f).sound(SoundType.FUNGUS)));

	public static final RegistryObject<Cheese> HERB_CHEESE = BLOCKS.register("herb_cheese", () -> new Cheese(
			BlockBehaviour.Properties.of(Material.PLANT).strength(0.8f, 1.5f).sound(SoundType.FUNGUS)));
}
