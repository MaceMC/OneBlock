package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.world.WorldGuard.WorldGuardService;
import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public final class KickSubCommand extends OneBlockSubCommand
{
	private KickSubCommand()
	{
		super("kick");
		this.setMinArguments(1);
	}

	protected void onCommand()
	{
		if (!isPlayer()) { tellError(SimpleLocalization.Commands.NO_CONSOLE); return; }

		Player p = getPlayer();
		PlayerData playerData = PlayerData.FindOrCreateData(p);
		if (!playerData.getOneBlockData().hasRegion()) { tell("You do not have an island! Use /ob create"); return; }

		HashMap<UUID, String> invited = playerData.getIslandData().getInvitedPlayers();
		UUID uuid = Bukkit.getPlayerUniqueId(args[0]);
		if (!invited.containsKey(uuid)) { tell(args[0] + " is not invited to your island!"); return; }
		String name = invited.get(uuid);

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
			PlayerData playerData = PlayerData.FindOrCreateData(p);
			return playerData.getIslandData().getInvitedPlayers().values().stream().toList();
		}
		return list;
	}

	private void kick(Player owner, UUID target)
	{
		PlayerData playerData = PlayerData.FindOrCreateData(owner);
		playerData.getIslandData().getInvitedPlayers().remove(target);

		WorldGuardService service = WorldGuardService.getInstance();
		service.removeMember(owner, target);
	}
}
