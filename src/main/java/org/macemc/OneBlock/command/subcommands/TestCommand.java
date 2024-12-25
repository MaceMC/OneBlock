package org.macemc.OneBlock.command.subcommands;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.listener.BlockListener;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.List;
import java.util.Map;

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
		customBlockData.setProtected(true);

		tellSuccess("Command executed successfully");
	}

	public void testMaterial(Map<Integer, List<String>> map)
	{
		for (List<String> s : map.values())
		{
			for (String str : s )
			{
				try
				{
					System.out.println("Testing for String " + str);
					Material mat = Material.getMaterial(str);
					if (mat != null)
					{
						System.out.println("Found!");
						continue;
					}
					EntityType entityType = EntityType.fromName(str);
					if (entityType != null)
					{
						System.out.println("Found!");
						continue;
					}
					System.out.println("Not found! " + str);
				}
				catch (Exception e)
				{
					Common.log(e.toString());
				}
			}
		}
	}
}
