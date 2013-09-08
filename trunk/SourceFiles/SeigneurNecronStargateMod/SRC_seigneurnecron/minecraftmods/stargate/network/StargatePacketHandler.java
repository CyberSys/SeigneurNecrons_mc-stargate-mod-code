package seigneurnecron.minecraftmods.stargate.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargate;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerDataList;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerStargateData;
import seigneurnecron.minecraftmods.stargate.tools.playerData.PlayerTeleporterData;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Seigneur Necron
 */
public class StargatePacketHandler implements IPacketHandler {
	
	// Command names :
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CommandName {
		// Nothing here.
	}
	
	@CommandName
	public static final String TELEPORT = "telport";
	
	@CommandName
	public static final String STARGATE_OPEN = "stargateOpen";
	
	@CommandName
	public static final String STARGATE_CLOSE = "stargateClose";
	
	@CommandName
	public static final String STARGATE_CREATE = "stargateCreate";
	
	@CommandName
	public static final String SHIELD = "shield";
	
	@CommandName
	public static final String SHIELD_AUTOMATED = "shieldAutomated";
	
	@CommandName
	public static final String SHIELD_CODE = "shieldCode";
	
	/**
	 * This method gets the list of the StargatePacketHandler class static fields, and registers the content of those which have the @CommandName annotation as a command packet name.
	 */
	public static void registerCommandPackets() {
		try {
			Field[] fields = StargatePacketHandler.class.getFields();
			
			for(Field field : fields) {
				if(field.isAnnotationPresent(CommandName.class)) {
					registerCommandPacket((String) field.get(null));
				}
			}
		}
		catch(Exception argh) {
			StargateMod.debug("Error while registering a command packet id. Teleporters and stargates will not be usable.", Level.SEVERE, true);
			argh.printStackTrace();
		}
	}
	
	// Tile entity mapping part :
	
	private static final Map<Integer, Class<? extends TileEntityStargate>> tileEntityPacketId_to_class_map = new HashMap<Integer, Class<? extends TileEntityStargate>>();
	private static final Map<Class<? extends TileEntityStargate>, Integer> class_to_tileEntityPacketId_map = new HashMap<Class<? extends TileEntityStargate>, Integer>();
	
	private static final Map<Integer, Class<? extends PlayerDataList>> playerDataPacketId_to_class_map = new HashMap<Integer, Class<? extends PlayerDataList>>();
	private static final Map<Class<? extends PlayerDataList>, Integer> class_to_playerDataPacketId_map = new HashMap<Class<? extends PlayerDataList>, Integer>();
	
	private static final Map<Integer, String> commandPacketId_to_name_map = new HashMap<Integer, String>();
	private static final Map<String, Integer> name_to_commandPacketId_map = new HashMap<String, Integer>();
	
	/**
	 * The next unused packet id.
	 */
	private static int nextId = 1;
	
	/**
	 * Adds a class extending TileEntityStargate to the mapping of tile entity packets.
	 * @param clazz - the class to add.
	 */
	public static void registerTileEntityPacket(Class<? extends TileEntityStargate> clazz) {
		if(!isTileEntityPacketMapped(clazz)) {
			int id = getNextId();
			tileEntityPacketId_to_class_map.put(id, clazz);
			class_to_tileEntityPacketId_map.put(clazz, id);
		}
	}
	
	/**
	 * Adds a class extending PlayerDataList to the mapping of player data packets.
	 * @param clazz - the class to add.
	 */
	public static void registerPlayerDataPacket(Class<? extends PlayerDataList> clazz) {
		if(!isPlayerDataPacketMapped(clazz)) {
			int id = getNextId();
			playerDataPacketId_to_class_map.put(id, clazz);
			class_to_playerDataPacketId_map.put(clazz, id);
		}
	}
	
	/**
	 * Adds a command name to the mapping of command packets.
	 * @param name - the command name to add.
	 */
	public static void registerCommandPacket(String name) {
		if(!isCommandPacketMapped(name)) {
			int id = getNextId();
			commandPacketId_to_name_map.put(id, name);
			name_to_commandPacketId_map.put(name, id);
		}
	}
	
