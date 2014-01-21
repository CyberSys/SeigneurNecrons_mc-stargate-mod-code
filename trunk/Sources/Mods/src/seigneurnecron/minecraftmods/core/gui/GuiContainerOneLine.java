package seigneurnecron.minecraftmods.core.gui;

import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public abstract class GuiContainerOneLine<T extends ContainerOneLine<?>> extends GuiContainerBasic<T> {
	
	// ####################################################################################################
	// Constructors :
	// ####################################################################################################
	
	protected GuiContainerOneLine(T container) {
		super(container);
	}
	
	// ####################################################################################################
	// Interface definition :
	// ####################################################################################################
	
	@Override
	protected void initInventory() {
		super.initInventory();
		this.initContainerInventory();
	}
	
	/**
	 * Adds the container inventory slots to the screen.
	 */
	protected void initContainerInventory() {
		int inventorySize = this.container.inventory.getSafeSizeInventory();
		int nbNormalSlots = this.container.inventory.getSafeNbNormalSlots();
		int slotSizeWithBorder = this.container.slotSizeWithBorder();
		int slotSizePlusMargin = this.container.slotSizePlusMargin();
		int firstSlotXPos = this.container.firstSlotXPos();
		int firstSlotYPos = this.container.firstSlotYPos();
		
		if(nbNormalSlots == inventorySize) {
			firstSlotXPos += ((9 - inventorySize) * slotSizePlusMargin) / 2;
			
			for(int i = 0; i < inventorySize; i++) {
				this.slotPanels.add(new Panel(this.panel_container, firstSlotXPos + i * slotSizePlusMargin, firstSlotYPos, slotSizeWithBorder, slotSizeWithBorder));
			}
		}
		else {
			for(int i = 0; i < nbNormalSlots; i++) {
				this.slotPanels.add(new Panel(this.panel_container, firstSlotXPos + i * slotSizePlusMargin, firstSlotYPos, slotSizeWithBorder, slotSizeWithBorder));
			}
			
			firstSlotXPos += (9 - inventorySize) * slotSizePlusMargin;
			
			for(int i = nbNormalSlots; i < inventorySize; i++) {
				this.slotPanels.add(new Panel(this.panel_container, firstSlotXPos + i * slotSizePlusMargin, firstSlotYPos, slotSizeWithBorder, slotSizeWithBorder));
			}
		}
	}
	
}
