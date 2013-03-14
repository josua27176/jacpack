package org.Jacpack.TechUp.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.crafting.IBlastFurnaceRecipe;
import org.Jacpack.TechUp.crafting.TechUpCraftingManager;
import org.Jacpack.TechUp.tileentitys.MultiBlockPattern;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class BlastFurnaceRecipeHandler extends FurnaceRecipeHandler {
	
	static final ItemStack[] afuels = (ItemStack[])TechUpCraftingManager.blastFurnace.getFuels().toArray(new ItemStack[0]);
	  public static Class guiclass;

	  public String getRecipeName()
	  {
	    return "Blast Furnace";
	  }

	  public void loadTransferRects()
	  {
	    this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 23, 24, 18), "railcraft.blastfurnace", new Object[0]));
	  }

	  public Class getGuiClass()
	  {
	    return guiclass;
	  }

	  public void loadCraftingRecipes(String outputId, Object[] results)
	  {
	    if ((outputId.equals("railcraft.blastfurnace")) && (getClass() == BlastFurnaceRecipeHandler.class))
	    {
	      List<IBlastFurnaceRecipe> recipes = TechUpCraftingManager.blastFurnace.getRecipes();
	      for (IBlastFurnaceRecipe recipe : recipes)
	      {
	        this.arecipes.add(new CachedBlastRecipe(recipe));
	      }
	    }
	    else
	    {
	      super.loadCraftingRecipes(outputId, results);
	    }
	  }

	  public void loadUsageRecipes(ItemStack ingred)
	  {
		List<IBlastFurnaceRecipe> recipes = TechUpCraftingManager.blastFurnace.getRecipes();
	    for (IBlastFurnaceRecipe recipe : recipes)
	    {
	      if (NEIClientUtils.areStacksSameTypeCrafting(recipe.getInput(), ingred))
	      {
	        this.arecipes.add(new CachedBlastRecipe(recipe));
	      }
	    }
	  }

	  public void loadCraftingRecipes(ItemStack result)
	  {
		List<IBlastFurnaceRecipe> recipes = TechUpCraftingManager.blastFurnace.getRecipes();
	    for (IBlastFurnaceRecipe recipe : recipes)
	    {
	      if (NEIClientUtils.areStacksSameType(result, recipe.getOutput()))
	      {
	        this.arecipes.add(new CachedBlastRecipe(recipe));
	      }
	    }
	  }

	  public String getGuiTexture()
	  {
	    return "/gui/furnace.png";
	  }

	  public void drawExtras(GuiContainerManager gui, int recipe)
	  {
	    drawProgressBar(gui, 51, 25, 176, 0, 14, 14, 48, 7);
	    drawProgressBar(gui, 74, 23, 176, 14, 24, 16, 48, 0);
	  }

	  public class CachedBlastRecipe extends TemplateRecipeHandler.CachedRecipe
	  {
	    IBlastFurnaceRecipe funace;

	    public CachedBlastRecipe(IBlastFurnaceRecipe base)
	    {
	      super();
	      this.funace = base;
	    }

	    public PositionedStack getResult()
	    {
	      return new PositionedStack(this.funace.getOutput(), 111, 24);
	    }

	    public ArrayList getIngredients()
	    {
	      ArrayList stacks = new ArrayList();
	      stacks.add(new PositionedStack(this.funace.getInput(), 51, 6));
	      return getCycledIngredients(BlastFurnaceRecipeHandler.this.cycleticks / 20, stacks);
	    }

	    public PositionedStack getOtherStack()
	    {
	      return new PositionedStack(BlastFurnaceRecipeHandler.afuels[(BlastFurnaceRecipeHandler.this.cycleticks / 48 % BlastFurnaceRecipeHandler.afuels.length)], 51, 42);
	    }
	  }
}
