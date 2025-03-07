package at.akunatur.cheesus.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BasicCheese extends Block {

	public static final IntegerProperty BITES = IntegerProperty.create("bites",
			0, 3);
	public static final int MAX_BITES = 4;

	public VoxelShape cheese0() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape,
				Shapes.box(0.125, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);

		return shape;
	}
	public VoxelShape cheese1() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape,
				Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.5, 0.5, 0.375, 0.875),
				BooleanOp.OR);

		return shape;
	}
	public VoxelShape cheese2() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape,
				Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);

		return shape;
	}
	public VoxelShape cheese3() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.5),
				BooleanOp.OR);

		return shape;
	}

	public BasicCheese(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel,
			BlockPos pPos, CollisionContext pContext) {
		switch (pState.getValue(BITES)) {
			case 0 :
				return cheese0();
			case 1 :
				return cheese1();
			case 2 :
				return cheese2();
		}
		return cheese3();
	}

	@Override
	public InteractionResult use(BlockState state, Level pLevel, BlockPos pos,
		Player player, InteractionHand handIn, BlockHitResult hit) {
		if (player.canEat(player.getFoodData().needsFood())) {
			player.getFoodData().eat(2, 0.1F);

			if (state.getValue(BITES) == 3) {
				pLevel.removeBlock(pos, false);
			} else {
				pLevel.setBlockAndUpdate(pos, state.setValue(BITES, state.getValue(BITES) + 1));
			}
			return InteractionResult.sidedSuccess(pLevel.isClientSide);
		}
		return InteractionResult.FAIL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(BITES, 0);
	}

	@Override
	protected void createBlockStateDefinition(
			StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BITES);
	}
}
