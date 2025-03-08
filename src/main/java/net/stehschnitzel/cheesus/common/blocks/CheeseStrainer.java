package net.stehschnitzel.cheesus.common.blocks;

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

public class CheeseStrainer extends Block implements EntityBlock {

	public VoxelShape makeShape() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape,
				Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375),
				BooleanOp.OR);
		shape = Shapes.join(shape,
				Shapes.box(0.0625, 0.0625, 0.0625, 0.9375, 0.5, 0.125),
				BooleanOp.OR);
		shape = Shapes.join(shape,
				Shapes.box(0.0625, 0.0625, 0.875, 0.9375, 0.5, 0.9375),
				BooleanOp.OR);
		shape = Shapes.join(shape,
				Shapes.box(0.875, 0.0625, 0.125, 0.9375, 0.5, 0.875),
				BooleanOp.OR);
		shape = Shapes.join(shape,
				Shapes.box(0.0625, 0.0625, 0.125, 0.125, 0.5, 0.875),
				BooleanOp.OR);
		shape = Shapes.join(shape,
				Shapes.box(0.125, 0.0625, 0.125, 0.875, 0.46875, 0.875),
				BooleanOp.OR);
		shape = Shapes.join(shape,
				Shapes.box(0.125, 0.0625, 0.125, 0.875, 0.4375, 0.875),
				BooleanOp.OR);

		return shape;
	}

	public static final IntegerProperty LEVEL = IntegerProperty.create("level",
			0, 2);

	public CheeseStrainer(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel,
			BlockPos pPos, CollisionContext pContext) {
		return makeShape();
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
			Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		int level = pLevel.getBlockState(pPos).getValue(LEVEL);
		ItemStack itemStack = pPlayer.getItemInHand(pHand);
		if (!pLevel.isClientSide) {
			if (level == 0 && itemStack.getItem() == Items.MILK_BUCKET) {
				pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 1));

				pPlayer.getItemInHand(pHand).shrink(1);
				pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
			} else if (level == 1) {
				pPlayer.setItemInHand(pHand, new ItemStack(Items.MILK_BUCKET));
				pPlayer.getItemInHand(pHand).shrink(1);
				
				pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 0));
			}
			if (level == 2) {
				ItemStack itemStack1 = null; // new ItemStack(BlockInit.CHEESE.get())
				if (itemStack.isEmpty()) {
					pPlayer.setItemInHand(pHand, itemStack1);
				} else if (pPlayer.getInventory().add(itemStack1)) {
					pPlayer.inventoryMenu.sendAllDataToRemote();
				} else {
					pPlayer.drop(itemStack1, false);
				}
				pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 0));

			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel,
			BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return pLevel.isClientSide
				? null
				: ($0, $1, $2,
						blockEntity) -> ((CheeseStrainerBlockEntity) blockEntity)
								.tick();
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		return defaultBlockState().setValue(LEVEL, 0);
	}

	@Override
	protected void createBlockStateDefinition(
			Builder<Block, BlockState> pBuilder) {
		pBuilder.add(LEVEL);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return BlockEntityInit.CHEESE_STRAINER.get().create(pPos, pState);
	}

}
