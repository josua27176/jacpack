package org.Jacpack.TechUp.api.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.modules.ModuleManager;
import org.Jacpack.TechUp.modules.ModuleManager.Module;
import org.Jacpack.TechUp.tileentitys.TileBlastFurnace;
import org.Jacpack.TechUp.tileentitys.TileMachineBase;

public enum EnumMachineAlpha implements IEnumMachine
{
    BLAST_FURNACE(Module.TANKS, "blast.furnace", TileBlastFurnace.class, new int[]{215, 215, 215, 215, 216, 215, 216, 217});
    private final Module module;
    private final String tag;
    private final Class tile;
    private final int[] texture;
    private static final List creativeList = new ArrayList();
    private static final EnumMachineAlpha[] VALUES = values();

    private EnumMachineAlpha(Module var3, String var4, Class var5, int ... var6)
    {
        this.module = var3;
        this.tile = var5;
        this.tag = var4;
        this.texture = var6;
    }

    public boolean isDepreciated()
    {
        return this.module == null;
    }

    public int getTexture(int var1)
    {
        if (var1 < 0 || var1 >= this.texture.length)
        {
            var1 = 0;
        }

        return this.texture[var1];
    }

    public static EnumMachineAlpha fromId(int var0)
    {
        if (var0 < 0 || var0 >= VALUES.length)
        {
            var0 = 0;
        }

        return VALUES[var0];
    }

    public static List getCreativeList()
    {
        return creativeList;
    }

    public String getTag()
    {
        return "tile.JAC." + this.tag;
    }

    public Class getTileClass()
    {
        return this.tile;
    }

    public TileMachineBase getTileEntity()
    {
        try
        {
            return (TileMachineBase)this.tile.newInstance();
        }
        catch (Exception var2)
        {
            return null;
        }
    }

    public ItemStack getItem()
    {
        return this.getItem(1);
    }

    public ItemStack getItem(int var1)
    {
        Block var2 = this.getBlock();
        return var2 == null ? null : new ItemStack(var2, var1, this.ordinal());
    }

    public Module getModule()
    {
        return this.module;
    }

    public Block getBlock()
    {
        return ModBlocks.getBlockMachineAlpha();
    }

    public int getBlockId()
    {
        Block var1 = this.getBlock();
        return var1 != null ? var1.blockID : 0;
    }

    public boolean isEnabled()
    {
        return ModuleManager.isModuleLoaded(this.getModule()) && this.getBlock() != null;
    }

    public void addItemInfo(ItemStack var1, EntityPlayer var2, List var3, boolean var4)
    {
        switch ($SwitchMap$org$Jacpack$TechUp$api$machines$EnumMachineAlpha[this.ordinal()])
        {
            case 1:
            case 2:
            default:
                String var5 = "Multi-Block: 3x4x3 (Hollow)";
                
                var3.add(JACTools.translate(var5));
        }
    }
    
    static final int[] $SwitchMap$org$Jacpack$TechUp$api$machines$EnumMachineAlpha = new int[EnumMachineAlpha.values().length];

    static
    {
        try
        {
        	$SwitchMap$org$Jacpack$TechUp$api$machines$EnumMachineAlpha[EnumMachineAlpha.BLAST_FURNACE.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }
    }

    static {
        creativeList.add(BLAST_FURNACE);
    }
}
