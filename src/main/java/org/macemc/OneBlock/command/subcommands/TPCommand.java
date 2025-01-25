package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.lang.Localization;
import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class TPCommand extends OneBlockSubCommand {
	private TPCommand() {
		super("tp");
	}

	@Override
	protected void onCommand() {
		if (!isPlayer()) {
			tellError(SimpleLocalization.Commands.NO_CONSOLE);
			return;
		}

		Player p = getPlayer();
		PlayerData playerData = PlayerData.findOrCreateData(p);
		Location location;

		if (args.length == 0) {
			if (!playerData.getOneBlockData().hasRegion()) {
				tell("You do not own a OneBlock");
				return;
			}
			location = playerData.getOneBlockData().getOneBlockLocation();
			tell(Localization.Commands.TP_REPLY);
		} else {
			if (!playerData.getInvitedToIsland().containsValue(args[0])) {
				tell("You can not teleport to this player's island!");
				return;
			}

			OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
			PlayerData targetData = PlayerData.findOrCreateData(op.getUniqueId());
			location = targetData.getOneBlockData().getOneBlockLocation();
		}

		p.teleportAsync(location.toCenterLocation().add(0, 1, 0));
	}

	@Override
	protected List<String> tabComplete() {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			Player p = getPlayer();
			PlayerData playerData = PlayerData.findOrCreateData(p);
			return playerData.getInvitedToIsland().values().stream().toList();
		}
		return list;
	}
}