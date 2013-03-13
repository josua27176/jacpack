package org.Jacpack.TechUp.gui.containers;

import java.util.logging.Level;

import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.gui.EnumGui;
import org.Jacpack.TechUp.tileentitys.TileBlastFurnace;
import org.Jacpack.TechUp.tileentitys.TileMultiBlock;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class FactoryContainer
{
    public static Container build(EnumGui gui, InventoryPlayer inv, Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        else if (obj instanceof TileMultiBlock && !((TileMultiBlock)obj).isStructureValid())
        {
            return null;
        }
        else
        {
            try
            {
                switch ($SwitchMap$org$Jacpack$TechUp$gui$EnumGui[gui.ordinal()])
                {
            		case 1:
            			return new ContainerBlastFurnace(inv, (TileBlastFurnace)obj);
            		
            		case 14:
            			return new ContainerBlastFurnace(inv, (TileBlastFurnace)obj);
            		
                	case 17:
                		return new ContainerBlastFurnace(inv, (TileBlastFurnace)obj);

                    default:
                        return null;
                }
            }
            catch (ClassCastException ex)
            {
                Game.log(Level.WARNING, "Error when attempting to build gui container {0}: {1}", new Object[] {gui, ex});
                return null;
            }
        }
    }
    
    static final int[] $SwitchMap$org$Jacpack$TechUp$gui$EnumGui = new int[EnumGui.values().length];

    static
    {
        try
        {
        	$SwitchMap$org$Jacpack$TechUp$gui$EnumGui[EnumGui.BLAST_FURNACE.ordinal()] = 1;
        }
        catch (NoSuchFieldError var17)
        {
            ;
        }
    }
}
