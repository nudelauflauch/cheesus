package at.akunatur.cheesus.common.blocks;

import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class CheeseCover extends Block {

	public CheeseCover(Properties properties) {
		super(properties);
	}

	private static final VoxelShape SHAPE = Stream
			.of(Block.makeCuboidShape(7, 12, 7, 9, 14, 9), Block.makeCuboidShape(1.7763568394002505e-15, 0, 0, 1, 1, 16),
					Block.makeCuboidShape(15.000000000000002, 0, 0, 16, 1, 16), Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
					Block.makeCuboidShape(1, 0, 15, 15, 1, 16), Block.makeCuboidShape(1, 1, 1, 15, 11, 2), Block.makeCuboidShape(1, 1, 14, 15, 11, 15),
					Block.makeCuboidShape(1, 1, 2, 2, 11, 14), Block.makeCuboidShape(14, 1, 2, 15, 11, 14), Block.makeCuboidShape(2, 11, 2, 14, 12, 14))
			.reduce((v1, v2) -> {
				return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
			}).get();
	
	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
			ISelectionContext p_220053_4_) {
		return SHAPE;
	}

}
