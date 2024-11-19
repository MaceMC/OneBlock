package org.macemc.OneBlock.world;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import lombok.Getter;
import org.bukkit.*;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.support.MVSupport;

public class WorldService
{
	@Getter
	private static final NamespacedKey worldKey = new NamespacedKey(OneBlockPlugin.getInstance(), "oneblock");
	@Getter
	private final MultiverseWorld world;

	private WorldService()
	{
		world = MVSupport.getPlugin().getMVWorldManager().getMVWorld(worldKey.getKey()) == null ? createWorld() : MVSupport.getPlugin().getMVWorldManager().getMVWorld(worldKey.getKey());
	}

	private MultiverseWorld createWorld()
	{
		MVWorldManager worldManager = MVSupport.getPlugin().getMVWorldManager();
		worldManager.addWorld(worldKey.getKey(), World.Environment.NORMAL, null, WorldType.NORMAL,false,null);
		return worldManager.getMVWorld(worldKey.getKey());
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