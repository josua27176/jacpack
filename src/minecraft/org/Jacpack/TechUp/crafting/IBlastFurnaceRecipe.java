package org.Jacpack.TechUp.crafting;

import net.minecraft.item.ItemStack;

public interface IBlastFurnaceRecipe
{
    int getCookTime();

    ItemStack getInput();

    ItemStack getOutput();

    int getOutputStackSize();

    boolean isRoomForOutput(ItemStack paramur);
}
