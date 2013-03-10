package ic2.api.energy.tile;

public interface IEnergyConductor extends IEnergyAcceptor, IEnergyEmitter
{
    double getConductionLoss();

    int getInsulationEnergyAbsorption();

    int getInsulationBreakdownEnergy();

    int getConductorBreakdownEnergy();

    void removeInsulation();

    void removeConductor();
}
