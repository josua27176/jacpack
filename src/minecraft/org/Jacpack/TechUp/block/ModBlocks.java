package org.Jacpack.TechUp.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.util.Config;
import org.Jacpack.TechUp.world.TerrainGenerator;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static BlockCustomOre blockOres;
	public static ItemStack itemOreTitanium;
	public static ItemStack itemOreAluminum;
	public static ItemStack itemOreCopper;
	public static ItemStack itemOreSilver;
	public static ItemStack itemOreTin;
	public static ItemStack itemOreUranite;
	public static ItemStack itemOreLimestone;
	public static ItemStack itemBlockTitanium;
	public static ItemStack itemBlockAluminum;
	public static ItemStack itemBlockCopper;
	public static ItemStack itemBlockTin;
	public static ItemStack itemBlockUranium;
	public static ItemStack itemBlockSilver;
	public static ItemStack itemBlockAdemantine;
	public static ItemStack itemBlockLimestone;
	public final static Block[] mBlocks = new Block[5];
	
	public static void init() {
		blockOres = new BlockCustomOre(Config.getBlockID("blocks.world.ores.id"));
        blockOres.setBlockName("TechUpores");
        GameRegistry.registerBlock(blockOres, ItemCustomOre.class, "TechUpores");
        itemOreTitanium = new ItemStack(blockOres, 1, 0);
        itemOreAluminum = new ItemStack(blockOres, 1, 1);
        itemOreCopper = new ItemStack(blockOres, 1, 2);
        itemOreSilver = new ItemStack(blockOres, 1, 3);
        itemOreTin = new ItemStack(blockOres, 1, 4);
        itemOreUranite = new ItemStack(blockOres, 1, 5);
        itemOreLimestone = new ItemStack(blockOres, 1, 6);
        itemBlockTitanium = new ItemStack(blockOres, 1, 7);
        itemBlockAluminum = new ItemStack(blockOres, 1, 8);
        itemBlockCopper = new ItemStack(blockOres, 1, 9);
        itemBlockTin = new ItemStack(blockOres, 1, 10);
        itemBlockUranium = new ItemStack(blockOres, 1, 11);
        itemBlockSilver = new ItemStack(blockOres, 1, 12);
        itemBlockAdemantine = new ItemStack(blockOres, 1, 13);
        itemBlockLimestone = new ItemStack(blockOres, 1, 14);
        
        
        GameRegistry.registerWorldGenerator(new TerrainGenerator(blockOres));
	}
	
}
