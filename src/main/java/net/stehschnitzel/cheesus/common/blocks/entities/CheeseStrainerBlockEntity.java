package net.stehschnitzel.cheesus.common.blocks.entities;

import net.stehschnitzel.cheesus.common.blocks.CheeseStrainer;
import net.stehschnitzel.cheesus.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CheeseStrainerBlockEntity extends BlockEntity {

	private int timer = 0;

	public CheeseStrainerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.CHEESE_STRAINER.get(), pWorldPosition,
				pBlockState);
	}

	public void tick() {
		if (!level.isClientSide()) {
			int level = this.getBlockState().getValue(CheeseStrainer.LEVEL);
			if ( level == 1) {
				timer++;
			}
			if (level == 1 && this.timer >= 200) {
				this.getLevel().setBlockAndUpdate(worldPosition,
						this.getBlockState().setValue(CheeseStrainer.LEVEL, 2));
				this.timer = 0;
			}
		}
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putInt("timer", timer);
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		timer = pTag.getInt("timer");

	}
}
