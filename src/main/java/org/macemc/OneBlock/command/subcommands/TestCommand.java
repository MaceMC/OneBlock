package org.macemc.OneBlock.command.subcommands;

import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.settings.SimpleLocalization;

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
		tellSuccess("Command executed successfully");
	}
}
