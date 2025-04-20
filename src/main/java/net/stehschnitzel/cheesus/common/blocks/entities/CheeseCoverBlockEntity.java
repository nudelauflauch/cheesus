package net.stehschnitzel.cheesus.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.stehschnitzel.cheesus.init.BlockEntityInit;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CheeseCoverBlockEntity extends BlockEntity {

    private int rotationDeg = 0;
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public CheeseCoverBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.CHEESE_COVER.get(), pPos, pBlockState);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public void increaseRotationDeg() {
        this.rotationDeg = this.rotationDeg > 359 ? 45 : this.rotationDeg + 45;
    }

    public int getRotationDeg() {
        return rotationDeg;
    }

    public void clearContents() {
        rotationDeg = 0;
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        rotationDeg = 0;
        Containers.dropContents(this.level, this.worldPosition.above(), inv);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", inventory.serializeNBT());
        pTag.putInt("rotation_deg", this.rotationDeg);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inventory.deserializeNBT(pTag.getCompound("inventory"));
        this.rotationDeg = pTag.getInt("rotation_deg");
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
