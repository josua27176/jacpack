package ic2.api;

import net.minecraft.item.ItemStack;

public interface IReactorComponent
{
    public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y);

    public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY);

    public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y);

    public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y);

    public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y);

    public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat);

    public float influenceExplosion(IReactor reactor, ItemStack yourStack);
}
