package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.command.OneBlockSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.settings.SimpleLocalization;

@SuppressWarnings("unused")
public final class SwitchCommand extends OneBlockSubCommand {

	private SwitchCommand() {
		super("switch");
	}

	@Override
	protected void onCommand() {
		if (!isPlayer()) {
			tellError(SimpleLocalization.Commands.NO_CONSOLE);
			return;
		}

		Player p = getPlayer();
		PlayerData playerData = PlayerData.findOrCreateData(p);

		if (!playerData.getInvitedToIsland().containsValue(args[0])) {
			tell("You can not switch to this player's island!");
			return;
		}

		OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
		switchIsland(p, op);
	}

	private void switchIsland(Player p, OfflinePlayer op) {
		p.setMetadata("Switched", new FixedMetadataValue(OneBlockPlugin.getInstance(), op.getUniqueId()));
		PlayerData playerData = PlayerData.findOrCreateData(op.getUniqueId());

	}
}
