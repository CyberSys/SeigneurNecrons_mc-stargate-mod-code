package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.block.Block;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityBase extends TileEntityGuiScreen {
	
	public static final String INV_NAME = "container.base";
	
	/**
	 * Defines what happen when the console is destroyed.
	 */
	public abstract void onConsoleDestroyed();
	
	/**
	 * Indicates whether the console is active.
	 * @return true if the console is active, else false.
	 */
	public abstract boolean isActive();
	
	/**
	 * Indicates wheter the console is intact.
	 * @return true if the console is intact, else false.
	 */
	public boolean isIntact() {
		return this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == this.getBaseBlock().blockID && this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) == this.getPanelBlock().blockID;
	}
	
	/**
	 * Returns the block contituting the base of the console.
	 * @return the block contituting the base of the console.
	 */
	public abstract Block getBaseBlock();
	
	/**
	 * Returns the block contituting the panel of the console.
	 * @return the block contituting the panel of the console.
	 */
	public abstract Block getPanelBlock();
	
}
