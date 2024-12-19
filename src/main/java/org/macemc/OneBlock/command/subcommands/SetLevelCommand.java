package org.macemc.OneBlock.command.subcommands;

import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.settings.SimpleLocalization;

public final class SetLevelCommand extends OneBlockSubCommand
{
	private SetLevelCommand()
	{
		super("setLevel");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) tellError(SimpleLocalization.Commands.NO_CONSOLE);
		final int level = 10;
		Player p = getPlayer();
		tell("Finding or creating file...");
		PlayerData playerData = PlayerData.FindOrCreateData(p);
		tell("Success");
		playerData.setLevel(level);
		tell("New Level set to: " + level);
		tellSuccess("Command executed successfully");
	}
}
