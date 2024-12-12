package org.macemc.OneBlock;

import lombok.Getter;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.macemc.OneBlock.data.PlayerDataManager;
import org.macemc.OneBlock.listener.OneBlockListenerGroup;
import org.macemc.OneBlock.world.VoidWorldBiome;
import org.macemc.OneBlock.world.VoidWorldGenerator;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class OneBlockPlugin extends SimplePlugin
{
	@Getter
	private static PlayerDataManager playerDataManager;

	@Override
	protected void onPluginStart()
	{
		this.registerAllEvents(OneBlockListenerGroup.class);
		playerDataManager = PlayerDataManager.getInstance();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id)
	{
		return new VoidWorldGenerator();
	}

	@Override
	public BiomeProvider getDefaultBiomeProvider(@NotNull String worldName, @Nullable String id)
	{
		return new VoidWorldBiome();
	}
}