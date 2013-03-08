package org.Jacpack.TechUp;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class CondensedBlockItem extends ItemBlock {

	//String that holds the names of all the ores
	private final String subNames[];
	
	public CondensedBlockItem(int id, String[] subNames) {
		
		super(id);
		this.subNames = subNames;
	}

	
	//Rather than returning only the name of the first ore return the correct one
	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		return itemstack.getItemDamage() < subNames.length ? subNames[itemstack.getItemDamage()] : null;
	}
	
}
