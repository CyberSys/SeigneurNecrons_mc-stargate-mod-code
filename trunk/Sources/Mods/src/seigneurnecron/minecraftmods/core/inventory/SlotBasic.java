package seigneurnecron.minecraftmods.core.inventory;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Seigneur Necron
 */
public class SlotBasic extends Slot {
	
	// Constructors :
	
	public SlotBasic(InventoryBasic inventory, int index, int xPos, int yPos) {
		super(inventory, index, xPos, yPos);
	}
	
	// Methods :
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return this.inventory.isItemValidForSlot(this.getSlotIndex(), itemStack);
	}
	
	@Override
	public int getSlotStackLimit() {
		return ((InventoryBasic) this.inventory).getInventoryStackLimit(this.getSlotIndex());
	}
	
}
