package at.akunatur.cheesus.common.te;

import at.akunatur.cheesus.common.blocks.CheeseStrainer;
import at.akunatur.cheesus.core.init.BlockEntityTypesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CheeseStrainerBlockEntity extends BlockEntity {

	private Item item;
	public Integer timer = 0;

	public CheeseStrainerBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityTypesInit.CHEESE_STRAINER_TILE_ENTITY.get(), pos, state);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		timer = tag.getInt("timer");
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		tag.putInt("timer", this.timer);
		return tag;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ItemStack getItemStack() {
		return new ItemStack(this.item);
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
		CheeseStrainerBlockEntity tile = (CheeseStrainerBlockEntity) be;
		
		if (!level.isClientSide()) {
			if (tile.item != null && tile.item != Items.MILK_BUCKET && tile.getItem() != Items.AIR) {
				tile.timer++;
				if (tile.timer >= 60) {
					
					if (tile.item == Items.ORANGE_DYE) {
						CheeseStrainer.setLevel(level, pos, state, 2);
						tile.item = Items.AIR;
					} else if (tile.item == Items.STONE || tile.item == Items.DIORITE || tile.item == Items.ANDESITE
							|| tile.item == Items.GRANITE || tile.item == Items.COBBLESTONE) {
						System.out.println("jaaaaaaa");
						CheeseStrainer.setLevel(level, pos, state, 3);
						tile.item = Items.AIR;
					} else if (tile.item == Items.FERN || tile.item == Items.GRASS) {
						CheeseStrainer.setLevel(level, pos, state, 4);
						tile.item = Items.AIR;
					} else if (tile.item == Items.BROWN_MUSHROOM) {
						CheeseStrainer.setLevel(level, pos, state, 5);
						tile.item = Items.AIR;
					} else if (tile.item == Items.RED_MUSHROOM) {
						CheeseStrainer.setLevel(level, pos, state, 6);
						tile.item = Items.AIR;
					} else if (tile.item == Items.GRAY_DYE) {
						CheeseStrainer.setLevel(level, pos, state, 7);
						tile.item = Items.AIR;
					}
					tile.timer = 0;
				}
			}
		}
	}

}
