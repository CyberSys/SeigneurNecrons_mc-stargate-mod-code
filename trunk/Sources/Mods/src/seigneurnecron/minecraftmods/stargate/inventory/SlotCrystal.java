package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;

/**
 * @author Seigneur Necron
 */
public class SlotCrystal extends Slot {
	
	// Constructors :
	
	public SlotCrystal(IInventory inventory, int index, int xPos, int yPos) {
		super(inventory, index, xPos, yPos);
	}
	
	// Methods :
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemCrystal;
	}
	
}
