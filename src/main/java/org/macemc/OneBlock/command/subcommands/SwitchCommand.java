package org.macemc.OneBlock.command.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.macemc.OneBlock.command.PlayerSubCommand;
import org.macemc.OneBlock.data.PlayerData;
import org.macemc.OneBlock.utility.OneBlockBossBar;
import org.macemc.OneBlock.utility.OneBlockUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.macemc.OneBlock.utility.OneBlockUtil.getSwitchedPlayerData;
import static org.macemc.OneBlock.utility.OneBlockUtil.teleportOneBlock;

@SuppressWarnings("unused")
public final class SwitchCommand extends PlayerSubCommand {

	private SwitchCommand() {
		super("switch");
		oneBlockState = OneBlockState.ONEBLOCK_ONLY;
		setMinArguments(1);
		setDescription("Switch to another player's island");
		setUsage("<player>");
		setCooldown(10, TimeUnit.SECONDS);
		setCooldownMessage("You are switching too fast!");
		setCooldownBypassPermission("ob.command.switch.bypass");
	}

	@Override
	protected void execute(Player p, PlayerData playerData) {

		if (!playerData.getInvitedToIsland().containsValue(args[0]) && !args[0].equals(p.getName())) {
			tell("You can not switch to this player's island!");
			return;
		}

		OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
		PlayerData targetData = PlayerData.findOrCreateData(op.getUniqueId());
		if (getSwitchedPlayerData(p).getUuid().equals(targetData.getUuid())) {
			tell("You are already on " + op.getName() + "'s island!");
			return;
		}
		tell("Switching to " + op.getName() + "'s island");
		switchIsland(p, targetData);
	}

	private void switchIsland(Player p, PlayerData targetData) {
		PersistentDataContainer container = p.getPersistentDataContainer();
		OneBlockBossBar.findOrCreateBossBar(targetData.getUuid()).removeViewer(p);
		OneBlockUtil.setSwitchPersistentData(p, targetData);
		Location location = targetData.getOneBlockData().getOneBlockLocation();
		teleportOneBlock(p, location);
	}

	@Override
	protected List<String> tabComplete() {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			Player p = getPlayer();
			list.add(p.getName());
			PlayerData playerData = PlayerData.findOrCreateData(p);
			list.addAll(playerData.getInvitedToIsland().values());
		}
		return list;
	}
}
