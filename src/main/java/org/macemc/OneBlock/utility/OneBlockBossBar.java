package org.macemc.OneBlock.utility;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.data.PlayerData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OneBlockBossBar {

	private static ConcurrentHashMap<UUID, OneBlockBossBar> bossBars = new ConcurrentHashMap<>();
	private static final BarColor color = BarColor.GREEN;
	private static final BarStyle style = BarStyle.SOLID;

	@Getter
	private final BossBar bossBar;

	private final PlayerData playerData;

	public OneBlockBossBar(UUID uuid) {
		bossBar = Bukkit.createBossBar("Level %ob_level%: Level Name", color, style);
		playerData = PlayerData.findOrCreateData(uuid);
	}

	public void addViewer(Player p) {
		bossBar.addPlayer(p);
	}

	public void removeViewer(Player p) {
		bossBar.removePlayer(p);
	}

	public void registerBreak() {
		bossBar.setProgress(calculateProgress(playerData.getOneBlockData().getBreaks(), playerData.getOneBlockData().getLevel() + 1));
	}

	public void levelUp() {
		bossBar.setTitle("Level %ob_level%: " + "Level Name");
		bossBar.setProgress(0);
	}

	public static int calculateBreaksForLevel(int level) {
		final int a = 1, b = 6;
		return (int) Math.ceil(Math.pow(level * b, 2) / a);
	}

	public static double calculateProgress(int breaks, int level) {
		return (double) breaks / calculateBreaksForLevel(level);
	}

	public static OneBlockBossBar findOrCreateBossBar(UUID uuid) {
		return bossBars.computeIfAbsent(uuid, OneBlockBossBar :: new);
	}
}
