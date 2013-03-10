package org.Jacpack.TechUp.item;

import org.Jacpack.TechUp.creativetabs.CreativeTabsHandler;
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
        itemResource = new ItemParts(Config.getItemID("items.base.resource.id"), "ComingSoon");
        itemResource.setCreativeTab(CreativeTabsHandler.tabTechUpI);
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
        itemResource.addItem(14, 62, "item.JAC.Lead");
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
        itemResource.addItem(24, 68, "item.JAC.AluminumPlate");
        itemResource.addItem(25, 69, "item.JAC.TitaniumPlate");
        itemResource.addItem(26, 70, "item.JAC.CarbonPlating");
        itemAdvancedAlloy = new ItemStack(itemResource, 1, 23);
        itemAluminumPlate = new ItemStack(itemResource, 1, 24);
        itemTitaniumPlate = new ItemStack(itemResource, 1, 25);
        itemCarbonPlating = new ItemStack(itemResource, 1, 26);
        System.out.println("Done Loading Stage 2.");
    }

    public static void initArmor()
    {
    	System.out.println("Loading Armor...");
    	itemBronzeHelment = (new ItemBronzeArmor(742, JACBronzeArmor, 0)).setItemName("JAC.bronzehelmet");
    	itemBronzeChestplate = (new ItemBronzeArmor(743, JACBronzeArmor, 1)).setItemName("JAC.bronzechesplate");
    	itemBronzeLeggings = (new ItemBronzeArmor(744, JACBronzeArmor, 2)).setItemName("JAC.bronzeleggings");
    	itemBronzeBoots = (new ItemBronzeArmor(745, JACBronzeArmor, 3)).setItemName("JAC.bronzeboots");
    	
    	itemSteelHelmet = (new ItemSteelArmor(746, JACSteelArmor, 0)).setItemName("JAC.steelhelmet");
    	itemSteelChestplate = (new ItemSteelArmor(747, JACSteelArmor, 1)).setItemName("JAC.steelchestplate");
    	itemSteelLeggings = (new ItemSteelArmor(748, JACSteelArmor, 2)).setItemName("JAC.steelleggings");
    	itemSteelBoots = (new ItemSteelArmor(749, JACSteelArmor, 3)).setItemName("JAC.steelboots");
    	
    	itemCopperHelment = (new ItemCopperArmor(780, JACCopperArmor, 0)).setItemName("JAC.copperhelment");
    	itemCopperChestplate = (new ItemCopperArmor(781, JACCopperArmor, 1)).setItemName("JAC.copperchestplate");
    	itemCopperLeggings = (new ItemCopperArmor(782, JACCopperArmor, 2)).setItemName("JAC.copperleggings");
    	itemCopperBoots = (new ItemCopperArmor(783, JACCopperArmor, 3)).setItemName("JAC.copperboots");
    	
    	itemSilverHelment = (new ItemSilverArmor(784, JACSilverArmor, 0)).setItemName("JAC.silverhelmet");
    	itemSilverChestplate = (new ItemSilverArmor(785, JACSilverArmor, 1)).setItemName("JAC.silverchestplate");
    	itemSilverLeggings = (new ItemSilverArmor(786, JACSilverArmor, 2)).setItemName("JAC.silverleggings");
    	itemSilverBoots = (new ItemSilverArmor(787, JACSilverArmor, 3)).setItemName("JAC.silverboots");
    	
    	itemHazmatHelment = (new ItemHazmatArmor(788, JACHazmatArmor, 0)).setItemName("JAC.hazmathelmet");
    	itemHazmatChesplate = (new ItemHazmatArmor(789, JACHazmatArmor, 1)).setItemName("JAC.hazmatchestplate");
    	itemHazmatLeggings = (new ItemHazmatArmor(790, JACHazmatArmor, 2)).setItemName("JAC.hazmatleggings");
    	itemHazmatBoots = (new ItemHazmatArmor(791, JACHazmatArmor, 3)).setItemName("JAC.hazmatboots");
    	System.out.println("Done Loading Armor.");
    	
    }
    
    public static void initTools() {
    	itemBronzePickaxe = (new ItemJacPickaxe(792, JACBronzeTool).setItemName("JAC.BronzePickaxe"));
    	itemBronzeSword = (new ItemJacSword(793, JACBronzeTool).setItemName("JAC.BronzeSword"));
    	itemBronzeHoe = (new ItemJacHoe(794, JACBronzeTool).setItemName("JAC.BronzeHoe"));
    	itemBronzeSpade = (new ItemJacSpade(795, JACBronzeTool).setItemName("JAC.BronzeSpade"));
        itemBronzeAxe = (new ItemJacAxe(796, JACBronzeTool).setItemName("JAC.BronzeAxe"));
        
        itemSteelPickaxe = (new ItemJacPickaxe(797, JACSteelTool).setItemName("JAC.SteelPickaxe"));
        itemSteelSword = (new ItemJacSword(798, JACSteelTool).setItemName("JAC.SteelSword"));
        itemSteelHoe = (new ItemJacHoe(799, JACSteelTool).setItemName("JAC.SteelHoe"));
        itemSteelSpade = (new ItemJacSpade(800, JACSteelTool).setItemName("JAC.SteelSpade"));
        itemSteelAxe = (new ItemJacAxe(801, JACSteelTool).setItemName("JAC.SteelAxe"));
        
        itemCopperPickaxe = (new ItemJacPickaxe(802, JACCopperTool).setItemName("JAC.CopperPickaxe"));
        itemCopperSword = (new ItemJacSword(803, JACCopperTool).setItemName("JAC.CopperSword"));
        itemCopperHoe = (new ItemJacHoe(804, JACCopperTool).setItemName("JAC.CopperHoe"));
        itemCopperSpade = (new ItemJacSpade(805, JACCopperTool).setItemName("JAC.CopperSpade"));
        itemCopperAxe = (new ItemJacAxe(806, JACCopperTool).setItemName("JAC.CopperAxe"));
        
        itemSilverPickaxe = (new ItemJacPickaxe(807, JACSilverTool).setItemName("JAC.SilverPickaxe"));
        itemSilverSword = (new ItemJacSword(808, JACSilverTool).setItemName("JAC.SilverSword"));
        itemSilverHoe = (new ItemJacHoe(809, JACSilverTool).setItemName("JAC.SilverHoe"));
        itemSilverSpade = (new ItemJacSpade(810, JACSilverTool).setItemName("JAC.SilverSpade"));
        itemSilverAxe = (new ItemJacAxe(811, JACSilverTool).setItemName("JAC.SilverAxe"));
        
        itemAluminumPickaxe = (new ItemJacPickaxe(812, JACAluminumTool).setItemName("JAC.AluminumPickaxe"));
        itemAluminumSword = (new ItemJacSword(813, JACAluminumTool).setItemName("JAC.AluminumSword"));
        itemAluminumHoe = (new ItemJacHoe(814, JACAluminumTool).setItemName("JAC.AluminumHoe"));
        itemAluminumSpade = (new ItemJacSpade(815, JACAluminumTool).setItemName("JAC.AluminumSpade"));
        itemAluminumAxe = (new ItemJacAxe(816, JACAluminumTool).setItemName("JAC.AluminumAxe"));
    	
    }
}
