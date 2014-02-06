package seigneurnecron.minecraftmods.core.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ListProviderSelect<T extends Object> extends ListProvider<T> {
	
	public int getSelectedIndex();
	
	public void setSelectedIndex(int index);
	
	public void onElementDoubleClicked();
	
}
