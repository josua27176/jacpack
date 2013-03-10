package org.Jacpack.TechUp.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

import org.Jacpack.TechUp.gui.slots.SlotBlastFurnaceFuel;
import org.Jacpack.TechUp.gui.slots.SlotBlastFurnaceInput;
import org.Jacpack.TechUp.tileentitys.TileBlastFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBlastFurnace extends JACContainer
{
    private TileBlastFurnace furnace;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;

    public ContainerBlastFurnace(InventoryPlayer var1, TileBlastFurnace var2)
    {
        super(var2);
        this.furnace = var2;
        this.addSlot(new SlotBlastFurnaceInput(var2, 0, 56, 17));
        this.addSlot(new SlotBlastFurnaceFuel(var2, 1, 56, 53));
        this.addSlot(new SlotFurnace(var1.player, var2, 2, 116, 35));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlot(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlot(new Slot(var1, var3, 8 + var3 * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting var1)
    {
        super.addCraftingToCrafters(var1);
        var1.sendProgressBarUpdate(this, 0, this.furnace.getCookTime());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastCookTime != this.furnace.getCookTime())
            {
                var2.sendProgressBarUpdate(this, 0, this.furnace.getCookTime());
            }

            if (this.lastBurnTime != this.furnace.burnTime)
            {
                var2.sendProgressBarUpdate(this, 1, this.furnace.burnTime);
            }

            if (this.lastItemBurnTime != this.furnace.currentItemBurnTime)
            {
                var2.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.furnace.getCookTime();
        this.lastBurnTime = this.furnace.burnTime;
        this.lastItemBurnTime = this.furnace.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int var1, int var2)
    {
        if (var1 == 0)
        {
            this.furnace.setCookTime(var2);
        }

        if (var1 == 1)
        {
            this.furnace.burnTime = var2;
        }

        if (var1 == 2)
        {
            this.furnace.currentItemBurnTime = var2;
        }
    }
}
