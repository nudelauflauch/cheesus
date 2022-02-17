package at.akunatur.cheesus.common.blocks.entity;

import at.akunatur.cheesus.common.blocks.CheeseStrainer;
import at.akunatur.cheesus.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class CheeseStrainerBlockEntity extends InventoryBlockEntity {

	public CheeseStrainerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(BlockEntityInit.CHEESE_STRAINER.get(), pWorldPosition, pBlockState, 1);
	}

	public boolean setItem(ItemStack stack) {
		final ItemStack current = getItemInSlot(0);
		if (current.isEmpty()) {
			this.inventory.setStackInSlot(0, stack);
			this.update();
			return true;
		}
		update();
		return false;
	}

	public ItemStack getItemStack() {
		return this.getItemInSlot(0).copy();
	}

	public int getTimer() {
		return this.timer;
	}

	@Override
	public void tick() {
		
		Item item = this.getItemStack().getItem();
		if (item != null && item != Items.AIR) {
			if (this.timer >= 60) {
				if (item == Items.ORANGE_DYE) {
					CheeseStrainer.setLevel(this.level, this.getBlockPos(), this.getBlockState(), 3);
					item = Items.AIR;
				} else if (item == Items.STONE || item == Items.DIORITE || item == Items.ANDESITE
						|| item == Items.GRANITE || item == Items.COBBLESTONE || item == Items.DEEPSLATE
						|| item == Items.TUFF) {
					CheeseStrainer.setLevel(this.level, this.getBlockPos(), this.getBlockState(), 4);
					item = Items.AIR;
				} else if (item == Items.FERN || item == Items.GRASS) {
					CheeseStrainer.setLevel(this.level, this.getBlockPos(), this.getBlockState(), 5);
					item = Items.AIR;
				} else if (item == Items.BROWN_MUSHROOM) {
					CheeseStrainer.setLevel(this.level, this.getBlockPos(), this.getBlockState(), 6);
					item = Items.AIR;
				} else if (item == Items.RED_MUSHROOM) {
					CheeseStrainer.setLevel(this.level, this.getBlockPos(), this.getBlockState(), 7);
					item = Items.AIR;
				} else if (item == Items.GRAY_DYE) {
					CheeseStrainer.setLevel(this.level, this.getBlockPos(), this.getBlockState(), 8);
					item = Items.AIR;
				}
				this.inventory.setStackInSlot(0, ItemStack.EMPTY);
				this.timer = 0;
			}
			this.timer++;
			this.update();
		}

		super.tick();
	}
}
