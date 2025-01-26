package org.macemc.OneBlock.command.subcommands;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.command.PlayerSubCommand;
import org.macemc.OneBlock.data.Data;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.listener.BlockListener;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;

import java.util.concurrent.TimeUnit;

import static org.macemc.OneBlock.utility.OneBlockUtil.teleportOneBlock;

@SuppressWarnings("unused")
public final class CreateCommand extends PlayerSubCommand
{
	private CreateCommand()
	{
		super("create");
		this.oneBlockState = OneBlockState.NO_ONEBLOCK_ONLY;
		setDescription("Create your OneBlock");
		setCooldown(1, TimeUnit.MINUTES);
		setCooldownMessage("You need to wait before creating a new OneBlock!");
		setCooldownBypassPermission("ob.command.create.bypass");
	}

	@Override
	protected void execute(Player p, PlayerData playerData)
	{
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
		teleportOneBlock(p, loc);

		Data.initLocationSearch(loc);
	}
}
