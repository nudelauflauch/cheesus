package at.akunatur.cheesus.common.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class CheeseStrainerContainer extends AbstractContainerMenu {

	protected CheeseStrainerContainer(MenuType<?> p_38851_, int p_38852_) {
		super(p_38851_, p_38852_);
	}

	public NonNullList<ItemStack> item = NonNullList.withSize(1, ItemStack.EMPTY);

	@Override
	public boolean stillValid(Player p_38874_) {
		return false;
	}

	@Override
	public void setItem(int i1, int i2, ItemStack itemstack) {
		this.item.set(i1, itemstack);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return this.item;
	}

}
