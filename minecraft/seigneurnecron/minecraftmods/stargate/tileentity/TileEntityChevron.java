package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.enums.GateState;

public class TileEntityChevron extends TileEntityStargatePart {
	
	/**
	 * Le numero du chevron.
	 */
	private int no = 0;
	
	/**
	 * Retourne le numero du chevron.
	 * @return le numero du chevron.
	 */
	public int getNo() {
		return this.no;
	}
	
	/**
	 * Positionne le numero du chevron.
	 * @param no - le nouveau numero du chevron.
	 */
	public void setNo(int no) {
		this.no = no;
		this.updateClients();
	}
	
	/**
	 * Indique si ce chevron est active.
	 * @return true si ce chevron est active, false sinon.
	 */
	public boolean isChevronActivated() {
		if(this.partOfGate) {
			TileEntityMasterChevron gate = this.getMasterChevron();
			return (gate != null && (gate.getState() == GateState.INPUT || gate.getState() == GateState.OUTPUT || (gate.getState() == GateState.ACTIVATING && gate.getActivationState().getValue() >= this.no)));
		}
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.no = par1NBTTagCompound.getInteger("no");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("no", this.no);
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.no);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.no = readInt(list);
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return ("TileEntityChevron[no: " + this.no + ",xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",partOfGate: " + this.partOfGate + "]");
	}
	
}
