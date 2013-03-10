package ic2.api;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class Ic2Recipes
{
    public static void addCraftingRecipe(ItemStack result, Object... args)
    {
        try
        {
            Class.forName(getPackage() + ".core.AdvRecipe").getMethod("addAndRegister", ItemStack.class, Array.newInstance(Object.class, 0).getClass()).invoke(null, result, args);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addShapelessCraftingRecipe(ItemStack result, Object... args)
    {
        try
        {
            Class.forName(getPackage() + ".core.AdvShapelessRecipe").getMethod("addAndRegister", ItemStack.class, Array.newInstance(Object.class, 0).getClass()).invoke(null, result, args);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static List<Map.Entry<ItemStack, ItemStack> > getCompressorRecipes()
    {
        if (TileEntityCompressor_recipes == null)
        {
            try
            {
                TileEntityCompressor_recipes = (List<Map.Entry<ItemStack, ItemStack> >) Class.forName(getPackage() + ".core.block.machine.tileentity.TileEntityCompressor").getField("recipes").get(null);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return TileEntityCompressor_recipes;
    }

    public static void addCompressorRecipe(ItemStack input, ItemStack output)
    {
        getCompressorRecipes().add(new AbstractMap.SimpleEntry<ItemStack, ItemStack>(input, output));
    }

    public static ItemStack getCompressorOutputFor(ItemStack input, boolean adjustInput)
    {
        return getOutputFor(input, adjustInput, getCompressorRecipes());
    }

    public static List<Map.Entry<ItemStack, ItemStack> > getExtractorRecipes()
    {
        if (TileEntityExtractor_recipes == null)
        {
            try
            {
                TileEntityExtractor_recipes = (List<Map.Entry<ItemStack, ItemStack> >) Class.forName(getPackage() + ".core.block.machine.tileentity.TileEntityExtractor").getField("recipes").get(null);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return TileEntityExtractor_recipes;
    }

    public static void addExtractorRecipe(ItemStack input, ItemStack output)
    {
        getExtractorRecipes().add(new AbstractMap.SimpleEntry<ItemStack, ItemStack>(input, output));
    }

    public static ItemStack getExtractorOutputFor(ItemStack input, boolean adjustInput)
    {
        return getOutputFor(input, adjustInput, getExtractorRecipes());
    }

    public static List<Map.Entry<ItemStack, ItemStack> > getMaceratorRecipes()
    {
        if (TileEntityMacerator_recipes == null)
        {
            try
            {
                TileEntityMacerator_recipes = (List<Map.Entry<ItemStack, ItemStack> >) Class.forName(getPackage() + ".core.block.machine.tileentity.TileEntityMacerator").getField("recipes").get(null);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return TileEntityMacerator_recipes;
    }

    public static void addMaceratorRecipe(ItemStack input, ItemStack output)
    {
        getMaceratorRecipes().add(new AbstractMap.SimpleEntry<ItemStack, ItemStack>(input, output));
    }

    public static ItemStack getMaceratorOutputFor(ItemStack input, boolean adjustInput)
    {
        return getOutputFor(input, adjustInput, getMaceratorRecipes());
    }

    private static ItemStack getOutputFor(ItemStack input, boolean adjustInput, List<Map.Entry<ItemStack, ItemStack> > recipeList)
    {
        assert input != null;

        for (Map.Entry<ItemStack, ItemStack> entry: recipeList)
        {
            if (entry.getKey().isItemEqual(input) && input.stackSize >= entry.getKey().stackSize)
            {
                if (adjustInput)
                {
                    input.stackSize -= entry.getKey().stackSize;
                }

                return entry.getValue().copy();
            }
        }

        return null;
    }

    public static List<ItemStack> getRecyclerBlacklist()
    {
        if (TileEntityRecycler_blacklist == null)
        {
            try
            {
                TileEntityRecycler_blacklist = (List<ItemStack>) Class.forName(getPackage() + ".core.block.machine.tileentity.TileEntityRecycler").getField("blacklist").get(null);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return TileEntityRecycler_blacklist;
    }

    public static void addRecyclerBlacklistItem(ItemStack newBlacklistedItem)
    {
        getRecyclerBlacklist().add(newBlacklistedItem);
    }

    public static void addRecyclerBlacklistItem(Item newBlacklistedItem)
    {
        addRecyclerBlacklistItem(new ItemStack(newBlacklistedItem, 1, -1));
    }

    public static void addRecyclerBlacklistItem(Block newBlacklistedBlock)
    {
        addRecyclerBlacklistItem(new ItemStack(newBlacklistedBlock, 1, -1));
    }

    public static boolean isRecyclerInputBlacklisted(ItemStack itemStack)
    {
        for (ItemStack blackItem: getRecyclerBlacklist())
        {
            if (itemStack.isItemEqual(blackItem))
            {
                return true;
            }
        }

        return false;
    }

    public static List<Map.Entry<ItemStack, Float>> getScrapboxDrops()
    {
        try
        {
            return (List<Map.Entry<ItemStack, Float>>) Class.forName(getPackage() + ".core.item.ItemScrapbox").getMethod("getDropList").invoke(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addScrapboxDrop(ItemStack dropItem, float chance)
    {
        try
        {
            Class.forName(getPackage() + ".core.item.ItemScrapbox").getMethod("addDrop", ItemStack.class, float.class).invoke(null, dropItem, chance);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addScrapboxDrop(Item dropItem, float chance)
    {
        addScrapboxDrop(new ItemStack(dropItem, 1), chance);
    }

    public static void addScrapboxDrop(Block dropItem, float chance)
    {
        addScrapboxDrop(new ItemStack(dropItem), chance);
    }

    public static List<Map.Entry<ItemStack, Integer> > getMatterAmplifiers()
    {
        if (TileEntityMatter_amplifiers == null)
        {
            try
            {
                TileEntityMatter_amplifiers = (List<Map.Entry<ItemStack, Integer> >) Class.forName(getPackage() + ".core.block.machine.tileentity.TileEntityMatter").getField("amplifiers").get(null);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        return TileEntityMatter_amplifiers;
    }

    public static void addMatterAmplifier(ItemStack amplifierItem, int value)
    {
        getMatterAmplifiers().add(new AbstractMap.SimpleEntry<ItemStack, Integer>(amplifierItem, value));
    }

    public static void addMatterAmplifier(Item amplifierItem, int value)
    {
        addMatterAmplifier(new ItemStack(amplifierItem, 1, -1), value);
    }

    public static void addMatterAmplifier(Block amplifierItem, int value)
    {
        addMatterAmplifier(new ItemStack(amplifierItem, 1, -1), value);
    }

    private static String getPackage()
    {
        Package pkg = Ic2Recipes.class.getPackage();

        if (pkg != null)
        {
            return pkg.getName().substring(0, pkg.getName().lastIndexOf('.'));
        }
        else
        {
            return "ic2";
        }
    }

    private static List<Map.Entry<ItemStack, ItemStack> > TileEntityCompressor_recipes;
    private static List<Map.Entry<ItemStack, ItemStack> > TileEntityExtractor_recipes;
    private static List<Map.Entry<ItemStack, ItemStack> > TileEntityMacerator_recipes;
    private static List<ItemStack> TileEntityRecycler_blacklist;
    private static List<Map.Entry<ItemStack, Integer> > TileEntityMatter_amplifiers;
}
