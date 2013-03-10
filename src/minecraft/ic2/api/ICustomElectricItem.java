package ic2.api;

import net.minecraft.item.ItemStack;

public interface ICustomElectricItem extends IElectricItem
{
    public int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate);

    public int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate);

    public boolean canUse(ItemStack itemStack, int amount);

    public boolean canShowChargeToolTip(ItemStack itemStack);
}
