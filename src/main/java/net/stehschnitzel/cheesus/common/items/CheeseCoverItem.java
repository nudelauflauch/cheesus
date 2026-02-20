package net.stehschnitzel.cheesus.common.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;

public class CheeseCoverItem extends BlockItem {
    public CheeseCoverItem(Block block, Properties properties) {
        super(block, properties);
        this.humanoidArmor(ArmorMaterials.LEATHER, ArmorType.HELMET);
    }

    public Item.Properties humanoidArmor(ArmorMaterial material, ArmorType type) {
        return new Properties()
                .attributes(material.createAttributes(type))
                .enchantable(material.enchantmentValue())
                .component(
                        DataComponents.EQUIPPABLE,
                        Equippable.builder(type.getSlot()).setEquipSound(material.equipSound()).setAsset(material.assetId()).build()
                )
                .repairable(material.repairIngredient());
    }
}
