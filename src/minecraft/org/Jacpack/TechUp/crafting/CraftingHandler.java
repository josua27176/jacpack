package org.Jacpack.TechUp.crafting;

import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.crafting.machines.BlastFurnaceCraftingManager;
import org.Jacpack.TechUp.item.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingHandler
{
    public static void init()
    {
    	TechUpCraftingManager.blastFurnace = new BlastFurnaceCraftingManager();
    	int burnTime = 0; 
    	TechUpCraftingManager.blastFurnace.addRecipe(Item.ingotIron.itemID, burnTime, new ItemStack(ModItems.itemResource, 1, 6));
    	
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 7), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotTitanium});
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 8), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotAluminum});
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 9), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotCopper});
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 10), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotTin});
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 11), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemRefinedUranium});
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 12), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemIngotSilver});
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOres, 1, 13), new Object[] {"GGG", "GGG", "GGG", 'G', ModItems.itemAdemartium});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemBronzeHelment, 1), new Object[] {"GGG", "G G", 'G', ModItems.itemIngotBronze});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemBronzeChestplate, 1), new Object[] {"G G", "GGG", "GGG", 'G', ModItems.itemIngotBronze});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemBronzeLeggings, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotBronze});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemBronzeBoots, 1), new Object[] {"G G", "G G", 'G', ModItems.itemIngotBronze});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSteelHelmet, 1), new Object[] {"GGG", "G G", 'G', ModItems.itemIngotSteel});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSteelChestplate, 1), new Object[] {"G G", "GGG", "GGG", 'G', ModItems.itemIngotSteel});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSteelLeggings, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotSteel});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSteelBoots, 1), new Object[] {"G G", 'G', ModItems.itemIngotSteel});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemCopperHelment, 1), new Object[] {"GGG", "G G", 'G', ModItems.itemIngotCopper});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemCopperChestplate, 1), new Object[] {"G G", "GGG", "GGG", 'G', ModItems.itemIngotCopper});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemCopperLeggings, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotCopper});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemCopperBoots, 1), new Object[] {"G G", 'G', ModItems.itemIngotCopper});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSilverHelment, 1), new Object[] {"GGG", "G G", 'G', ModItems.itemIngotSilver});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSilverChestplate, 1), new Object[] {"G G", "GGG", "GGG", 'G', ModItems.itemIngotSilver});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSilverLeggings, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotSilver});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemSilverBoots, 1), new Object[] {"G G", 'G', ModItems.itemIngotSilver});
        
        
        //HAZMAT SUIT HAS NO ITEM TO BE CRAFTABLE
        GameRegistry.addRecipe(new ItemStack(ModItems.itemHazmatHelment, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotSilver});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemHazmatChesplate, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotSilver});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemHazmatLeggings, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotSilver});
        GameRegistry.addRecipe(new ItemStack(ModItems.itemHazmatBoots, 1), new Object[] {"GGG", "G G", "G G", 'G', ModItems.itemIngotSilver});
        
        
        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotTitanium, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 7)});
        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotAluminum, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 8)});
        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotCopper, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 9)});
        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotTin, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 10)});
        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemRefinedUranium, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 11)});
        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemIngotSilver, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 12)});
        GameRegistry.addRecipe(CraftingHelper.copyStack(ModItems.itemAdemartium, 9), new Object[] {"G", 'G', new ItemStack(ModBlocks.blockOres, 1, 13)});
    }
}
