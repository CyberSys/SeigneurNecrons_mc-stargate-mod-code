package seigneurnecron.minecraftmods.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

/**
 * @author Seigneur Necron
 */
public abstract class ContainerOneLine<T extends InventoryOneLine<?>> extends ContainerBasic<T> {
	
	// Constants :
	
	public static final int OUT_OF_VIEW = -1000;
	
	// Constructors :
	
	protected ContainerOneLine(EntityPlayer player, T inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	/**
	 * Create a new slot to add to the container.
	 * @param index - the index of the slot in the inventory.
	 * @param xPos - the X position of the slot in the gui.
	 * @param yPos - the Y position of the slot in the gui.
	 * @return a new slot to add to the container.
	 */
	protected abstract Slot getNewSlot(int index, int xPos, int yPos);
	
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
				this.addSlotToContainer(this.getNewSlot(i, firstSlotXPos + (i * slotSize), firstSlotYPos));
			}
			else {
				this.addSlotToContainer(this.getNewSlot(i, OUT_OF_VIEW, OUT_OF_VIEW));
			}
		}
	}
	
	@Override
	public int containerHeight() {
		return this.toolBarHeight();
	}
	
}
