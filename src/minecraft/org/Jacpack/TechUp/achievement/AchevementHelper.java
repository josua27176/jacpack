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
	
	public static void registerAchievement(int id, String name, int x, int y, ItemStack icon, Object require, boolean special)
	  {
	    Achievement acreq = null;
	    if ((require instanceof Achievement))
	      acreq = (Achievement)require;
	    else if ((require instanceof String)) {
	      acreq = (Achievement)achievelist.get((String)require);
	    }
	    Achievement ac = new Achievement(id, name, x, y, icon, acreq);
	    ac.registerAchievement();
	    if (special) ac.setSpecial();
	    achievelist.put(name, ac);
	    achievepage.getAchievements().add(ac);
	  }
	
    public static void registerAchievement(int id, String name, int x, int y, ItemStack icon, Object require)
    {
        registerAchievement(id, name, x, y, icon, require, false);
      }
    
    public static void addCraftingAchievement(ItemStack target, String id)
    {
        Achievement ac = (Achievement)achievelist.get(id);
        if (ac == null) return;
        achievebycraft.put(target, ac);
      }
    
    public static void triggerAchievement(EntityPlayer var0, String var1)
    {
        Achievement var2 = (Achievement)achievelist.get(var1);

        if (var2 != null)
        {
            var0.triggerAchievement(var2);
        }
    }
    
    public static void onCrafting(EntityPlayer player, ItemStack ist) {
        Achievement ac = (Achievement)achievebycraft.get(ist);
        if (ac == null) return;
        player.triggerAchievement(ac);
      }
	
}
