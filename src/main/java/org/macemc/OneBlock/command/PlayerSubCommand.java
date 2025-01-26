package org.macemc.OneBlock.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.utility.OneBlockUtil;

public abstract class PlayerSubCommand extends OneBlockSubCommand {

	protected enum OneBlockState {
		ONEBLOCK_ONLY, NO_ONEBLOCK_ONLY, BOTH
	}

	protected OneBlockState oneBlockState = OneBlockState.BOTH;

	protected PlayerSubCommand(final String sublabel) {
		super(sublabel);
	}

	@Override
	protected void execute(CommandSender sender) {
		if (!isPlayer()) {
			tell("This command is for players only!");
			return;
		}

		Player p = getPlayer();
		PlayerData playerData = PlayerData.findOrCreateData(p);

		if (oneBlockState == OneBlockState.ONEBLOCK_ONLY && !OneBlockUtil.hasOneBlock(playerData)) {
			tell("You don't have a OneBlock! Use /ob create!");
			return;
		}

		if (oneBlockState == OneBlockState.NO_ONEBLOCK_ONLY && OneBlockUtil.hasOneBlock(playerData)) {
			tell("You cannot use this command, you have a OneBlock!");
			return;
		}
		execute(p, playerData);
	}

	protected abstract void execute(Player p, PlayerData playerData);
}
