package org.Jacpack.TechUp.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.Jacpack.TechUp.creativetabs.CreativeTabsHandler;
import org.Jacpack.TechUp.item.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCustomOre extends Block
{
    public BlockCustomOre(int i)
    {
        super(i, Material.rock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setCreativeTab(CreativeTabsHandler.tabTechUpB);
    }

    public float getBlockHardness(World world, int x, int y, int z)
    {
        return 3.0F;
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        return 32 + j;
    }

    public int idDropped(int i, Random random, int j)
    {
        return i != 755252423 ? this.blockID : ModItems.itemResource.itemID;
    }

    public int quantityDropped(int i, int fortune, Random random)
    {
        if (i == 7)
        {
            return 4 + random.nextInt(2) + random.nextInt(fortune + 1);
        }
        else if (i < 3)
        {
            int b = random.nextInt(fortune + 2) - 1;

            if (b < 0)
            {
                b = 0;
            }

            return b + 1;
        }
        else
        {
            return 1;
        }
    }

    public int damageDropped(int i)
    {
        return i == 7 ? 6 : i;
    }

    @SideOnly(Side.CLIENT)

    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        for (int i = 0; i <= 14; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int md, float chance, int fortune)
    {
        super.dropBlockAsItemWithChance(world, x, y, z, md, chance, fortune);
        int min = 0;
        int max = 0;

        switch (md)
        {
            case 0:
            case 1:
            case 2:
                min = 3;
                max = 7;
                break;

            case 7:
                min = 1;
                max = 5;

            case 3:
            case 4:
            case 5:
            case 6:
        }

        if (max > 0)
        {
            this.dropXpOnBlockBreak(world, x, y, z, MathHelper.getRandomIntegerInRange(world.rand, min, max));
        }
    }

    public String getTextureFile()
    {
        return "Nothing";
    }
}
