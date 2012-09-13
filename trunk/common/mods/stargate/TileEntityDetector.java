package mods.stargate;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;

public class TileEntityDetector extends TileEntityGui {
	
	/**
	 * Port�e minimale de d�tection.
	 */
	private static final int minRange = 1;
	
	/**
	 * Port�e maximale de d�tection.
	 */
	private static final int maxRange = 10;
	
	/**
	 * La port�e � laquelle ce d�tecteur peut d�ctecter une entit�.
	 */
	private int range = 2;
	
	/**
	 * Indique si ce d�tecteur envoie un signal quand il d�tecte une entit� ou quand il ne d�tecte pas d'entit�.
	 */
	private boolean inverted = false;
	
	/**
	 * Indique si ce d�tecteur fourni du courant.
	 */
	private boolean providingPower = false;
	
	/**
	 * Retourne la port�e � laquelle ce d�tecteur peut d�ctecter une entit�.
	 * @return la port�e � laquelle ce d�tecteur peut d�ctecter une entit�.
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Indique si ce d�tecteur envoie un signal quand il d�tecte une entit� ou quand il ne d�tecte pas d'entit�.
	 * @return true si ce d�tecteur envoie un signal quand il d�tecte une entit�, false s'il envoie un signal quand il ne d�tecte pas d'entit�.
	 */
	public boolean isInverted() {
		return inverted;
	}
	
	/**
	 * Indique si ce d�tecteur fourni du courant.
	 * @return true si ce d�tecteur fourni du courant, false sinon.
	 */
	public boolean isProvidingPower() {
		return this.providingPower;
	}
	
	/**
	 * Positionne la port�e de d�tection de ce d�tecteur.
	 * @param range - la nouvelle port�e de d�tection de ce d�tecteur.
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
	 * Indique � ce d�tecteur si sa sortie doit �tre invers�e.
	 * @param inverted - true si la sortie doit �tre invers�e, false sinon.
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	/**
	 * Indique � ce d�tecteur s'il doit fournir du courant.
	 * @param providingPower - true si ce d�tecteur doit fournir du courant, false sinon.
	 */
	private void setProvidingPower(boolean providingPower) {
		if(this.providingPower != providingPower) {
			this.providingPower = providingPower;
			this.updateClients();
			this.updateNeighborBlocks();
		}
	}
	
	/**
	 * Verifie s'il y a un joueur � port�e de d�tection.
	 * @return true s'il y a un un joueur � port�e de d�tection, false sinon.
	 */
	public boolean anyPlayerInRange() {
		return (this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.range) != null);
	}
	
	/**
	 * Met � jour les blocks situ�s sur les c�t�s.
	 */
	private void updateNeighborBlocks() {
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord + 1, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord - 1, this.yCoord, this.zCoord, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord + 1, StargateMod.detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord - 1, StargateMod.detector.blockID);
	}
	
	/**
	 * Cette methode est appel�e � chaque tick, elle sert � scanner la zone � la recherche d'entit�s.
	 */
	@Override
	public void updateEntity() {
		// Si on est c�t� server.
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
	 * Enregistre les donn�es de la tileEntity dans une List de Byte, dans le but de cr�er un packet.
	 * @return les donn�es de la tileEntity sous la forme d'une List de Byte.
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
	 * Charge les donn�es de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les donn�es � charger.
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
	 * Verifie que l'id fournie est une id correcte pour un packet destin� � cette tile entity.
	 * @param id - l'id � tester.
	 * @return true si l'id est correcte, false sinon.
	 */
	@Override
	protected boolean isCorrectId(int id) {
		return (super.isCorrectId(id) || id == packetId_CloseGuiDetector);
	}
	
	/**
	 * Retourne une repr�sentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityDhdCoord[providingPower: " + this.providingPower + ",inverted: " + this.inverted + ",range: " + this.range + "]");
	}
	
}
