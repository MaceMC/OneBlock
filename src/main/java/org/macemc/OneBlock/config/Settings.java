package org.macemc.OneBlock.config;

import org.bukkit.Location;
import org.mineacademy.fo.settings.SimpleSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public final class Settings extends SimpleSettings
{
	public static class OneBlock
	{
		public static Integer size = 20;
		public static Location spawnLocation = new Location(null, 0, 64, 0);
		public static Map<Integer, List<String>> map  = new HashMap<>();

		private static void init()
		{
			setPathPrefix("OneBlock");

			if (isSetDefault("Size"))
				size = getInteger("Size");

			if (isSetDefault("Spawn.Location"))
			{
				spawnLocation = Location.deserialize(getMap("Spawn.Location").asMap());
			}

			if (isSetDefault("LootPool"))
				map = getMapList("LootPool", Integer.class, String.class, null);
		}
	}
}
