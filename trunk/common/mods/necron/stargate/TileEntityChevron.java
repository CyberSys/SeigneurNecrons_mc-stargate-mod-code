package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;

public class TileEntityChevron extends TileEntityStargatePart {
	
	/**
	 * Le numéro du chevron.
	 */
	private int no = 0;
	
	/**
	 * Retourne le numéro du chevron.
	 * @return le numéro du chevron.
	 */
	public int getNo() {
		return this.no;
	}
	
	/**
	 * Positionne le numéro du chevron.
	 * @param no - le nouveau numéro du chevron.
	 */
	public void setNo(int no) {
		this.no = no;
		this.updateClients();
	}
	
	/**
	 * Indique si ce chevron est activé.
	 * @return true si ce chevron est activé, false sinon.
	 */
	public boolean isChevronActivated() {
		if(this.partOfGate) {
			TileEntityMasterChevron gate = this.getMasterChevron();
			return (gate != null && (gate.getState() == GateState.INPUT || gate.getState() == GateState.OUTPUT || (gate.getState() == GateState.ACTIVATING && gate.getActivationState().getValue() >= this.no)));
		}
		return false;
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.no = par1NBTTagCompound.getInteger("no");
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("no", this.no);
	}
	
	/**
	 * Enregistre les données de la tileEntity dans une List de Byte, dans le but de créer un packet.
	 * @return les données de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.no);
		
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
			this.no = readInt(list);
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	/**
	 * Retourne une représentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityChevron[no: " + this.no + ",xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",partOfGate: " + this.partOfGate + "]");
	}
	
}
