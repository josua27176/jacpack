package org.Jacpack.TechUp;

import org.Jacpack.TechUp.achievement.AchevementHandler;
import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.crafting.CraftingHandler;
import org.Jacpack.TechUp.gui.GuiHandler;
import org.Jacpack.TechUp.item.ModItems;
import org.Jacpack.TechUp.modules.ModuleManager;
import org.Jacpack.TechUp.tileentitys.TileEntityHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

public class GameHelper {
	
	public static void init() {
		
		ModBlocks.init();
		
		ModItems.init();
		
		AchevementHandler.init();
		
		CraftingHandler.init();
		
		ModuleManager.init();
		
		TileEntityHandler.init();
		
	}
	
}
