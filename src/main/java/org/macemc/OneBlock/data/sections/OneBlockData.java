package org.macemc.OneBlock.data.sections;

import lombok.Getter;
import org.bukkit.Location;
import org.macemc.OneBlock.config.Settings;
import org.macemc.OneBlock.data.Data;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.List;
import java.util.Objects;

@Getter
public class OneBlockData extends Data {
	public enum Keys {
		RegionID, Location, Level, Breaks
	}

	private String regionID = "N/A";
	private Location oneBlockLocation = new Location(null, 0, 64, 0);
	;
	private int level = 0, breaks = 0;
	private List<String> accessible = List.of("GRASS_BLOCK");

	public OneBlockData() {
		Common.runAsync(() -> this.accessible = getAccessibleRewards());
	}

	private OneBlockData(String regionID, Location oneBlockLocation, int level, int breaks) {
		this.regionID = regionID;
		this.oneBlockLocation = oneBlockLocation;
		this.level = level;
		this.breaks = breaks;
		Common.runAsync(() -> this.accessible = getAccessibleRewards());
	}

	public SerializedMap serialize() {
		SerializedMap map = new SerializedMap();
		map.put(Keys.RegionID.name(), this.regionID);
		map.put(Keys.Location.name(), this.oneBlockLocation.serialize());
		map.put(Keys.Level.name(), this.level);
		map.put(Keys.Breaks.name(), this.breaks);
		return map;
	}

	public static OneBlockData deserialize(SerializedMap map) {
		String regionID = map.getString(Keys.RegionID.name());
		Location oneBlockLocation = Location.deserialize(map.getMap(Keys.Location.name()).asMap());
		int level = map.getInteger(Keys.Level.name());
		int breaks = map.getInteger(Keys.Breaks.name());
		return new OneBlockData(regionID, oneBlockLocation, level, breaks);
	}

	public List<String> getAccessibleRewards() {
		return Settings.OneBlock.map.entrySet().stream().filter(entry -> entry.getKey() <= level).flatMap(entry -> entry.getValue().stream()).toList();
	}

	public boolean hasRegion() {
		return !Objects.equals(regionID, "N/A");
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
		saveChanges();
	}

	public void setOneBlockLocation(Location oneBlockLocation) {
		this.oneBlockLocation = oneBlockLocation;
		saveChanges();
	}

	public void setLevel(int level) {
		this.level = level;
		Common.runAsync(() -> this.accessible = this.getAccessibleRewards());
		saveChanges();
	}

	public void registerBreak() {
		this.breaks++;
		saveChanges();
	}

	public boolean checkNewLevel() {
		int calculatedLevel = calculateLevelWithBreaks(this.breaks);
		if (calculatedLevel <= this.level) return false;
		setLevel(calculatedLevel);
		return true;
	}

	public static int calculateLevelWithBreaks(int breaks) {
		final int a = 1, b = 6;
		return (int) Math.floor(Math.sqrt(a * breaks) / b);
	}
}