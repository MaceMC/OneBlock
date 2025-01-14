package org.macemc.OneBlock.world.WorldGuard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class WorldGuardService
{
	@Getter
	private static final WorldGuardService Instance = new WorldGuardService();

	@Getter
	private WorldGuard worldGuard;
	@Getter
	private RegionContainer regionContainer;
	@Getter
	private RegionManager regionManager;

	private WorldGuardService()
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") == null) throw new RuntimeException("WorldGuard plugin not found!");

		worldGuard = WorldGuard.getInstance();
		regionContainer = worldGuard.getPlatform().getRegionContainer();
		regionManager = regionContainer.get(BukkitAdapter.adapt(Objects.requireNonNull(Bukkit.getServer().getWorld("world"))));
	}

	/**
	 * Creates the OneBlock WorldGuard region w/ default flags
	 * @param p OneBlock region owner
	 * @param loc OneBlock location
	 */
	public void addOBRegion(final Player p, final Location loc)
	{
		BlockVector3 vector3 = BukkitAdapter.asBlockVector(loc);
		regionManager.addRegion(new ProtectedCuboidRegion("ob-" + p.getName(), vector3, vector3));
		ProtectedRegion region = regionManager.getRegion("ob-" + p.getName());
		assert region != null;
		region.setFlag(Flags.PASSTHROUGH, StateFlag.State.DENY);
		region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
		region.setFlag(Flags.INTERACT, StateFlag.State.ALLOW);
		//TODO: Owner & Trusted only
	}

}
