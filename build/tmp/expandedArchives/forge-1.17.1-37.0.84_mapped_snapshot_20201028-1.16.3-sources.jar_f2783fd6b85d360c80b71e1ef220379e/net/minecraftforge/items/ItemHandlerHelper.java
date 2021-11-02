/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.items;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemHandlerHelper
{
    @Nonnull
    public static ItemStack insertItem(IItemHandler dest, @Nonnull ItemStack stack, boolean simulate)
    {
        if (dest == null || stack.m_41619_())
            return stack;

        for (int i = 0; i < dest.getSlots(); i++)
        {
            stack = dest.insertItem(i, stack, simulate);
            if (stack.m_41619_())
            {
                return ItemStack.f_41583_;
            }
        }

        return stack;
    }

    public static boolean canItemStacksStack(@Nonnull ItemStack a, @Nonnull ItemStack b)
    {
        if (a.m_41619_() || !a.m_41656_(b) || a.m_41782_() != b.m_41782_())
            return false;

        return (!a.m_41782_() || a.m_41783_().equals(b.m_41783_())) && a.areCapsCompatible(b);
    }

    /**
     * A relaxed version of canItemStacksStack that stacks itemstacks with different metadata if they don't have subtypes.
     * This usually only applies when players pick up items.
     */
    public static boolean canItemStacksStackRelaxed(@Nonnull ItemStack a, @Nonnull ItemStack b)
    {
        if (a.m_41619_() || b.m_41619_() || a.m_41720_() != b.m_41720_())
            return false;

        if (!a.m_41753_())
            return false;

        // Metadata value only matters when the item has subtypes
        // Vanilla stacks non-subtype items with different metadata together
        // TODO Item subtypes, is this still necessary?
        /* e.g. a stick with metadata 0 and a stick with metadata 1 stack
        if (a.getHasSubtypes() && a.getMetadata() != b.getMetadata())
            return false;
*/
        if (a.m_41782_() != b.m_41782_())
            return false;

        return (!a.m_41782_() || a.m_41783_().equals(b.m_41783_())) && a.areCapsCompatible(b);
    }

    @Nonnull
    public static ItemStack copyStackWithSize(@Nonnull ItemStack itemStack, int size)
    {
        if (size == 0)
            return ItemStack.f_41583_;
        ItemStack copy = itemStack.m_41777_();
        copy.m_41764_(size);
        return copy;
    }

    /**
     * Inserts the ItemStack into the inventory, filling up already present stacks first.
     * This is equivalent to the behaviour of a player picking up an item.
     * Note: This function stacks items without subtypes with different metadata together.
     */
    @Nonnull
    public static ItemStack insertItemStacked(IItemHandler inventory, @Nonnull ItemStack stack, boolean simulate)
    {
        if (inventory == null || stack.m_41619_())
            return stack;

        // not stackable -> just insert into a new slot
        if (!stack.m_41753_())
        {
            return insertItem(inventory, stack, simulate);
        }

        int sizeInventory = inventory.getSlots();

        // go through the inventory and try to fill up already existing items
        for (int i = 0; i < sizeInventory; i++)
        {
            ItemStack slot = inventory.getStackInSlot(i);
            if (canItemStacksStackRelaxed(slot, stack))
            {
                stack = inventory.insertItem(i, stack, simulate);

                if (stack.m_41619_())
                {
                    break;
                }
            }
        }

        // insert remainder into empty slots
        if (!stack.m_41619_())
        {
            // find empty slot
            for (int i = 0; i < sizeInventory; i++)
            {
                if (inventory.getStackInSlot(i).m_41619_())
                {
                    stack = inventory.insertItem(i, stack, simulate);
                    if (stack.m_41619_())
                    {
                        break;
                    }
                }
            }
        }

        return stack;
    }

    /** giveItemToPlayer without preferred slot */
    public static void giveItemToPlayer(Player player, @Nonnull ItemStack stack) {
        giveItemToPlayer(player, stack, -1);
    }

    /**
     * Inserts the given itemstack into the players inventory.
     * If the inventory can't hold it, the item will be dropped in the world at the players position.
     *
     * @param player The player to give the item to
     * @param stack  The itemstack to insert
     */
    public static void giveItemToPlayer(Player player, @Nonnull ItemStack stack, int preferredSlot)
    {
        if (stack.m_41619_()) return;

        IItemHandler inventory = new PlayerMainInvWrapper(player.m_150109_());
        Level world = player.f_19853_;

        // try adding it into the inventory
        ItemStack remainder = stack;
        // insert into preferred slot first
        if (preferredSlot >= 0 && preferredSlot < inventory.getSlots())
        {
            remainder = inventory.insertItem(preferredSlot, stack, false);
        }
        // then into the inventory in general
        if (!remainder.m_41619_())
        {
            remainder = insertItemStacked(inventory, remainder, false);
        }

        // play sound if something got picked up
        if (remainder.m_41619_() || remainder.m_41613_() != stack.m_41613_())
        {
            world.m_6263_(null, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_(),
                    SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2F, ((world.f_46441_.nextFloat() - world.f_46441_.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }

        // drop remaining itemstack into the world
        if (!remainder.m_41619_() && !world.f_46443_)
        {
            ItemEntity entityitem = new ItemEntity(world, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_(), remainder);
            entityitem.m_32010_(40);
            entityitem.m_20256_(entityitem.m_20184_().m_82542_(0, 1, 0));

            world.m_7967_(entityitem);
        }
    }

    /**
     * This method uses the standard vanilla algorithm to calculate a comparator output for how "full" the inventory is.
     * This method is an adaptation of Container#calcRedstoneFromInventory(IInventory).
     * @param inv The inventory handler to test.
     * @return A redstone value in the range [0,15] representing how "full" this inventory is.
     */
    public static int calcRedstoneFromInventory(@Nullable IItemHandler inv)
    {
        if (inv == null)
        {
            return 0;
        }
        else
        {
            int itemsFound = 0;
            float proportion = 0.0F;

            for (int j = 0; j < inv.getSlots(); ++j)
            {
                ItemStack itemstack = inv.getStackInSlot(j);

                if (!itemstack.m_41619_())
                {
                    proportion += (float)itemstack.m_41613_() / (float)Math.min(inv.getSlotLimit(j), itemstack.m_41741_());
                    ++itemsFound;
                }
            }

            proportion = proportion / (float)inv.getSlots();
            return Mth.m_14143_(proportion * 14.0F) + (itemsFound > 0 ? 1 : 0);
        }
    }
}
