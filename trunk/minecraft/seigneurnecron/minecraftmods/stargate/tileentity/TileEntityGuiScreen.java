package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;

import java.util.LinkedList;

public abstract class TileEntityGuiScreen extends TileEntityStargate {
	
	/**
	 * Indique si quelqu'un est en train d'editer cette tile entity.
	 */
	protected boolean editable = true;
	
	/**
	 * Indique si quelqu'un est en train d'editer cette tile entity.
	 * @return true si cette tile entity est editable, false si quelqu'un est deja en train de l'editer.
	 */
	public boolean isEditable() {
		return this.editable;
	}
	
	/**
	 * Rend editable ou non cette tile entity.
	 * @param editable - true si l'entity doit etre editable, false sinon.
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
