package org.Jacpack.TechUp.nei;

import java.util.logging.Level;

import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.item.ModItems;

import codechicken.nei.MultiItemRange;
import codechicken.nei.api.API;

public class NeiHandler {
	
	public static void init() {
		try {
		      BlastFurnaceRecipeHandler.guiclass = Class.forName("org.Jacpack.TechUp.client.gui.GuiBlastFurnace");
		      System.out.println("Hello");
		    } catch (ClassNotFoundException ex) {
		    	System.out.println("Bye");
		      //Game.log(Level.FINE, "Failed to detect GUIS from Railcraft", new Object[0]);
		    }
		API.registerRecipeHandler(new BlastFurnaceRecipeHandler());
	    API.registerUsageHandler(new BlastFurnaceRecipeHandler());
	    
	    MultiItemRange parts = new MultiItemRange();
	    parts.add(new ItemStack(ModItems.itemResource, 1, 6));
	    API.addSetRange("TechUp.Parts", parts);
	}
	
}
