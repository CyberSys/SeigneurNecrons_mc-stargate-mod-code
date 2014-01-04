package seigneurnecron.minecraftmods.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.core.gui.GuiConstants;

/**
 * @author Seigneur Necron
 */
public abstract class ContainerBasic<T extends InventoryBasic> extends Container {
	
	// Fields :
	
	public final T inventory;
	
	// Constructors :
	
	protected ContainerBasic(InventoryPlayer inventoryPlayer, T inventory) {
		this.inventory = inventory;
		this.init();
		this.bindPlayerInventory(inventoryPlayer);
	}
	
	// Methods :
	
	/**
	 * Initializes the container. IMPORTANT : the slots must be added here.
	 */
	protected abstract void init();
	
	/**
	 * Commonly used vanilla code that adds the player's inventory, modified to be parameterizable.
	 * @param inventoryPlayer - player's inventory.
	 */
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		int slotSize = this.slotSizePlusMargin();
		int firstSlotXPos = this.firstSlotXPos() + this.borderSize();
		int firstInvSlotYPos = this.firstSlotYPos() + this.borderSize() + this.containerHeight() + this.panelMargin();
		int firstToolBarSlotYPos = this.firstSlotYPos() + this.borderSize() + this.containerHeight() + this.inventoryHeight() + (2 * this.panelMargin());
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, firstSlotXPos + j * slotSize, firstInvSlotYPos + i * slotSize));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, firstSlotXPos + i * slotSize, firstToolBarSlotYPos));
		}
	}
	
	/**
	 * Returns the size of panel margins.
	 * @return the size of panel margins.
	 */
	public int panelMargin() {
		return GuiConstants.PANEL_MARGIN;
	}
	
	/**
	 * Returns the size of title margins.
	 * @return the size of title margins.
	 */
	public int titleMargin() {
		return GuiConstants.TITLE_MARGIN;
	}
	
	/**
	 * Returns the height of a field, including its top margin.
	 * @return the height of a field.
	 */
	public int titleSize() {
		return GuiConstants.FIELD_HEIGHT + 2 * this.titleMargin();
	}
	
	/**
	 * Returns the size of slot margins.
	 * @return the size of slot margins.
	 */
	public int slotMargin() {
		return GuiConstants.SLOT_MARGIN;
	}
	
	/**
	 * Returns the width of a border.
	 * @return the width of a border.
	 */
	public int borderSize() {
		return GuiConstants.BORDER_SIZE;
	}
	
	/**
	 * Returns the size of a slot.
	 * @return the size of a slot.
	 */
	public int slotSize() {
		return GuiConstants.SLOT_SIZE;
	}
	
	/**
	 * Returns the size of a slot, including its borders.
	 * @return the size of a slot, including its borders.
	 */
	public int slotSizeWithBorder() {
		return this.slotSize() + (2 * this.borderSize());
	}
	
	/**
	 * Returns the size of a slot, including its borders, plus the slot margin.
	 * @return the size of a slot, including its borders, plus the slot margin.
	 */
	public int slotSizePlusMargin() {
		return this.slotSizeWithBorder() + this.slotMargin();
	}
	
	/**
	 * Returns the height of the container panel (containing the container inventory slots).
	 * @return the height of the container panel.
	 */
	public abstract int containerHeight();
	
	/**
	 * Returns the height of the inventory panel (containing the player inventory slots).
	 * @return the height of the inventory panel.
	 */
	public int inventoryHeight() {
		return (2 * this.borderSize()) + (3 * this.slotSizePlusMargin()) + this.titleSize();
	}
	
	/**
	 * Returns the height of the tool bar panel (containing the player tool bar slots).
	 * @return the height of the tool bar panel.
	 */
	public int toolBarHeight() {
		return (2 * this.borderSize()) + this.slotSizePlusMargin() + this.titleSize();
	}
	
	/**
	 * Returns the width of the main panel (containing the container, inventory and tool bar panels).
	 * @return the width of the main panel.
	 */
	public int mainPanelWidth() {
		return (2 * this.borderSize()) + (9 * this.slotSizePlusMargin()) + this.slotMargin();
	}
	
	/**
	 * Returns the height of the main panel (containing the container, inventory and tool bar panels).
	 * @return the height of the main panel.
	 */
	public int mainPanelHeight() {
		return this.containerHeight() + this.inventoryHeight() + this.toolBarHeight() + (2 * this.panelMargin());
	}
	
	/**
	 * Returns the X position of the first slot in panel.
	 * @return the X position of the first slot in panel.
	 */
	public int firstSlotXPos() {
		return this.borderSize() + this.slotMargin();
	}
	
	/**
	 * Returns the Y position of the first slot in panel.
	 * @return the Y position of the first slot in panel.
	 */
	public int firstSlotYPos() {
		return this.borderSize() + this.titleSize();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.inventory.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) this.inventorySlots.get(slot);
		
		// Check that the slot exists and contains an item stack.
		if(slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			
			int inventorySize = this.inventory.getSizeInventory();
			
			// Merges the item into player inventory since its in the tileEntity.
			if(slot < inventorySize) {
				if(!this.mergeItemStack(stackInSlot, inventorySize, inventorySize + 36, true)) {
					return null;
				}
			}
			// Places it into the tileEntity if possible since its in the player inventory.
			else {
				int maxStackSize = this.inventory.getInventoryStackLimit();
				boolean succes = false;
				
				for(int i = 0; i < inventorySize; i++) {
					Slot s = (Slot) this.inventorySlots.get(i);
					
					if(!s.getHasStack() && s.isItemValid(stackInSlot)) {
						if(stackInSlot.stackSize <= maxStackSize) {
							s.putStack(stackInSlot.copy());
							stackInSlot.stackSize = 0;
						}
						else {
							s.putStack(new ItemStack(stackInSlot.itemID, maxStackSize, stackInSlot.getItemDamage()));
							stackInSlot.stackSize -= maxStackSize;
						}
						
						succes = true;
						break;
					}
				}
				
				if(!succes) {
					return null;
				}
			}
			
			if(stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			}
			else {
				slotObject.onSlotChanged();
			}
			
			if(stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		
		return stack;
	}
	
}
