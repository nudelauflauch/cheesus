package net.stehschnitzel.cheesus.common.blocks;

import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseCoverBlockEntity;
import org.jetbrains.annotations.Nullable;

public class CheeseCover extends BaseEntityBlock {

	public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 12, 15);
    public static final MapCodec<CheeseCover> CODEC = simpleCodec(CheeseCover::new);

	public CheeseCover(Properties pProperties) {
		super(pProperties);
	}

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {
        if (level.getBlockEntity(pos) instanceof CheeseCoverBlockEntity cheeseCoverBlockEntity) {
            cheeseCoverBlockEntity.drops();
            level.updateNeighbourForOutputSignal(pos, this);
        }

        return super.onDestroyedByPlayer(state, level, pos, player, toolStack, willHarvest, fluid);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof CheeseCoverBlockEntity cheeseCoverBlockEntity) {
            if (player.isCrouching()) {
                cheeseCoverBlockEntity.increaseRotationDeg();
                return InteractionResult.SUCCESS;
            } else if (cheeseCoverBlockEntity.inventory.getResource(0).isEmpty()) {
                cheeseCoverBlockEntity.inventory.set(0, ItemResource.of(stack.copy()), 1);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);

            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof CheeseCoverBlockEntity cheeseCoverBlockEntity) {
            if (player.isCrouching()) {
                cheeseCoverBlockEntity.increaseRotationDeg();
                return InteractionResult.SUCCESS;
            } else if (!cheeseCoverBlockEntity.inventory.getResource(0).isEmpty()) {
//				ItemStack stackOnCheeseCover = cheeseCoverBlockEntity.inventory.extractItem(0, 1, false);
//				if (pPlayer.addItem(stackOnCheeseCover)) {
//					pPlayer.drop(stackOnCheeseCover, false);
//				}
                cheeseCoverBlockEntity.drops();
                cheeseCoverBlockEntity.clearContents();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
}