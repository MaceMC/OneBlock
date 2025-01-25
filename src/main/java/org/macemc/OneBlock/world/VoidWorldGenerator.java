package org.macemc.OneBlock.world;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class VoidWorldGenerator extends ChunkGenerator
{
	@Override
	public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData)
	{
		if (chunkX == 0 && chunkZ == 0)
			chunkData.setBlock(0, 64, 0, Material.GRASS_BLOCK);
	}
}
