package xfinity.jacpack.common;

import net.minecraft.item.Item;

// ItemJac is sub class of SuperClass Item.java
public class ItemJac extends Item
{

	// Below takes item id from ItemJac.java and sends ID to super class Item.Java
	public ItemJac(int id, int texture) 
	{
		super(id);
		this.iconIndex = texture;
	}
	
	public String getTextureFile()
	{
		return CommonProxy.ITEMS;
	}
	
	

}