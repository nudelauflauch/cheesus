package net.stehschnitzel.cheesus.common.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;

public class EquipableCheeseCover extends CheeseCover implements Equipable {
    public static final MapCodec<EquipableCheeseCover> CODEC = simpleCodec(EquipableCheeseCover::new);

    @Override
    public MapCodec<EquipableCheeseCover> codec() {
        return CODEC;
    }

    public EquipableCheeseCover(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
