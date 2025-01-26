package org.macemc.OneBlock.command.subcommands;

import org.bukkit.entity.Player;
import org.macemc.OneBlock.command.PlayerSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.lang.Localization;

import java.util.concurrent.TimeUnit;

import static org.macemc.OneBlock.utility.OneBlockUtil.getSwitchedPlayerData;
import static org.macemc.OneBlock.utility.OneBlockUtil.teleportOneBlock;

@SuppressWarnings("unused")
public final class TPCommand extends PlayerSubCommand {
	private TPCommand() {
		super("tp|join");
		oneBlockState = OneBlockState.ONEBLOCK_ONLY;
		setDescription("Teleport to your current island");
		setCooldown(5, TimeUnit.SECONDS);
		setCooldownMessage("You are teleporting too fast!");
		setCooldownBypassPermission("ob.command.tp.bypass");
	}

	@Override
	protected void execute(Player p, PlayerData playerData) {

		tell(Localization.Commands.TP_REPLY);
		teleportOneBlock(p, getSwitchedPlayerData(p));
	}
}