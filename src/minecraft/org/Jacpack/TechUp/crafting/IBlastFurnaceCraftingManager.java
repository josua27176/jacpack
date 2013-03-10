package org.Jacpack.TechUp.crafting;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IBlastFurnaceCraftingManager
{
    void addRecipe(int var1, int var2, int var3, ItemStack var4);

    void addRecipe(int var1, int var2, ItemStack var3);

    void addRecipe(ItemStack var1, boolean var2, int var3, ItemStack var4);

    List getFuels();

    IBlastFurnaceRecipe getRecipe(int var1, int var2);

    IBlastFurnaceRecipe getRecipe(ItemStack var1);

    List getRecipes();
}
