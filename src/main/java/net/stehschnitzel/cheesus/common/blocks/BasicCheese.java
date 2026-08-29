package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BasicCheese extends EatableCheese {

	public BasicCheese(Properties pProperties) {
		super(pProperties);
	}

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(BITES) == 0 && stack.is(ItemTags.SWORDS)) {
            level.setBlockAndUpdate(pos, BlockInit.BLUE_MOLD_CHEESE.get().defaultBlockState());

            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.5D;
            double d2 = (double)pos.getZ() + 0.5D;

            for (int i = 0; i < 20; i++) {
                double r0 = level.getRandom().nextDouble() * 0.6 - 0.3D;
                double r1 = level.getRandom().nextDouble() * 0.1;
                double r2 = level.getRandom().nextDouble() * 0.6 - 0.3D;
                level.addParticle(ParticleTypes.CRIT, d0 + r0, d1 + r1, d2 + r2,
                        0.0D, 0.0D, 0.0D);
            }
            level.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(BITES) == 0;
	}

	@Override
	public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pState.getValue(BITES) != 0) return;

		double d0 = (double)pPos.getX() + 0.5D;
		double d1 = (double)pPos.getY() + 0.5D;
		double d2 = (double)pPos.getZ() + 0.5D;

		double r0 = pRandom.nextDouble() * 0.6 - 0.3D;
		double r1 = pRandom.nextDouble() * 0.1;
		double r2 = pRandom.nextDouble() * 0.6 - 0.3D;

		if (pLevel.dimensionTypeRegistration() == BuiltinDimensionTypes.OVERWORLD) {
			if (pPos.getY() > 150) {
				pLevel.addParticle(
						new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.WHITE_CONCRETE.defaultBlockState()),
						d0 + r0, d1 + r1, d2 + r2,
						0.0D, 0.0D, 0.0D);
			} else if (pLevel.getRawBrightness(pPos, 0) < 5) {
				pLevel.addParticle(ParticleTypes.MYCELIUM, d0 + r0, d1 + r1, d2 + r2,
						0.0D, 0.0D, 0.0D);
			}
		} else if (pLevel.dimensionTypeRegistration() == BuiltinDimensionTypes.NETHER) {
			pLevel.addParticle(ParticleTypes.FALLING_LAVA, d0 + r0, d1 + r1, d2 + r2,
					0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pLevel.dimensionTypeRegistration() == BuiltinDimensionTypes.OVERWORLD) {
			if (pPos.getY() > 150) {
				pLevel.setBlockAndUpdate(pPos, BlockInit.ALTITUDE_CHEESE.get().defaultBlockState());
			} else if (pLevel.getRawBrightness(pPos, 0) < 5) {
				pLevel.setBlockAndUpdate(pPos, BlockInit.WHITE_MOLD_CHEESE.get().defaultBlockState());
			}
		} else if (pLevel.dimensionTypeRegistration() == BuiltinDimensionTypes.NETHER) {
			pLevel.setBlockAndUpdate(pPos, BlockInit.DIABOLICAL_CHEESE.get().defaultBlockState());
		}
	}
}