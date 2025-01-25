package org.macemc.OneBlock.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.Data;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;

public class WorldListener extends OneBlockListenerGroup {

	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		if (e.getWorld().getName().equals("world")) {
			Location location = new Location(e.getWorld(), 0, 64, 0);
			initSpawn(e.getWorld(), location);
			Data.initLocationSearch(location);
		}
	}

	public static void initSpawn(World world, Location location) {
		if (BlockListener.isOneBlock(world.getBlockAt(location))) return;
		world.setSpawnLocation(0, 65, 0);

		Block block = world.getBlockAt(location);

		CustomBlockData customBlockData = new CustomBlockData(block, OneBlockPlugin.getInstance());
		customBlockData.set(BlockListener.isOneBlockKey, PersistentDataType.BOOLEAN, true);
		customBlockData.set(BlockListener.ownerKey, PersistentDataType.STRING, "server");

		WorldGuardService worldGuardService = WorldGuardService.getInstance();
		worldGuardService.addOBRegion("server", location);
		worldGuardService.addIslandRegion("server", location);
	}
}
