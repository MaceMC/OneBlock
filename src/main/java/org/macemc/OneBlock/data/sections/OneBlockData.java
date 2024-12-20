package org.macemc.OneBlock.data.sections;

import lombok.Getter;
import org.bukkit.Location;
import org.mineacademy.fo.collection.SerializedMap;

@Getter
public class OneBlockData extends Data
{
	public enum Keys
	{
		Location("Location"),
		Level("Level"),
		Breaks("Breaks");

		private final String key;

		Keys(String key)
		{
			this.key = key;
		}

		@Override
		public String toString()
		{
			return key;
		}
	}

	private Location oneBlockLocation;
	private int level, breaks;

	private OneBlockData(Location oneBlockLocation, int level, int breaks)
	{
		this.oneBlockLocation = oneBlockLocation;
		this.level = level;
		this.breaks = breaks;
	}

	@Override
	public SerializedMap serialize()
	{
		SerializedMap map = new SerializedMap();
		map.put(Keys.Location.toString(), oneBlockLocation.serialize());
		map.put(Keys.Level.toString(), level);
		map.put(Keys.Breaks.toString(), breaks);
		return map;
	}

	public static OneBlockData deserialize(SerializedMap map)
	{
		Location oneBlockLocation = Location.deserialize(map.getMap(Keys.Location.toString()).asMap());
		int level = map.getInteger(Keys.Level.toString());
		int breaks = map.getInteger(Keys.Breaks.toString());
		return new OneBlockData(oneBlockLocation, level, breaks);
	}

	public void setOneBlockLocation(Location oneBlockLocation)
	{
		this.oneBlockLocation = oneBlockLocation;
		saveChanges();
	}

	public void setLevel(int level)
	{
		this.level = level;
		saveChanges();
	}

	public void setBreaks(int breaks)
	{
		this.breaks = breaks;
		saveChanges();
	}
}