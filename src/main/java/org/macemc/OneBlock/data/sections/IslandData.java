package org.macemc.OneBlock.data.sections;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.Data;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class IslandData extends Data
{
	public enum Keys
	{
		Name,
		IslandID,
		InvitedPlayers
	}

	private String name = "Island";
	private String islandID = "is-";
	private HashMap<UUID, String> invitedPlayers = new HashMap<>();

	public IslandData()
	{
	}

	private IslandData(String name, String islandID, HashMap<UUID, String> trustedPlayers)
	{
		this.name = name;
		this.islandID = islandID;
		this.invitedPlayers = trustedPlayers;
	}

	@Override
	public SerializedMap serialize()
	{
		SerializedMap map = new SerializedMap();
		map.put(Keys.Name.name(), name);
		map.put(Keys.IslandID.name(), islandID);
		map.put(Keys.InvitedPlayers.name(), invitedPlayers);
		return map;
	}

	public static IslandData deserialize(SerializedMap map)
	{
		String name = map.getString(Keys.Name.name());
		String islandID = map.getString(Keys.IslandID.name());
		HashMap<UUID, String> invited = map.getMap(Keys.InvitedPlayers.name(), UUID.class, String.class);
		return new IslandData(name, islandID, invited);
	}

	public void setName(String name)
	{
		this.name = name;
		saveChanges();
	}

	public void setInvitedPlayers(HashMap<UUID, String> invitedPlayers)
	{
		this.invitedPlayers = invitedPlayers;
		saveChanges();
	}

	public void invitePlayer(Player p)
	{
		this.invitedPlayers.put(p.getUniqueId(), p.getName());
		saveChanges();
	}
}
