package org.Jacpack.TechUp.api;

import net.minecraftforge.common.ForgeDirection;

class JACTools$1
{
    static final int[] $SwitchMap$net$minecraftforge$common$ForgeDirection = new int[ForgeDirection.values().length];

    static
    {
        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.UP.ordinal()] = 1;
        }
        catch (NoSuchFieldError var6)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.DOWN.ordinal()] = 2;
        }
        catch (NoSuchFieldError var5)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.EAST.ordinal()] = 3;
        }
        catch (NoSuchFieldError var4)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.WEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError var3)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.NORTH.ordinal()] = 5;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.SOUTH.ordinal()] = 6;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
