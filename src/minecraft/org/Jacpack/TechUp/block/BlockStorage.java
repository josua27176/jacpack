package org.Jacpack.TechUp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockStorage extends Block
{
    public BlockStorage(int i)
    {
        super(i, Material.iron);
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundMetalFootstep);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        return 80 + j;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int i)
    {
        return i;
    }

    public String getTextureFile()
    {
        return "Nothing";
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        for (int i = 0; i <= 5; i++)
            list.add(new ItemStack(this, 1, i));
    }
}
