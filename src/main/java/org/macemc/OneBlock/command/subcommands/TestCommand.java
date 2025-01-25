package org.macemc.OneBlock.command.subcommands;

import org.macemc.OneBlock.command.OneBlockSubCommand;

public final class TestCommand extends OneBlockSubCommand {
	private TestCommand() {
		super("test");
	}

	@Override
	protected void onCommand() {
		tellError("Hey");
		tell("Can you see this?");
	}
}
