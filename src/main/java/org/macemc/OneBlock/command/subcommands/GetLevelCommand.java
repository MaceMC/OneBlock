package org.macemc.OneBlock.command.subcommands;

import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class GetLevelCommand extends OneBlockSubCommand
{
	private GetLevelCommand()
	{
		super("getLevel");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) { tellError(SimpleLocalization.Commands.NO_CONSOLE); return; }
		Player p = getPlayer();
		PlayerData playerData = PlayerData.FindOrCreateData(p);
		tell("Current Level is: " + playerData.getOneBlockData().getLevel());
		tellSuccess("Command executed successfully");
	}
}
