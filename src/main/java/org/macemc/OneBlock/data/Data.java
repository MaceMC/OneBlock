package org.macemc.OneBlock.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.macemc.OneBlock.config.Settings;
import org.macemc.OneBlock.listener.BlockListener;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
public abstract class Data implements Serializable
{
	protected PlayerData playerData;
	@Getter
	protected static ArrayList<Location> freeLocations = new ArrayList<>();

	protected void saveChanges()
	{
		Common.runAsync(() -> playerData.saveToDatabase());
	}

	public abstract SerializedMap serialize();

	public static void findFreeLocations(Location location)
	{
		Common.runAsync(() ->
		{
			System.out.println("Finding free locations... from " + location);
			if (freeLocations.size() >= 20) return;
			int size = Settings.OneBlock.size;
			// Check +x
			Location posX = location.clone().add(size * 2 * 16, 0, 0);
			validLocation(posX);
			// Check -x
			Location negX = location.clone().add(size * 2 * -16, 0, 0);
			validLocation(negX);
			// Check +z
			Location posZ = location.clone().add(0, 0, size * 2 * 16);
			validLocation(posZ);
			// Check -z
			Location negZ = location.clone().add(0, 0, size * 2 * -16);
			validLocation(negZ);

			findFreeLocations(posX);
			findFreeLocations(negX);
			findFreeLocations(posZ);
			findFreeLocations(negZ);
		});
	}

	public static void validLocation(Location location)
	{
		Block block = location.getBlock();
		if (BlockListener.isOneBlock(block) || freeLocations.contains(location)) return;
		System.out.println("Found free location: " + location);
		freeLocations.add(location);
	}
}
