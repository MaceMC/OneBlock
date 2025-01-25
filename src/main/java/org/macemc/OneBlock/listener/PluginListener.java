package org.macemc.OneBlock.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.Data;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;

public class PluginListener extends OneBlockListenerGroup {

	@EventHandler
	public void onPluginEnable(PluginEnableEvent e) {
		if (e.getPlugin().getName().equals("WorldGuard")) {
			World world = Bukkit.getWorld("world");
			Location location = new Location(world, 0, 64, 0);
			assert world != null;
			initSpawn(world, location);
			Data.initLocationSearch(location);
		}
	}

	public static void initSpawn(World world, Location location) {
		Block block = world.getBlockAt(location);
		if (BlockListener.isOneBlock(block)) return;
		world.setSpawnLocation(0, 65, 0);

		CustomBlockData customBlockData = new CustomBlockData(block, OneBlockPlugin.getInstance());
		customBlockData.set(BlockListener.isOneBlockKey, PersistentDataType.BOOLEAN, true);
		customBlockData.set(BlockListener.ownerKey, PersistentDataType.STRING, "server");

		WorldGuardService worldGuardService = WorldGuardService.getInstance();
		worldGuardService.addOBRegion("server", location);
		worldGuardService.addIslandRegion("server", location);
	}
}
