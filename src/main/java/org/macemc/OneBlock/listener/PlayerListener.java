package org.macemc.OneBlock.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.macemc.OneBlock.data.PlayerData;

public class PlayerListener extends OneBlockListenerGroup {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		PlayerData.findOrCreateData(e.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PlayerData.removeLoadedData(e.getPlayer().getUniqueId());
	}
}