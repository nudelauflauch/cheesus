package net.stehschnitzel.cheesus.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.stehschnitzel.cheesus.init.BlockEntityInit;

import javax.annotation.Nullable;

public class CheeseCoverBlockEntity extends BlockEntity {

    private int rotationDeg = 0;
    public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(1) {
        @Override
        protected int getCapacity(int index, ItemResource resource) {
            return 1;
        }
    };

    public CheeseCoverBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.CHEESE_COVER.get(), pPos, pBlockState);
    }

    public ItemStacksResourceHandler getInventory() {
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
        inventory.set(0, ItemResource.EMPTY, 1);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            inv.setItem(i, inventory.getResource(i).toStack());
        }

        rotationDeg = 0;
        Containers.dropContents(this.level, this.worldPosition.above(), inv);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        inventory.serialize(output);
        output.putInt("rotation_deg", this.rotationDeg);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        inventory.deserialize(input);
        this.rotationDeg = input.getInt("rotation_deg").get();
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
