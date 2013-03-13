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

    private EnumMachineAlpha(Module module, String tag, Class tile, int[] texture)
    {
        this.module = module;
        this.tile = tile;
        this.tag = tag;
        this.texture = texture;
      }

      public boolean isDepreciated()
      {
        return this.module == null;
      }

      public int getTexture(int index)
      {
        if ((index < 0) || (index >= this.texture.length)) {
          index = 0;
        }
        return this.texture[index];
     }

      public static EnumMachineAlpha fromId(int id) {
    	    if ((id < 0) || (id >= VALUES.length)) {
    	      id = 0;
    	    }
    	    return VALUES[id];
    	  }

    public static List getCreativeList() {
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

    public TileMachineBase getTileEntity() {
        try {
          return (TileMachineBase)this.tile.newInstance();
        } catch (Exception ex) {
        }
        return null;
      }

      public ItemStack getItem()
      {
        return getItem(1);
      }

    public ItemStack getItem(int qty)
    {
        Block block = getBlock();
        if (block == null) {
          return null;
        }
        return new ItemStack(block, qty, ordinal());
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

    public void addItemInfo(ItemStack stack, EntityPlayer player, List info, boolean adv) {
        switch ($SwitchMap$org$Jacpack$TechUp$api$machines$EnumMachineAlpha[this.ordinal()])
        {
            case 1:
            case 2:
            default:
                String var5 = "Multi-Block: 3x4x3 (Hollow)";
                
                info.add(JACTools.translate(var5));
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
