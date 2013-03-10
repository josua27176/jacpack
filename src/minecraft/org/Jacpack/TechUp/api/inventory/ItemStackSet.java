package org.Jacpack.TechUp.api.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.Jacpack.TechUp.api.JACTools;

import net.minecraft.item.ItemStack;

public class ItemStackSet implements Set
{
    private List set = new ArrayList();

    public int size()
    {
        return this.set.size();
    }

    public boolean isEmpty()
    {
        return this.set.isEmpty();
    }

    public boolean contains(Object var1)
    {
        if (!(var1 instanceof ItemStack))
        {
            return false;
        }
        else
        {
            ItemStack var2 = (ItemStack)var1;
            Iterator var3 = this.set.iterator();
            ItemStack var4;

            do
            {
                if (!var3.hasNext())
                {
                    return false;
                }

                var4 = (ItemStack)var3.next();
            }
            while (!JACTools.isItemEqual(var4, var2));

            return true;
        }
    }

    public Iterator iterator()
    {
        return this.set.iterator();
    }

    public Object[] toArray()
    {
        return this.set.toArray();
    }

    public Object[] toArray(Object[] var1)
    {
        return this.set.toArray(var1);
    }

    public boolean add(ItemStack var1)
    {
        if (this.contains(var1))
        {
            return false;
        }
        else
        {
            this.set.add(var1);
            return true;
        }
    }

    public boolean remove(Object var1)
    {
        if (!(var1 instanceof ItemStack))
        {
            return false;
        }
        else
        {
            boolean var2 = false;
            ItemStack var3 = (ItemStack)var1;
            Iterator var4 = this.set.iterator();

            while (var4.hasNext())
            {
                ItemStack var5 = (ItemStack)var4.next();

                if (JACTools.isItemEqual(var5, var3))
                {
                    var4.remove();
                    var2 = true;
                }
            }

            return var2;
        }
    }

    public boolean containsAll(Collection var1)
    {
        Iterator var2 = var1.iterator();
        Object var3;

        do
        {
            if (!var2.hasNext())
            {
                return true;
            }

            var3 = var2.next();

            if (!(var3 instanceof ItemStack))
            {
                return false;
            }
        }
        while (this.contains((ItemStack)var3));

        return false;
    }

    public boolean addAll(Collection var1)
    {
        boolean var2 = false;
        ItemStack var4;

        for (Iterator var3 = var1.iterator(); var3.hasNext(); var2 |= this.add(var4))
        {
            var4 = (ItemStack)var3.next();
        }

        return var2;
    }

    public boolean retainAll(Collection var1)
    {
        boolean var2 = false;
        Iterator var3 = this.set.iterator();

        while (var3.hasNext())
        {
            ItemStack var4 = (ItemStack)var3.next();

            if (!var1.contains(var3))
            {
                var3.remove();
                var2 = true;
            }
        }

        return var2;
    }

    public boolean removeAll(Collection var1)
    {
        boolean var2 = false;
        Iterator var3 = this.set.iterator();

        while (var3.hasNext())
        {
            ItemStack var4 = (ItemStack)var3.next();

            if (var1.contains(var3))
            {
                var3.remove();
                var2 = true;
            }
        }

        return var2;
    }

    public void clear()
    {
        this.set.clear();
    }

    public boolean add(Object var1)
    {
        return this.add((ItemStack)var1);
    }
}
