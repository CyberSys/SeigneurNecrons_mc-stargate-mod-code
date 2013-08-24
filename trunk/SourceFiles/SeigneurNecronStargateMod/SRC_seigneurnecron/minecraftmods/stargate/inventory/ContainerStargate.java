package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiContainer;

/**
 * @author Seigneur Necron
 */
public abstract class ContainerStargate<T extends TileEntityGuiContainer> extends Container {
	
	protected T tileEntity;
	
	public ContainerStargate(InventoryPlayer inventoryPlayer, T tileEntity) {
		this.tileEntity = tileEntity;
		this.init();
		this.bindPlayerInventory(inventoryPlayer);
	}
	
	/**
	 * Initializes the container. IMPORTANT : the slots are added here.
	 */
	protected abstract void init();
	
	/**
	 * Commonly used vanilla code that adds the player's inventory.
	 * @param inventoryPlayer - player's inventory.
	 */
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) this.inventorySlots.get(slot);
		
		// Check that the slot exists and contains an item stack.
		if(slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			
			int inventorySize = this.tileEntity.getSizeInventory();
			
			// Merges the item into player inventory since its in the tileEntity.
			if(slot < inventorySize) {
				if(!this.mergeItemStack(stackInSlot, inventorySize, inventorySize + 36, true)) {
					return null;
				}
			}
			// Places it into the tileEntity if possible since its in the player inventory.
			else {
				int maxStackSize = this.tileEntity.getInventoryStackLimit();
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
