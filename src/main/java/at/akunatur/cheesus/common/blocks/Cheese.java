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

	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 3);

	public Cheese(Properties porperties) {
		super(porperties);
	}

	private static final VoxelShape SHAPE_ALPINE_CHEESE_0 = Block.box(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_ALPINE_CHEESE_1 = Shapes.join(Block.box(2, 0, 8, 8, 4, 14),
			Block.box(8, 0, 2, 14, 4, 14), BooleanOp.OR);
	private static final VoxelShape SHAPE_ALPINE_CHEESE_2 = Block.box(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_ALPINE_CHEESE_3 = Block.box(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_CAMEMBERT_0 = Block.box(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CAMEMBERT_1 = Shapes.join(Block.box(2, 0, 8, 8, 4, 14),
			Block.box(8, 0, 2, 14, 4, 14), BooleanOp.OR);
	private static final VoxelShape SHAPE_CAMEMBERT_2 = Block.box(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CAMEMBERT_3 = Block.box(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_CHEDDAR_0 = Block.box(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CHEDDAR_1 = Shapes.join(Block.box(2, 0, 8, 8, 4, 14),
			Block.box(8, 0, 2, 14, 4, 14), BooleanOp.OR);
	private static final VoxelShape SHAPE_CHEDDAR_2 = Block.box(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_CHEDDAR_3 = Block.box(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_0 = Block.box(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_1 = Shapes.join(Block.box(2, 0, 8, 8, 4, 14),
			Block.box(8, 0, 2, 14, 4, 14), BooleanOp.OR);
	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_2 = Block.box(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_DIABOLICAL_CHEESE_3 = Block.box(8, 0, 2, 14, 4, 8);

	private static final VoxelShape SHAPE_GRAUKAS_0 = Block.box(2, 0, 2, 14, 3, 14);
	private static final VoxelShape SHAPE_GRAUKAS_1 = Shapes.join(Block.box(2, 0, 8, 8, 3, 14),
			Block.box(8, 0, 2, 14, 3, 14), BooleanOp.OR);
	private static final VoxelShape SHAPE_GRAUKAS_2 = Block.box(8, 0, 2, 14, 3, 14);
	private static final VoxelShape SHAPE_GRAUKAS_3 = Block.box(8, 0, 2, 14, 3, 8);

	private static final VoxelShape SHAPE_HERB_CHEESE_0 = Block.box(2, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_HERB_CHEESE_1 = Shapes.join(Block.box(2, 0, 8, 8, 4, 14),
			Block.box(8, 0, 2, 14, 4, 14), BooleanOp.OR);
	private static final VoxelShape SHAPE_HERB_CHEESE_2 = Block.box(8, 0, 2, 14, 4, 14);
	private static final VoxelShape SHAPE_HERB_CHEESE_3 = Block.box(8, 0, 2, 14, 4, 8);

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		int i = state.getValue(LEVEL);
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
