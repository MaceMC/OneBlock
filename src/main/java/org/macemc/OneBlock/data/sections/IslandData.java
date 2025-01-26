package org.macemc.OneBlock.data.sections;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.Data;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class IslandData extends Data {
	public enum Keys {
		IslandName, IslandID, InvitedPlayers
	}

	private String islandName = "Island";
	private String islandID = "N/A";
	private HashMap<UUID, String> invitedPlayers = new HashMap<>();

	public IslandData() {
	}

	private IslandData(String islandName, String islandID, HashMap<UUID, String> invitedPlayers) {
		this.islandName = islandName;
		this.islandID = islandID;
		this.invitedPlayers = invitedPlayers;
	}

	@Override
	public SerializedMap serialize() {
		SerializedMap map = new SerializedMap();
		map.put(Keys.IslandName.name(), islandName);
		map.put(Keys.IslandID.name(), islandID);
		map.put(Keys.InvitedPlayers.name(), invitedPlayers);
		return map;
	}

	public static IslandData deserialize(SerializedMap map) {
		String name = map.getString(Keys.IslandName.name());
		String islandID = map.getString(Keys.IslandID.name());
		HashMap<UUID, String> invited = map.getMap(Keys.InvitedPlayers.name(), UUID.class, String.class);
		return new IslandData(name, islandID, invited);
	}

	public void setIslandName(String islandName) {
		this.islandName = islandName;
		saveChanges();
	}

	public void setIslandID(String islandID) {
		this.islandID = islandID;
		saveChanges();
	}

	public void invitePlayer(Player p) {
		this.invitedPlayers.put(p.getUniqueId(), p.getName());
		saveChanges();
	}
}
