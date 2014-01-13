package seigneurnecron.minecraftmods.core.gui;

import java.util.List;

/**
 * @author Seigneur Necron
 */
public interface ListProvider<T extends Object> {
	
	public List<T> getList();
	
}
