package org.macemc.OneBlock;

import lombok.Getter;
import org.macemc.OneBlock.data.PlayerDataManager;
import org.macemc.OneBlock.listener.OneBlockListenerGroup;
import org.macemc.OneBlock.world.WorldService;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class OneBlockPlugin extends SimplePlugin
{
	@Getter
	private static WorldService worldService;
	@Getter
	private static PlayerDataManager playerDataManager;

	@Override
	protected void onPluginStart()
	{
		this.registerAllEvents(OneBlockListenerGroup.class);
		worldService = WorldService.getInstance();
		playerDataManager = PlayerDataManager.getInstance();
	}
}