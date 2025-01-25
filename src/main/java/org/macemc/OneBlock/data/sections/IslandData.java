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
		Name, IslandID, InvitedPlayers
	}

	private String name = "Island";
	private String islandID = "N/A";
	private HashMap<UUID, String> invitedPlayers = new HashMap<>();

	public IslandData() {
	}

	private IslandData(String name, String islandID, HashMap<UUID, String> invitedPlayers) {
		this.name = name;
		this.islandID = islandID;
		this.invitedPlayers = invitedPlayers;
	}

	@Override
	public SerializedMap serialize() {
		SerializedMap map = new SerializedMap();
		map.put(Keys.Name.name(), name);
		map.put(Keys.IslandID.name(), islandID);
		map.put(Keys.InvitedPlayers.name(), invitedPlayers);
		return map;
	}

	public static IslandData deserialize(SerializedMap map) {
		String name = map.getString(Keys.Name.name());
		String islandID = map.getString(Keys.IslandID.name());
		HashMap<UUID, String> invited = map.getMap(Keys.InvitedPlayers.name(), UUID.class, String.class);
		return new IslandData(name, islandID, invited);
	}

	public void setName(String name) {
		this.name = name;
		saveChanges();
	}

	public void setInvitedPlayers(HashMap<UUID, String> invitedPlayers) {
		this.invitedPlayers = invitedPlayers;
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
