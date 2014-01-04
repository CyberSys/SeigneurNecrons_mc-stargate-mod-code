package seigneurnecron.minecraftmods.core.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

/**
 * @author Seigneur Necron
 */
public abstract class ContainerOneLine<T extends InventoryOneLine> extends ContainerBasic<T> {
	
	// Constructors :
	
	protected ContainerOneLine(InventoryPlayer inventoryPlayer, T inventory) {
		super(inventoryPlayer, inventory);
	}
	
	// Methods :
	
	@Override
	protected void init() {
		int inventorySize = this.inventory.getSizeInventory();
		int nbSlotToDisplay = this.inventory.nbSlotToDisplay();
		
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
		
		int slotSize = this.slotSizePlusMargin();
		int firstSlotXPos = this.firstSlotXPos() + this.borderSize() + (((9 - nbSlotToDisplay) * slotSize) / 2);
		int firstSlotYPos = this.firstSlotYPos() + this.borderSize();
		
		for(int i = 0; i < inventorySize; i++) {
			if(i < nbSlotToDisplay) {
				this.addSlotToContainer(new Slot(this.inventory, i, firstSlotXPos + (i * slotSize), firstSlotYPos));
			}
			else {
				this.addSlotToContainer(new Slot(this.inventory, i, -500, -500));
			}
		}
	}
	
	@Override
	public int containerHeight() {
		return this.toolBarHeight();
	}
	
}
