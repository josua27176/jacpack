package org.Jacpack.TechUp.modules;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.crafting.TechUpCraftingManager;
import org.Jacpack.TechUp.item.ModItems;

public class ModuleCore extends JACModule {
	
	public void initFirst()
    {
		ModBlocks.registerBlockMachineAlpha();
		//ModBlocks.registerBlockMachineBeta();
    }
	
}
