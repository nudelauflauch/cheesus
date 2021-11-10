package at.akunatur.cheesus.common.blocks;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class Timer extends EnergyStorage implements INBTSerializable<Tag> {

	private static int timer;

	public Timer(int capacity) {
		super(timer, timer, capacity, 0);
	}

	public Timer(int capacity, int maxTransfer) {
		super(capacity, maxTransfer, maxTransfer, 0);
	}

	public Timer(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract, 0);
	}

	public int getTimer() {
		return this.capacity;
	}

	public void tick() {
		this.capacity++;
	}

}
