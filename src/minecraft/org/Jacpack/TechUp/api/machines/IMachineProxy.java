package org.Jacpack.TechUp.api.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IMachineProxy
{
    String getTag(int paramInt);

    int getTexture(int paramInt, int paramInt2);

    TileEntity getTileEntity(int paramInt);

    List getCreativeList();

    Class getTileClass(int paramInt);

    void addItemInfo(ItemStack paramur, EntityPlayer paramqx, List paramList, boolean paramBoolean);
}
