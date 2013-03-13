package org.Jacpack.TechUp.client.gui;

import java.util.logging.Level;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;

import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.gui.EnumGui;
import org.Jacpack.TechUp.tileentitys.TileBlastFurnace;
import org.Jacpack.TechUp.tileentitys.TileMultiBlock;

public class FactoryGui
{
    public static GuiScreen build(EnumGui gui, InventoryPlayer inv, Object obj)
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
                        return new GuiBlastFurnace(inv, (TileBlastFurnace)obj);
                        
                    case 17:
                        return new GuiBlastFurnace(inv, (TileBlastFurnace)obj);
                    
                    default:
                        return null;
                }
            }
            catch (ClassCastException var4)
            {
                Game.log(Level.WARNING, "Error when attempting to build gui {0}: {1}", new Object[] {gui, var4});
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
        catch (NoSuchFieldError var29)
        {
            ;
        }
    }
}
