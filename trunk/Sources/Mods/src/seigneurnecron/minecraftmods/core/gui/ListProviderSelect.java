package seigneurnecron.minecraftmods.core.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ListProviderSelect<T extends Object> extends ListProvider<T> {
	
	public int getSelectedIndex();
	
	public void setSelectedIndex(int index);
	
	public void onElementDoubleClicked();
	
}
