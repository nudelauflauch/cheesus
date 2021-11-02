package at.akunatur.cheesus.common.blocks;

import at.akunatur.cheesus.core.init.BlockInit;
import at.akunatur.cheesus.core.util.CheesusBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class Cheese extends Block {

	public static final IntegerProperty LEVEL = CheesusBlockState.LEVEL_0_4;

	public Cheese(Properties porperties) {
		super(porperties);
	}

	private static final VoxelShape SHAPE_ALPINE_CHEESE_0 = Block.makeCuboidShape(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_ALPINE_CHEESE_1 = VoxelShapes.combineAndSimplify(
			Block.makeCuboidShape(2, 0, 8, 8, 4, 14), Block.makeCuboidShape(8, 0, 2, 14, 4, 14), IBooleanFunction.OR);
	private static final VoxelShape SHAPE_ALPINE_CHEESE_2 = Block.makeCuboidShape(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_ALPINE_CHEESE_3 = Block.makeCuboidShape(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_CAMEMBERT_0 = Block.makeCuboidShape(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CAMEMBERT_1 = VoxelShapes.combineAndSimplify(
			Block.makeCuboidShape(2, 0, 8, 8, 4, 14), Block.makeCuboidShape(8, 0, 2, 14, 4, 14), IBooleanFunction.OR);
	private static final VoxelShape SHAPE_CAMEMBERT_2 = Block.makeCuboidShape(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CAMEMBERT_3 = Block.makeCuboidShape(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_CHEDDAR_0 = Block.makeCuboidShape(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CHEDDAR_1 = VoxelShapes.combineAndSimplify(
			Block.makeCuboidShape(2, 0, 8, 8, 4, 14), Block.makeCuboidShape(8, 0, 2, 14, 4, 14), IBooleanFunction.OR);
	private static final VoxelShape SHAPE_CHEDDAR_2 = Block.makeCuboidShape(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CHEDDAR_3 = Block.makeCuboidShape(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_0 = Block.makeCuboidShape(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_1 = VoxelShapes.combineAndSimplify(
			Block.makeCuboidShape(2, 0, 8, 8, 4, 14), Block.makeCuboidShape(8, 0, 2, 14, 4, 14), IBooleanFunction.OR);
	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_2 = Block.makeCuboidShape(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_3 = Block.makeCuboidShape(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_GRAUKAS_0 = Block.makeCuboidShape(2, 0, 2, 14, 3, 14);
	private static final VoxelShape SHAPE_GRAUKAS_1 = VoxelShapes.combineAndSimplify(
			Block.makeCuboidShape(2, 0, 8, 8, 3, 14), Block.makeCuboidShape(8, 0, 2, 14, 3, 14), IBooleanFunction.OR);
	private static final VoxelShape SHAPE_GRAUKAS_2 = Block.makeCuboidShape(8, 0, 2, 14, 3, 14);
	private static final VoxelShape SHAPE_GRAUKAS_3 = Block.makeCuboidShape(8, 0, 2, 14, 3, 8);

	private static final VoxelShape SHAPE_HERB_CHEESE_0 = Block.makeCuboidShape(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_HERB_CHEESE_1 = VoxelShapes.combineAndSimplify(
			Block.makeCuboidShape(2, 0, 8, 8, 4, 14), Block.makeCuboidShape(8, 0, 2, 14, 4, 14), IBooleanFunction.OR);
	private static final VoxelShape SHAPE_HERB_CHEESE_2 = Block.makeCuboidShape(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_HERB_CHEESE_3 = Block.makeCuboidShape(8, 0, 2, 14, 4, 8);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int i = state.get(LEVEL);
		if (state.getBlock() == BlockInit.ALPINE_CHEESE.get()) {
			if (i == 0) {
				return SHAPE_ALPINE_CHEESE_0;
			} else if (i == 1) {
				return SHAPE_ALPINE_CHEESE_1;
			} else if (i == 2) {
				return SHAPE_ALPINE_CHEESE_2;
			} else {
				return SHAPE_ALPINE_CHEESE_3;
			}
		} else if (state.getBlock() == BlockInit.CAMEMBERT.get()) {
			if (i == 0) {
				return SHAPE_CAMEMBERT_0;
			} else if (i == 1) {
				return SHAPE_CAMEMBERT_1;
			} else if (i == 2) {
				return SHAPE_CAMEMBERT_2;
			} else {
				return SHAPE_CAMEMBERT_3;
			}
		} else if (state.getBlock() == BlockInit.CHEDDAR.get()) {
			if (i == 0) {
				return SHAPE_CHEDDAR_0;
			} else if (i == 1) {
				return SHAPE_CHEDDAR_1;
			} else if (i == 2) {
				return SHAPE_CHEDDAR_2;
			} else {
				return SHAPE_CHEDDAR_3;
			}
		} else if (state.getBlock() == BlockInit.DIABOLICAL_CHEESE.get()) {
			if (i == 0) {
				return SHAPE_DIABOLICAL_CHEESE_0;
			} else if (i == 1) {
				return SHAPE_DIABOLICAL_CHEESE_1;
			} else if (i == 2) {
				return SHAPE_DIABOLICAL_CHEESE_2;
			} else {
				return SHAPE_DIABOLICAL_CHEESE_3;
			}
		} else if (state.getBlock() == BlockInit.GRAUKAS.get()) {
			if (i == 0) {
				return SHAPE_GRAUKAS_0;
			} else if (i == 1) {
				return SHAPE_GRAUKAS_1;
			} else if (i == 2) {
				return SHAPE_GRAUKAS_2;
			} else {
				return SHAPE_GRAUKAS_3;
			}
		} else if (state.getBlock() == BlockInit.HERB_CHEESE.get()) {
			if (i == 0) {
				return SHAPE_HERB_CHEESE_0;
			} else if (i == 1) {
				return SHAPE_HERB_CHEESE_1;
			} else if (i == 2) {
				return SHAPE_HERB_CHEESE_2;
			} else {
				return SHAPE_HERB_CHEESE_3;
			}
		} else {

			return SHAPE_ALPINE_CHEESE_0;
		}
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			if (this.eat(worldIn, pos, state, player).isSuccessOrConsume()) {
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.CONSUME;

	}

	private ActionResultType eat(IWorld worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn) {
		if (!playerIn.canEat(false)) {
			return ActionResultType.PASS;
		} else {
			playerIn.getFoodStats().addStats(2, 0.1F);
			int i = state.get(LEVEL);
			if (i < 3) {
				System.out.println("ja");
				worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(i += 1, 0, 3))), 3);
			} else {
				worldIn.removeBlock(pos, false);
			}

			return ActionResultType.SUCCESS;
		}
	}
}
