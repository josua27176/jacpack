package org.Jacpack.TechUp.util.misc;

import net.minecraft.item.ItemStack;

public class CommonProxy {
	
    public void registerRenderers() {
    	
    }
    
    public int getRenderId() {
    	return 0;
    }
    
    public String getItemDisplayName(ItemStack var1)
    {
        return var1 == null ? "" : var1.getItemName();
    }

    public int getItemRarityColor(ItemStack var1)
    {
        return 15;
    }
    
    public void initClient()
    {
    	
    }
    
}
