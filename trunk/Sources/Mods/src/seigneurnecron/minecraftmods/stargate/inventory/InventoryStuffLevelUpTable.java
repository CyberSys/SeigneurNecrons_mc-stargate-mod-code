package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStuffLevelUpTable;

/**
 * @author Seigneur Necron
 */
public class InventoryStuffLevelUpTable extends InventoryConsolePanel<ConsoleStuffLevelUpTable> {
	
	// Constants :
	
	public static final String INV_NAME = "container.stuffLevelUpTable";
	
	// Fields :
	
	/**
	 * The unique inventory slot, where you can put an item to enchant.
	 */
	private ItemStack stuff;
	
	// Constructors :
	
	public InventoryStuffLevelUpTable(TileEntityConsoleBase tileEntity, ConsoleStuffLevelUpTable console) {
		super(tileEntity, console);
	}
	
	// Getters :
	
	/**
	 * Returns the item stack inserted in the stuff slot.
	 * @return the item stack inserted in the stuff slot.
	 */
	public ItemStack getStuff() {
		return this.stuff;
	}
	
	// Setters :
	
	/**
	 * Updates the stuff slot.
	 * @param stuff - the new item stack to put in the slot.
	 */
	private void setStuff(ItemStack stuff) {
		if(!ItemStack.areItemStacksEqual(this.stuff, stuff)) {
			this.stuff = stuff;
			this.console.updateEnchantInfo();
		}
	}
	
	// Methods :
	
	@Override
	public String getInvName() {
		return INV_NAME;
	}
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return (index == 0) ? this.stuff : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index == 0) {
			this.setStuff(itemStack);
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack) {
		return true;
	}
	
}
