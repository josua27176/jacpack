package org.Jacpack.TechUp.api.machines;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class MachineProxyAlpha implements IMachineProxy
{
    public String getTag(int meta)
    {
        return EnumMachineAlpha.fromId(meta).getTag();
    }

    public int getTexture(int meta, int side)
    {
        return EnumMachineAlpha.fromId(meta).getTexture(side);
    }

    public TileEntity getTileEntity(int meta)
    {
        return EnumMachineAlpha.fromId(meta).getTileEntity();
    }

    public Class getTileClass(int meta)
    {
        return EnumMachineAlpha.fromId(meta).getTileClass();
    }

    public List getCreativeList()
    {
        return EnumMachineAlpha.getCreativeList();
    }

    public void addItemInfo(ItemStack stack, EntityPlayer player, List info, boolean adv)
    {
        EnumMachineAlpha.fromId(stack.getItemDamage()).addItemInfo(stack, player, info, adv);
    }
}
