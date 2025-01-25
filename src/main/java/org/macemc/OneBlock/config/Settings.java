package org.macemc.OneBlock.config;

import org.bukkit.Location;
import org.bukkit.World;
import org.macemc.OneBlock.OneBlockPlugin;
import org.mineacademy.fo.settings.SimpleSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public final class Settings extends SimpleSettings
{
	public static class OneBlock
	{
		public static int size = 20;
		public static World world = OneBlockPlugin.getInstance().getServer().getWorld("world");
		public static Location spawnLocation = new Location(world, 0, 64, 0);
		public static Map<Integer, List<String>> map  = new HashMap<>();

		private static void init()
		{
			setPathPrefix("OneBlock");

			if (isSetDefault("Size"))
				size = getInteger("Size");

			if (isSetDefault("Spawn.Location"))
			{
				spawnLocation = Location.deserialize(getMap("Spawn.Location").asMap());
				world = OneBlockPlugin.getInstance().getServer().getWorld(spawnLocation.getWorld().getKey());
			}

			if (isSetDefault("LootPool"))
				map = getMapList("LootPool", Integer.class, String.class, null);
		}
	}
}
