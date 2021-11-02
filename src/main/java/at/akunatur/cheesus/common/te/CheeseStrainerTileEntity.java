package at.akunatur.cheesus.common.te;

import at.akunatur.cheesus.core.init.TileEntityTypesInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CheeseStrainerTileEntity extends TileEntity implements ITickableTileEntity {

	public static final String TIMER = "timer";

	public ItemStack item;

	public int timer = 0;

	public CheeseStrainerTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
	}

	public CheeseStrainerTileEntity() {
		this(TileEntityTypesInit.CHEESE_STRAINER_TILE_ENTITY.get());
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		this.timer = compound.getInt(TIMER);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt(TIMER, this.timer);
		return compound;
	}

	@Override
	public void tick() {
		timer++;
	}

	public ItemStack getItem() {
		return this.item;
	}

	public void setItem(int i, ItemStack item) {
//		if (this.getBlockState().get(Cheese_strainer.LEVEL) == 0) {
//			
//		}
		this.item = item;
	}

}
