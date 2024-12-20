package org.macemc.OneBlock.data;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.sections.IslandData;
import org.macemc.OneBlock.data.sections.OneBlockData;
import org.mineacademy.fo.settings.ConfigItems;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.Set;
import java.util.UUID;

@Getter
public class PlayerData extends YamlConfig
{
	private final static ConfigItems<PlayerData> loadedData = ConfigItems.fromFolder("playerdata", PlayerData.class);

	private IslandData islandData;
	private OneBlockData oneBlockData;

	private PlayerData(final Player p)
	{
		UUID uuid = p.getUniqueId();
		loadConfiguration("playerdata/uuid.yml", "playerdata/" + uuid + ".yml");
	}

	private PlayerData(final String uuid)
	{
		if (uuid.equals("uuid.yml")) return;
		loadConfiguration("playerdata/uuid.yml", "playerdata/" + uuid + ".yml");
	}

	@Override
	protected void onLoad()
	{
		setPathPrefix("");

		/* Loading island data */
		this.islandData = IslandData.deserialize(getMap("Island"));
		this.islandData.setPlayerData(this);

		/* Loading OneBlock data */
		this.oneBlockData = OneBlockData.deserialize(getMap("OneBlock"));
		this.oneBlockData.setPlayerData(this);
	}

	@Override
	protected void onSave()
	{
		setPathPrefix("");

		/* Saving island data */
		this.set("Island", islandData.serialize());

		/* Saving OneBlock data */
		this.set("OneBlock", oneBlockData.serialize());
	}

	@Override
	protected boolean saveComments()
	{
		return false;
	}

	public static PlayerData FindOrCreateData(final Player p)
	{
		return FindData(p) != null ? FindData(p) : CreateData(p);
	}

	public static PlayerData CreateData(final Player p)
	{
		return loadedData.loadOrCreateItem(p.getUniqueId().toString(), () -> new PlayerData(p));
	}

	public void removeData(final PlayerData templateFolderData)
	{
		loadedData.removeItem(templateFolderData);
	}

	public void removeDataByName(final String name)
	{
		loadedData.removeItemByName(name);
	}

	public static PlayerData FindData(final Player p)
	{
		return loadedData.findItem(p.getUniqueId().toString());
	}

	public static boolean IsDataLoaded(final String name)
	{
		return loadedData.isItemLoaded(name);
	}

	public static Set<String> GetLoadedDataNames()
	{
		return loadedData.getItemNames();
	}

	public static void loadData()
	{
		loadedData.loadItems();
	}

}
