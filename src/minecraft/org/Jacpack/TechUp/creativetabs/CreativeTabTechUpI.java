package org.Jacpack.TechUp.creativetabs;

import org.Jacpack.TechUp.item.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public final class CreativeTabTechUpI extends CreativeTabs
{
    public CreativeTabTechUpI(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    @SideOnly(Side.CLIENT)

    public int getTabIconItemIndex()
    {
        return ModItems.itemResource.itemID;
    }
}