package org.macemc.OneBlock.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import com.jeff_media.customblockdata.events.CustomBlockDataEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.data.sections.OneBlockData;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.RandomUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

		Player p = e.getPlayer();
		CustomBlockData customBlockData = new CustomBlockData(block, plugin);

		String ownerString = Objects.requireNonNull(customBlockData.get(ownerKey, PersistentDataType.STRING));

		if (ownerString.equalsIgnoreCase("server")) handleServerOneBlock(e);
		else handlePlayerOneBlock(e, block, ownerString);
	}

	private void handlePlayerOneBlock(BlockBreakEvent e, Block block, String ownerString) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(ownerString));
		PlayerData playerData = PlayerData.findOrCreateData(op.getUniqueId());

		String action = playerData.getOneBlockData().getAccessible().get(RandomUtil.nextBetween(0, playerData.getOneBlockData().getAccessible().size() - 1));
		playerData.getOneBlockData().registerBreak(e.getPlayer());
		handleAction(action, block);
	}

	private void handleServerOneBlock(BlockBreakEvent e) {
		List<String> rewards = OneBlockData.getAccessibleRewards(2);
		String action = rewards.get(RandomUtil.nextBetween(0, rewards.size() - 1));
		handleAction(action, e.getBlock());
	}

	private void handleAction(String action, Block block) {
		Material material = Material.getMaterial(action);
		EntityType entity = EntityType.fromName(action);

		if (material != null) Common.runLater(1, () -> block.setType(material, false));
		if (entity != null) Common.runLater(1, () -> {
			block.setType(Material.GRASS_BLOCK, false);
			block.getWorld().spawnEntity(block.getLocation().add(0, 1, 0), entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
		});
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
		e.setCancelled(true);
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
}
