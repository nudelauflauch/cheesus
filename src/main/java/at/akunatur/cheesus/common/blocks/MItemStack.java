package at.akunatur.cheesus.common.blocks;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class MItemStack implements INBTSerializable<CompoundTag> {

	protected NonNullList<ItemStack> itemStack;
	
	@Override
	public CompoundTag serializeNBT() {
		ListTag nbtTagList = new ListTag();
		for (int i = 0; i < itemStack.size(); i++) {
			if (!itemStack.get(i).isEmpty()) {
				CompoundTag itemTag = new CompoundTag();
				itemTag.putInt("Slot", i);
				itemStack.get(i).save(itemTag);
				nbtTagList.add(itemTag);
			}
		}
		CompoundTag nbt = new CompoundTag();
		nbt.put("Items", nbtTagList);
		nbt.putInt("Size", itemStack.size());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		ListTag tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.size(); i++) {
			CompoundTag itemTags = tagList.getCompound(i);
			int slot = itemTags.getInt("Slot");

			if (slot >= 0 && slot < itemStack.size()) {
				itemStack.set(slot, ItemStack.of(itemTags));
			}
		}
	}

}
