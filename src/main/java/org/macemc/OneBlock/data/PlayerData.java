package org.macemc.OneBlock.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.sections.IslandData;
import org.macemc.OneBlock.data.sections.OneBlockData;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

@Getter
@Setter
public class PlayerData
{
	private static final ConcurrentSkipListMap<UUID, PlayerData> loadedData = new ConcurrentSkipListMap<>();

	private final UUID uuid;
	private LinkedHashMap<UUID, String> invitedToIsland = new LinkedHashMap<>();

	private IslandData islandData;
	private OneBlockData oneBlockData;


	private PlayerData(UUID uuid)
	{
		this.uuid = uuid;
		loadFromDatabase();
	}

	private void loadFromDatabase()
	{
		DatabaseService.getInstance().loadPlayerData(this);
		assert this.islandData != null && this.oneBlockData != null;
		this.islandData.setPlayerData(this);
		this.oneBlockData.setPlayerData(this);
	}

	public void saveToDatabase()
	{
		Common.runAsync(() -> DatabaseService.getInstance().savePlayerData(this));
	}

	public SerializedMap serialized()
	{
		SerializedMap map = new SerializedMap();
		map.put("invited", invitedToIsland);
		map.put("island", islandData.serialize());
		map.put("ob", oneBlockData.serialize());
		return map;
	}

	public void setAttributes(SerializedMap map)
	{
		IslandData islandData = IslandData.deserialize(map.getMap("island"));
		OneBlockData oneBlockData = OneBlockData.deserialize(map.getMap("ob"));
		LinkedHashMap<UUID, String> invited = map.getMap("invited", UUID.class, String.class);
		this.islandData = islandData;
		this.oneBlockData = oneBlockData;
		this.invitedToIsland = invited;
	}

	public void gotInvited(Player p)
	{
		invitedToIsland.put(p.getUniqueId(), p.getName());
		saveToDatabase();
	}

	public static PlayerData findOrCreateData(Player p)
	{
		return findOrCreateData(p.getUniqueId());
	}

	public static PlayerData findOrCreateData(UUID uuid)
	{
		return loadedData.computeIfAbsent(uuid, PlayerData :: new);
	}

	public static void saveAll()
	{
		Common.runAsync(() -> loadedData.values().forEach(PlayerData :: saveToDatabase));
	}
}