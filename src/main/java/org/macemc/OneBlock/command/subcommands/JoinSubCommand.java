package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;

public final class JoinSubCommand extends OneBlockSubCommand
{
	private JoinSubCommand()
	{
		super("join");
	}

	@Override
	protected void onCommand()
	{
		if (!isPlayer()) tellError("asdad");
		Player p = getPlayer();
		Location location = new Location(getPlayer().getWorld(), 0, 64,0);
		p.teleport(location);
		PlayerData.init(p);
		tell("You were sent to OneBlock!");
	}
}
