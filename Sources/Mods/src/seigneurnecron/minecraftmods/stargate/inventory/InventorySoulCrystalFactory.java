package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.item.ItemSoulCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleSoulCrystalFactory;

/**
 * @author Seigneur Necron
 */
public class InventorySoulCrystalFactory extends InventoryConsolePanel<ConsoleSoulCrystalFactory> {
	
	// Constants :
	
	public static final String INV_NAME = "container.soulCrystalFactory";
	
	// Fields :
	
	/**
	 * The unique inventory slot, where you can insert a soul crystal.
	 */
	private ItemStack crystal;
	
	// Constructors :
	
	public InventorySoulCrystalFactory(TileEntityConsoleBase tileEntity, ConsoleSoulCrystalFactory console) {
		super(tileEntity, console);
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
	public void setCrystal(ItemStack crystal) {
		if(!ItemStack.areItemStacksEqual(this.crystal, crystal)) {
			this.crystal = crystal;
			this.onInventoryChanged();
		}
	}
	
	// Methods :
	
	public boolean isCrystalValid() {
		return this.crystal != null && this.crystal.getItem() == StargateMod.item_crystalSoulEmpty;
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
		return (index == 0) ? this.crystal : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index == 0) {
			this.setCrystal(itemStack);
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemSoulCrystal;
	}
	
}
