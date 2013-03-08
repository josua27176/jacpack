package xfinity.jacpack.common;

import net.minecraftforge.client.MinecraftForgeClient;

public class CommonProxy 
{
	//registers item textures
	public static String ITEMS = "/xfinity/jacpack/Items.png";
	//registers block textures
	public static String BLOCKS = "/xfinity/jacpack/Blocks.png";
			
	public void registerRenders()
	{
		MinecraftForgeClient.preloadTexture(CommonProxy.ITEMS);
	}
}
