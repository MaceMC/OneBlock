package org.macemc.OneBlock;

import org.macemc.OneBlock.listener.OneBlockListenerGroup;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class OneBlockPlugin extends SimplePlugin
{
	@Override
	protected void onPluginStart()
	{
		this.registerAllEvents(OneBlockListenerGroup.class);
	}
}