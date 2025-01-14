package org.macemc.OneBlock.data.sections;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class IslandData extends Data
{
	public enum Keys
	{
		Name,
		InvitedPlayers
	}

	private PlayerData playerData;

	private String name;
	private ArrayList<UUID> invitedPlayers;

	private IslandData(@NotNull String name, @NotNull ArrayList<UUID> trustedPlayers)
	{
		this.name = name;
		this.invitedPlayers = trustedPlayers;
	}

	@Override
	public SerializedMap serialize()
	{
		SerializedMap map = new SerializedMap();
		map.put(Keys.Name.name(), name);
		map.put(Keys.InvitedPlayers.name(), invitedPlayers);
		return map;
	}

	public static IslandData deserialize(SerializedMap map)
	{
		String name = map.getString(Keys.Name.name());
		ArrayList<UUID> trustedPlayers = (ArrayList<UUID>) map.getList(Keys.InvitedPlayers.name(), UUID.class);
		return new IslandData(name, trustedPlayers);
	}

	public void setName(String name)
	{
		this.name = name;
		saveChanges();
	}

	public void setInvitedPlayers(ArrayList<UUID> invitedPlayers)
	{
		this.invitedPlayers = invitedPlayers;
		saveChanges();
	}
}
