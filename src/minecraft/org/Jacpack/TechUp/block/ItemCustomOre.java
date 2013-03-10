package org.Jacpack.TechUp.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCustomOre extends ItemBlock
{
    public static final String[] blockType = new String[]
    {
        "oreJACTitanium",
        "oreJACAluminum",
        "oreJACCopper",
        "oreJACSilver",
        "oreJACTin",
        "oreJACUranite",
        "oreJACLimestone",
        "blockJACTitanium",
        "blockJACAluminum",
        "blockJACCopper",
        "blockJACTin",
        "blockJACUranium",
        "blockJACSilver",
        "blockJACAdemantine",
        "blockJACLimestone"
    };

    public ItemCustomOre(int i)
    {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }

    public int getMetadata(int i)
    {
        return i;
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        return "tile." + blockType[itemstack.getItemDamage()];
    }
}
