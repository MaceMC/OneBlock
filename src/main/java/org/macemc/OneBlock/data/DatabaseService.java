package org.macemc.OneBlock.data;

import lombok.Getter;
import org.macemc.OneBlock.OneBlockPlugin;
import org.macemc.OneBlock.data.sections.IslandData;
import org.macemc.OneBlock.data.sections.OneBlockData;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.database.SimpleDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService extends SimpleDatabase
{
	private static final String url = "jdbc:sqlite:" + OneBlockPlugin.getInstance().getDataFolder() + "/data.db";

	@Getter
	private static DatabaseService instance = new DatabaseService();

	private DatabaseService()
	{
		Common.runAsync(() ->
		{
			connect(url);
			createTable(new TableCreator("playerdata")
					.add("uuid", "TEXT UNIQUE")
					.add("playerData", "TEXT"));
			createTable(new TableCreator("general")
					.add("key", "TEXT UNIQUE")
					.add("value", "TEXT"));
		});
	}

	public void loadPlayerData(PlayerData playerData)
	{
		try
		{
			PreparedStatement stmt = prepareStatement("SELECT * FROM playerdata WHERE uuid = ?");
			stmt.setString(1, playerData.getUuid().toString());
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				SerializedMap map = SerializedMap.fromJson(rs.getString("playerData"));
				playerData.setAttributes(map);
			} else
			{
				playerData.setIslandData(new IslandData());
				playerData.setOneBlockData(new OneBlockData());
			}
		} catch (SQLException e)
		{
			Common.warning(e.getMessage());
		}
	}

	public void savePlayerData(PlayerData playerData)
	{
		try
		{
			PreparedStatement stmt = prepareStatement("INSERT INTO playerdata (uuid, playerData) VALUES (?, ?) " +
					"ON CONFLICT(uuid) DO UPDATE SET playerData = excluded.playerData");
			SerializedMap map = playerData.serialized();
			stmt.setString(1, playerData.getUuid().toString());
			stmt.setString(2, map.toJson());
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			Common.warning(e.getMessage());
		}
	}

	public void loadGeneralData()
	{
	}
}