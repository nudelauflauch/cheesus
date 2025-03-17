package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseCoverBlockEntity;
import org.jetbrains.annotations.Nullable;

public class CheeseCover extends BaseEntityBlock {

	public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 12, 15);

	public CheeseCover(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new CheeseCoverBlockEntity(pPos, pState);
	}

	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos,
						 BlockState pNewState, boolean pMovedByPiston) {
		if (pState.getBlock() != pNewState.getBlock()) {
			if (pLevel.getBlockEntity(pPos) instanceof CheeseCoverBlockEntity cheeseCoverBlockEntity) {
				cheeseCoverBlockEntity.drops();
				pLevel.updateNeighbourForOutputSignal(pPos, this);
			}
		}
		super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (pLevel.getBlockEntity(pPos) instanceof CheeseCoverBlockEntity cheeseCoverBlockEntity) {
			ItemStack stack = pPlayer.getMainHandItem();

			if (cheeseCoverBlockEntity.inventory.getStackInSlot(0).isEmpty()) {
				cheeseCoverBlockEntity.inventory.insertItem(0, stack.copy(), false);
				stack.shrink(1);
				pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);

			} else if (!cheeseCoverBlockEntity.inventory.getStackInSlot(0).isEmpty()) {
//				ItemStack stackOnCheeseCover = cheeseCoverBlockEntity.inventory.extractItem(0, 1, false);
//				if (pPlayer.addItem(stackOnCheeseCover)) {
//					pPlayer.drop(stackOnCheeseCover, false);
//				}
				cheeseCoverBlockEntity.drops();
				cheeseCoverBlockEntity.clearContents();
				pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
			}
		}

		return InteractionResult.SUCCESS;
	}
}