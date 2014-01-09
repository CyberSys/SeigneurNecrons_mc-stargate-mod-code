package seigneurnecron.minecraftmods.core.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class SelectionList<T extends Object> extends CustomGuiScrollingList {
	
	// Fields :
	
	protected final ListProviderGui<T> gui;
	
	// Constructors :
	
	protected SelectionList(ListProviderGui<T> gui, int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
		this.gui = gui;
	}
	
	// Methods :
	
	@Override
	protected int getSize() {
		return this.gui.getList().size();
	}
	
	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		this.gui.setSelectedIndex(index);
		
		if(doubleClick) {
			this.gui.onElementDoubleClicked();
		}
	}
	
	@Override
	protected boolean isSelected(int index) {
		return this.gui.getSelectedIndex() == index;
	}
	
}
