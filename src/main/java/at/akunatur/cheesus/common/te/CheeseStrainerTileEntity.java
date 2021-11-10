package at.akunatur.cheesus.common.te;

import at.akunatur.cheesus.common.blocks.Timer;
import at.akunatur.cheesus.core.init.TileEntityTypesInit;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CheeseStrainerTileEntity extends BlockEntity implements Tickable {

	public static final String TIMER = "timer";

	public ItemStack item;

	public Timer timer = new Timer(0);

	public CheeseStrainerTileEntity(BlockPos pos, BlockState state) {
		super(TileEntityTypesInit.CHEESE_STRAINER_TILE_ENTITY.get(), pos, state);
	}

	@Override
	public void load(CompoundTag tag) {
		item.deserializeNBT(tag.getCompound("item"));
		timer.deserializeNBT(tag.get("timer"));

		super.load(tag);
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		tag.put("item", item.serializeNBT());
		tag.put("timer", timer.serializeNBT());

		return super.save(tag);
	}

	@Override
	public void tick() {
		timer.tick();
	}

	public ItemStack getItemStack() {
		return this.item;
	}

	public void setItem(int i, ItemStack itemstack) {
//		if (this.getBlockState().get(Cheese_strainer.LEVEL) == 0) {
//			
//		}
		this.item = itemstack;
	}

}
