package at.akunatur.cheesus;

import at.akunatur.cheesus.core.init.ItemInit;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CheesusItemGroup extends CreativeModeTab {

	public CheesusItemGroup(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return ItemInit.CHEESE_STRAINER.get().getDefaultInstance();
	}

	@Override
	public void fillItemList(NonNullList<ItemStack> items) {
		registerTools(items);
		registerCheese(items);
		registerCheesePieces(items);
		registerFood(items);
	}

	private void registerCheese(NonNullList<ItemStack> items) {
		items.add(new ItemStack(ItemInit.ALPINE_CHEESE.get()));
		items.add(new ItemStack(ItemInit.CAMAEMBERT.get()));
		items.add(new ItemStack(ItemInit.CHEDDAR.get()));
		items.add(new ItemStack(ItemInit.DIABOLICAL_CHEESE.get()));
		items.add(new ItemStack(ItemInit.GRAUKAS.get()));
		items.add(new ItemStack(ItemInit.HERB_CHEESE.get()));
	}

	private void registerCheesePieces(NonNullList<ItemStack> items) {
		items.add(new ItemStack(ItemInit.PIECE_ALPINE_CHEESE.get()));
		items.add(new ItemStack(ItemInit.PIECE_CAMAEMBERT.get()));
		items.add(new ItemStack(ItemInit.PIECE_CHEDDAR.get()));
		items.add(new ItemStack(ItemInit.PIECE_DIABOLICAL_CHEESE.get()));
		items.add(new ItemStack(ItemInit.PIECE_GRAUKAS.get()));
		items.add(new ItemStack(ItemInit.PIECE_HERB_CHEESE.get()));
	}

	private void registerTools(NonNullList<ItemStack> items) {
		items.add(new ItemStack(ItemInit.CHEESE_COVER.get()));
		items.add(new ItemStack(ItemInit.CHEESE_STRAINER.get()));
	}

	private void registerFood(NonNullList<ItemStack> items) {
		items.add(new ItemStack(ItemInit.BREADED_CAMBERT.get()));
		items.add(new ItemStack(ItemInit.CHEESE_BREAD.get()));
		items.add(new ItemStack(ItemInit.CHEESE_FROM_HELL.get()));
		items.add(new ItemStack(ItemInit.GRAUKAS_KNEDL.get()));
		items.add(new ItemStack(ItemInit.GRAUKAS_SOUP.get()));
		items.add(new ItemStack(ItemInit.SCOLLOPED_POTATO.get()));
		items.add(new ItemStack(ItemInit.SALMON_HERB_CHEESE.get()));
		items.add(new ItemStack(ItemInit.TARTE_DE_SOLEIL_AU_CAMEMBER.get()));
	}

}
