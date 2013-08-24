package seigneurnecron.minecraftmods.stargate.network;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargate;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Seigneur Necron
 */
public class StargatePacketHandler implements IPacketHandler {
	
	// Tile entity mapping part :
	
	private static final HashMap<Integer, Class<? extends TileEntityStargate>> tileEntityUpdatePacketId_to_class_map = new HashMap<Integer, Class<? extends TileEntityStargate>>();
	private static final HashMap<Class<? extends TileEntityStargate>, Integer> class_to_tileEntityUpdatePacketId_map = new HashMap<Class<? extends TileEntityStargate>, Integer>();
	private static final HashMap<Integer, Class<? extends TileEntityStargate>> guiClosedPacketId_to_class_map = new HashMap<Integer, Class<? extends TileEntityStargate>>();
	private static final HashMap<Class<? extends TileEntityStargate>, Integer> class_to_guiClosedPacketId_map = new HashMap<Class<? extends TileEntityStargate>, Integer>();
	
	private static int nextId = 0;
	
	/**
	 * Adds a class extending TileEntityStargate to the mapping of tile entity update packets.
	 * @param clazz - the class to add.
	 */
	public static void registerTileEntityUpdatePacket(Class<? extends TileEntityStargate> clazz) {
		if(!isTileEntityUpdatePaketMapped(clazz)) {
			int id = getAvailableId();
			tileEntityUpdatePacketId_to_class_map.put(id, clazz);
			class_to_tileEntityUpdatePacketId_map.put(clazz, id);
		}
	}
	
	/**
	 * Adds a class extending TileEntityStargate to the mapping of gui closed packets.
	 * @param clazz - the class to add.
	 */
	public static void registerGuiClosedPacket(Class<? extends TileEntityStargate> clazz) {
		if(!isGuiClosedPaketMapped(clazz)) {
			int id = getAvailableId();
			guiClosedPacketId_to_class_map.put(id, clazz);
			class_to_guiClosedPacketId_map.put(clazz, id);
		}
	}
	
	/**
	 * Returns the id of the tile entity update packet for the given tile entity class.
	 * @param clazz - a class extending TileEntityStargate.
	 * @return the id of the tile entity update packet if it exists, else -1.
	 */
	public static int getTileEntityUpdatePacketIdFromClass(Class<? extends TileEntityStargate> clazz) {
		Integer id = class_to_tileEntityUpdatePacketId_map.get(clazz);
		return(id != null ? id : -1);
	}
	
	/**
	 * Returns the id of the gui closed packet for the given tile entity class.
	 * @param clazz - a class extending TileEntityStargate.
	 * @return the id of the gui closed packet if it exists, else -1.
	 */
	public static int getGuiClosedPacketIdFromClass(Class<? extends TileEntityStargate> clazz) {
		Integer id = class_to_guiClosedPacketId_map.get(clazz);
		return(id != null ? id : -1);
	}
	
	/**
	 * Returns the tile entity class for the given tile entity update packet id.
	 * @param id - the id of a tile entity update packet.
	 * @return a class extending TileEntityStargate if the id is in the map, else null.
	 */
	public static Class<? extends TileEntityStargate> getClassFromTileEntityUpdatePacketId(int id) {
		return tileEntityUpdatePacketId_to_class_map.get(id);
	}
	
	/**
	 * Returns the tile entity class for the given gui closed packet id.
	 * @param id - the id of a gui closed packet.
	 * @return a class extending TileEntityStargate if the id is in the map, else null.
	 */
	public static Class<? extends TileEntityStargate> getClassFromGuiClosedPacketId(int id) {
		return guiClosedPacketId_to_class_map.get(id);
	}
	
	/**
	 * Indicates whether the given id is a tile entity update packet id.
	 * @param id - the id the check.
	 * @return true if the id is a tile entity update packet id, else false.
	 */
	public static boolean isTileEntityUpdatePaketId(int id) {
		return tileEntityUpdatePacketId_to_class_map.containsKey(id);
	}
	
	/**
	 * Indicates whether the given id is a gui closed packet id.
	 * @param id - the id the check.
	 * @return true if the id is a gui closed packet id, else false.
	 */
	public static boolean isGuiClosedPaketId(int id) {
		return guiClosedPacketId_to_class_map.containsKey(id);
	}
	
	/**
	 * Indicates whether the given tile entity class is in the tile entity update packet map.
	 * @param clazz - a class extending TileEntityStargate.
	 * @return true if the class is in the tile entity update packet map, else false.
	 */
	public static boolean isTileEntityUpdatePaketMapped(Class<? extends TileEntityStargate> clazz) {
		return class_to_tileEntityUpdatePacketId_map.containsKey(clazz);
	}
	
	/**
	 * Indicates whether the given tile entity class is in the gui closed packet map.
	 * @param clazz - a class extending TileEntityStargate.
	 * @return true if the class is in the gui closed packet map, else false.
	 */
	public static boolean isGuiClosedPaketMapped(Class<? extends TileEntityStargate> clazz) {
		return class_to_guiClosedPacketId_map.containsKey(clazz);
	}
	
	/**
	 * Indicates whether the given id is a valid id for a packet addressed to an instance of the given tile entity class.
	 * @param id - a packet id.
	 * @param clazz -  a class extending TileEntityStargate.
	 * @return true if the given id is a valid id for a packet addressed to an instance of the given tile entity class, else false.
	 */
	public static boolean isValidPacketIdForTileEntity(int id, Class<? extends TileEntityStargate> clazz) {
		return(getClassFromTileEntityUpdatePacketId(id) == clazz || getClassFromGuiClosedPacketId(id) == clazz);
	}
	
