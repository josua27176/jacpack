package org.Jacpack.TechUp.creativetabs;

import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.item.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public final class CreativeTabTechUpB extends CreativeTabs
{
    public CreativeTabTechUpB(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    @SideOnly(Side.CLIENT)

    public int getTabIconItemIndex()
    {
        return ModBlocks.blockOres.blockID;
    }
}
