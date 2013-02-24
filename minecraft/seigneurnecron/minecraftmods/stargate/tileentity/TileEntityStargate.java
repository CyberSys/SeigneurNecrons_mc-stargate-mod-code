package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.arrayToList;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.changeId;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.getIdFromClass;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.listToArray;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.stargate.StargateMod;

public abstract class TileEntityStargate extends TileEntity {
	
	// Partie interface externe :
	
	/**
	 * Retourne un pack contenant les informations de cette tileEntity.
	 * @return un pack contenant les informations de cette tileEntity.
	 */
	@Override
	public Packet getDescriptionPacket() {
		return new Packet250CustomPayload(StargateMod.chanel, listToArray(this.getEntityData()));
	}
	
	/**
	 * Retourne un pack contenant les informations de cette tileEntity, avec l'id choisit.
	 * @param id - l'id a attribuer au packet.
	 * @return un pack contenant les informations de cette tileEntity, avec l'id choisit.
	 */
	public Packet getDescriptionPacketWhithId(int id) {
		LinkedList<Byte> list = this.getEntityData();
		changeId(list, id);
		return new Packet250CustomPayload(StargateMod.chanel, listToArray(list));
	}
	
	/**
	 * Charge les donnees de la tileEntity depuis le packet.
	 * @param networkManager - le NetworkManager d'ou provient le packet.
	 * @param packet - le packet contenant les donnees a charger.
	 */
	public void onDataPacket(INetworkManager networkManager, Packet250CustomPayload packet) {
		if(packet != null) {
			this.loadEntityData(arrayToList(packet.data));
		}
	}
	
	// Partie lecture/ecriture :
	
	/**
	 * Enregistre les donnees de la tileEntity dans une List de Byte, dans le but de creer un packet.
	 * @return les donnees de la tileEntity sous la forme d'une List de Byte.
	 */
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = new LinkedList<Byte>();
		
		writeInt(list, getIdFromClass(this.getClass()));
		writeInt(list, this.worldObj.getWorldInfo().getDimension());
		writeInt(list, this.xCoord);
		writeInt(list, this.yCoord);
		writeInt(list, this.zCoord);
		
		return list;
	}
	
	/**
	 * Charge les donnees de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les donnees a charger.
	 * @return true si le chargement est un succes, false sinon.
	 */
	protected boolean loadEntityData(LinkedList<Byte> list) {
		int id = readInt(list);
		if(this.isCorrectId(id)) {
			readInt(list); // dim
			readInt(list); // x
			readInt(list); // y
			readInt(list); // z
			return true;
		}
		return false;
	}
	
	/**
	 * Verifie que l'id fournie est une id correcte pour un packet destine a cette tile entity.
	 * @param id - l'id a tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	protected boolean isCorrectId(int id) {
		return id == getIdFromClass(this.getClass());
	}
	
	// Partie mise a jour :
	
	/**
	 * Transmet les modifications aux clients.
	 */
	protected void updateClients() {
		if(this.worldObj != null && !this.worldObj.isRemote) {
			StargateMod.sendPacketToAllPlayersInDimension(this.getDescriptionPacket(), this.worldObj.getWorldInfo().getDimension());
		}
	}
	
	/**
	 * Signal au renderer que le block lie a cette tile entity a besoin d'etre mit a jour.
	 */
	protected void updateBlockTexture() {
		if(this.worldObj != null && this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
}
