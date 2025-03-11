package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseStrainerBlockEntity;
import net.stehschnitzel.cheesus.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.stehschnitzel.cheesus.init.BlockInit;

public class CheeseStrainer extends Block {

	public static final IntegerProperty LEVEL = IntegerProperty.create("level",
			0, 6);

	public CheeseStrainer(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel,
			BlockPos pPos, CollisionContext pContext) {
		return CheesusVoxels.CheeseStrainer();
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
			Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		int level = pLevel.getBlockState(pPos).getValue(LEVEL);
		Item item = pPlayer.getItemInHand(pHand).getItem();

		if (level < 3 && item == Items.MILK_BUCKET) {
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, level+1));

			if (!pPlayer.isCreative()) {
				pPlayer.getMainHandItem().shrink(1);
				addItemOrDrop(Items.BUCKET, pPlayer);
			}
			return InteractionResult.sidedSuccess(pLevel.isClientSide());

		} else if (level == 0 && item == BlockInit.CHEESE.get().asItem()) {
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 5));
			if (!pPlayer.isCreative()) {
				pPlayer.getItemInHand(pHand).shrink(1);
			}

			return InteractionResult.sidedSuccess(pLevel.isClientSide());

		} else if (level == 4) {
			addItemOrDrop(BlockInit.CHEESE.get(), pPlayer);
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 0));

			return InteractionResult.sidedSuccess(pLevel.isClientSide());
		} else if (level == 6) {
			addItemOrDrop(BlockInit.GREY_CHEESE.get(), pPlayer);
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 0));

			return InteractionResult.sidedSuccess(pLevel.isClientSide());
		}

		return InteractionResult.FAIL;
	}

	private void addItemOrDrop(ItemLike item, Player player) {
		if (!player.addItem(new ItemStack(item))) {
			player.drop(new ItemStack(item), false);
		}
	}

	@Override
	public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
		if (isRandomlyTicking(pState)) {
			pLevel.addParticle(ParticleTypes.FLAME, pPos.getX(), pPos.getY(), pPos.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(LEVEL) == 3 || pState.getValue(LEVEL) == 5;
	}

	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pState.getValue(LEVEL) == 3) {
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 4));
		} else if (pState.getValue(LEVEL) == 5) {
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 6));
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		return defaultBlockState().setValue(LEVEL, 0);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
		pBuilder.add(LEVEL);
	}
}
