package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.arrayToList;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.changeId;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.getTileEntityUpdatePacketIdFromClass;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.isValidPacketIdForTileEntity;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.listToArray;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityStargate extends TileEntity {
	
	// Interface part :
	
	/**
	 * Returns a packet containing data about this tile entity.
	 * @return a packet containing data about this tile entity.
	 */
	@Override
	public Packet getDescriptionPacket() {
		return new Packet250CustomPayload(StargateMod.CHANEL, listToArray(this.getEntityData()));
	}
	
	/**
	 * Returns a packet containing data about this tile entity, with a chosen id.
	 * @param id - the id to be assigned to the packet.
	 * @return a packet containing data about this tile entity, with a chosen id.
	 */
	public Packet getDescriptionPacketWhithId(int id) {
		LinkedList<Byte> list = this.getEntityData();
		changeId(list, id);
		return new Packet250CustomPayload(StargateMod.CHANEL, listToArray(list));
	}
	
	/**
	 * Loads the tile entity data from the packet.
	 * @param networkManager - the network manager which handled the packet.
	 * @param packet - the packet containing the data to load.
	 */
	public void onDataPacket(INetworkManager networkManager, Packet250CustomPayload packet) {
		if(packet != null) {
			this.loadEntityData(arrayToList(packet.data));
		}
	}
	
	// Read/write part :
	
	/**
	 * Saves the tile entity data in a Byte list, in order to create a packet.
	 * @return the tile entity data in a Byte list.
	 */
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = new LinkedList<Byte>();
		
		writeInt(list, getTileEntityUpdatePacketIdFromClass(this.getClass()));
		writeInt(list, this.worldObj.provider.dimensionId);
		writeInt(list, this.xCoord);
		writeInt(list, this.yCoord);
		writeInt(list, this.zCoord);
		
		return list;
	}
	
	/**
	 * Loads the tile entity from a LinkedList of Byte.
	 * @param list - the LinkedList of Byte containing the data to load.
	 * @return true if the data were successfully loaded, else false.
	 */
	protected boolean loadEntityData(LinkedList<Byte> list) {
		int id = readInt(list);
		if(this.isCorrectId(id)) {
			readInt(list); // dimension
			readInt(list); // x position
			readInt(list); // y position
			readInt(list); // z position
			return true;
		}
		return false;
	}
	
	/**
	 * Checks that the given number is a valid id for a packet addressed to this tileentity.
	 * @param id - the number to check.
	 * @return true if the number is a valid id, else false.
	 */
	protected boolean isCorrectId(int id) {
		return isValidPacketIdForTileEntity(id, this.getClass());
	}
	
	// Update part :
	
	/**
	 * Transmits changes to clients.
	 */
	protected void updateClients() {
		if(this.worldObj != null && !this.worldObj.isRemote) {
			StargateMod.sendPacketToAllPlayersInDimension(this.getDescriptionPacket(), this.worldObj.provider.dimensionId);
		}
	}
	
	/**
	 * Informs the renderer that the block associated with this tile entity needs to be updated.
	 */
	protected void updateBlockTexture() {
		if(this.worldObj != null && this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
}
