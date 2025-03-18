package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BasicCheese extends Block {

	public static final IntegerProperty BITES = IntegerProperty.create("bites",
			0, 3);
	public static final int MAX_BITES = 4;

	public BasicCheese(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel,
			BlockPos pPos, CollisionContext pContext) {
		return CheesusVoxels.NORMAL_SHAPE_BY_BITE[pState.getValue(BITES)];
	}

	@Override
	public InteractionResult use(BlockState state, Level pLevel, BlockPos pos,
		Player player, InteractionHand handIn, BlockHitResult hit) {
		if (player.canEat(player.getFoodData().needsFood())) {
			player.getFoodData().eat(2, 2F);

			if (state.getValue(BITES) == MAX_BITES - 1) {
				pLevel.removeBlock(pos, false);
			} else {
				pLevel.setBlockAndUpdate(pos, state.setValue(BITES, state.getValue(BITES) + 1));
			}

			pLevel.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0F, 1.0F, false);
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