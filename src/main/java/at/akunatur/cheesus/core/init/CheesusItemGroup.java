package at.akunatur.cheesus.core.init;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CheesusItemGroup extends CreativeModeTab {

	public CheesusItemGroup(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(BlockInit.CHEDDAR.get());
	}

	@Override
	public void fillItemList(NonNullList<ItemStack> items) {
		 registerTools(items);
		 registerCheese(items);
		 registerCheesePieces(items);
		 registerFood(items);
		 items.add(new ItemStack(BlockInit.CHEESE.get()));
		 items.add(new ItemStack(BlockInit.REFINIED_CHEESE.get()));
		 items.add(new ItemStack(BlockInit.WHITE_MOLD_CHEESE.get()));
	}

	private void registerCheese(NonNullList<ItemStack> items) {
		items.add(new ItemStack(BlockInit.ALPINE_CHEESE.get()));
		items.add(new ItemStack(BlockInit.CAMEMBERT.get()));
		items.add(new ItemStack(BlockInit.CHEDDAR.get()));
		items.add(new ItemStack(BlockInit.DIABOLICAL_CHEESE.get()));
		items.add(new ItemStack(BlockInit.BLUE_CHEESE.get()));
		items.add(new ItemStack(BlockInit.HERB_CHEESE.get()));
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
		items.add(new ItemStack(BlockInit.CHEESE_COVER.get()));
		items.add(new ItemStack(BlockInit.CHEESE_STRAINER.get()));
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
