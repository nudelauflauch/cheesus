package net.stehschnitzel.cheesus.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.stehschnitzel.cheesus.init.BlockEntityInit;

import javax.annotation.Nullable;

public class CheeseCoverBlockEntity extends BlockEntity {

    private int rotationDeg = 0;
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        inventory.serializeNBT(registries);
        tag.putInt("rotation_deg", this.rotationDeg);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag);
        this.rotationDeg = tag.getInt("rotation_deg");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}
