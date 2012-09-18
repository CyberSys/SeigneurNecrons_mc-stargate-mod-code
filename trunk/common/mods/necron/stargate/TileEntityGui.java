package mods.necron.stargate;

import java.util.LinkedList;

public abstract class TileEntityGui extends TileEntityStargate {
	
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
	
	/**
	 * Enregistre les donnees de la tileEntity dans une List de Byte, dans le but de creer un packet.
	 * @return les donnees de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeBoolean(list, this.editable);
		
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
			this.editable = readBoolean(list);
			return true;
		}
		return false;
	}
	
}
