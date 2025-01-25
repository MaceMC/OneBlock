package org.macemc.OneBlock.command.subcommands;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.Data;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.listener.BlockListener;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class CreateCommand extends OneBlockSubCommand
{
	private CreateCommand()
	{
		super("create");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) tellError(SimpleLocalization.Commands.NO_CONSOLE);

		Player p = getPlayer();
		PlayerData playerData = PlayerData.findOrCreateData(p);
		if (playerData.getOneBlockData().hasRegion()) {
			tell("You already have a OneBlock!");
			return;
		}

		Block block = p.getWorld().getBlockAt(Data.getFreeLocations().remove());
		CustomBlockData customBlockData = new CustomBlockData(block, OneBlockPlugin.getInstance());
		customBlockData.set(BlockListener.isOneBlockKey, PersistentDataType.BOOLEAN, true);
		customBlockData.set(BlockListener.ownerKey, PersistentDataType.STRING, p.getUniqueId().toString());
		block.setType(Material.GRASS_BLOCK);

		// WorldGuard Region
		WorldGuardService worldGuardService = WorldGuardService.getInstance();
		Location loc = block.getLocation();

		worldGuardService.prepareRegions(p, loc);

		tell("Your OneBlock was created!");
		p.teleportAsync(loc.clone().add(0, 1, 0).toCenterLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
		p.setRespawnLocation(loc.clone().add(0, 1, 0).toCenterLocation(), true);

		Data.findFreeLocations(loc);
	}
}
