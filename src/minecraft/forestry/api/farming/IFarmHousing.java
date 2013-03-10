package forestry.api.farming;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidStack;


public interface IFarmHousing {
	
	int[] getCoords();
	int[] getArea();
	int[] getOffset();

	World getWorld();

	/**
	 * Will run the work cycle on a master TE. Will do nothing on any other farm component.
	 * @return true if any work was done, false otherwise.
	 */
	boolean doWork();
	
	boolean hasLiquid(LiquidStack liquid);
	void removeLiquid(LiquidStack liquid);
	
	boolean hasResources(ItemStack[] resources);
	void removeResources(ItemStack[] resources);
	
	/**
	 * Callback for {@link IFarmLogic}s to plant a sapling, seed, germling, stem. Will remove the appropriate germling from the farm's inventory.
	 * It's up to the logic to only call this on a valid location. 
	 * @param farmable
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return true if planting was successful, false otherwise.
	 */
	boolean plantGermling(IFarmable farmable, World world, int x, int y, int z);
	
	/* INTERACTION WITH HATCHES */
	boolean acceptsAsGermling(ItemStack itemstack);
	boolean acceptsAsResource(ItemStack itemstack);
	boolean acceptsAsFertilizer(ItemStack itemstack);
	
	/* LOGIC */
	/**
	 * Set a farm logic for the given direction. UP/DOWN/UNKNOWN are invalid!
	 * @param direction
	 * @param logic
	 */
	void setFarmLogic(ForgeDirection direction, IFarmLogic logic);
	/**
	 * Reset the farm logic for the given direction to default. UP/DOWN/UNKNOWN are invalid!
	 * @param direction
	 */
	void resetFarmLogic(ForgeDirection direction);
}
