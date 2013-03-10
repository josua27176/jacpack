package ic2.api.energy.tile;

import net.minecraft.tileentity.TileEntity;

import ic2.api.Direction;

public interface IEnergyEmitter extends IEnergyTile
{
    boolean emitsEnergyTo(TileEntity receiver, Direction direction);
}
