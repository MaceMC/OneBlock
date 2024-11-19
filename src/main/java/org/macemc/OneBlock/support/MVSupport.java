package org.macemc.OneBlock.support;

import com.onarandombox.MultiverseCore.MultiverseCore;
import lombok.Getter;

public abstract class MVSupport
{
	@Getter
	private static MultiverseCore plugin;
	private static boolean enabled;

	public static void init(MultiverseCore instance)
	{
		plugin = instance;
		enabled = instance != null;
	}
}