package org.macemc.OneBlock.data.sections;

import java.util.List;
import java.util.Objects;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.config.Settings;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.Common;
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
		Common.runAsync(() -> this.accessible = this.getAccessibleRewards());
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
		Common.runAsync(() -> this.accessible = this.getAccessibleRewards());
		this.saveChanges();
	}

	public void setBreaks(int breaks)
	{
		this.breaks = breaks;
		this.saveChanges();
	}

	public void registerBreak()
	{
		System.out.println("Registering break");
		this.breaks++;
		this.saveChanges();
	}

	public boolean checkNewLevel()
	{
		int calculatedLevel = calculateLevelWithBreaks(this.breaks);
		System.out.println("Calculated level: " + calculatedLevel + ", Level: " + this.level);
		if (calculatedLevel <= this.level) return false;
		this.level++;
		Common.runAsync(() -> this.accessible = this.getAccessibleRewards());
		return true;
	}

	public static int calculateLevelWithBreaks(int breaks)
	{
		final int a = 1, b = 6;
		return (int) Math.floor(Math.sqrt(a * breaks) / b);
	}
}