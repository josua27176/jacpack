package org.Jacpack.TechUp.crafting.machines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.crafting.IBlastFurnaceCraftingManager;
import org.Jacpack.TechUp.crafting.IBlastFurnaceRecipe;
import org.Jacpack.TechUp.crafting.TechUpCraftingManager;

public class BlastFurnaceCraftingManager implements IBlastFurnaceCraftingManager
{
    private final List recipes = new ArrayList();

    public static IBlastFurnaceCraftingManager getInstance()
    {
        return TechUpCraftingManager.blastFurnace;
    }

    public List getFuels()
    {
        ArrayList var1 = new ArrayList();
        var1.add(new ItemStack(Item.coal, 1, 1));
        return var1;
    }

    public List getRecipes()
    {
        ArrayList var1 = new ArrayList(this.recipes);
        return var1;
    }

    public void addRecipe(int var1, int var2, int var3, ItemStack var4)
    {
        if (var4 != null)
        {
            this.recipes.add(new BlastFurnaceRecipe(var1, var2, var3, var4));
        }
    }

    public void addRecipe(int var1, int var2, ItemStack var3)
    {
        this.addRecipe(var1, -1, var2, var3);
    }

    public void addRecipe(ItemStack var1, boolean var2, int var3, ItemStack var4)
    {
        if (var1 != null && var4 != null)
        {
            this.recipes.add(new BlastFurnaceRecipe(var1.itemID, var2 ? var1.getItemDamage() : -1, var3, var4));
        }
    }

    public IBlastFurnaceRecipe getRecipe(int var1, int var2)
    {
        Iterator var3 = this.recipes.iterator();
        BlastFurnaceRecipe var4;

        do
        {
            if (!var3.hasNext())
            {
                var3 = this.recipes.iterator();

                do
                {
                    if (!var3.hasNext())
                    {
                        return null;
                    }

                    var4 = (BlastFurnaceRecipe)var3.next();
                }
                while (BlastFurnaceRecipe.access$000(var4) != var1 || BlastFurnaceRecipe.access$100(var4) != -1);

                return var4;
            }

            var4 = (BlastFurnaceRecipe)var3.next();
        }
        while (BlastFurnaceRecipe.access$000(var4) != var1 || BlastFurnaceRecipe.access$100(var4) != var2);

        return var4;
    }

    public IBlastFurnaceRecipe getRecipe(ItemStack var1)
    {
        return var1 == null ? null : this.getRecipe(var1.itemID, var1.getItemDamage());
    }
    
    public static class BlastFurnaceRecipe implements IBlastFurnaceRecipe
    {
        private final int inputId;
        private final int inputDamage;
        private final int cookTime;
        private final ItemStack output;

        public BlastFurnaceRecipe(int var1, int var2, int var3, ItemStack var4)
        {
            this.inputId = var1;
            this.inputDamage = var2;
            this.cookTime = var3;
            this.output = var4;
        }

        public boolean isRoomForOutput(ItemStack var1)
        {
            return var1 == null || this.output == null || var1.isItemEqual(this.output) && var1.stackSize + this.output.stackSize <= this.output.getMaxStackSize();
        }

        public ItemStack getInput()
        {
            return new ItemStack(this.inputId, 1, this.inputDamage);
        }

        public ItemStack getOutput()
        {
            return this.output == null ? null : this.output.copy();
        }

        public int getOutputStackSize()
        {
            return this.output == null ? 0 : this.output.stackSize;
        }

        public int getCookTime()
        {
            return this.cookTime;
        }

        static int access$000(BlastFurnaceRecipe var0)
        {
            return var0.inputId;
        }

        static int access$100(BlastFurnaceRecipe var0)
        {
            return var0.inputDamage;
        }
    }
}
