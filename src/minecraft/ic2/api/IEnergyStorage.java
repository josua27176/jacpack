package ic2.api;

public interface IEnergyStorage
{
    public int getStored();

    public void setStored(int energy);

    public int addEnergy(int amount);

    public int getCapacity();

    public int getOutput();

    public boolean isTeleporterCompatible(Direction side);
}
