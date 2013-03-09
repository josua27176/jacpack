package xfinity.jacpack.common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "jac", name = "JacPack", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class JacpackMain 
{
	@Instance("jac")
	public static JacpackMain instance;
	
	@SidedProxy(clientSide = "xfinity.jacpack.client.ClientProxy", serverSide = "xfinity.jacpack.common.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		
	}
	
	// REGISTER ITEMS AND BLOCKS RECIPES AND EVERYTHING LOLOL
	@Init
	public void init(FMLInitializationEvent event)
	{
		//----------------------------------------------------I T E M S--------------------------------------------------------------------
		godstick = new ItemJac(1000, 0).setItemName("GodStick").setMaxDamage(999).setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat);
		LanguageRegistry.addName(godstick, "Godstick [WIP] -xfin");
		
		
		Frame = new ItemFrame(1001, 0).setItemName("Frame").setMaxStackSize(64).setCreativeTab(CreativeTabs.tabTools);
		LanguageRegistry.addName(Frame, "Frame");
		
		FluxCapicitor = new ItemFluxCapicitor(1002, 0).setItemName("Flux Capicitor").setMaxStackSize(64).setCreativeTab(CreativeTabs.tabAllSearch);
		LanguageRegistry.addName(FluxCapicitor, "Flux Capicitor");
		
		Glass = new ItemGlass (1003, 0).setItemName("JAC Glass").setMaxStackSize(64).setCreativeTab(CreativeTabs.tabAllSearch);
		LanguageRegistry.addName(Glass, "Jac Glass");
		
		PlutoniumReactor = new ItemPlutoniumReactor (1004, 0).setItemName("Plutonium Reactor").setMaxStackSize(64).setCreativeTab(CreativeTabs.tabMisc);
		LanguageRegistry.addName(PlutoniumReactor, "Plutonium Reactor");
		
		Dashboard = new ItemDashboard (1005, 0).setItemName("Dashboard").setMaxStackSize(64).setCreativeTab(CreativeTabs.tabMaterials);
		LanguageRegistry.addName(Dashboard, "Dashbaord");
		
		Seats = new ItemSeats (1006, 0).setItemName("Seats").setMaxStackSize(64).setCreativeTab(CreativeTabs.tabMaterials);
		LanguageRegistry.addName(Seats, "Seats");
		
		Engine = new ItemEngine (1007, 0).setItemName("Engine").setMaxStackSize(64).setCreativeTab(CreativeTabs.tabMisc);
		LanguageRegistry.addName(Engine, "Engine");
		
		//----------------------------------------------------T E S T I N G----------------------------------------------------------------

		//----------------------------------------------------R E N D E R I N G B L O C K S------------------------------------------------
		GameRegistry.registerWorldGenerator(new WorldGeneratorJacPack());
		//----------------------------------------------------B L O C K S------------------------------------------------------------------
		BlockOre = new BlockOre(1001, 0).setBlockName("zmnaster").setCreativeTab(CreativeTabs.tabBlock).setHardness(5).setResistance(20);
		GameRegistry.registerBlock(BlockOre, BlockOre.getBlockName());
		LanguageRegistry.addName(BlockOre, "TestBlock");
		
		//----------------------------------------------------R E C I P E S----------------------------------------------------------------
		// Give 1 Craftingbench of Godsticks get 10 dirt
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(Block.dirt, 10), true, new Object[]{
			"CCC", "CCC", "CCC", Character.valueOf('C'), godstick
		}));
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	//ITEMS
	public static Item godstick;
	public static Item Frame;
	public static Item FluxCapicitor;
	public static Item Glass;
	public static Item PlutoniumReactor;
	public static Item Dashboard;
	public static Item Seats;
	public static Item Engine;
	
	//BLOCKS
	public static Block BlockOre;
}
