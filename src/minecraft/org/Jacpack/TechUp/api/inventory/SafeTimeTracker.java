package org.Jacpack.TechUp.api.inventory;

import net.minecraft.world.World;

public class SafeTimeTracker {

	private long lastMark = 0;

	/**
	 * Return true if a given delay has passed since last time marked was called successfully.
	 */
	public boolean markTimeIfDelay(World world, long delay) {
		if (world == null)
			return false;

		long currentTime = world.getWorldTime();

		if (currentTime < lastMark) {
			lastMark = currentTime;
			return false;
		} else if (lastMark + delay <= currentTime) {
			lastMark = world.getWorldTime();
			return true;
		} else
			return false;

	}

	public void markTime(World world) {
		lastMark = world.getWorldTime();
	}
}
