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
		public static Map<Integer, List<String>> map  = new HashMap<>();

		private static void init()
		{
			setPathPrefix("OneBlock");

			if (isSetDefault("LootPool"))
				map = getMapList("LootPool", Integer.class, String.class, null);
		}
	}
}
