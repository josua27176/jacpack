package org.Jacpack.TechUp.item;

import org.Jacpack.TechUp.creativetabs.CreativeTabsHandler;
import org.Jacpack.TechUp.item.armor.ItemAluminumArmor;
import org.Jacpack.TechUp.item.armor.ItemBronzeArmor;
import org.Jacpack.TechUp.item.armor.ItemCopperArmor;
import org.Jacpack.TechUp.item.armor.ItemHazmatArmor;
import org.Jacpack.TechUp.item.armor.ItemSilverArmor;
import org.Jacpack.TechUp.item.armor.ItemSteelArmor;
import org.Jacpack.TechUp.item.tools.ItemJacAxe;
import org.Jacpack.TechUp.item.tools.ItemJacHoe;
import org.Jacpack.TechUp.item.tools.ItemJacPickaxe;
import org.Jacpack.TechUp.item.tools.ItemJacSpade;
import org.Jacpack.TechUp.item.tools.ItemJacSword;
import org.Jacpack.TechUp.util.Config;
import org.Jacpack.TechUp.util.misc.Reference;

import static org.Jacpack.TechUp.item.armor.ArmorHelper.*;
import static org.Jacpack.TechUp.item.tools.ToolsHelper.*; 

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ModItems
{
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
    public static ItemStack itemLead;

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
    
    public static Item itemBronzeHelment;
    public static Item itemBronzeChestplate;
    public static Item itemBronzeLeggings;
    public static Item itemBronzeBoots;
    
    public static Item itemSteelHelmet;
    public static Item itemSteelChestplate;
    public static Item itemSteelLeggings;
    public static Item itemSteelBoots;
    
    public static Item itemCopperHelment;
    public static Item itemCopperChestplate;
    public static Item itemCopperLeggings;
    public static Item itemCopperBoots;
    
    public static Item itemSilverHelment;
    public static Item itemSilverChestplate;
    public static Item itemSilverLeggings;
    public static Item itemSilverBoots;
    
    public static Item itemAluminumHelment;
    public static Item itemAluminumChestplate;
    public static Item itemAluminumLeggings;
    public static Item itemAluminumBoots;
    
    public static Item itemHazmatHelment;
    public static Item itemHazmatChesplate;
    public static Item itemHazmatLeggings;
    public static Item itemHazmatBoots;
    
    public static Item itemBronzePickaxe;
    public static Item itemBronzeSword;
    public static Item itemBronzeHoe;
    public static Item itemBronzeSpade;
    public static Item itemBronzeAxe;
    
    public static Item itemSteelPickaxe;
    public static Item itemSteelSword;
    public static Item itemSteelHoe;
    public static Item itemSteelSpade;
    public static Item itemSteelAxe;
    
    public static Item itemCopperPickaxe;
    public static Item itemCopperSword;
    public static Item itemCopperHoe;
    public static Item itemCopperSpade;
    public static Item itemCopperAxe;
    
    public static Item itemSilverPickaxe;
    public static Item itemSilverSword;
    public static Item itemSilverHoe;
    public static Item itemSilverSpade;
    public static Item itemSilverAxe;
    
    public static Item itemAluminumPickaxe;
    public static Item itemAluminumSword;
    public static Item itemAluminumHoe;
    public static Item itemAluminumSpade;
    public static Item itemAluminumAxe;

    public static void init()
    {
        System.out.println("Loading Stage 1...");
        itemResource = new ItemParts(Config.getItemID("items.base.resource.id"), Reference.SPRITE_SHEET_LOCATION + Reference.ITEM_SPRITE_SHEET);
        itemResource.setCreativeTab(CreativeTabsHandler.tabTechUpI);
        itemResource.addItem(0, 166, "item.JAC.Titanium");
        itemResource.addItem(1, 165, "item.JAC.Aluminum");
        itemResource.addItem(2, 161, "item.JAC.Copper");
        itemResource.addItem(3, 164, "item.JAC.Silver");
        itemResource.addItem(4, 160, "item.JAC.Tin");
        itemResource.addItem(5, 162, "item.JAC.Bronze");
        itemResource.addItem(6, 167, "item.JAC.Steel");
        itemResource.addItem(7, 145, "item.JAC.Crushed.Copper");
        itemResource.addItem(8, 144, "item.JAC.Crushed.Tin");
        itemResource.addItem(9, 146, "item.JAC.Crushed.Bronze");
        itemResource.addItem(10, 168, "item.JAC.Refined.Uranium");
        itemResource.addItem(11, 32, "item.JAC.Ademartium");
        itemResource.addItem(12, 170, "item.JAC.Rubber");
        itemResource.addItem(13, 169, "item.JAC.Plutonium");
        itemResource.addItem(14, 163, "item.JAC.Lead");
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
        itemLead = new ItemStack(itemResource, 1, 14);
        System.out.println("Done Loading Stage 1.");
        initCarParts();
        initStage2();
        initArmor();
        initTools();
    }

    public static void initCarParts()
    {
        System.out.println("Loading Car Parts...");
        itemResource.addItem(15, 63, "item.JAC.Chasis");
        itemResource.addItem(16, 64, "item.JAC.Frame");
        itemResource.addItem(17, 65, "item.JAC.Flux-Capicitor");
        itemResource.addItem(18, 66, "item.JAC.Glass");
        itemResource.addItem(19, 67, "item.JAC.PlutoniumReactor");
        itemResource.addItem(20, 68, "item.JAC.Dashboard");
        itemResource.addItem(21, 69, "item.JAC.Seats");
        itemResource.addItem(22, 70, "item.JAC.Engine");
        itemChasis = new ItemStack(itemResource, 1, 15);
        itemFrame = new ItemStack(itemResource, 1, 16);
        itemFluxCapicitor = new ItemStack(itemResource, 1, 17);
        itemGlass = new ItemStack(itemResource, 1, 18);
        itemPlutoniumReactor = new ItemStack(itemResource, 1, 19);
        itemDashboard = new ItemStack(itemResource, 1, 20);
        itemSeats = new ItemStack(itemResource, 1, 21);
        itemEngine = new ItemStack(itemResource, 1, 22);
        System.out.println("Done Loading Car Parts.");
    }

    public static void initStage2()
    {
        System.out.println("Loading Stage 2...");
        itemResource.addItem(23, 67, "item.JAC.AdvancedAlloy");
        itemResource.addItem(24, 181, "item.JAC.AluminumPlate");
        itemResource.addItem(25, 182, "item.JAC.TitaniumPlate");
        itemResource.addItem(26, 186, "item.JAC.CarbonPlating");
        itemAdvancedAlloy = new ItemStack(itemResource, 1, 23);
        itemAluminumPlate = new ItemStack(itemResource, 1, 24);
        itemTitaniumPlate = new ItemStack(itemResource, 1, 25);
        itemCarbonPlating = new ItemStack(itemResource, 1, 26);
        System.out.println("Done Loading Stage 2.");
    }

    public static void initArmor()
    {
    	System.out.println("Loading Armor...");
    	itemBronzeHelment = (new ItemBronzeArmor(Config.getItemID("items.base.Bronze.helmet.id"), JACBronzeArmor, 0)).setIconCoord(2, 0).setItemName("JAC.bronzehelmet");
    	itemBronzeChestplate = (new ItemBronzeArmor(Config.getItemID("items.base.Bronze.Chestplate.id"), JACBronzeArmor, 1)).setIconCoord(2, 1).setItemName("JAC.bronzechesplate");
    	itemBronzeLeggings = (new ItemBronzeArmor(Config.getItemID("items.base.Bronze.Leggings.id"), JACBronzeArmor, 2)).setIconCoord(2, 2).setItemName("JAC.bronzeleggings");
    	itemBronzeBoots = (new ItemBronzeArmor(Config.getItemID("items.base.Bronze.Boots.id"), JACBronzeArmor, 3)).setIconCoord(2, 3).setItemName("JAC.bronzeboots");
    	
    	itemSteelHelmet = (new ItemSteelArmor(Config.getItemID("items.base.Steel.helmet.id"), JACSteelArmor, 0)).setItemName("JAC.steelhelmet");
    	itemSteelChestplate = (new ItemSteelArmor(Config.getItemID("items.base.Steel.Chestplate.id"), JACSteelArmor, 1)).setItemName("JAC.steelchestplate");
    	itemSteelLeggings = (new ItemSteelArmor(Config.getItemID("items.base.Steel.Leggings.id"), JACSteelArmor, 2)).setItemName("JAC.steelleggings");
    	itemSteelBoots = (new ItemSteelArmor(Config.getItemID("items.base.Steel.Boots.id"), JACSteelArmor, 3)).setItemName("JAC.steelboots");
    	
    	itemCopperHelment = (new ItemCopperArmor(Config.getItemID("items.base.Copper.helmet.id"), JACCopperArmor, 0)).setItemName("JAC.copperhelment");
    	itemCopperChestplate = (new ItemCopperArmor(Config.getItemID("items.base.Copper.Chestplate.id"), JACCopperArmor, 1)).setItemName("JAC.copperchestplate");
    	itemCopperLeggings = (new ItemCopperArmor(Config.getItemID("items.base.Copper.Leggings.id"), JACCopperArmor, 2)).setItemName("JAC.copperleggings");
    	itemCopperBoots = (new ItemCopperArmor(Config.getItemID("items.base.Copper.Boots.id"), JACCopperArmor, 3)).setItemName("JAC.copperboots");
    	
    	itemSilverHelment = (new ItemSilverArmor(Config.getItemID("items.base.Silver.helmet.id"), JACSilverArmor, 0)).setItemName("JAC.silverhelmet");
    	itemSilverChestplate = (new ItemSilverArmor(Config.getItemID("items.base.Silver.Chestplate.id"), JACSilverArmor, 1)).setItemName("JAC.silverchestplate");
    	itemSilverLeggings = (new ItemSilverArmor(Config.getItemID("items.base.Silver.Leggings.id"), JACSilverArmor, 2)).setItemName("JAC.silverleggings");
    	itemSilverBoots = (new ItemSilverArmor(Config.getItemID("items.base.Silver.Boots.id"), JACSilverArmor, 3)).setItemName("JAC.silverboots");
    	
    	itemAluminumHelment = (new ItemAluminumArmor(Config.getItemID("items.base.Aluminum.helmet.id"), JACAluminumArmor, 0)).setItemName("JAC.Aluminumhelmet");
    	itemAluminumChestplate = (new ItemAluminumArmor(Config.getItemID("items.base.Aluminum.Chestplate.id"), JACAluminumArmor, 1)).setItemName("JAC.Aluminumchestplate");
    	itemAluminumLeggings = (new ItemAluminumArmor(Config.getItemID("items.base.Aluminum.Leggings.id"), JACAluminumArmor, 2)).setItemName("JAC.Aluminumleggings");
    	itemAluminumBoots = (new ItemAluminumArmor(Config.getItemID("items.base.Aluminum.Boots.id"), JACAluminumArmor, 3)).setItemName("JAC.Aluminumboots");
    	
    	itemHazmatHelment = (new ItemHazmatArmor(Config.getItemID("items.base.Hazmat.helmet.id"), JACHazmatArmor, 0)).setItemName("JAC.hazmathelmet");
    	itemHazmatChesplate = (new ItemHazmatArmor(Config.getItemID("items.base.Hazmat.Chestplate.id"), JACHazmatArmor, 1)).setItemName("JAC.hazmatchestplate");
    	itemHazmatLeggings = (new ItemHazmatArmor(Config.getItemID("items.base.Hazmat.Leggings.id"), JACHazmatArmor, 2)).setItemName("JAC.hazmatleggings");
    	itemHazmatBoots = (new ItemHazmatArmor(Config.getItemID("items.base.Hazmat.Boots.id"), JACHazmatArmor, 3)).setItemName("JAC.hazmatboots");
    	System.out.println("Done Loading Armor.");
    	
    }
    
    public static void initTools() {
    	itemBronzePickaxe = (new ItemJacPickaxe(Config.getItemID("items.base.Bronze.Pickaxe.id"), JACBronzeTool).setIconCoord(2, 6).setItemName("JAC.BronzePickaxe"));
    	itemBronzeSword = (new ItemJacSword(Config.getItemID("items.base.Bronze.Sword.id"), JACBronzeTool).setIconCoord(2, 4).setItemName("JAC.BronzeSword"));
    	itemBronzeHoe = (new ItemJacHoe(Config.getItemID("items.base.Bronze.Hoe.id"), JACBronzeTool).setIconCoord(2, 8).setItemName("JAC.BronzeHoe"));
    	itemBronzeSpade = (new ItemJacSpade(Config.getItemID("items.base.Bronze.Spade.id"), JACBronzeTool).setIconCoord(2, 5).setItemName("JAC.BronzeSpade"));
        itemBronzeAxe = (new ItemJacAxe(Config.getItemID("items.base.Bronze.Axe.id"), JACBronzeTool).setIconCoord(2, 7).setItemName("JAC.BronzeAxe"));
        
        itemSteelPickaxe = (new ItemJacPickaxe(Config.getItemID("items.base.Steel.Pickaxe.id"), JACSteelTool).setItemName("JAC.SteelPickaxe"));
        itemSteelSword = (new ItemJacSword(Config.getItemID("items.base.Steel.Sword.id"), JACSteelTool).setItemName("JAC.SteelSword"));
        itemSteelHoe = (new ItemJacHoe(Config.getItemID("items.base.Steel.Hoe.id"), JACSteelTool).setItemName("JAC.SteelHoe"));
        itemSteelSpade = (new ItemJacSpade(Config.getItemID("items.base.Steel.Spade.id"), JACSteelTool).setItemName("JAC.SteelSpade"));
        itemSteelAxe = (new ItemJacAxe(Config.getItemID("items.base.Steel.Axe.id"), JACSteelTool).setItemName("JAC.SteelAxe"));
        
        itemCopperPickaxe = (new ItemJacPickaxe(Config.getItemID("items.base.Copper.Pickaxe.id"), JACCopperTool).setItemName("JAC.CopperPickaxe"));
        itemCopperSword = (new ItemJacSword(Config.getItemID("items.base.Copper.Sword.id"), JACCopperTool).setItemName("JAC.CopperSword"));
        itemCopperHoe = (new ItemJacHoe(Config.getItemID("items.base.Copper.Hoe.id"), JACCopperTool).setItemName("JAC.CopperHoe"));
        itemCopperSpade = (new ItemJacSpade(Config.getItemID("items.base.Copper.Spade.id"), JACCopperTool).setItemName("JAC.CopperSpade"));
        itemCopperAxe = (new ItemJacAxe(Config.getItemID("items.base.Copper.Axe.id"), JACCopperTool).setItemName("JAC.CopperAxe"));
        
        itemSilverPickaxe = (new ItemJacPickaxe(Config.getItemID("items.base.Silver.Pickaxe.id"), JACSilverTool).setItemName("JAC.SilverPickaxe"));
        itemSilverSword = (new ItemJacSword(Config.getItemID("items.base.Silver.Sword.id"), JACSilverTool).setItemName("JAC.SilverSword"));
        itemSilverHoe = (new ItemJacHoe(Config.getItemID("items.base.Silver.Hoe.id"), JACSilverTool).setItemName("JAC.SilverHoe"));
        itemSilverSpade = (new ItemJacSpade(Config.getItemID("items.base.Silver.Spade.id"), JACSilverTool).setItemName("JAC.SilverSpade"));
        itemSilverAxe = (new ItemJacAxe(Config.getItemID("items.base.Silver.Axe.id"), JACSilverTool).setItemName("JAC.SilverAxe"));
        
        itemAluminumPickaxe = (new ItemJacPickaxe(Config.getItemID("items.base.Aluminum.Pickaxe.id"), JACAluminumTool).setItemName("JAC.AluminumPickaxe"));
        itemAluminumSword = (new ItemJacSword(Config.getItemID("items.base.Aluminum.Sword.id"), JACAluminumTool).setItemName("JAC.AluminumSword"));
        itemAluminumHoe = (new ItemJacHoe(Config.getItemID("items.base.Aluminum.Hoe.id"), JACAluminumTool).setItemName("JAC.AluminumHoe"));
        itemAluminumSpade = (new ItemJacSpade(Config.getItemID("items.base.Aluminum.Spade.id"), JACAluminumTool).setItemName("JAC.AluminumSpade"));
        itemAluminumAxe = (new ItemJacAxe(Config.getItemID("items.base.Aluminum.Axe.id"), JACAluminumTool).setItemName("JAC.AluminumAxe"));
    	
    }
}
