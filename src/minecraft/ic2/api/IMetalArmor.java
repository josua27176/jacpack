package ic2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IMetalArmor
{
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player);
}
