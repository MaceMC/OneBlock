package org.macemc.OneBlock.command.subcommands;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.listener.BlockListener;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class TestCommand extends OneBlockSubCommand
{
	private TestCommand()
	{
		super("test");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) tellError(SimpleLocalization.Commands.NO_CONSOLE);
		Player p = getPlayer();
		PlayerData playerData = PlayerData.FindOrCreateData(p);
		Block block = p.getWorld().getBlockAt(new Location(p.getWorld(), 0, 64 ,0));
		CustomBlockData customBlockData = new CustomBlockData(block, OneBlockPlugin.getInstance());
		customBlockData.set(BlockListener.isOneBlockKey, PersistentDataType.BOOLEAN, true);

		// WorldGuard Region
		WorldGuardService worldGuardService = WorldGuardService.getInstance();
		Location loc = block.getLocation();

		worldGuardService.addOBRegion(p, loc);

		tellSuccess("Command executed successfully");
	}
}
