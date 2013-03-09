package xfinity.jacpack.common;

import net.minecraft.item.Item;

// ItemJac is sub class of SuperClass Item.java
public class ItemPlutoniumReactor extends Item
{

	// Below takes item id from ItemJac.java and sends ID to super class Item.Java
	public ItemPlutoniumReactor(int id, int texture) 
	{
		super(id);
		this.iconIndex = texture;
	}
	
	public String getTextureFile()
	{
		return CommonProxy.ITEMS;
	}
	
	

}
