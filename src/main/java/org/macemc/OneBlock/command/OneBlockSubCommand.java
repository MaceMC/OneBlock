package org.macemc.OneBlock.command;

import org.bukkit.command.CommandSender;
import org.macemc.OneBlock.config.Settings;
import org.mineacademy.fo.command.SimpleSubCommand;

public abstract class OneBlockSubCommand extends SimpleSubCommand {

	protected OneBlockSubCommand(final String sublabel) {
		super(sublabel);
		setTellPrefix(Settings.PLUGIN_PREFIX);
	}

	@Override
	protected void onCommand() {
		execute(getSender());
	}

	protected abstract void execute(CommandSender sender);
}