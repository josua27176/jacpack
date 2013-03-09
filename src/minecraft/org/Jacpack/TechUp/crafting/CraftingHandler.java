package org.Jacpack.TechUp.crafting;

import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.item.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class CraftingHandler {
	
	public static void init() {
	        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 7), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotTitanium});
	        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 8), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotAluminum});
	        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 9), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotCopper});
	        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 10), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotTin});
	        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 11), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemRefinedUranium});
	        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 12), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotSilver});
	        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 13), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemAdemartium});
	        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotTitanium, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 7)});
	        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotAluminum, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 8)});
	        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotCopper, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 9)});
	        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotTin, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 10)});
	        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemRefinedUranium, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 11)});
	        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotSilver, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 12)});
	        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemAdemartium, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 13)});
	}
	
}
