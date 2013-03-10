package org.Jacpack.TechUp.gui;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import java.util.logging.Level;

import org.Jacpack.TechUp.TechUp;
import org.Jacpack.TechUp.api.old.Game;
import org.Jacpack.TechUp.client.gui.FactoryGui;
import org.Jacpack.TechUp.gui.containers.FactoryContainer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    public static void openGui(EnumGui var0, EntityPlayer var1, World var2, int var3, int var4, int var5)
    {
        if (Game.isHost(var2))
        {
            if (var0.hasContainer())
            {
                var1.openGui(TechUp.getMod(), var0.getId(), var2, var3, var4, var5);
            }
        }
        else if (!var0.hasContainer())
        {
            TileEntity var6 = var2.getBlockTileEntity(var3, var4, var5);
            System.out.println("Correct");
            FMLClientHandler.instance().displayGuiScreen(var1, FactoryGui.build(var0, var1.inventory, var6));
        }
    }

    public static void openGui(EnumGui var0, EntityPlayer var1, World var2, Entity var3)
    {
        if (Game.isHost(var2))
        {
            if (var0.hasContainer())
            {
                var1.openGui(TechUp.getMod(), var0.getId(), var2, var3.entityId, -1, 0);
            }
        }
        else if (!var0.hasContainer())
        {
            FMLClientHandler.instance().displayGuiScreen(var1, FactoryGui.build(var0, var1.inventory, var3));
        }
    }

    public Object getServerGuiElement(int var1, EntityPlayer var2, World var3, int var4, int var5, int var6)
    {
        if (var5 < 0)
        {
            Entity var8 = var3.getEntityByID(var4);

            if (var8 == null)
            {
                Game.log(Level.WARNING, "[Server] Entity not found when opening GUI: {0}", new Object[] {Integer.valueOf(var4)});
                return null;
            }
            else
            {
                return FactoryContainer.build(EnumGui.fromId(var1), var2.inventory, var8);
            }
        }
        else
        {
            TileEntity var7 = var3.getBlockTileEntity(var4, var5, var6);
            return FactoryContainer.build(EnumGui.fromId(var1), var2.inventory, var7);
        }
    }

    public Object getClientGuiElement(int var1, EntityPlayer var2, World var3, int var4, int var5, int var6)
    {
        if (var5 < 0)
        {
            Entity var8 = var3.getEntityByID(var4);

            if (var8 == null)
            {
                Game.log(Level.WARNING, "[Client] Entity not found when opening GUI: {0}", new Object[] {Integer.valueOf(var4)});
                return null;
            }
            else
            {
                return FactoryGui.build(EnumGui.fromId(var1), var2.inventory, var8);
            }
        }
        else
        {
            TileEntity var7 = var3.getBlockTileEntity(var4, var5, var6);
            return FactoryGui.build(EnumGui.fromId(var1), var2.inventory, var7);
        }
    }
}
