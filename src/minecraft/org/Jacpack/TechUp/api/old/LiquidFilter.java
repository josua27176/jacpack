package org.Jacpack.TechUp.api.old;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.liquids.LiquidStack;

public class LiquidFilter
{
    public static final LiquidStack WATER = new LiquidStack(Block.waterStill, 0);
    public static final LiquidStack LAVA = new LiquidStack(Block.lavaStill, 0);
    
    
    //Important! Make sure to add liquid block materials to the enumeration!!!!
    public static enum liquidMaterials
    {
    	LAVA(Material.lava),
    	WATER(Material.water);
    	
    	public static final liquidMaterials[] LIQUID_MATERIALS = { LAVA,WATER };
    	public static boolean isLiquid(Material mat)
    	{
    		for(liquidMaterials i : LIQUID_MATERIALS)
    		{
    			if(mat == i.material)
    				return true;
    		}
    		return false;
    	}
    	public static Material[] getLiquidMaterials()
    	{
    		Material[] ret = new Material[length];
    		
    		for(int i = 0; i < length; i++)
    			ret[i] = LIQUID_MATERIALS[i].material;
    		
    		return ret;
    			
    	}
    	
    	public static final int length = LIQUID_MATERIALS.length;
    	private Material material;
    	private liquidMaterials(Material mat)
    	{
    		material = mat;
    	}
    }
}
