package org.macemc.OneBlock.data.sections;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.ConfigSerializable;

@Setter
public abstract class Data implements ConfigSerializable
{
	protected PlayerData playerData;

	protected void saveChanges()
	{
		Common.runAsync(() -> playerData.save());
	}
}
