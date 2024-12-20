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
		Name("Name"),
		TrustedPlayers("InvitedPlayers");

		private final String key;

		Keys(String key)
		{
			this.key = key;
		}

		@Override
		public String toString()
		{
			return key;
		}
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
		map.put(Keys.Name.toString(), name);
		map.put(Keys.TrustedPlayers.toString(), invitedPlayers);
		return map;
	}

	public static IslandData deserialize(SerializedMap map)
	{
		String name = map.getString(Keys.Name.toString());
		ArrayList<UUID> trustedPlayers = (ArrayList<UUID>) map.getList(Keys.TrustedPlayers.toString(), UUID.class);
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
