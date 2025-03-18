package net.stehschnitzel.cheesus.common.blocks.entities;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.stehschnitzel.cheesus.common.blocks.CheeseStrainer;
import net.stehschnitzel.cheesus.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class CheeseStrainerBlockEntity extends BlockEntity {

	private int timer = 0;

	public CheeseStrainerBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.CHEESE_STRAINER.get(), pos, state);
	}

	public void tick(Level level, BlockPos pos, BlockState state) {
		if (!level.isClientSide()) {

			int cheeseLevel = this.getBlockState().getValue(CheeseStrainer.LEVEL);
			if (cheeseLevel > 6) {
				this.timer++;
			}
			if (this.timer >= 100) {
				if (cheeseLevel == 11) {
					this.getLevel().setBlockAndUpdate(this.getBlockPos(),
							this.getBlockState().setValue(CheeseStrainer.LEVEL, 0));
					this.timer = 0;

				} else if (cheeseLevel > 6) {
					this.getLevel().setBlockAndUpdate(this.getBlockPos(),
							this.getBlockState().setValue(CheeseStrainer.LEVEL,
									this.getBlockState().getValue(CheeseStrainer.LEVEL) + 1));
					this.timer = 0;
				}
			}
		}
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putInt("timer", this.timer);
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		this.timer = pTag.getInt("timer");
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}
}
