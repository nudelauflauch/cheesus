package at.akunatur.cheesus.common.blocks;

import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CheeseCover extends Block {

	public CheeseCover(Properties properties) {
		super(properties);
	}

	private static final VoxelShape SHAPE = Stream
			.of(Block.box(7, 12, 7, 9, 14, 9), Block.box(1.7763568394002505e-15, 0, 0, 1, 1, 16),
					Block.box(15.000000000000002, 0, 0, 16, 1, 16), Block.box(1, 0, 0, 15, 1, 1),
					Block.box(1, 0, 15, 15, 1, 16), Block.box(1, 1, 1, 15, 11, 2), Block.box(1, 1, 14, 15, 11, 15),
					Block.box(1, 1, 2, 2, 11, 14), Block.box(14, 1, 2, 15, 11, 14), Block.box(2, 11, 2, 14, 12, 14))
			.reduce((v1, v2) -> {
				return Shapes.join(v1, v2, BooleanOp.OR);
			}).get();

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_,
			CollisionContext p_220053_4_) {
		return SHAPE;
	}



}
