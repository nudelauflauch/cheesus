package at.akunatur.cheesus.common.blocks;

import at.akunatur.cheesus.core.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Cheese extends Block {
	public static final IntegerProperty LEVEL = IntegerProperty.create("bites", 0, 3);

	public Cheese(Properties porperties) {
		super(porperties);
	}

	public VoxelShape cheese3() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.5), BooleanOp.OR);

		return shape;
	}

	public VoxelShape cheese2() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);

		return shape;
	}

	public VoxelShape cheese1() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.5, 0.5, 0.375, 0.875), BooleanOp.OR);

		return shape;
	}

	public VoxelShape cheese0() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.5, 0.5, 0.375, 0.875), BooleanOp.OR);

		return shape;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		if (state.getBlock() != BlockInit.GRAUKAS.get()) {
			System.out.println("");
			switch (state.getValue(LEVEL)) {
			case 0:
				return cheese0();
			case 1:
				return cheese1();
			case 2:
				return cheese2();
			default:
				return cheese3();
			}
		} else {
			switch (state.getValue(LEVEL)) {
			case 0:
				return Block.box(2, 0, 2, 14, 3, 14);
			case 1:
				return Shapes.join(Block.box(2, 0, 8, 8, 3, 14), Block.box(8, 0, 2, 14, 3, 14), BooleanOp.OR);
			case 2:
				return Block.box(8, 0, 2, 14, 3, 14);
			default:
				return Block.box(8, 0, 2, 14, 3, 8);
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		if (!worldIn.isClientSide) {
			if (this.eat(worldIn, pos, state, player).consumesAction()) {
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.CONSUME;

	}

	private InteractionResult eat(LevelAccessor worldIn, BlockPos pos, BlockState state, Player playerIn) {
		if (!playerIn.canEat(false)) {
			return InteractionResult.PASS;
		} else {
			playerIn.getFoodData().eat(2, 0.1F);
			int i = state.getValue(LEVEL);
			if (i < 3) {
				worldIn.setBlock(pos, state.setValue(LEVEL, Integer.valueOf(Mth.clamp(i += 1, 0, 3))), 3);
			} else {
				worldIn.removeBlock(pos, false);
			}

			return InteractionResult.SUCCESS;
		}
	}
}
