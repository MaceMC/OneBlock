package org.macemc.OneBlock.world;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.event.KeyValuePair;

import java.util.*;

public class VoidWorldGenerator extends ChunkGenerator
{
	// Gelöschte freie Insel coords in db vormerken

	private static final int island_chunks = 4;
	private static final int island_chunkGap = 0;

	private static final LinkedList<Chunk> islandChunks = new LinkedList<>();

	@Override
	public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData)
	{
		if (chunkX == 0 && chunkZ == 0)
			chunkData.setBlock(0, 64, 0, Material.GRASS_BLOCK);
	}
}
