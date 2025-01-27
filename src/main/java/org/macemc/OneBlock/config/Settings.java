package org.macemc.OneBlock.config;

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
		public static Map<Integer, List<String>> map  = new HashMap<>();
		public static Map<Integer, String> levelNames = new HashMap<>();

		private static void init()
		{
			setPathPrefix("OneBlock");

			if (isSetDefault("Size"))
				size = getInteger("Size");

			if (isSetDefault("LootPool"))
				map = getMapList("LootPool", Integer.class, String.class, null);

			extractLevelNames(map);
		}

		private static void extractLevelNames(Map<Integer, List<String>> map) {
			for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
				List<String> list = entry.getValue();
				if (!list.isEmpty()) {
					levelNames.put(entry.getKey(), list.removeFirst());
				}
			}
		}
	}
}