	/**
	 * Returns the tile entity packet id for the given tile entity class.
	 * @param clazz - a class extending TileEntityStargate.
	 * @return the tile entity packet id if it exists, else -1.
	 */
	public static int getTileEntityPacketIdFromClass(Class<? extends TileEntityStargate> clazz) {
		Integer id = class_to_tileEntityPacketId_map.get(clazz);
		return(id != null ? id : -1);
	}
	
	/**
	 * Returns the player data packet id for the given tile entity class.
	 * @param clazz - a class extending PlayerDataList.
	 * @return the player data packet id if it exists, else -1.
	 */
	public static int getPlayerDataPacketIdFromClass(Class<? extends PlayerDataList> clazz) {
		Integer id = class_to_playerDataPacketId_map.get(clazz);
		return(id != null ? id : -1);
	}
	
	/**
	 * Returns the command packet id for the given command name.
	 * @param name - a command name.
	 * @return the command packet id if it exists, else -1.
	 */
	public static int getCommandPacketIdFromName(String name) {
		Integer id = name_to_commandPacketId_map.get(name);
		return(id != null ? id : -1);
	}
	
	/**
	 * Returns the tile entity class for the given tile entity packet id.
	 * @param id - the id of a tile entity packet.
	 * @return a class extending TileEntityStargate if the id is in the map, else null.
	 */
	public static Class<? extends TileEntityStargate> getClassFromTileEntityPacketId(int id) {
		return tileEntityPacketId_to_class_map.get(id);
	}
	
	/**
	 * Returns the tile entity class for the given player data packet id.
	 * @param id - the id of a player data packet.
	 * @return a class extending PlayerDataList if the id is in the map, else null.
	 */
	public static Class<? extends PlayerDataList> getClassFromPlayerDataPacketId(int id) {
		return playerDataPacketId_to_class_map.get(id);
	}
	
	/**
	 * Returns the command name for the given command packet id.
	 * @param id - the id of command packet.
	 * @return a commande name if the id is in the map, else null.
	 */
	public static String getNameFromCommandPacketId(int id) {
		return commandPacketId_to_name_map.get(id);
	}
	
	/**
	 * Indicates whether the given id is a tile entity packet id.
	 * @param id - the id to check.
	 * @return true if the id is a tile entity packet id, else false.
	 */
	public static boolean isTileEntityPacketId(int id) {
		return tileEntityPacketId_to_class_map.containsKey(id);
	}
	
	/**
	 * Indicates whether the given id is a player data packet id.
	 * @param id - the id to check.
	 * @return true if the id is a player data packet id, else false.
	 */
	public static boolean isPlayerDataPacketId(int id) {
		return playerDataPacketId_to_class_map.containsKey(id);
	}
	
	/**
	 * Indicates whether the given id is a command packet id.
	 * @param id - the id to check.
	 * @return true if the id is a command packet id, else false.
	 */
	public static boolean isCommandPacketId(int id) {
		return commandPacketId_to_name_map.containsKey(id);
	}
	
	/**
	 * Indicates whether the given tile entity class is in the tile entity packet map.
	 * @param clazz - a class extending TileEntityStargate.
	 * @return true if the class is in the tile entity packet map, else false.
	 */
	public static boolean isTileEntityPacketMapped(Class<? extends TileEntityStargate> clazz) {
		return class_to_tileEntityPacketId_map.containsKey(clazz);
	}
	
	/**
	 * Indicates whether the given player data class is in the player data packet map.
	 * @param clazz - a class extending PlayerDataList.
	 * @return true if the class is in the player data packet map, else false.
	 */
	public static boolean isPlayerDataPacketMapped(Class<? extends PlayerDataList> clazz) {
		return class_to_playerDataPacketId_map.containsKey(clazz);
	}
	
