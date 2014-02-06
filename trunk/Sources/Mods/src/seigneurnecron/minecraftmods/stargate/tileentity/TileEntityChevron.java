package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.nbt.NBTTagCompound;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class TileEntityChevron extends TileEntityStargatePart {
	
	// NBTTags names :
	
	private static final String NO = "no";
	private static final String ACTIVATING = "activating";
	
	// Fields :
	
	/**
	 * The number of the chevron.
	 */
	private int no = 0;
	
	/**
	 * If this is true, this gate is not broken when the block is destroyed. This is used to replace chevron blocks by their active/inactive version.
	 */
	private boolean activating = false;
	
	// Getters :
	
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
	
	// Setters :
	
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
	
	// Methods :
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.no = compound.getInteger(NO);
		this.activating = compound.getBoolean(ACTIVATING);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(NO, this.no);
		compound.setBoolean(ACTIVATING, this.activating);
	}
	
}
