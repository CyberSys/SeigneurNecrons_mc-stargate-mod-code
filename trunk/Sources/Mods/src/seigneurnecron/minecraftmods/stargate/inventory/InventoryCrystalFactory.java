package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.core.inventory.InventoryOneLine;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityCrystalFactory;

/**
 * @author Seigneur Necron
 */
public class InventoryCrystalFactory extends InventoryOneLine<TileEntityCrystalFactory> {
	
	// Constants :
	
	public static final String INV_NAME = "container.crystalFactory";
	
	// Fields :
	
	/**
	 * The unique inventory slot, where you can insert a crystal.
	 */
	private ItemStack crystal;
	
	// Constructors :
	
	public InventoryCrystalFactory(TileEntityCrystalFactory tileEntity) {
		super(tileEntity);
	}
	
	// Getters :
	
	/**
	 * Returns the item stack inserted in the crystal slot.
	 * @return the item stack inserted in the crystal slot.
	 */
	public ItemStack getCrystal() {
		return this.crystal;
	}
	
	// Setters :
	
	/**
	 * Updates the crystal slot.
	 * @param crystal - the new item stack to insert in the crystal slot.
	 */
	protected void setCrystal(ItemStack crystal) {
		if(!ItemStack.areItemStacksEqual(this.crystal, crystal)) {
			this.crystal = crystal;
			this.tileEntity.onCrystalChanged();
		}
	}
	
	// Methods :
	
	/**
	 * Indicates whether a valid crystal is inserted in the inventory.
	 * @return true if a valid crystal is inserted, else false.
	 */
	public boolean isCrystalValid() {
		if(this.crystal != null) {
			Item item = this.crystal.getItem();
			return (item instanceof ItemCrystal) && !(item instanceof ItemSoulCrystalFull);
		}
		
		return false;
	}
	
	/**
	 * Transform the crystal in the slot in another crystal.
	 * @param index - the index of the new crystal in the craftable crystal list.
	 * @return true if the crystal was succesfully transformed, false if the input crystal isn't valid.
	 */
	public boolean reconfigureCrystal(int index) {
		if(this.isCrystalValid() && index >= 0 && index < ItemCrystal.getCraftableCristals().size()) {
			ItemCrystal newCrystal = ItemCrystal.getCraftableCristals().get(index);
			ItemCrystal oldCrystal = (ItemCrystal) this.crystal.getItem();
			
			if(newCrystal != oldCrystal) {
				this.setCrystal(new ItemStack(newCrystal, this.crystal.stackSize));
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
		return index == 0 ? this.crystal : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index == 0) {
			this.setCrystal(itemStack);
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof ItemCrystal) && !(item instanceof ItemSoulCrystalFull);
	}
	
}
