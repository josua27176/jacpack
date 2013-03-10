package org.Jacpack.TechUp.api.inventory;
import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.items.IItemType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecartRegistry;

public enum EnumItemType implements IItemType
{
    FUEL;

    public static void initialize()
    {
        EnumItemType[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2)
        {
            EnumItemType var3 = var0[var2];
            IItemType.types.put(var3.name(), var3);
        }
    }

    public boolean isItemType(ItemStack var1)
    {
        if (var1 == null)
        {
            return false;
        }
        else
        {
            switch (EnumItemType$1.$SwitchMap$railcraft$common$util$inventory$EnumItemType[this.ordinal()])
            {
                case 1:
                    return JACTools.getItemBurnTime(var1) > 0;
                case 5:
                    return var1.getItem() instanceof ItemFood || var1.itemID == Item.wheat.itemID || var1.getItem() instanceof ItemSeeds;

                default:
                    return false;
            }
        }
    }
}
