package net.stehschnitzel.cheesus.common.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;

public class CheeseCoverItem extends BlockItem implements Equipable {

	public CheeseCoverItem(Block block, Properties proberties) {
		super(block, proberties);
	}
	
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		EquipmentSlot equipmentslot = EquipmentSlot.HEAD;
		ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
		if (itemstack1.isEmpty()) {
			pPlayer.setItemSlot(equipmentslot, itemstack.copy());
			if (!pLevel.isClientSide()) {
				pPlayer.awardStat(Stats.ITEM_USED.get(this));
			}

			itemstack.setCount(0);
			return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == this) {
			player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0));
		}

		super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}
}
