package org.macemc.OneBlock.placeholder;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.config.Settings;
import org.macemc.OneBlock.data.PlayerData;

public final class PlaceholderAPIHook extends PlaceholderExpansion
{
	private final Plugin plugin;

	public PlaceholderAPIHook(Plugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public @NotNull String getIdentifier()
	{
		return "ob";
	}

	@Override
	public @NotNull String getAuthor()
	{
		return "toobiii";
	}

	@Override
	public @NotNull String getVersion()
	{
		return "0.0.1";
	}

	@Override
	public boolean persist()
	{
		return true;
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params)
	{
		System.out.println("onPlaceholderRequest: " + player + ", " + params);

		return null;
	}

	@Override
	public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params)
	{
		System.out.println("onRequest: " + offlinePlayer + ", " + params);
		PlayerData playerData = PlayerData.findOrCreateData(offlinePlayer.getUniqueId());
		if (playerData == null) return null;

		if (params.equalsIgnoreCase("level"))
			return String.valueOf(playerData.getOneBlockData().getLevel());

		if (params.equalsIgnoreCase("breaks"))
			return String.valueOf(playerData.getOneBlockData().getBreaks());

		return null;
	}
}
