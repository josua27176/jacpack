package org.Jacpack.TechUp.api.items;

import java.util.Map;
import java.util.TreeMap;
import net.minecraft.item.ItemStack;

public final class ItemRegistry
{

    private static final Map<String, ItemStack> registry = new TreeMap<String, ItemStack>();

    private ItemRegistry()
    {
    }

    public static ItemStack getItem(String tag, int qty)
    {
        ItemStack stack = registry.get(tag);
        if(stack != null) {
            stack = stack.copy();
            stack.stackSize = qty;
        }
        return stack;
    }

    public static void registerItem(String tag, ItemStack item)
    {
        tag = tag.replace("rc.", "");
        registry.put(tag, item);
    }

    public static void printItemTags()
    {
        System.out.println();
        System.out.println("Printing all registered Railcraft items:");
        for(String tag : registry.keySet()) {
            System.out.println(tag);
        }
        System.out.println();
    }

    public static Map<String, ItemStack> getItems()
    {
        return registry;
    }
}
