package org.macemc.OneBlock.command.subcommands;

import org.bukkit.command.CommandSender;
import org.macemc.OneBlock.command.OneBlockSubCommand;

@SuppressWarnings("unused")
public final class TestCommand extends OneBlockSubCommand {
	private TestCommand() {
		super("test");
	}

	@Override
	protected void execute(CommandSender sender) {
		tell("Test command executed!");
	}
}
