package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;

import java.util.LinkedList;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityGuiScreen extends TileEntityStargate {
	
	/**
	 * Indicates whether someone is currently editing the tile entity.
	 */
	protected boolean editable = true;
	
	/**
	 * Indicates whether someone is currently editing the tile entity.
	 * @return true if this tile entity is editable, false if someone is already editing the tile entity.
	 */
	public boolean isEditable() {
		return this.editable;
	}
	
	/**
	 * Makes this tile entity editable / not editable.
	 * @param editable - true if this tile entity must be editable, else false.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
		this.updateClients();
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeBoolean(list, this.editable);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.editable = readBoolean(list);
			return true;
		}
		return false;
	}
	
}
