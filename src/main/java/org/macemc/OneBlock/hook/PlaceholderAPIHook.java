package org.macemc.OneBlock.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.PlayerData;

public final class PlaceholderAPIHook extends PlaceholderExpansion
{

	@Override
	public @NotNull String getAuthor()
	{
		return "tooobiiii";
	}

	@Override
	public @NotNull String getIdentifier()
	{
		return "ob";
	}

	@Override
	public @NotNull String getVersion()
	{
		return OneBlockPlugin.getVersion();
	}

	@Override
	public boolean persist()
	{
		return true;
	}

	@Override
	public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params)
	{
		PlayerData playerData = PlayerData.findOrCreateData(offlinePlayer.getUniqueId());
		if (playerData == null) return null;

		if (params.equalsIgnoreCase("level"))
			return String.valueOf(playerData.getOneBlockData().getLevel());

		if (params.equalsIgnoreCase("breaks"))
			return String.valueOf(playerData.getOneBlockData().getBreaks());

		if (params.equalsIgnoreCase("islandName")) return playerData.getIslandData().getIslandName();

		return null;
	}
}
