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
	
}
