package org.macemc.OneBlock.utility;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.PlayerData;

import java.util.UUID;

public class OneBlockUtil {

	public static NamespacedKey switchKey = new NamespacedKey(OneBlockPlugin.getInstance(), "switched");

	public static boolean hasOneBlock(PlayerData playerData) {
		return playerData.getOneBlockData().hasRegion();
	}

	public static Location getSpawnLocation(PlayerData playerData) {
		return getSpawnLocation(playerData.getOneBlockData().getOneBlockLocation());
	}

	public static Location getSpawnLocation(Location obLocation) {
		return obLocation.clone().toCenterLocation().toHighestLocation().add(0, 1, 0);
	}

	public static void teleportOneBlock(Player p, Location location) {
		p.teleportAsync(getSpawnLocation(location), PlayerTeleportEvent.TeleportCause.PLUGIN);
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1, 1);
	}

	public static void teleportOneBlock(Player p, PlayerData playerData) {
		p.teleportAsync(getSpawnLocation(playerData), PlayerTeleportEvent.TeleportCause.PLUGIN);
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1, 1);
	}

	public static void setSwitchPersistentData(Player p, PlayerData target) {
		p.getPersistentDataContainer().set(switchKey, PersistentDataType.STRING, target.getUuid().toString());
	}

	public static PlayerData getSwitchedPlayerData(Player p) {
		String switchedID = p.getPersistentDataContainer().getOrDefault(switchKey, PersistentDataType.STRING, p.getUniqueId().toString());
		return PlayerData.findOrCreateData(UUID.fromString(switchedID));
	}
}
