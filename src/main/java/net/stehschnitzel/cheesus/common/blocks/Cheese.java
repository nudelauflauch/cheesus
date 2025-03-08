package net.stehschnitzel.cheesus.common.blocks;

import net.stehschnitzel.cheesus.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

public class Cheese extends BasicCheese {

	public Cheese(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public InteractionResult use(BlockState state, Level pLevel, BlockPos pos,
			Player player, InteractionHand handIn, BlockHitResult hit) {

		if (state.getValue(BITES) == 0) {
			Item item = player.getItemInHand(handIn).getItem();
			RegistryObject<BasicCheese> newBlock = null;

			if (item == Items.BROWN_MUSHROOM) {
				newBlock = BlockInit.WHITE_MOLD_CHEESE;

			} else if (item == Items.HONEYCOMB) {
				newBlock = BlockInit.REFINED_CHEESE;

			} else if (item == Items.FLINT_AND_STEEL) {
				newBlock = BlockInit.DIABOLICAL_CHEESE;

			} else if (item == Items.POWDER_SNOW_BUCKET) {
				newBlock = BlockInit.ALPINE_CHEESE;

			} else if (item == Items.GRAY_DYE) {
				newBlock = BlockInit.BLUE_CHEESE;

			} else if (item == Items.KELP) {
				newBlock = BlockInit.HERB_CHEESE;

			}

			if (newBlock != null) {
				pLevel.setBlockAndUpdate(pos, newBlock.get().defaultBlockState());
				player.getMainHandItem().shrink(1);

				return InteractionResult.CONSUME;
			}
		}

		return super.use(state, pLevel, pos, player, handIn, hit);
	}
}