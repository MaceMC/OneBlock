package org.macemc.OneBlock.command.subcommands;

import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.settings.SimpleLocalization;

public final class GetLevelCommand extends OneBlockSubCommand
{
	private GetLevelCommand()
	{
		super("getLevel");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) tellError(SimpleLocalization.Commands.NO_CONSOLE);
		Player p = getPlayer();
		PlayerData playerData = PlayerData.FindOrCreateData(p);
		tell(playerData.get("OneBlock.Level", Integer.class).toString());
		tellSuccess("Command executed successfully");
	}
}
