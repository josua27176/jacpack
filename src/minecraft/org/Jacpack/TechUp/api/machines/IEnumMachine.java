package org.Jacpack.TechUp.api.machines;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IEnumMachine
{
    String getTag();

    boolean isEnabled();

    ItemStack getItem();

    ItemStack getItem(int paramInt);

    int getTexture(int paramInt);

    Class getTileClass();

    int ordinal();

    Block getBlock();

    int getBlockId();

    boolean isDepreciated();
}
