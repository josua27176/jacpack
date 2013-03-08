package org.Jacpack.TechUp.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCustomOre extends ItemBlock
{
    public ItemCustomOre(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getPlacedBlockMetadata(int var1)
    {
        return var1;
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }

    public String getItemNameIS(ItemStack var1)
    {
        switch (var1.getItemDamage())
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

            case 5:
                return "tile.oreJACBronze";

            case 6:
                return "tile.oreJACSteel";

            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
