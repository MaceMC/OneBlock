package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.PlayerSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public final class KickCommand extends PlayerSubCommand
{
	private KickCommand()
	{
		super("kick");
		setMinArguments(1);
		setDescription("Kick a player from your island");
		setUsage("<player>");
		oneBlockState = OneBlockState.ONEBLOCK_ONLY;
	}

	protected void execute(Player p, PlayerData playerData)
	{
		HashMap<UUID, String> invited = playerData.getIslandData().getInvitedPlayers();

		if (!invited.containsValue(args[0])) { tell(args[0] + " is not invited to your island!"); return; }

		String name = args[0];
		UUID uuid = Bukkit.getOfflinePlayer(name).getUniqueId();

		if (p.getUniqueId().equals(uuid)) { tell("Are you trying to get you rid of your own property???"); return; }

		kick(p, uuid);
		tell("You kicked " + name + " from your island!");
	}

	@Override
	protected List<String> tabComplete()
	{
		List<String> list = new ArrayList<>();
		if (args.length == 1)
		{
			Player p = getPlayer();
			PlayerData playerData = PlayerData.findOrCreateData(p);
			return playerData.getIslandData().getInvitedPlayers().values().stream().toList();
		}
		return list;
	}

	private void kick(Player owner, UUID target)
	{
		PlayerData playerData = PlayerData.findOrCreateData(owner);
		playerData.getIslandData().getInvitedPlayers().remove(target);

		PlayerData targetData = PlayerData.findOrCreateData(target);
		playerData.getInvitedToIsland().remove(owner.getUniqueId(), owner.getName());

		WorldGuardService service = WorldGuardService.getInstance();
		service.removeMember(owner, target);
	}
}