	/**
	 * Indicates whether the given id is already used in the packet mapping.
	 * @param id - an id.
	 * @return true if the id is already used in the packet mapping, else false.
	 */
	public static boolean isIdUsed(int id) {
		return(isTileEntityUpdatePaketId(id) || isGuiClosedPaketId(id));
	}
	
	/**
	 * Returns the next available id for the paket mapping.
	 * @return the next available id for the paket mapping.
	 */
	private static int getAvailableId() {
		while(isIdUsed(nextId)) {
			nextId++;
		}
		
		return nextId;
	}
	
	// Conversion part :
	
	/**
	 * Transforms a byte array in a LinkedList of Byte.
	 * @param array - a byte array.
	 * @return a LinkedList of Byte.
	 */
	public static LinkedList<Byte> arrayToList(byte[] array) {
		LinkedList<Byte> list = new LinkedList<Byte>();
		for(int i = 0; i < array.length; ++i) {
			list.add(array[i]);
		}
		return list;
	}
	
	/**
	 * Transforms a LinkedList of Byte in a byte array.
	 * @param list - a LinkedList of Byte.
	 * @return a byte array.
	 */
	public static byte[] listToArray(LinkedList<Byte> list) {
		byte[] array = new byte[list.size()];
		for(int i = 0; i < list.size(); ++i) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * Writes the given integer, in the form of 4 bytes, at the end of the given LinkedList of Byte.
	 * @param list - a LinkedList of Byte.
	 * @param value - an integer.
	 */
	public static void writeInt(LinkedList<Byte> list, int value) {
		byte[] tmp = ByteBuffer.allocate(4).putInt(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Writes the given float, in the form of 4 bytes, at the end of the given LinkedList of Byte.
	 * @param list - a LinkedList of Byte.
	 * @param value - a float.
	 */
	public static void writeFloat(LinkedList<Byte> list, float value) {
		byte[] tmp = ByteBuffer.allocate(4).putFloat(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Writes the given double, in the form of 8 bytes, at the end of the given LinkedList of Byte.
	 * @param list - a LinkedList of Byte.
	 * @param value - a double.
	 */
	public static void writeDouble(LinkedList<Byte> list, double value) {
		byte[] tmp = ByteBuffer.allocate(8).putDouble(value).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.add(tmp[i]);
		}
	}
	
	/**
	 * Writes the given boolean, in the form of 1 byte, at the end of the given LinkedList of Byte.
	 * @param list - a LinkedList of Byte.
	 * @param value - a boolean.
	 */
	public static void writeBoolean(LinkedList<Byte> list, boolean value) {
		byte tmp = (byte) (value ? 1 : 0);
		list.add(tmp);
	}
	
	/**
	 * Reads an integer from the first 4 elements of the given LinkedList of Byte.<br />
	 * (The read elements are deleted from de list)
	 * @param list - a LinkedList of Byte, containing at least 4 bytes.
	 * @return an integer.
	 */
	public static int readInt(LinkedList<Byte> list) {
		ByteBuffer tmp = ByteBuffer.allocate(4);
		for(int i = 0; i < tmp.capacity(); ++i) {
			tmp.put(i, list.remove(0));
		}
		return tmp.getInt(0);
	}
	
	/**
	 * Reads a float from the first 4 elements of the given LinkedList of Byte.<br />
	 * (The read elements are deleted from de list)
	 * @param list - a LinkedList of Byte, containing at least 4 bytes.
	 * @return a float.
	 */
	public static float readFloat(LinkedList<Byte> list) {
		ByteBuffer tmp = ByteBuffer.allocate(4);
		for(int i = 0; i < tmp.capacity(); ++i) {
			tmp.put(i, list.remove(0));
		}
		return tmp.getFloat(0);
	}
	
	/**
	 * Reads a double from the first 8 elements of the given LinkedList of Byte.<br />
	 * (The read elements are deleted from de list)
	 * @param list - a LinkedList of Byte, containing at least 8 bytes.
	 * @return a double.
	 */
	public static double readDouble(LinkedList<Byte> list) {
		ByteBuffer tmp = ByteBuffer.allocate(8);
		for(int i = 0; i < tmp.capacity(); ++i) {
			tmp.put(i, list.remove(0));
		}
		return tmp.getDouble(0);
	}
	
	/**
	 * Reads a boolean from the first element of the given LinkedList of Byte.<br />
	 * (The read elements are deleted from de list)
	 * @param list - a LinkedList of Byte, containing at least 1 byte.
	 * @return a boolean.
	 */
	public static boolean readBoolean(LinkedList<Byte> list) {
		byte tmp = list.remove(0);
		return(tmp != 0);
	}
	
	/**
	 * Writes the given integer on the first 4 bytes of the given LinkedList of Byte.<br />
	 * The purpose being to change the id of the packet.
	 * @param list - a LinkedList of Byte.
	 * @param id - the id to be assigned to the packet.
	 */
	public static void changeId(LinkedList<Byte> list, int id) {
		byte[] tmp = ByteBuffer.allocate(4).putInt(id).array();
		for(int i = 0; i < tmp.length; ++i) {
			list.set(i, tmp[i]);
		}
	}
	
	// Packet handling part :
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		// Checks that the packet is a packet of this mod and that the packet length is at least 4 (int id).
		if(packet != null && packet.channel.equals(StargateMod.CHANEL) && packet.data != null && packet.length >= 4) {
			handlePacket(manager, packet, player);
		}
	}
	
	protected void handlePacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		// Packet handling on server/client side.
	}
	
}
