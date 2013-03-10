package org.Jacpack.TechUp.api.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IMachineProxy
{
    String getTag(int var1);

    int getTexture(int var1, int var2);

    TileEntity getTileEntity(int var1);

    List getCreativeList();

    Class getTileClass(int var1);

    void addItemInfo(ItemStack var1, EntityPlayer var2, List var3, boolean var4);
}
