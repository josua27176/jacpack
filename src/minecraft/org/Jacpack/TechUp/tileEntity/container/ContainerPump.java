package org.Jacpack.TechUp.tileEntity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import org.Jacpack.TechUp.tileEntity.AbstactTileEntityMachine;

public class ContainerPump extends AbstractMachineContainer {

	ContainerPump(InventoryPlayer inv, AbstactTileEntityMachine TE) {
		super(inv, TE);
		
		addSlotToContainer(new Slot(inv,36,0,0));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUsableByPlayer(player);
	}
}
