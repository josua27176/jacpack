package ic2.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWrenchable
{
    boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side);

    short getFacing();

    void setFacing(short facing);

    boolean wrenchCanRemove(EntityPlayer entityPlayer);

    float getWrenchDropRate();

    ItemStack getWrenchDrop(EntityPlayer entityPlayer);
}
