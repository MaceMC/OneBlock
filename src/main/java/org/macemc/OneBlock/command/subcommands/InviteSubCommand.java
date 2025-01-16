package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class InviteSubCommand extends OneBlockSubCommand
{
	private InviteSubCommand()
	{
		super("invite");
		this.setMinArguments(1);
	}

	protected void onCommand()
	{
		if (!isPlayer()) { tellError(SimpleLocalization.Commands.NO_CONSOLE); return; }

		Player p = getPlayer();
		PlayerData playerData = PlayerData.FindOrCreateData(p);
		if (!playerData.getOneBlockData().hasRegion()) { tell("You do not have an island! Use /ob create"); return; }

		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target != null)
		{
			if (p.equals(target)) { tell("You are the owner..."); return; }

			invite(p, target);
			tell("You invited " + target.getName() + " to your island!");
		}
		else
			tellError("Player " + this.args[0] + " is not online!");
	}

	private void invite(Player owner, Player target)
	{
		PlayerData playerData = PlayerData.FindOrCreateData(owner);
		playerData.getIslandData().invitePlayer(target);

		WorldGuardService service = WorldGuardService.getInstance();
		service.trustPlayer(owner, target);
	}
}
