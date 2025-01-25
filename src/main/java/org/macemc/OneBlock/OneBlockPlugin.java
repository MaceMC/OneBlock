package org.macemc.OneBlock;

import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.macemc.OneBlock.data.DatabaseService;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.listener.OneBlockListenerGroup;
import org.macemc.OneBlock.placeholder.PlaceholderAPIHook;
import org.macemc.OneBlock.world.VoidWorldBiome;
import org.macemc.OneBlock.world.VoidWorldGenerator;
import org.mineacademy.fo.plugin.SimplePlugin;


public final class OneBlockPlugin extends SimplePlugin
{
	DatabaseService databaseService;

	@Override
	protected void onPluginStart()
	{
		databaseService = DatabaseService.getInstance();
		this.registerAllEvents(OneBlockListenerGroup.class);

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
			new PlaceholderAPIHook(this).register();
	}

	@Override
	protected void onPluginStop()
	{
		PlayerData.saveAll();
		databaseService.saveGeneralData();
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