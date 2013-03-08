package org.Jacpack.TechUp.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemParts extends Item
{
    String textureFile;
    HashMap names = new HashMap();
    HashMap icons = new HashMap();
    ArrayList valid = new ArrayList();

    public ItemParts(int i, String txf)
    {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.textureFile = txf;
    }

    public void addItem(int dmg, int icon, String name)
    {
        this.icons.put(Integer.valueOf(dmg), Integer.valueOf(icon));
        this.names.put(Integer.valueOf(dmg), name);
        this.valid.add(Integer.valueOf(dmg));
    }

    public String getItemNameIS(ItemStack ist)
    {
        String var2 = (String)this.names.get(Integer.valueOf(ist.getItemDamage()));

        if (var2 == null)
        {
            throw new IndexOutOfBoundsException();
        }
        else
        {
            return var2;
        }
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int i)
    {
        Integer tr = (Integer)this.icons.get(Integer.valueOf(i));

        if (tr == null)
        {
            throw new IndexOutOfBoundsException();
        }
        else
        {
            return tr.intValue();
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int id, CreativeTabs tab, List list)
    {
        Iterator var4 = this.valid.iterator();

        while (var4.hasNext())
        {
            Integer var5 = (Integer)var4.next();
            list.add(new ItemStack(this, 1, var5.intValue()));
        }
    }

    public String getTextureFile()
    {
        return this.textureFile;
    }
}
