package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class InviteCommand extends OneBlockSubCommand
{
	private InviteCommand()
	{
		super("invite");
		this.setMinArguments(1);
	}

	protected void onCommand()
	{
		if (!isPlayer()) { tell(SimpleLocalization.Commands.NO_CONSOLE); return; }

		Player p = getPlayer();
		PlayerData playerData = PlayerData.findOrCreateData(p);
		if (!playerData.getOneBlockData().hasRegion()) { tell("You do not have an island! Use /ob create"); return; }

		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target != null)
		{
			if (p.equals(target)) { tell("You are the owner..."); return; }

			if (playerData.getIslandData().getInvitedPlayers().containsKey(target.getUniqueId())) { tell("You already invited this player!"); return; }

			invite(p, target);
			tell("You invited " + target.getName() + " to your island!");
		}
		else
			tell("Player " + this.args[0] + " is not online!");
	}

	private void invite(Player owner, Player target)
	{
		PlayerData playerData = PlayerData.findOrCreateData(owner);
		playerData.getIslandData().invitePlayer(target);

		PlayerData targetData = PlayerData.findOrCreateData(target);
		targetData.gotInvited(owner);

		WorldGuardService service = WorldGuardService.getInstance();
		service.addMember(owner, target);
	}
}
