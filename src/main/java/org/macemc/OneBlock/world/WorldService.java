package org.macemc.OneBlock.world;

import lombok.Getter;
import org.bukkit.*;
import org.macemc.OneBlock.OneBlockPlugin;

public class WorldService
{
	@Getter
	private static final NamespacedKey worldKey = new NamespacedKey(OneBlockPlugin.getInstance(), "oneblock");
	@Getter
	private final World world;

	private WorldService()
	{
		world = (Bukkit.getWorld(worldKey) == null) ? createWorld() : Bukkit.getWorld(worldKey);
	}

	private World createWorld()
	{
		WorldCreator creator = new WorldCreator(worldKey);
		creator.generator(new VoidWorldGenerator());
		return creator.createWorld();
	}

	private static final class SingletonHolder
	{
		private static final WorldService INSTANCE = new WorldService();
	}

	public static WorldService getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
}