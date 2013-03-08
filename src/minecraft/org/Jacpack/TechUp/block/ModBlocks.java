package org.Jacpack.TechUp.block;

import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.util.Config;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static BlockCustomOre blockOres;
    public static BlockStorage blockStorage;
	
	public static void init() {
		blockOres = new BlockCustomOre(Config.getBlockID("blocks.world.ores.id"));
        blockOres.setBlockName("TechUpores");
        GameRegistry.registerBlock(blockOres, ItemCustomOre.class, "ores");
	}
	
}
