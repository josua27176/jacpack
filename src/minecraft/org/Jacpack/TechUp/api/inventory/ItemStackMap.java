package org.Jacpack.TechUp.api.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.item.ItemStack;

public class ItemStackMap implements Map
{
    private Map map = new HashMap();

    public Object put(ItemStack var1, Object var2)
    {
        return this.map.put(new ItemStackMap$KeyWrapper(var1), var2);
    }

    public Collection values()
    {
        return this.map.values();
    }

    public int size()
    {
        return this.map.size();
    }

    public Object remove(Object var1)
    {
        return var1 instanceof ItemStack ? this.map.remove(new ItemStackMap$KeyWrapper((ItemStack)var1)) : null;
    }

    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    public int hashCode()
    {
        return this.map.hashCode();
    }

    public Object get(Object var1)
    {
        return var1 instanceof ItemStack ? this.map.get(new ItemStackMap$KeyWrapper((ItemStack)var1)) : null;
    }

    public boolean equals(Object var1)
    {
        return var1 instanceof ItemStackMap ? this.map.equals(((ItemStackMap)var1).map) : false;
    }

    public boolean containsValue(Object var1)
    {
        return this.map.containsValue(var1);
    }

    public boolean containsKey(Object var1)
    {
        return var1 instanceof ItemStack ? this.map.containsKey(new ItemStackMap$KeyWrapper((ItemStack)var1)) : false;
    }

    public void clear()
    {
        this.map.clear();
    }

    public void putAll(Map var1)
    {
        Iterator var2 = var1.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();
            this.put((ItemStack)var3.getKey(), var3.getValue());
        }
    }

    public Set keySet()
    {
        HashSet var1 = new HashSet();
        Iterator var2 = this.map.keySet().iterator();

        while (var2.hasNext())
        {
            ItemStackMap$KeyWrapper var3 = (ItemStackMap$KeyWrapper)var2.next();
            var1.add(var3.getStack());
        }

        return var1;
    }

    public Set entrySet()
    {
        HashSet var1 = new HashSet();
        Iterator var2 = this.map.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();
            var1.add(new ItemStackMap$EntryWrapper(this, var3));
        }

        return var1;
    }

    public Object put(Object var1, Object var2)
    {
        return this.put((ItemStack)var1, var2);
    }
}
