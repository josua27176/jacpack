package org.Jacpack.TechUp.achievement;

import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.item.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import static org.Jacpack.TechUp.item.ModItems.*;

public class AchevementHandler
{
    public static void init()
    {
        AchevementHelper.registerAchievement(117027, "TechUpMakeTitanium", 0, 0, new ItemStack(ModBlocks.blockOres, 1, 7), AchievementList.openInventory);
        AchevementHelper.registerAchievement(117028, "TechUpMakeAluminum", 4, 0, new ItemStack(ModBlocks.blockOres, 1, 8), "TechUpMakeTitanium");
        AchevementHelper.addCraftingAchievement(new ItemStack(ModBlocks.blockOres, 1, 7), "TechUpMakeTitanium");
        AchevementHelper.addCraftingAchievement(new ItemStack(ModBlocks.blockOres, 1, 8), "TechUpMakeAluminum");
        AchievementPage.registerAchievementPage(AchevementHelper.achievepage);
    }
}
