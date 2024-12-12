package org.macemc.OneBlock.world;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.macemc.OneBlock.OneBlockPlugin;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Island implements ConfigSerializable
{
	private UUID ownerID;
	private String name;
	private Location oneBlockLocation;
	private ArrayList<UUID> trustedPlayers;

	public Island(@NotNull Player p)
	{
		this.ownerID = p.getUniqueId();
		this.name = p.getName() + "'s Island";
		this.oneBlockLocation = new Location(OneBlockPlugin.getInstance().getServer().getWorld("world"), 0, 64 ,0);
		this.trustedPlayers = new ArrayList<>();
	}

	@Override
	public SerializedMap serialize()
	{
		return null;
	}
}
