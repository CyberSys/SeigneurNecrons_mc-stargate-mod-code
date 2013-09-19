package seigneurnecron.minecraftmods.stargate.tileentity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Seigneur Necron
 */
public class TileEntityChevron extends TileEntityStargatePart {
	
	/**
	 * The number of the chevron.
	 */
	private int no = 0;
	
	/**
	 * If this is true, this gate is not broken when the block is destroyed. This is used to replace chevron blocks by their active/inactive version.
	 */
	private boolean activating = false;
	
	/**
	 * Returns the number of the chevron.
	 * @return the number of the chevron.
	 */
	public int getNo() {
		return this.no;
	}
	
	/**
	 * If this is true, this gate is not broken when the block is destroyed. This is used to replace chevron blocks by their active/inactive version.
	 * @return true true if the chevron is activating, so the gate should not be broken when the block is destroyed, else false.
	 */
	public boolean isActivating() {
		return this.activating;
	}
	
	/**
	 * Sets the number of the chevron (informs clients).
	 * @param no - the number of the chevron.
	 */
	public void setNo(int no) {
		this.no = no;
		this.setChanged();
		this.update();
	}
	
	/**
	 * Set the state of the chevron. If set to true, this gate is not broken when the block is destroyed. This is used to replace chevron blocks by their active/inactive version.
	 * @param activating - true if the chevron is activating, so the gate should not be broken when the block is destroyed, else false.
	 */
	public void setActivating(boolean activating) {
		this.activating = activating;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.no = compound.getInteger("no");
		this.activating = compound.getBoolean("activating");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("no", this.no);
		compound.setBoolean("activating", this.activating);
	}
	
	@Override
	protected void getTileEntityData(DataOutputStream output) throws IOException {
		super.getTileEntityData(output);
		
		output.writeInt(this.no);
		output.writeBoolean(this.activating);
	}
	
	@Override
	protected void loadEntityData(DataInputStream input) throws IOException {
		super.loadEntityData(input);
		
		this.no = input.readInt();
		this.activating = input.readBoolean();
	}
	
}
