package seigneurnecron.minecraftmods.stargate.tileentity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
		this.setChanged();
		this.update();
	}
	
	@Override
	protected void getEntityData(DataOutputStream output) throws IOException {
		super.getEntityData(output);
		
		output.writeBoolean(this.editable);
	}
	
	@Override
	protected void loadEntityData(DataInputStream input) throws IOException {
		super.loadEntityData(input);
		
		this.editable = input.readBoolean();
	}
	
}
