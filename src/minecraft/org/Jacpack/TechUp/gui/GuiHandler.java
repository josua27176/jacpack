package org.Jacpack.TechUp.gui;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import java.util.logging.Level;

import org.Jacpack.TechUp.TechUp;
import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.client.gui.FactoryGui;
import org.Jacpack.TechUp.gui.containers.FactoryContainer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    public static void openGui(EnumGui gui, EntityPlayer player, World world, int x, int y, int z)
    {
        if (Game.isHost(world))
        {
            if (gui.hasContainer())
            {
            	player.openGui(TechUp.getMod(), gui.getId(), world, x, y, z);
            }
        }
        else if (!gui.hasContainer())
        {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            FMLClientHandler.instance().displayGuiScreen(player, FactoryGui.build(gui, player.inventory, tile));
        }
    }

    public static void openGui(EnumGui gui, EntityPlayer player, World world, Entity entity)
    {
        if (Game.isHost(world))
        {
            if (gui.hasContainer())
            {
            	player.openGui(TechUp.getMod(), gui.getId(), world, entity.entityId, -1, 0);
            }
        }
        else if (!gui.hasContainer())
        {
            FMLClientHandler.instance().displayGuiScreen(player, FactoryGui.build(gui, player.inventory, entity));
        }
    }

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (y < 0)
        {
            Entity entity = world.getEntityByID(x);

            if (entity == null)
            {
                Game.log(Level.WARNING, "[Server] Entity not found when opening GUI: {0}", new Object[] {Integer.valueOf(x)});
                return null;
            }
            else
            {
                return FactoryContainer.build(EnumGui.fromId(ID), player.inventory, entity);
            }
        }
        else
        {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            return FactoryContainer.build(EnumGui.fromId(ID), player.inventory, tile);
        }
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World var3, int x, int y, int z)
    {
        if (y < 0)
        {
            Entity entity = var3.getEntityByID(x);

            if (entity == null)
            {
                Game.log(Level.WARNING, "[Client] Entity not found when opening GUI: {0}", new Object[] {Integer.valueOf(x)});
                return null;
            }
            else
            {
                return FactoryGui.build(EnumGui.fromId(ID), player.inventory, entity);
            }
        }
        else
        {
            TileEntity tile = var3.getBlockTileEntity(x, y, z);
            return FactoryGui.build(EnumGui.fromId(ID), player.inventory, tile);
        }
    }
}
