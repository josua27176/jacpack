package org.Jacpack.TechUp.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCustomOre extends ItemBlock
{
	
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

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int i)
    {
        return i;
    }
    
    public String getItemNameIS(ItemStack itemstack)
    {
        switch (itemstack.getItemDamage())
        {
            case 0:
                return "tile.oreJACTitanium";
            case 1:
                return "tile.oreJACAluminum";
            case 2:
                return "tile.oreJACCopper";
            case 3:
                return "tile.oreJACSilver";
            case 4:
                return "tile.oreJACTin";
        }
        throw new IndexOutOfBoundsException();
    }
}
