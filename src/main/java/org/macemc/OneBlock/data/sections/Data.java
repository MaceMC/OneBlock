package org.macemc.OneBlock.data.sections;

import lombok.Setter;
import org.macemc.OneBlock.data.PlayerData;
import org.mineacademy.fo.model.ConfigSerializable;

@Setter
public abstract class Data implements ConfigSerializable
{
	protected PlayerData playerData;

	protected void saveChanges()
	{
		playerData.save();
	}
}
