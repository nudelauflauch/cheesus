package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class Cheese extends BasicCheese {

	public Cheese(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResult use(BlockState state, Level pLevel, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {

		if (state.getValue(BITES) == 0 && player.getMainHandItem().areShareTagsEqual(new ItemStack(Items.IRON_SWORD))) {
			pLevel.setBlockAndUpdate(pos, BlockInit.BLUE_MOLD_CHEESE.get().defaultBlockState());

			return InteractionResult.sidedSuccess(pLevel.isClientSide());
		}

		return super.use(state, pLevel, pos, player, handIn, hit);
	}

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(BITES) == 0;
	}

	@Override
	public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pPos.getY() > 150 || pLevel.getRawBrightness(pPos, 0) < 5 || pLevel.dimensionTypeId() == BuiltinDimensionTypes.NETHER) {
			pLevel.addParticle(ParticleTypes.FLAME, pPos.getX(), pPos.getY(), pPos.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pPos.getY() > 150) {
			pLevel.setBlockAndUpdate(pPos, BlockInit.ALTITUDE_CHEESE.get().defaultBlockState());
		} else if (pLevel.getRawBrightness(pPos, 0) < 5) {
			pLevel.setBlockAndUpdate(pPos, BlockInit.WHITE_MOLD_CHEESE.get().defaultBlockState());
		} else if (pLevel.dimensionTypeId() == BuiltinDimensionTypes.NETHER) {
			pLevel.setBlockAndUpdate(pPos, BlockInit.DIABOLICAL_CHEESE.get().defaultBlockState());
		}
	}
}