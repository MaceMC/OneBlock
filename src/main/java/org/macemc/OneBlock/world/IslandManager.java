package org.macemc.OneBlock.world;

import java.util.ArrayList;

public class IslandManager
{
	private final ArrayList<Island> islands = new ArrayList<>();

	private static final IslandManager INSTANCE = new IslandManager();

	private IslandManager()
	{

	}

	public static IslandManager getInstance()
	{
		return IslandManager.INSTANCE;
	}
}
