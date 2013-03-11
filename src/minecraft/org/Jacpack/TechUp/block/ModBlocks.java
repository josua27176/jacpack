package org.Jacpack.TechUp.block;

import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.block.Machines.MachinePump;
import org.Jacpack.TechUp.util.Config;
import org.Jacpack.TechUp.world.TerrainGenerator;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static BlockCustomOre blockOres;
	public static MachinePump machinePump;
	public static ItemStack itemOreTitanium;
	public static ItemStack itemOreAluminum;
	public static ItemStack itemOreCopper;
	public static ItemStack itemOreSilver;
	public static ItemStack itemOreTin;
	public static ItemStack itemOreUranite;
	public static ItemStack itemOreLimestone;
	
    public static BlockStorage blockStorage;
	
	public static void init() {
		blockOres = new BlockCustomOre(Config.getBlockID("blocks.world.ores.id"));
        machinePump = new MachinePump(Config.getBlockID("blocks.machines.pump.id"));
        
        GameRegistry.registerBlock(blockOres, ItemCustomOre.class, "TechUpores");
        itemOreTitanium = new ItemStack(blockOres, 1, 0);
        itemOreAluminum = new ItemStack(blockOres, 1, 1);
        itemOreCopper = new ItemStack(blockOres, 1, 2);
        itemOreSilver = new ItemStack(blockOres, 1, 3);
        itemOreTin = new ItemStack(blockOres, 1, 4);
        itemOreUranite = new ItemStack(blockOres, 1, 5);
        itemOreLimestone = new ItemStack(blockOres, 1, 6);
        
        
        GameRegistry.registerWorldGenerator(new TerrainGenerator(blockOres));
	}
	
	public static void initMachines() {
		
	}
	
}
