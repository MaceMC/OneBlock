package org.macemc.OneBlock.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.macemc.OneBlock.data.PlayerData;

import static org.macemc.OneBlock.utility.OneBlockUtil.getSpawnLocation;
import static org.macemc.OneBlock.utility.OneBlockUtil.getSwitchedPlayerData;

public class PlayerListener extends OneBlockListenerGroup {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		PlayerData.findOrCreateData(e.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PlayerData.removeLoadedData(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		PlayerData playerData = getSwitchedPlayerData(p);

		if (p.getUniqueId().equals(playerData.getUuid()) && !playerData.getOneBlockData().hasRegion())
			return;
		e.setRespawnLocation(getSpawnLocation(playerData));
	}
}