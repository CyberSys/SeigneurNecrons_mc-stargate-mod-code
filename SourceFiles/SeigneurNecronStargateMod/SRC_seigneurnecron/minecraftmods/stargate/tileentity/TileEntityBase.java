package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityBase extends TileEntityGuiScreen {
	
	/**
	 * Informs the renderer that the block associated with this tile entity and the block on top of it needs to be updated.
	 */
	@Override
	protected void updateBlockTexture() {
		if(this.worldObj != null && this.worldObj.isRemote) {
			super.updateBlockTexture();
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + 1, this.zCoord);
		}
	}
	
	/**
	 * Defines what happen when the console is destroyed.
	 */
	public void onConsoleDestroyed() {
		// Nothing to do here.
	}
	
	/**
	 * Defines what happen when the console panel is activated.
	 * @param player - the player who activated the console.
	 * @param side - the side from where the console was activated.
	 * @param xOffset - the x offset of the click position.
	 * @param yOffset - the y offset of the click position.
	 * @param zOffset - the z offset of the click position.
	 */
	public abstract void activate(EntityPlayer player, int side, float xOffset, float yOffset, float zOffset);
	
	/**
	 * Indicates whether the console is active.
	 * @return
	 */
	public abstract boolean isActive();
	
}
