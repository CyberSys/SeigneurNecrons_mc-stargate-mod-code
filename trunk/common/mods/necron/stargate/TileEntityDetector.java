package mods.necron.stargate;

import static mods.necron.stargate.StargatePacketHandler.readBoolean;
import static mods.necron.stargate.StargatePacketHandler.readInt;
import static mods.necron.stargate.StargatePacketHandler.writeBoolean;
import static mods.necron.stargate.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;

public class TileEntityDetector extends TileEntityGui {
	
	/**
	 * Portee minimale de detection.
	 */
	private static final int minRange = 1;
	
	/**
	 * Portee maximale de detection.
	 */
	private static final int maxRange = 10;
	
	/**
	 * La portee a laquelle ce detecteur peut dectecter une entite.
	 */
	private int range = 2;
	
	/**
	 * Indique si ce detecteur envoie un signal quand il detecte une entite ou quand il ne detecte pas d'entite.
	 */
	private boolean inverted = false;
	
	/**
	 * Indique si ce detecteur fourni du courant.
	 */
	private boolean providingPower = false;
	
	/**
	 * Retourne la portee a laquelle ce detecteur peut dectecter une entite.
	 * @return la portee a laquelle ce detecteur peut dectecter une entite.
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Indique si ce detecteur envoie un signal quand il detecte une entite ou quand il ne detecte pas d'entite.
	 * @return true si ce detecteur envoie un signal quand il detecte une entite, false s'il envoie un signal quand il ne detecte pas d'entite.
	 */
	public boolean isInverted() {
		return inverted;
	}
	
	/**
	 * Indique si ce detecteur fourni du courant.
	 * @return true si ce detecteur fourni du courant, false sinon.
	 */
	public boolean isProvidingPower() {
		return this.providingPower;
	}
	
	/**
	 * Positionne la portee de detection de ce detecteur.
	 * @param range - la nouvelle portee de detection de ce detecteur.
	 */
	public void setRange(int range) {
		if(range < minRange) {
			this.range = minRange;
		}
		else if(range > maxRange) {
			this.range = maxRange;
		}
		else {
			this.range = range;
		}
	}
	
	/**
	 * Indique a ce detecteur si sa sortie doit etre inversee.
	 * @param inverted - true si la sortie doit etre inversee, false sinon.
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	/**
	 * Indique a ce detecteur s'il doit fournir du courant.
	 * @param providingPower - true si ce detecteur doit fournir du courant, false sinon.
	 */
	private void setProvidingPower(boolean providingPower) {
		if(this.providingPower != providingPower) {
			this.providingPower = providingPower;
			this.updateClients();
			this.updateNeighborBlocks();
		}
	}
	
	/**
	 * Verifie s'il y a un joueur a portee de detection.
	 * @return true s'il y a un un joueur a portee de detection, false sinon.
	 */
	public boolean anyPlayerInRange() {
		return (this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.range) != null);
	}
	
	/**
	 * Met a jour les blocks situes sur les cotes.
	 */
	private void updateNeighborBlocks() {
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord + 1, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord - 1, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord + 1, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord - 1, StargateMod.detector.blockID);
	}
	
	/**
	 * Cette methode est appelee a chaque tick, elle sert a scanner la zone a la recherche d'entites.
	 */
	@Override
	public void updateEntity() {
		// Si on est cote server.
		if(!this.worldObj.isRemote) {
			this.setProvidingPower(this.inverted != this.anyPlayerInRange());
		}
		super.updateEntity();
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.range = par1NBTTagCompound.getInteger("range");
		this.inverted = par1NBTTagCompound.getBoolean("inverted");
		this.providingPower = par1NBTTagCompound.getBoolean("providingPower");
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("range", this.range);
		par1NBTTagCompound.setBoolean("inverted", this.inverted);
		par1NBTTagCompound.setBoolean("providingPower", this.providingPower);
	}
	
	/**
	 * Enregistre les donnees de la tileEntity dans une List de Byte, dans le but de creer un packet.
	 * @return les donnees de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.range);
		writeBoolean(list, this.inverted);
		writeBoolean(list, this.providingPower);
		
		return list;
	}
	
	/**
	 * Charge les donnees de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les donnees a charger.
	 * @return true si le chargement est un succes, false sinon.
	 */
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.range = readInt(list);
			this.inverted = readBoolean(list);
			this.providingPower = readBoolean(list);
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	/**
	 * Verifie que l'id fournie est une id correcte pour un packet destine a cette tile entity.
	 * @param id - l'id a tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	@Override
	protected boolean isCorrectId(int id) {
		return (super.isCorrectId(id) || id == StargatePacketHandler.packetId_CloseGuiDetector);
	}
	
	/**
	 * Retourne une representation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityDhdCoord[providingPower: " + this.providingPower + ",inverted: " + this.inverted + ",range: " + this.range + "]");
	}
	
}
