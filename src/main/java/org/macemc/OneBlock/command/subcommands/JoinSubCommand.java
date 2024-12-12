package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
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
		if (!isPlayer()) tellError(SimpleLocalization.Commands.NO_CONSOLE);
		Player p = getPlayer();
		Location location = new Location(getPlayer().getWorld(), 0, 64,0);
		p.teleport(location);
		tell("You were sent to OneBlock!");
	}
}
