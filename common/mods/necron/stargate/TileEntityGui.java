package mods.necron.stargate;

import java.util.LinkedList;


public abstract class TileEntityGui extends TileEntityStargate {
	
	/**
	 * Indique si quelqu'un est en train d'�diter cette tile entity.
	 */
	protected boolean editable = true;
	
	/**
	 * Indique si quelqu'un est en train d'�diter cette tile entity.
	 * @return true si cette tile entity est editable, false si quelqu'un est d�j� en train de l'�diter.
	 */
	public boolean isEditable() {
		return this.editable;
	}
	
	/**
	 * Rend editable ou non cette tile entity.
	 * @param editable - true si l'entity doit �tre �ditable, false sinon.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
		this.updateClients();
	}
	
	/**
	 * Enregistre les donn�es de la tileEntity dans une List de Byte, dans le but de cr�er un packet.
	 * @return les donn�es de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeBoolean(list, this.editable);
		
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
			this.editable = readBoolean(list);
			return true;
		}
		return false;
	}
	
}