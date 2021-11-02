package at.akunatur.cheesus.common.blocks;

import java.util.stream.Stream;

import at.akunatur.cheesus.common.te.CheeseStrainerTileEntity;
import at.akunatur.cheesus.core.init.TileEntityTypesInit;
import at.akunatur.cheesus.core.util.CheesusBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CheeseStrainer extends Block {

	public static final IntegerProperty LEVEL = CheesusBlockState.LEVEL_0_7;

	private static final VoxelShape SHAPE = Stream.of(Block.makeCuboidShape(13, 0, 13, 15, 2, 15),
			Block.makeCuboidShape(2, 1, 2, 14, 4, 14), Block.makeCuboidShape(1, 0, 13, 3, 2, 15),
			Block.makeCuboidShape(1, 0, 1, 3, 2, 3), Block.makeCuboidShape(13, 0, 1, 15, 2, 3),
			Block.makeCuboidShape(1, 3, 1, 15, 10, 2), Block.makeCuboidShape(1, 3, 14, 15, 10, 15),
			Block.makeCuboidShape(14, 3, 2, 15, 10, 14), Block.makeCuboidShape(1, 3, 2, 2, 10, 14)).reduce((v1, v2) -> {
				return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
			}).get();

	public CheeseStrainer(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
			ISelectionContext p_220053_4_) {
		return SHAPE;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		int i = state.get(LEVEL);
		ItemStack itemstack = player.getHeldItem(handIn);
		// Item item = itemstack.getItem();
		if (itemstack.isEmpty()) {
			return ActionResultType.PASS;
		}
		CheeseStrainerTileEntity tile = (CheeseStrainerTileEntity) worldIn.getTileEntity(pos);
		tile.setItem(0, itemstack);
		return ActionResultType.FAIL;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypesInit.CHEESE_STRAINER_TILE_ENTITY.get().create();
	}

}
