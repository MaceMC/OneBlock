package org.macemc.OneBlock.data;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.OneBlockPlugin;

import java.io.File;
import java.util.ArrayList;

public class PlayerDataManager
{
	@Getter
	private final ArrayList<PlayerData> playerData = new ArrayList<>();
	@Getter
	private static String path;

	private PlayerDataManager()
	{
		path = "playerdata";
		File dir = new File(OneBlockPlugin.getInstance().getDataFolder(), path);
		if (!dir.exists())
			dir.mkdirs();
	}

	public void addPlayerData(@NonNull PlayerData data)
	{
		playerData.add(data);
	}

	public @NonNull PlayerData getPlayerData(@NonNull Player p)
	{
		for (PlayerData data : playerData)
		{
			if (data.getFileName().equals(p.getUniqueId().toString()))
				return data;
		}
		return new PlayerData(p);
	}

	private static final class SingletonHolder
	{
		private static final PlayerDataManager INSTANCE = new PlayerDataManager();
	}

	public static PlayerDataManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
}
