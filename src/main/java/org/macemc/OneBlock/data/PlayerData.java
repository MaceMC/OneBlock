package org.macemc.OneBlock.data;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.fo.FileUtil;
import org.mineacademy.fo.settings.ConfigItems;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Getter
public class PlayerData extends YamlConfig
{
	private final static ConfigItems<PlayerData> loadedData = ConfigItems.fromFolder("playerdata", PlayerData.class);

	private String islandName;
	private List<String> invited;

	private Location blockLocation;
	private int level, breaks;

	private PlayerData(final Player p)
	{
		UUID uuid = p.getUniqueId();
		loadConfiguration("playerdata/uuid.yml", "playerdata/" + uuid + ".yml");
	}

	@SuppressWarnings("unused")
	private PlayerData(final String uuid)
	{
		if (uuid.equals("uuid.yml")) return;
		loadConfiguration("playerdata/uuid.yml", "playerdata/" + uuid + ".yml");
	}

	@Override
	protected void onLoad()
	{
		/* Loading island data */
		setPathPrefix("Island");

		if (isSetDefault("Name"))
			islandName = getString("Name");

		if (isSetDefault("Invited"))
			invited = getStringList("Invited");

		/* Loading OneBlock data */
		setPathPrefix("OneBlock");

		if (isSetDefault("Location"))
			blockLocation = Location.deserialize(getMap("Location").asMap());

		if (isSetDefault("Level"))
			level = getInteger("Level");

		if (isSetDefault("Breaks"))
			breaks = getInteger("Breaks");

		setPathPrefix("");
	}

	@Override
	protected void onSave()
	{
		setPathPrefix("Island");

		this.set("Name", islandName);
		this.set("Invited", invited);

		setPathPrefix("OneBlock");

		this.set("Location", blockLocation.serialize());
		this.set("Level", level);
		this.set("Breaks", breaks);

		setPathPrefix("");
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

	public void setIslandName(String islandName)
	{
		this.islandName = islandName;

		this.save();
	}

	public void setInvited(List<String> invited)
	{
		this.invited = invited;

		this.save();
	}

	public void setBlockLocation(Location blockLocation)
	{
		this.blockLocation = blockLocation;

		this.save();
	}

	public void setLevel(int level)
	{
		this.level = level;

		this.save();
	}

	public void setBreaks(int breaks)
	{
		this.breaks = breaks;

		this.save();
	}
}
