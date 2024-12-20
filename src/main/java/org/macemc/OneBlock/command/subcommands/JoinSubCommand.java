package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.lang.Localization;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class JoinSubCommand extends OneBlockSubCommand
{
	private JoinSubCommand()
	{
		super("join");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) { tellError(SimpleLocalization.Commands.NO_CONSOLE); return; }
		Player p = getPlayer();
		PlayerData playerData = PlayerData.FindOrCreateData(p);
		Location location = playerData.getOneBlockData().getOneBlockLocation();
		p.teleport(location);
		tell(Localization.Commands.JOIN_REPLY);
	}
}