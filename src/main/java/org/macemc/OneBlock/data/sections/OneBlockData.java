package org.macemc.OneBlock.data.sections;

import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.config.Settings;
import org.mineacademy.fo.collection.SerializedMap;

@Getter
public class OneBlockData extends Data
{
	public enum Keys
	{
		RegionID,
		Location,
		Level,
		Breaks;
	}

	private String regionID;
	private Location oneBlockLocation;
	private int level;
	private int breaks;
	private List<String> accessible = List.of("GRASS_BLOCK");

	private OneBlockData(String regionID, Location oneBlockLocation, int level, int breaks)
	{
		this.regionID = regionID;
		this.oneBlockLocation = oneBlockLocation;
		this.level = level;
		this.breaks = breaks;
		Bukkit.getScheduler().runTaskAsynchronously(OneBlockPlugin.getInstance(), () -> this.accessible = this.getAccessibleRewards());
	}

	public SerializedMap serialize()
	{
		SerializedMap map = new SerializedMap();
		map.put(org.macemc.OneBlock.data.sections.OneBlockData.Keys.RegionID.name(), this.regionID);
		map.put(org.macemc.OneBlock.data.sections.OneBlockData.Keys.Location.name(), this.oneBlockLocation.serialize());
		map.put(org.macemc.OneBlock.data.sections.OneBlockData.Keys.Level.name(), this.level);
		map.put(org.macemc.OneBlock.data.sections.OneBlockData.Keys.Breaks.name(), this.breaks);
		return map;
	}

	public static OneBlockData deserialize(SerializedMap map)
	{
		String regionID = map.getString(org.macemc.OneBlock.data.sections.OneBlockData.Keys.RegionID.name());
		Location oneBlockLocation = Location.deserialize(map.getMap(org.macemc.OneBlock.data.sections.OneBlockData.Keys.Location.name()).asMap());
		int level = map.getInteger(org.macemc.OneBlock.data.sections.OneBlockData.Keys.Level.name());
		int breaks = map.getInteger(org.macemc.OneBlock.data.sections.OneBlockData.Keys.Breaks.name());
		return new OneBlockData(regionID, oneBlockLocation, level, breaks);
	}

	public List<String> getAccessibleRewards()
	{
		return Settings.OneBlock.map.entrySet().stream()
				.filter(entry -> entry.getKey() <= level)
				.flatMap(entry -> entry.getValue().stream())
				.toList();
	}

	public boolean hasRegion()
	{
		return regionID != null;
	}

	public void setRegionID(String regionID)
	{
		this.regionID = regionID;
		this.saveChanges();
	}

	public void setOneBlockLocation(Location oneBlockLocation)
	{
		this.oneBlockLocation = oneBlockLocation;
		this.saveChanges();
	}

	public void setLevel(int level)
	{
		this.level = level;
		Bukkit.getScheduler().runTaskAsynchronously(OneBlockPlugin.getInstance(), () -> this.accessible = this.getAccessibleRewards());
		this.saveChanges();
	}

	public void setBreaks(int breaks)
	{
		this.breaks = breaks;
		this.saveChanges();
	}
}