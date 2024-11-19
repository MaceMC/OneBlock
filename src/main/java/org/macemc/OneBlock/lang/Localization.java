package org.macemc.OneBlock.lang;

import org.mineacademy.fo.settings.SimpleLocalization;
import org.mineacademy.fo.settings.YamlStaticConfig;

@SuppressWarnings("unused")
public class Localization extends SimpleLocalization
{
	public static final class Commands
	{
		public static String TEST_REPLY = "This is a test";

		public Commands() { }

		private static void init()
		{
			setPathPrefix("Commands");
			if (YamlStaticConfig.isSetDefault("Test_Reply"))
				TEST_REPLY = YamlStaticConfig.getString("Test_Reply");
		}
	}
}
