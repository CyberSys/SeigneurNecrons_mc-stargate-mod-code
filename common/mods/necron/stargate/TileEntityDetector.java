package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;

public class TileEntityDetector extends TileEntityGui {
	
	/**
	 * Portée minimale de détection.
	 */
	private static final int minRange = 1;
	
	/**
	 * Portée maximale de détection.
	 */
	private static final int maxRange = 10;
	
	/**
	 * La portée à laquelle ce détecteur peut déctecter une entité.
	 */
	private int range = 2;
	
	/**
	 * Indique si ce détecteur envoie un signal quand il détecte une entité ou quand il ne détecte pas d'entité.
	 */
	private boolean inverted = false;
	
	/**
	 * Indique si ce détecteur fourni du courant.
	 */
	private boolean providingPower = false;
	
	/**
	 * Retourne la portée à laquelle ce détecteur peut déctecter une entité.
	 * @return la portée à laquelle ce détecteur peut déctecter une entité.
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Indique si ce détecteur envoie un signal quand il détecte une entité ou quand il ne détecte pas d'entité.
	 * @return true si ce détecteur envoie un signal quand il détecte une entité, false s'il envoie un signal quand il ne détecte pas d'entité.
	 */
	public boolean isInverted() {
		return inverted;
	}
	
	/**
	 * Indique si ce détecteur fourni du courant.
	 * @return true si ce détecteur fourni du courant, false sinon.
	 */
	public boolean isProvidingPower() {
		return this.providingPower;
	}
	
	/**
	 * Positionne la portée de détection de ce détecteur.
	 * @param range - la nouvelle portée de détection de ce détecteur.
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
	 * Indique à ce détecteur si sa sortie doit être inversée.
	 * @param inverted - true si la sortie doit être inversée, false sinon.
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	/**
	 * Indique à ce détecteur s'il doit fournir du courant.
	 * @param providingPower - true si ce détecteur doit fournir du courant, false sinon.
	 */
	private void setProvidingPower(boolean providingPower) {
		if(this.providingPower != providingPower) {
			this.providingPower = providingPower;
			this.updateClients();
			this.updateNeighborBlocks();
		}
	}
	
	/**
	 * Verifie s'il y a un joueur à portée de détection.
	 * @return true s'il y a un un joueur à portée de détection, false sinon.
	 */
	public boolean anyPlayerInRange() {
		return (this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.range) != null);
	}
	
	/**
	 * Met à jour les blocks situés sur les côtés.
	 */
	private void updateNeighborBlocks() {
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord + 1, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord - 1, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord + 1, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord - 1, StargateMod.detector.blockID);
	}
	
	/**
	 * Cette methode est appelée à chaque tick, elle sert à scanner la zone à la recherche d'entités.
	 */
	@Override
	public void updateEntity() {
		// Si on est côté server.
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
	 * Enregistre les données de la tileEntity dans une List de Byte, dans le but de créer un packet.
	 * @return les données de la tileEntity sous la forme d'une List de Byte.
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
	 * Charge les données de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les données à charger.
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
	 * Verifie que l'id fournie est une id correcte pour un packet destiné à cette tile entity.
	 * @param id - l'id à tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	@Override
	protected boolean isCorrectId(int id) {
		return (super.isCorrectId(id) || id == packetId_CloseGuiDetector);
	}
	
	/**
	 * Retourne une représentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityDhdCoord[providingPower: " + this.providingPower + ",inverted: " + this.inverted + ",range: " + this.range + "]");
	}
	
}
