package seigneurnecron.minecraftmods.core.network.packetmapping;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * A Bi-directionnal map used to store data to handle packets of a specific chanel.
 * @author Seigneur Necron
 * @param <T> - The type of stored data.
 */
public abstract class PacketMapping<T> {
	
	// Fields :
	
	private final String chanel;
	private final Map<Integer, T> idToDataMap = new HashMap<Integer, T>();
	private final Map<T, Integer> dataToIdMap = new HashMap<T, Integer>();
	
	private int nextId = 1;
	
	// Constructors :
	
	protected PacketMapping(String chanel) {
		this.chanel = chanel;
	}
	
	// Getters :
	
	/**
	 * Returns the chanel of this packet mapping.
	 * @return the chanel of this packet mapping.
	 */
	public String getChanel() {
		return this.chanel;
	}
	
	// Methods :
	
	/**
	 * Register data to handle a packet and give it an id.
	 * @param data - the data to register.
	 */
	public int register(T data) {
		if(!this.isValidData(data)) {
			int id = this.nextId++;
			this.idToDataMap.put(id, data);
			this.dataToIdMap.put(data, id);
			return id;
		}
		
		return -1;
	}
	
	/**
	 * Returns the data corresponding to the given packet id.
	 * @param id - the packet id.
	 * @return the data corresponding to the packet id if it exists, else null.
	 */
	public T getData(int id) {
		return this.idToDataMap.get(id);
	}
	
	/**
	 * Returns the packet id corresponding to the given data.
	 * @param clazz - the data.
	 * @return the packet id if it exists, else -1.
	 */
	public int getId(T data) {
		Integer id = this.dataToIdMap.get(data);
		return(id != null ? id : -1);
	}
	
	/**
	 * Indicates whether the given data are registered in the mapping.
	 * @param clazz - the data.
	 * @return true if the data are registered in the mapping, else false.
	 */
	public boolean isValidData(T data) {
		return this.dataToIdMap.containsKey(data);
	}
	
	/**
	 * Indicates whether the given packet id registered in the mapping.
	 * @param id - the packet id.
	 * @return true if the packet id is registered in the mapping, else false.
	 */
	public boolean isValidId(int id) {
		return this.idToDataMap.containsKey(id);
	}
	
	/**
	 * Returns the min lenght of packets which are sent on the chanel.
	 * @return the min lenght of packets which are sent on the chanel.
	 */
	public abstract int minPacketLenght();
	
	/**
	 * Handle a packet for the given player.
	 * @param manager - the network manager this packet arrived from.
	 * @param packet - the packet.
	 * @param player - the player associated with the client which sent/received the packet.
	 */
	public abstract void handlePacket(INetworkManager manager, Packet250CustomPayload packet, EntityPlayer player);
	
}
