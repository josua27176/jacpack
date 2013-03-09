package org.Jacpack.TechUp.item;

import org.Jacpack.TechUp.util.Config;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModItems {
	
	public static ItemParts itemResource;
	public static ItemStack itemIngotTitanium;
    public static ItemStack itemIngotAluminum;
    public static ItemStack itemIngotCopper;
    public static ItemStack itemIngotSilver;
    public static ItemStack itemIngotTin;
    public static ItemStack itemIngotBronze;
    public static ItemStack itemIngotSteel;
    public static ItemStack itemCrushedCopper;
    public static ItemStack itemCrushedTin;
    public static ItemStack itemCrushedBronze;
    public static ItemStack itemRefinedUranium;
    public static ItemStack itemAdemartium;
    public static ItemStack itemRubber;
    public static ItemStack itemPlutonium;
    
    public static ItemStack itemChasis;
    public static ItemStack itemFrame;
    public static ItemStack itemFluxCapicitor;
    public static ItemStack itemGlass;
    public static ItemStack itemPlutoniumReactor;
    public static ItemStack itemDashboard;
    public static ItemStack itemSeats;
    public static ItemStack itemEngine;
    
    public static ItemStack itemAdvancedAlloy;
    public static ItemStack itemAluminumPlate;
    public static ItemStack itemTitaniumPlate;
    public static ItemStack itemCarbonPlating;
    
    public static void init() {
        /* Initialize each mod item individually */
    	itemResource = new ItemParts(Config.getItemID("items.base.resource.id"), "ComingSoon");
        itemResource.setCreativeTab(CreativeTabs.tabMaterials);
        itemResource.addItem(0, 48, "item.JAC.Titanium");
        itemResource.addItem(1, 49, "item.JAC.Aluminum");
        itemResource.addItem(2, 50, "item.JAC.Copper");
        itemResource.addItem(3, 51, "item.JAC.Silver");
        itemResource.addItem(4, 52, "item.JAC.Tin");
        itemResource.addItem(5, 53, "item.JAC.Bronze");
        itemResource.addItem(6, 54, "item.JAC.Steel");
        itemResource.addItem(7, 55, "item.JAC.Crushed.Copper");
        itemResource.addItem(8, 56, "item.JAC.Crushed.Tin");
        itemResource.addItem(9, 57, "item.JAC.Crushed.Bronze");
        itemResource.addItem(10, 58, "item.JAC.Refined.Uranium");
        itemResource.addItem(11, 59, "item.JAC.Ademartium");
        itemResource.addItem(12, 60, "item.JAC.Rubber");
        itemResource.addItem(13, 61, "item.JAC.Plutonium");
        itemIngotTitanium = new ItemStack(itemResource, 1, 0);
        itemIngotAluminum = new ItemStack(itemResource, 1, 1);
        itemIngotCopper = new ItemStack(itemResource, 1, 2);
        itemIngotSilver = new ItemStack(itemResource, 1, 3);
        itemIngotTin = new ItemStack(itemResource, 1, 4);
        itemIngotBronze = new ItemStack(itemResource, 1, 5);
        itemIngotSteel = new ItemStack(itemResource, 1, 6);
        itemCrushedCopper = new ItemStack(itemResource, 1, 7);
        itemCrushedTin = new ItemStack(itemResource, 1, 8);
        itemCrushedBronze = new ItemStack(itemResource, 1, 9);
        itemRefinedUranium = new ItemStack(itemResource, 1, 10);
        itemAdemartium = new ItemStack(itemResource, 1, 11);
        itemRubber = new ItemStack(itemResource, 1, 12);
        itemPlutonium = new ItemStack(itemResource, 1, 13);
        
        initCarParts();
        initStage2();
        
    }
    
    public static void initCarParts() {
    	
        itemResource.addItem(14, 62, "item.JAC.Chasis");
        itemResource.addItem(15, 63, "item.JAC.Frame");
        itemResource.addItem(16, 64, "item.JAC.Flux-Capicitor");
        itemResource.addItem(17, 65, "item.JAC.Glass");
        itemResource.addItem(18, 66, "item.JAC.PlutoniumReactor");
        itemResource.addItem(19, 67, "item.JAC.Dashboard");
        itemResource.addItem(20, 68, "item.JAC.Seats");
        itemResource.addItem(21, 69, "item.JAC.Engine");
        
        itemChasis = new ItemStack(itemResource, 1, 14);
        itemFrame = new ItemStack(itemResource, 1, 15);
        itemFluxCapicitor = new ItemStack(itemResource, 1, 16);
        itemGlass = new ItemStack(itemResource, 1, 17);
        itemPlutoniumReactor = new ItemStack(itemResource, 1, 18);
        itemDashboard = new ItemStack(itemResource, 1, 19);
        itemSeats = new ItemStack(itemResource, 1, 20);
        itemEngine = new ItemStack(itemResource, 1, 21);
        
    }
    
    public static void initStage2() {
    	itemResource.addItem(22, 66, "item.JAC.AdvancedAlloy");
        itemResource.addItem(23, 67, "item.JAC.AluminumPlate");
        itemResource.addItem(24, 68, "item.JAC.TitaniumPlate");
        itemResource.addItem(25, 69, "item.JAC.CarbonPlating");
        
        itemAdvancedAlloy = new ItemStack(itemResource, 1, 22);
        itemAluminumPlate = new ItemStack(itemResource, 1, 23);
        itemTitaniumPlate = new ItemStack(itemResource, 1, 24);
        itemCarbonPlating = new ItemStack(itemResource, 1, 25);
    }
    
}
