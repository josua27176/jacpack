package ic2.api.energy.tile;

import net.minecraft.tileentity.TileEntity;

import ic2.api.Direction;

public interface IEnergyAcceptor extends IEnergyTile
{
    boolean acceptsEnergyFrom(TileEntity emitter, Direction direction);
}
