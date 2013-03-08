package xfinity.jacpack.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockOre extends Block
{
	
	public BlockOre(int id, int texture)
	{
		super(id, texture, Material.rock);
	}
	
	public String getTextureFile()
	{
		return CommonProxy.BLOCKS;
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.dragonEgg.blockID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 4;
    }
	
	
	
	
	
}
