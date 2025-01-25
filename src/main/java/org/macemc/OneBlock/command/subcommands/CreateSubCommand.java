package org.macemc.OneBlock.command.subcommands;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.ListPersistentDataTypeProvider;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.listener.BlockListener;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class CreateSubCommand extends OneBlockSubCommand
{
	private CreateSubCommand()
	{
		super("create");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) tellError(SimpleLocalization.Commands.NO_CONSOLE);

		Player p = getPlayer();
		PlayerData playerData = PlayerData.findOrCreateData(p);
		Block block = p.getWorld().getBlockAt(new Location(p.getWorld(), 0, 64 ,0));
		CustomBlockData customBlockData = new CustomBlockData(block, OneBlockPlugin.getInstance());
		customBlockData.set(BlockListener.isOneBlockKey, PersistentDataType.BOOLEAN, true);
		customBlockData.set(BlockListener.ownerKey, PersistentDataType.STRING, p.getUniqueId().toString());
		block.setType(Material.GRASS_BLOCK);

		// WorldGuard Region
		WorldGuardService worldGuardService = WorldGuardService.getInstance();
		Location loc = block.getLocation();

		worldGuardService.addOBRegion(p, loc);
		worldGuardService.addIslandRegion(p, loc);

		tell("Your OneBlock was created!");
	}
}
