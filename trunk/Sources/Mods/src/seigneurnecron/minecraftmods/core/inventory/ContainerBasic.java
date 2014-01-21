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
	public final EntityPlayer player;
	
	// Constructors :
	
	protected ContainerBasic(EntityPlayer player, T inventory) {
		this.inventory = inventory;
		this.player = player;
		this.init();
		this.bindPlayerInventory(player.inventory);
	}
	
	// Methods :
	
	/**
	 * Initializes the container. IMPORTANT : the slots must be added here.
	 */
	protected abstract void init();
	
	/**
	 * Create a new slot to add to the container.
	 * @param index - the index of the slot in the inventory.
	 * @param xPos - the X position of the slot in the gui.
	 * @param yPos - the Y position of the slot in the gui.
	 * @return a new slot to add to the container.
	 */
	protected Slot getNewSlot(int index, int xPos, int yPos) {
		return new SlotBasic(this.inventory, index, xPos, yPos);
	}
	
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
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			itemstack = stackInSlot.copy();
			
			int inventorySize = this.inventory.getSizeInventory();
			
			// If the item stack is in the container inventory, try to merge it in the player inventory.
			if(index < inventorySize) {
				if(!this.mergeItemStack(stackInSlot, inventorySize, this.inventorySlots.size(), true)) {
					return null;
				}
			}
			// If the item stack is in the player inventory, try to merge it in the container inventory.
			else if(!this.mergeItemStack(stackInSlot, 0, inventorySize, false)) {
				return null;
			}
			
			if(stackInSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			}
			else {
				slot.onSlotChanged();
			}
		}
		
		return itemstack;
	}
	
	/**
	 * Improved version of container.mergeItemStack(...) : checks slot.getSlotStackLimit() and slot.isItemValid(itemStack). <br />
	 * Merges provided ItemStack with the first avaliable ones in the container/player inventory.
	 */
	@Override
	protected boolean mergeItemStack(ItemStack itemStack, int beginIndex, int endIndex, boolean startByTheEnd) {
		boolean succes = false;
		int index = (startByTheEnd) ? (endIndex - 1) : beginIndex;
		
		if(itemStack.isStackable()) {
			while(itemStack.stackSize > 0 && (!startByTheEnd && index < endIndex || startByTheEnd && index >= beginIndex)) {
				Slot slot = (Slot) this.inventorySlots.get(index);
				ItemStack itemstackInSlot = slot.getStack();
				
				if(itemstackInSlot != null && itemstackInSlot.itemID == itemStack.itemID && (!itemStack.getHasSubtypes() || itemStack.getItemDamage() == itemstackInSlot.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemStack, itemstackInSlot)) {
					int totalSize = itemstackInSlot.stackSize + itemStack.stackSize;
					int maxStackSize = Math.min(itemStack.getMaxStackSize(), slot.getSlotStackLimit());
					
					if(totalSize <= maxStackSize) {
						itemStack.stackSize = 0;
						itemstackInSlot.stackSize = totalSize;
						slot.onSlotChanged();
						succes = true;
					}
					else if(itemstackInSlot.stackSize < maxStackSize) {
						itemStack.stackSize -= maxStackSize - itemstackInSlot.stackSize;
						itemstackInSlot.stackSize = maxStackSize;
						slot.onSlotChanged();
						succes = true;
					}
				}
				
				if(startByTheEnd) {
					--index;
				}
				else {
					++index;
				}
			}
		}
		
		if(itemStack.stackSize > 0) {
			index = (startByTheEnd) ? (endIndex - 1) : beginIndex;
			
			while(itemStack.stackSize > 0 && (!startByTheEnd && index < endIndex || startByTheEnd && index >= beginIndex)) {
				Slot slot = (Slot) this.inventorySlots.get(index);
				ItemStack itemstackInSlot = slot.getStack();
				
				if(itemstackInSlot == null && slot.isItemValid(itemStack)) {
					int maxStackSize = slot.getSlotStackLimit();
					ItemStack newItemStack = itemStack.copy();
					
					if(itemStack.stackSize <= maxStackSize) {
						itemStack.stackSize = 0;
					}
					else {
						newItemStack.stackSize = maxStackSize;
						itemStack.stackSize -= maxStackSize;
					}
					
					slot.putStack(newItemStack);
					slot.onSlotChanged();
					succes = true;
				}
				
				if(startByTheEnd) {
					--index;
				}
				else {
					++index;
				}
			}
		}
		
		return succes;
	}
	
}
