package org.macemc.OneBlock.data.sections;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.config.Settings;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.List;

@Getter
public class OneBlockData extends Data
{
	public enum Keys
	{
		Location,
		Level,
		Breaks;
	}

	private Location oneBlockLocation;
	private int level, breaks;

	private List<String> accessible;

	private OneBlockData(Location oneBlockLocation, int level, int breaks)
	{
		this.oneBlockLocation = oneBlockLocation;
		this.level = level;
		this.breaks = breaks;

		Bukkit.getScheduler().runTaskAsynchronously(OneBlockPlugin.getInstance(), () -> accessible = getAccessibleRewards(level));
	}

	@Override
	public SerializedMap serialize()
	{
		SerializedMap map = new SerializedMap();
		map.put(Keys.Location.name(), oneBlockLocation.serialize());
		map.put(Keys.Level.name(), level);
		map.put(Keys.Breaks.name(), breaks);
		return map;
	}

	public static OneBlockData deserialize(SerializedMap map)
	{
		Location oneBlockLocation = Location.deserialize(map.getMap(Keys.Location.name()).asMap());
		int level = map.getInteger(Keys.Level.name());
		int breaks = map.getInteger(Keys.Breaks.name());
		return new OneBlockData(oneBlockLocation, level, breaks);
	}

	public static List<String> getAccessibleRewards(int level)
	{
		return Settings.OneBlock.map.entrySet().stream()
				.filter(entry -> entry.getKey() <= level)
				.flatMap(entry -> entry.getValue().stream())
				.toList();
	}


	public void setOneBlockLocation(Location oneBlockLocation)
	{
		this.oneBlockLocation = oneBlockLocation;
		saveChanges();
	}

	public void setLevel(int level)
	{
		this.level = level;
		this.accessible = getAccessibleRewards(level);
		saveChanges();
	}

	public void setBreaks(int breaks)
	{
		this.breaks = breaks;
		saveChanges();
	}
}