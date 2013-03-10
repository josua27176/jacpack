package ic2.api;

import net.minecraft.item.ItemStack;

public interface IBoxable
{
    public abstract boolean canBeStoredInToolbox(ItemStack itemstack);
}
