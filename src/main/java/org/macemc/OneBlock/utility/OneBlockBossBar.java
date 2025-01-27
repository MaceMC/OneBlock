package org.macemc.OneBlock.utility;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.macemc.OneBlock.config.Settings;
import org.macemc.OneBlock.data.PlayerData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OneBlockBossBar {

	private static final ConcurrentHashMap<UUID, OneBlockBossBar> loadedBossBars = new ConcurrentHashMap<>();
	private static final BarColor color = BarColor.GREEN;
	private static final BarStyle style = BarStyle.SOLID;
	private static final Map<Integer, String> levelNames = Settings.OneBlock.levelNames;

	@Getter
	private final BossBar bossBar;

	private final PlayerData playerData;
	private int nextLevel;
	private int nextLevelBreaks;

	public OneBlockBossBar(UUID uuid) {
		playerData = PlayerData.findOrCreateData(uuid);
		nextLevel = playerData.getOneBlockData().getLevel() + 1;
		nextLevelBreaks = calculateBreaksForLevel(nextLevel) - calculateBreaksForLevel(nextLevel - 1);
		bossBar = Bukkit.createBossBar(levelNames.get(nextLevel - 1), color, style);
		bossBar.setProgress(0);
	}

	public void addViewer(Player p) {
		if (!bossBar.getPlayers().contains(p))
			bossBar.addPlayer(p);
	}

	public void removeViewer(Player p) {
		if (bossBar.getPlayers().contains(p))
			bossBar.removePlayer(p);
		loadedBossBars.entrySet().removeIf(entry -> entry.getValue().equals(this));
	}

	public void registerBreak() {
		int currentBreaks = playerData.getOneBlockData().getBreaks();
		int breaksForCurrentLevel = currentBreaks - calculateBreaksForLevel(nextLevel - 1);
		bossBar.setProgress((double) breaksForCurrentLevel / nextLevelBreaks);
	}

	public void levelUp() {
		bossBar.setTitle("Level " + nextLevel + ": Level Name");
		nextLevel++;
		nextLevelBreaks = calculateBreaksForLevel(nextLevel) - calculateBreaksForLevel(nextLevel - 1);
		bossBar.setProgress(0);
	}

	public static int calculateBreaksForLevel(int level) {
		final int a = 1, b = 6;
		return (int) Math.ceil(Math.pow(level * b, 2) / a);
	}

	public static OneBlockBossBar findOrCreateBossBar(UUID uuid) {
		return loadedBossBars.computeIfAbsent(uuid, OneBlockBossBar :: new);
	}
}
