package seigneurnecron.minecraftmods.core.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface Component {
	
	/**
	 * Returns the component width.
	 * @return the component width.
	 */
	public int getComponentWidth();
	
	/**
	 * Returns the component height.
	 * @return the component height.
	 */
	public int getComponentHeight();
	
	/**
	 * Returns the component X position in the parent container.
	 * @return the component X position in the parent container.
	 */
	public int getXPos();
	
	/**
	 * Returns the component Y position in the parent container.
	 * @return the component Y position in the parent container.
	 */
	public int getYPos();
	
	/**
	 * Returns the component right side position in the parent container.
	 * @return the component right side position in the parent container.
	 */
	public int getRight();
	
	/**
	 * Returns the component bottom position in the parent container.
	 * @return the component bottom position in the parent container.
	 */
	public int getBottom();
	
}
