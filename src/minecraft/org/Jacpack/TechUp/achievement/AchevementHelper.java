package org.Jacpack.TechUp.achievement;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import org.Jacpack.TechUp.crafting.CraftingHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchevementHelper {
	
	private static HashMap achievelist = new HashMap();
	public static Comparator itemStackComparator = new CraftingHelper();
	public static AchievementPage achievepage = new AchievementPage("Time-Traveler", new Achievement[0]);
	private static TreeMap achievebycraft = new TreeMap(itemStackComparator);
	
	public static void registerAchievement(int var0, String var1, int var2, int var3, ItemStack var4, Object var5, boolean var6)
    {
        Achievement var7 = null;

        if (var5 instanceof Achievement)
        {
            var7 = (Achievement)var5;
        }
        else if (var5 instanceof String)
        {
            var7 = (Achievement)achievelist.get((String)var5);
        }

        Achievement var8 = new Achievement(var0, var1, var2, var3, var4, var7);
        var8.registerAchievement();

        if (var6)
        {
            var8.setSpecial();
        }

        achievelist.put(var1, var8);
        achievepage.getAchievements().add(var8);
    }

    public static void registerAchievement(int var0, String var1, int var2, int var3, ItemStack var4, Object var5)
    {
        registerAchievement(var0, var1, var2, var3, var4, var5, false);
    }
    
    public static void addCraftingAchievement(ItemStack var0, String var1)
    {
        Achievement var2 = (Achievement)achievelist.get(var1);

        if (var2 != null)
        {
            achievebycraft.put(var0, var2);
        }
    }
    
    public static void triggerAchievement(EntityPlayer var0, String var1)
    {
        Achievement var2 = (Achievement)achievelist.get(var1);

        if (var2 != null)
        {
            var0.triggerAchievement(var2);
        }
    }

    public static void onCrafting(EntityPlayer var0, ItemStack var1)
    {
        Achievement var2 = (Achievement)achievebycraft.get(var1);

        if (var2 != null)
        {
            var0.triggerAchievement(var2);
        }
    }
	
}
