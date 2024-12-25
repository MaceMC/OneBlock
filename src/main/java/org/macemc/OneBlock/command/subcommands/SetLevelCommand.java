package org.macemc.OneBlock.command.subcommands;

import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.settings.SimpleLocalization;

public final class SetLevelCommand extends OneBlockSubCommand
{
	private SetLevelCommand()
	{
		super("setLevel");
		setMinArguments(1);
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) { tellError(SimpleLocalization.Commands.NO_CONSOLE); return; }
		try
		{
			Player p = getPlayer();
			PlayerData playerData = PlayerData.FindOrCreateData(p);

			final int level = Integer.parseInt(args[0]);
			playerData.getOneBlockData().setLevel(level);

			tell("New Level set to: " + level);
		}
		catch (NumberFormatException e)
		{
			tellError(SimpleLocalization.Commands.INVALID_NUMBER);
		}
	}
}
