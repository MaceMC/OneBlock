package org.macemc.OneBlock.lang;

import org.mineacademy.fo.settings.SimpleLocalization;
import org.mineacademy.fo.settings.YamlStaticConfig;

@SuppressWarnings("unused")
public class Localization extends SimpleLocalization
{
	public static final class Commands
	{
		public static String TP_REPLY = "You were sent to &x&0&0&A&B&5&2&lOn&x&0&F&C&0&6&B&leB&x&1&D&D&5&8&5&llo&x&2&C&E&A&9&E&lck&7&l&f";

		public Commands() { }

		private static void init()
		{
			setPathPrefix("Commands");
			if (YamlStaticConfig.isSetDefault("Join_Reply"))
				TP_REPLY = YamlStaticConfig.getString("Join_Reply");
		}
	}
}
