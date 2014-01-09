package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.core.inventory.InventoryOneLine;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystalFull;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;

/**
 * @author Seigneur Necron
 */
public class InventoryMobGenerator extends InventoryOneLine<TileEntityMobGenerator> {
	
	// Constants :
	
	public static final String INV_NAME = "container.mobGenerator";
	
	// Fields :
	
	/**
	 * The unique inventory slot, where you can insert a soul crystal.
	 */
	private ItemStack crystal;
	
	// Constructors :
	
	public InventoryMobGenerator(TileEntityMobGenerator tileEntity) {
		super(tileEntity);
	}
	
	// Getters :
	
	/**
	 * Returns the item inserted in the crystal slot.
	 * @return the item inserted in the crystal slot.
	 */
	public ItemStack getCrystal() {
		return this.crystal;
	}
	
	// Setters :
	
	/**
	 * Updates the crystal slot (informs clients).
	 * @param crystal - the new item to insert in the crystal slot.
	 */
	protected void setCrystal(ItemStack crystal) {
		if(!ItemStack.areItemStacksEqual(this.crystal, crystal)) {
			this.crystal = crystal;
			this.tileEntity.onCrystalChanged();
		}
	}
	
	// Methods :
	
	/**
	 * Indicates whether a soul crystal is inserted in the generator.
	 * @return true if a crystal is inserted, else false.
	 */
	public boolean isCrystalInserted() {
		return(this.crystal != null && this.crystal.getItem() instanceof ItemSoulCrystalFull);
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
	public boolean isItemValidForSlot(int index, ItemStack itemstack) {
		return itemstack.getItem() instanceof ItemSoulCrystalFull;
	}
	
}
