package org.Jacpack.TechUp.api.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class MachineProxyAlpha implements IMachineProxy
{
    public String getTag(int var1)
    {
        return EnumMachineAlpha.fromId(var1).getTag();
    }

    public int getTexture(int var1, int var2)
    {
        return EnumMachineAlpha.fromId(var1).getTexture(var2);
    }

    public TileEntity getTileEntity(int var1)
    {
        return EnumMachineAlpha.fromId(var1).getTileEntity();
    }

    public Class getTileClass(int var1)
    {
        return EnumMachineAlpha.fromId(var1).getTileClass();
    }

    public List getCreativeList()
    {
        return EnumMachineAlpha.getCreativeList();
    }

    public void addItemInfo(ItemStack var1, EntityPlayer var2, List var3, boolean var4)
    {
        EnumMachineAlpha.fromId(var1.getItemDamage()).addItemInfo(var1, var2, var3, var4);
    }
}
