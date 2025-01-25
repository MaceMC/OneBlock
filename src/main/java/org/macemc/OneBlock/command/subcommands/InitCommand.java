package org.macemc.OneBlock.command.subcommands;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.Data;
import org.macemc.OneBlock.listener.BlockListener;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class InitCommand extends OneBlockSubCommand {
	private InitCommand() {
		super("init");
	}

	@Override
	protected void onCommand() {
		if (!isPlayer()) tellError(SimpleLocalization.Commands.NO_CONSOLE);

		Player p = getPlayer();
		Block block = p.getWorld().getBlockAt(new Location(p.getWorld(), 0, 64, 0));

		CustomBlockData customBlockData = new CustomBlockData(block, OneBlockPlugin.getInstance());
		customBlockData.set(BlockListener.isOneBlockKey, PersistentDataType.BOOLEAN, true);
		customBlockData.set(BlockListener.ownerKey, PersistentDataType.STRING, "server");

		block.setType(Material.GRASS_BLOCK);

		// WorldGuard Region
		WorldGuardService worldGuardService = WorldGuardService.getInstance();
		Location loc = block.getLocation();

		worldGuardService.prepareRegions(p, loc);

		tell("OneBlock was initialized!");

		Data.findFreeLocations(loc);
	}
}
