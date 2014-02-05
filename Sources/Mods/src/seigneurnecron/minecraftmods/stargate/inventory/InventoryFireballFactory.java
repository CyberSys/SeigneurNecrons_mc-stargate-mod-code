package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.item.fireball.ItemFireballBasic;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleFireballFactory;

/**
 * @author Seigneur Necron
 */
public class InventoryFireballFactory extends InventoryConsolePanel<ConsoleFireballFactory> {
	
	// Constants :
	
	public static final String INV_NAME = "container.fireballFactory";
	
	// Fields :
	
	/**
	 * The unique inventory slot, where you can insert a fireball.
	 */
	private ItemStack fireball;
	
	// Constructors :
	
	public InventoryFireballFactory(TileEntityConsoleBase tileEntity, ConsoleFireballFactory console) {
		super(tileEntity, console);
	}
	
	// Getters :
	
	/**
	 * Returns the item stack inserted in the fireball slot.
	 * @return the item stack inserted in the fireball slot.
	 */
	public ItemStack getFireball() {
		return this.fireball;
	}
	
	// Setters :
	
	/**
	 * Updates the fireball slot.
	 * @param fireball - the new item stack to insert in the fireball slot.
	 */
	protected void setFireball(ItemStack fireball) {
		if(!ItemStack.areItemStacksEqual(this.fireball, fireball)) {
			this.fireball = fireball;
			this.onInventoryChanged();
		}
	}
	
	// Methods :
	
	/**
	 * Indicates whether a valid fireball is inserted in the inventory.
	 * @return true if a valid fireball is inserted, else false.
	 */
	public boolean isFireballValid() {
		if(this.fireball != null) {
			Item item = this.fireball.getItem();
			return (item == Item.fireballCharge) || (item instanceof ItemFireballBasic);
		}
		
		return false;
	}
	
	/**
	 * Transform the fireball in the slot in another fireball.
	 * @param index - the index of the new fireball in the craftable fireball list.
	 * @return true if the fireball was succesfully transformed, false if the input fireball isn't valid.
	 */
	public boolean reconfigureFireball(int index) {
		if(this.isFireballValid() && index >= 0 && index < ItemFireballBasic.getCraftableFireballs().size()) {
			Item newFireball = ItemFireballBasic.getCraftableFireballs().get(index);
			Item oldFireball = this.fireball.getItem();
			
			if(newFireball != oldFireball) {
				this.setFireball(new ItemStack(newFireball, this.fireball.stackSize));
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
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
		return index == 0 ? this.fireball : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index == 0) {
			this.setFireball(itemStack);
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item == Item.fireballCharge) || (item instanceof ItemFireballBasic);
	}
	
}
