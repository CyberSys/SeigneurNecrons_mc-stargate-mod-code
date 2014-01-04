package seigneurnecron.minecraftmods.core.gui;

import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * @author Seigneur Necron
 */
public abstract class GuiContainerOneLine<T extends ContainerOneLine<?>> extends GuiContainerBasic<T> {
	
	// Constructors :
	
	protected GuiContainerOneLine(T container) {
		super(container);
	}
	
	// Methods :
	
	@Override
	protected void initInventory() {
		super.initInventory();
		
		int inventorySize = this.container.inventory.getSizeInventory();
		int nbSlotToDisplay = this.container.inventory.nbSlotToDisplay();
		
		if(inventorySize < 0) {
			inventorySize = 0;
		}
		else if(inventorySize > 9) {
			inventorySize = 9;
		}
		
		if(nbSlotToDisplay < 0) {
			nbSlotToDisplay = 0;
		}
		else if(nbSlotToDisplay > inventorySize) {
			nbSlotToDisplay = inventorySize;
		}
		
		int slotSizeWithBorder = this.container.slotSizeWithBorder();
		int slotSizePlusMargin = this.container.slotSizePlusMargin();
		int firstSlotXPos = this.container.firstSlotXPos() + (((9 - nbSlotToDisplay) * slotSizePlusMargin) / 2);
		int firstSlotYPos = this.container.firstSlotYPos();
		
		for(int i = 0; i < nbSlotToDisplay; i++) {
			this.slotPanels.add(new Panel(this.panel_container, firstSlotXPos + i * slotSizePlusMargin, firstSlotYPos, slotSizeWithBorder, slotSizeWithBorder));
		}
	}
	
}