	/**
	 * Indicates whether the given command name is in the command packet map.
	 * @param name - a command name.
	 * @return true if the command name is in the command packet map, else false.
	 */
	public static boolean isCommandPacketMapped(String name) {
		return name_to_commandPacketId_map.containsKey(name);
	}
	
	/**
	 * Returns the next available id for the packet mapping.
	 * @return the next available id for the packet mapping.
	 */
	private static int getNextId() {
		return nextId++;
	}
	
	// Packet handling part :
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if(packet != null && packet.channel != null && packet.data != null) {
			if(packet.channel.equals(StargateMod.CHANEL_TILE_ENTITY) && packet.length >= 20) {
				// Here, packet lenght must be >= 20 because the packet must contain at least : id + dim + x + y + z (5 * intSize = 20).
				this.handleTileEntityPacket(packet, (EntityPlayer) player);
			}
			else if(packet.channel.equals(StargateMod.CHANEL_COMMANDS) && packet.length >= 20) {
				// Here, packet lenght must be >= 20 because the packet must contain at least : id + dim + x + y + z (5 * intSize = 20).
				this.handleCommandPacket(packet, (EntityPlayer) player);
			}
			else if(packet.channel.equals(StargateMod.CHANEL_PLAYER_DATA) && packet.length >= 4) {
				// Here, packet lenght must be >= 4 because the packet must contain at least : id (intSize = 4).
				this.handlePlayerDataPacket(packet, (EntityPlayer) player);
			}
		}
	}
	
	/**
	 * Handle a tile entity packet for the given player.
	 * @param packet - the tile entity packet.
	 * @param player - the player associated with the client which sent/received the packet.
	 */
	protected void handleTileEntityPacket(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int id = input.readInt();
			int dim = input.readInt();
			int x = input.readInt();
			int y = input.readInt();
			int z = input.readInt();
			
			World world = this.getWorldForDimension(dim);
			
			if(world != null) {
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
				
				if(tileEntity != null && tileEntity instanceof TileEntityStargate) {
					TileEntityStargate tileEntityStargate = (TileEntityStargate) tileEntity;
					
					if(getTileEntityPacketIdFromClass(tileEntityStargate.getClass()) == id) {
						tileEntityStargate.onDataPacket(input);
					}
					else {
						StargateMod.debug("Error while reading a tile entity packet : wrong id.", Level.WARNING, true);
					}
				}
			}
			
			input.close();
		}
		catch(IOException argh) {
			StargateMod.debug("Error while reading in a DataInputStream. Couldn't read a tile entity packet.", Level.SEVERE, true);
			argh.printStackTrace();
		}
	}
	
	/**
	 * Handle a command packet for the given player.
	 * @param packet - the command packet.
	 * @param player - the player associated with the client which sent/received the packet.
	 */
	protected void handleCommandPacket(Packet250CustomPayload packet, EntityPlayer player) {
		// Handling on server side only.
	}
	
	/**
	 * Handle a player data packet for the given player.
	 * @param packet - the player data packet.
	 * @param player - the player associated with the client which sent/received the packet.
	 */
	protected void handlePlayerDataPacket(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int id = input.readInt();
			Class<? extends PlayerDataList> clazz = getClassFromPlayerDataPacketId(id);
			
			PlayerDataList playerData = null;
			
			if(clazz == PlayerTeleporterData.class) {
				playerData = PlayerTeleporterData.get(player);
			}
			else if(clazz == PlayerStargateData.class) {
				playerData = PlayerStargateData.get(player);
			}
			
			if(playerData != null) {
				playerData.loadProperties(input);
			}
			
			input.close();
		}
		catch(IOException argh) {
			StargateMod.debug("Error while reading in a DataInputStream. Couldn't read a player data packet.", Level.SEVERE, true);
			argh.printStackTrace();
			return;
		}
	}
	
	/**
	 * Returns the world corresponding to the given dimension id, if it exists.
	 * @param dim - the dimension id.
	 * @return the world corresponding to the dimension id if it exists, else null.
	 */
	public World getWorldForDimension(int dim) {
		// True return on server/client side. This class can't be abstract.
		return null;
	}
	
}
