package org.macemc.OneBlock.world.WorldGuard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.macemc.OneBlock.data.PlayerData;

@Getter
public final class WorldGuardService
{
	@Getter
	private static final WorldGuardService Instance = new WorldGuardService();
	private final WorldGuard worldGuard;
	private final RegionContainer regionContainer;
	private final RegionManager regionManager;

	private WorldGuardService()
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") == null)
		{
			throw new RuntimeException("WorldGuard plugin not found!");
		} else
		{
			this.worldGuard = WorldGuard.getInstance();
			this.regionContainer = this.worldGuard.getPlatform().getRegionContainer();
			this.regionManager = this.regionContainer.get(BukkitAdapter.adapt(Objects.requireNonNull(Bukkit.getServer().getWorld("world"))));
		}
	}

	public void addOBRegion(Player p, Location loc)
	{
		BlockVector3 vector3 = BukkitAdapter.asBlockVector(loc);
		String regionName = "ob-" + p.getName().toLowerCase();
		this.regionManager.addRegion(new ProtectedCuboidRegion(regionName, vector3, vector3));
		PlayerData.FindOrCreateData(p).getOneBlockData().setRegionID(regionName);
		ProtectedRegion region = this.regionManager.getRegion(regionName);

		assert region != null;

		region.setFlag(Flags.PASSTHROUGH, State.DENY);
		region.setFlag(Flags.LEAF_DECAY, State.DENY);
		region.setFlag(Flags.TNT, State.DENY);
		region.setFlag(Flags.OTHER_EXPLOSION, State.DENY);
		region.setFlag(Flags.CREEPER_EXPLOSION, State.DENY);
		region.setFlag(Flags.GHAST_FIREBALL, State.DENY);
		region.setFlag(Flags.ENDERDRAGON_BLOCK_DAMAGE, State.DENY);
		region.setFlag(Flags.BLOCK_PLACE, State.DENY);
		region.setFlag(Flags.BLOCK_BREAK.getRegionGroupFlag(), RegionGroup.MEMBERS);
		region.getMembers().addPlayer(p.getUniqueId());
	}

	public void trustPlayer(Player owner, Player trust)
	{
		ProtectedRegion region = getProtectedRegion(owner);
		if (region == null) return;

		region.getMembers().addPlayer(trust.getUniqueId());
	}

	public void removeMember(Player owner, UUID target)
	{
		ProtectedRegion region = getProtectedRegion(owner);
		if (region == null) return;

		region.getMembers().removePlayer(target);
	}

	private @Nullable ProtectedRegion getProtectedRegion(Player owner)
	{
		PlayerData playerData = PlayerData.FindOrCreateData(owner);
		String regionID = playerData.getOneBlockData().getRegionID();
		return regionManager.getRegion(regionID);
	}
}