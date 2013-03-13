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
    	LAVA(11),
    	WATER(9),
    	FLOWINGLAVA(11),
    	FLOWINGWATER(8);
    	
    	public static final liquidMaterials[] LIQUID_MATERIALS = { LAVA,WATER };
    	public static final liquidMaterials[] LIQUID_FLOWING = { FLOWINGLAVA,FLOWINGWATER };
    	
    	public static boolean isLiquid(int mat)
    	{
    		for(liquidMaterials i : LIQUID_MATERIALS)
    		{
    			if(mat == i.material)
    				return true;
    		}
    		for(liquidMaterials i : LIQUID_FLOWING)
    		{
    			if(mat == i.material)
    				return true;
    		}
    		
    		return false;
    	}
    	public static int[] getLiquidMaterials()
    	{
    		int[] t = new int[LIQUID_MATERIALS.length];
    		
    		for(int x = 0; x < LIQUID_MATERIALS.length; x++)
    			t[x] = LIQUID_MATERIALS[x].material;
    		return t;
    			
    	}
    	
    	public static boolean isFlowing(int mat)
    	{
    		for(liquidMaterials i : LIQUID_FLOWING)
    		{
    			if(mat == i.material)
    				return true;
    		}
    		return false;
    	}
    	
    	public static final int length = LIQUID_MATERIALS.length;
    	private int material;
    	private liquidMaterials(int mat)
    	{
    		material = mat;
    	}
    }
}
