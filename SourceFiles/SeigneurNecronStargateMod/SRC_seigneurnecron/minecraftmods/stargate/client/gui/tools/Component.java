package seigneurnecron.minecraftmods.stargate.client.gui.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface Component {
	
	public int getComponentWidth();
	
	public int getComponentHeight();
	
}
