package seigneurnecron.minecraftmods.core.gui;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public interface ListProvider<T extends Object> {
	
	public List<T> getList();
	
}
