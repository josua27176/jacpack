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
		//----------------------------------------------------T E S T I N G----------------------------------------------------------------

		//----------------------------------------------------R E N D E R I N G B L O C K S------------------------------------------------
		GameRegistry.registerWorldGenerator(new WorldGeneratorJacPack());
		//----------------------------------------------------B L O C K S------------------------------------------------------------------
		BlockOre = new BlockOre(1001, 0).setBlockName("TestBlock").setCreativeTab(CreativeTabs.tabBlock).setHardness(5).setResistance(20);
		GameRegistry.registerBlock(BlockOre);
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
	
	//BLOCKS
	public static Block BlockOre;
}
