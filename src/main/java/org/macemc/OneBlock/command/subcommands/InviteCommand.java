package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.PlayerSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;

@SuppressWarnings("unused")
public final class InviteCommand extends PlayerSubCommand
{
	private InviteCommand()
	{
		super("invite");
		setMinArguments(1);
		setDescription("Invite a player to your island");
		setUsage("<player>");
		this.oneBlockState = OneBlockState.ONEBLOCK_ONLY;
	}

	@Override
	protected void execute(Player p, PlayerData playerData)
	{
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
