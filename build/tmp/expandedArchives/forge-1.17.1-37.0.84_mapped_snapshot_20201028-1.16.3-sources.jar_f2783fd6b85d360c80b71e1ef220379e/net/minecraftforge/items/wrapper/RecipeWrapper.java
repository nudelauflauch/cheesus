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

package net.minecraftforge.items.wrapper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class RecipeWrapper implements Container {

    protected final IItemHandlerModifiable inv;

    public RecipeWrapper(IItemHandlerModifiable inv)
    {
        this.inv = inv;
    }

    /**
     * Returns the size of this inventory.  Must be equivalent to {@link #getHeight()} * {@link #getWidth()}.
     */
    @Override
    public int m_6643_()
    {
        return inv.getSlots();
    }

    /**
     * Returns the stack in this slot.  This stack should be a modifiable reference, not a copy of a stack in your inventory.
     */
    @Override
    public ItemStack m_8020_(int slot)
    {
        return inv.getStackInSlot(slot);
    }

    /**
     * Attempts to remove n items from the specified slot.  Returns the split stack that was removed.  Modifies the inventory.
     */
    @Override
    public ItemStack m_7407_(int slot, int count)
    {
        ItemStack stack = inv.getStackInSlot(slot);
        return stack.m_41619_() ? ItemStack.f_41583_ : stack.m_41620_(count);
    }

    /**
     * Sets the contents of this slot to the provided stack.
     */
    @Override
    public void m_6836_(int slot, ItemStack stack)
    {
        inv.setStackInSlot(slot, stack);
    }

    /**
     * Removes the stack contained in this slot from the underlying handler, and returns it.
     */
    @Override
    public ItemStack m_8016_(int index)
    {
        ItemStack s = m_8020_(index);
        if(s.m_41619_()) return ItemStack.f_41583_;
        m_6836_(index, ItemStack.f_41583_);
        return s;
    }

    @Override
    public boolean m_7983_()
    {
        for(int i = 0; i < inv.getSlots(); i++)
        {
            if(!inv.getStackInSlot(i).m_41619_()) return false;
        }
        return true;
    }

    @Override
    public boolean m_7013_(int slot, ItemStack stack)
    {
        return inv.isItemValid(slot, stack);
    }

    @Override
    public void m_6211_() 
    {
        for(int i = 0; i < inv.getSlots(); i++)
        {
            inv.setStackInSlot(i, ItemStack.f_41583_);
        }
    }

    //The following methods are never used by vanilla in crafting.  They are defunct as mods need not override them.
    @Override
    public int m_6893_() { return 0; }
    @Override
    public void m_6596_() {}
    @Override
    public boolean m_6542_(Player player) { return false; }
    @Override
    public void m_5856_(Player player) {}
    @Override
    public void m_5785_(Player player) {}

}
