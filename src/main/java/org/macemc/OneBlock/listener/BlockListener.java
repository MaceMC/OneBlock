package org.macemc.OneBlock.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import com.jeff_media.customblockdata.events.CustomBlockDataEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.RandomUtil;

import java.util.*;

public class BlockListener extends OneBlockListenerGroup
{
	public static final NamespacedKey isOneBlockKey = new NamespacedKey(OneBlockPlugin.getInstance(), "oneBlock.isOneBlock");
	public static final NamespacedKey ownerKey = new NamespacedKey(OneBlockPlugin.getInstance(), "oneBlock.owner");

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e)
	{
		Block block = e.getBlock();
		Plugin plugin = OneBlockPlugin.getInstance();
		if (!isOneBlock(block)) return;

		CustomBlockData customBlockData = new CustomBlockData(block, plugin);
		UUID uuid = UUID.fromString(Objects.requireNonNull(customBlockData.get(ownerKey, PersistentDataType.STRING)));
		OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
		PlayerData playerData = PlayerData.FindOrCreateData(op);
		final Location location = block.getLocation().toCenterLocation().add(0, 1, 0);

		Map.Entry<EntityType, Material> action = actionSet(playerData.getOneBlockData().getAccessible());

		if (action.getKey() != null)
		{
			plugin.getServer().getScheduler().runTaskLater(plugin, () ->
			{
				block.setType(Material.GRASS_BLOCK, false);
				block.getWorld().spawnEntity(location, action.getKey());
			}, 1L);
		}
		else
		{
			plugin.getServer().getScheduler().runTaskLater(plugin, () -> block.setType(action.getValue(), false), 1L);
		}
	}

	// For OneBlock Physics keeping and other fixes
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent e)
	{
		if (isOneBlock(e.getBlock()))
			e.setCancelled(true);
	}

	// OneBlock drop functionality
	@EventHandler
	public void onBlockDropItem(BlockDropItemEvent e)
	{
		if (!isOneBlock(e.getBlock()))
			return;
		Location loc = e.getBlock().getLocation().toCenterLocation().add(0, 0.5, 0);
		e.getItems().forEach(is -> e.getPlayer().getWorld().dropItem(loc, is.getItemStack()));
	}



	@EventHandler
	public void onCustomBlockData(CustomBlockDataEvent e)
	{
		if (e.getReason() == CustomBlockDataEvent.Reason.BLOCK_BREAK)
			e.setCancelled(true);
	}

	public static boolean isOneBlock(Block block)
	{
		CustomBlockData customBlockData = new CustomBlockData(block, OneBlockPlugin.getInstance());
		return customBlockData.getOrDefault(isOneBlockKey, PersistentDataType.BOOLEAN, false);
	}

	public static Map.Entry<EntityType, Material> actionSet(List<String> accessible)
	{
		int i = RandomUtil.nextBetween(0, accessible.size() - 1);
		EntityType entityType = EntityType.fromName(accessible.get(i));
		Material material = Material.getMaterial(accessible.get(i));

		return new AbstractMap.SimpleEntry<>(entityType, material);
	}
}
