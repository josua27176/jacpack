package ic2.api;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IReactor
{
    public ChunkCoordinates getPosition();

    public World getWorld();

    public int getHeat();

    public void setHeat(int heat);

    public int addHeat(int amount);

    public int getMaxHeat();

    public void setMaxHeat(int newMaxHeat);

    public float getHeatEffectModifier();

    public void setHeatEffectModifier(float newHEM);

    public int getOutput();

    public int addOutput(int energy);

    @Deprecated
    public int getPulsePower();

    public ItemStack getItemAt(int x, int y);

    public void setItemAt(int x, int y, ItemStack item);

    public void explode();

    public int getTickRate();

    public boolean produceEnergy();
}
