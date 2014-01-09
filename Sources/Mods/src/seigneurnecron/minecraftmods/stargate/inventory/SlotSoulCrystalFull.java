package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;

/**
 * @author Seigneur Necron
 */
public class SlotSoulCrystalFull extends Slot {
	
	// Constructors :
	
	public SlotSoulCrystalFull(IInventory inventory, int index, int xPos, int yPos) {
		super(inventory, index, xPos, yPos);
	}
	
	// Methods :
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemSoulCrystalFull;
	}
	
}
