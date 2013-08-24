package seigneurnecron.minecraftmods.stargate.playerData;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.Configuration;

public class PlayerData {
	
	private static final String TELEPORTER_DATA = "teleporterData";
	private static final String STARGATE_DATA = "stargateData";
	private static final String SHIELD_DATA = "shieldData";
	
	/**
	 * The config object.
	 */
	private static Configuration config;
	
	/**
	 * A map of registered teleporter coordinates and names.
	 */
	private static Map<Coordinates, String> teleporterData = new HashMap<Coordinates, String>();
	
	/**
	 * A map of registered stargate address and names.
	 */
	private static Map<String, String> stargateData = new HashMap<String, String>();
	
	/**
	 * A map of registered stargate address and code.
	 */
	private static Map<String, Integer> shieldData = new HashMap<String, Integer>();
	
	/**
	 * Loads all data from the file.
	 */
	public static void loadPlayerData() {
		// TODO
	}
	
	private static void saveTeleporterDate() {
		// FIXME - ecraser la valeur existante.
		config.get(Configuration.CATEGORY_GENERAL, TELEPORTER_DATA, new String[] {"1", "2", "pwet", "plop"});
		saveConfig();
	}
	
	private static void saveConfig() {
		config.save();
	}
	
}
