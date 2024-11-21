package org.macemc.OneBlock.data;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;
import java.util.UUID;

@Getter
public class PlayerData extends YamlConfig
{
	private String islandName;
	private List<String> invited;

	private Location blockLocation;
	private int level;

	PlayerData(Player p)
	{
		UUID uuid = p.getUniqueId();
		loadConfiguration("playerdata/uuid.yml", PlayerDataManager.getPath() + "/" + uuid + ".yml");
		PlayerDataManager.getInstance().addPlayerData(this);
	}

	public static PlayerData init(Player p)
	{
		return PlayerDataManager.getInstance().getPlayerData(p);
	}

	@Override
	public void onLoad()
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
			blockLocation = getLocation("Location");

		if (isSetDefault("Level"))
			level = getInteger("Level");

		if (isSetDefault("Breaks"))
			blockLocation = getLocation("Breaks");
	}
}
