package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleSoulCrystalFactory;

/**
 * @author Seigneur Necron
 */
public class InventorySoulCrystalFactory extends InventoryConsolePanel<ConsoleSoulCrystalFactory> {
	
	// Constants :
	
	public static final String INV_NAME = "container.crystalFactory";
	
	// Fields :
	
	/**
	 * The unique inventory slot, where you can insert a soul crystal.
	 */
	private ItemStack crystal;
	
	/**
	 * The container binded with that inventory.
	 */
	public ContainerStuffLevelUpTable container;
	
	// Constructors :
	
	public InventorySoulCrystalFactory(TileEntityConsoleBase tileEntity, ConsoleSoulCrystalFactory console) {
		super(tileEntity, console);
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
	public void setCrystal(ItemStack crystal) {
		if(!ItemStack.areItemStacksEqual(this.crystal, crystal)) {
			this.crystal = crystal;
			this.onInventoryChanged();
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
		return index == 0 ? this.crystal : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index == 0) {
			this.setCrystal(itemStack);
		}
	}
	
	@Override
	public void onInventoryChanged() {
		this.tileEntity.onInventoryChanged();
		
		if(this.container != null) {
			this.container.onCraftMatrixChanged(this);
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack) {
		return itemstack.getItem() instanceof ItemSoulCrystal;
	}
	
}
