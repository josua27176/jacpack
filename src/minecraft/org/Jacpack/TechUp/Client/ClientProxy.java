package org.Jacpack.TechUp.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

import org.Jacpack.TechUp.client.render.JacPack_Renderer;
import org.Jacpack.TechUp.util.Config;
import org.Jacpack.TechUp.util.misc.CommonProxy;
import org.Jacpack.TechUp.util.misc.Reference;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	public int getItemRarityColor(ItemStack var1)
    {
        return var1.getItem().getRarity(var1).rarityColor;
    }
	
	public String getItemDisplayName(ItemStack var1)
    {
        return var1.getItem().getItemDisplayName(var1);
    }
    
	public int getRenderId()
    {
        return RenderingRegistry.getNextAvailableRenderId();
    }
	
	@Override
    public void registerRenderers() {
		
		MinecraftForgeClient.preloadTexture(Reference.SPRITE_SHEET_LOCATION + Reference.BLOCK_SPRITE_SHEET);
		MinecraftForgeClient.preloadTexture(Reference.SPRITE_SHEET_LOCATION + Reference.ITEM_SPRITE_SHEET);
		
		new JacPack_Renderer();
		
	}
	
	@SuppressWarnings("resource")
	private void addCapeLists()
	{
		if (Config.mShowCapes && Config.mOnline)
		{
			try
			{
				Scanner tScanner = new Scanner((new URL("https://dl.dropbox.com/u/48633261/minecraft/Capes/CapeList.txt")).openStream());
				while (tScanner.hasNextLine()) {
					String tName = tScanner.nextLine();
					if (!Config.StaffJACCapeList.contains(tName)) {
						Config.StaffJACCapeList.add(tName);
					}
                }
			}
			catch (Throwable var3)
			{
				;
			}
		}
	}
}