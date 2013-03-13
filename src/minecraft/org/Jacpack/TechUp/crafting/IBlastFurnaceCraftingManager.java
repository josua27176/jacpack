package org.Jacpack.TechUp.crafting;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IBlastFurnaceCraftingManager
{
    void addRecipe(int paramInt1, int paramInt2, int paramInt3, ItemStack paramur);

    void addRecipe(int paramInt1, int paramInt2, ItemStack paramur);

    void addRecipe(ItemStack paramur1, boolean paramBoolean, int paramInt, ItemStack paramur2);

    List getFuels();

    IBlastFurnaceRecipe getRecipe(int paramInt1, int paramInt2);

    IBlastFurnaceRecipe getRecipe(ItemStack paramur);

    List getRecipes();
}
