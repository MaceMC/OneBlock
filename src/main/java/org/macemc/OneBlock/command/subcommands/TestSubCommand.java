package org.macemc.OneBlock.command.subcommands;

import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.UUID;

@SuppressWarnings("unused")
public final class TestSubCommand extends OneBlockSubCommand
{
	private TestSubCommand()
	{
		super("test");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) { tellError(SimpleLocalization.Commands.NO_CONSOLE); return; }

		Player p = getPlayer();
		PlayerData playerData = PlayerData.getLoadedData().findItem(p.getUniqueId().toString());

		boolean found = playerData != null;
		tell("Found playerData: " + found);
	}
}
