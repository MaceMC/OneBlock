package org.macemc.OneBlock;

import com.onarandombox.MultiverseCore.MultiverseCore;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.macemc.OneBlock.listener.OneBlockListenerGroup;
import org.macemc.OneBlock.support.MVSupport;
import org.macemc.OneBlock.world.WorldService;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class OneBlockPlugin extends SimplePlugin
{
	@Getter
	private static WorldService worldService;

	@Override
	protected void onPluginStart()
	{
		apiSupport();
		this.registerAllEvents(OneBlockListenerGroup.class);
		worldService = WorldService.getInstance();
	}

	public void apiSupport()
	{
		MVSupport.init((MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core"));
	}
}