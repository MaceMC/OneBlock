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
import java.util.LinkedList;

@Setter
public abstract class Data implements Serializable
{
	protected PlayerData playerData;
	@Getter
	@Setter
	protected static LinkedList<Location> freeLocations = new LinkedList<>();

	protected void saveChanges()
	{
		Common.runAsync(() -> playerData.saveToDatabase());
	}

	public abstract SerializedMap serialize();

	public static void findFreeLocations(Location location)
	{
		Common.runAsync(() ->
		{
			if (freeLocations.size() >= 8) return;
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
		freeLocations.add(location);
	}

	public static SerializedMap serializeGeneralData() {
		SerializedMap map = new SerializedMap();
		map.put("freeLocations", freeLocations);
		return map;
	}
}
