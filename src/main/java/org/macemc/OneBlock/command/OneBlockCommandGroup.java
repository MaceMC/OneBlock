package org.macemc.OneBlock.command;

import org.macemc.OneBlock.command.subcommands.OneBlockSubCommand;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.ReloadCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;

import java.util.List;

@SuppressWarnings("unused")
@AutoRegister
public final class OneBlockCommandGroup extends SimpleCommandGroup
{
	public OneBlockCommandGroup()
	{
		super("oneblock", List.of("ob"));
	}

	@Override
	protected void registerSubcommands()
	{
		this.registerSubcommand(OneBlockSubCommand.class);
		this.registerSubcommand(new ReloadCommand());
	}

	@Override
	protected String getCredits()
	{
		return "Use '/ob ?' to see what commands we offer!";
	}
}
