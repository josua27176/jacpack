package ic2.api;

import net.minecraft.world.World;

public interface IPaintableBlock
{
    public boolean colorBlock(World world, int x, int y, int z, int color);
}
