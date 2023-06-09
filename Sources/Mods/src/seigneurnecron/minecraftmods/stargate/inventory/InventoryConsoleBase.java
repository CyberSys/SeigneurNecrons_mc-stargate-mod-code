package seigneurnecron.minecraftmods.stargate.inventory;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.core.inventory.InventoryOneLine;
import seigneurnecron.minecraftmods.stargate.item.ItemCrystal;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class InventoryConsoleBase extends InventoryOneLine<TileEntityConsoleBase> {
	
	// Constants :
	
	public static final String INV_NAME = "container.consoleBase";
	public static final int NB_CRYSTALS = 5;
	
	// Fields :
	
	/**
	 * The inventory slots, where you can insert crystals.
	 */
	private ItemStack[] crystals = new ItemStack[NB_CRYSTALS];
	
	// Constructors :
	
	public InventoryConsoleBase(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Getters :
	
	/**
	 * Returns the item stack inserted in the crystal slot.
	 * @return the item stack inserted in the crystal slot.
	 */
	public ItemStack getCrystal(int i) {
		if(i < 0 || i >= this.crystals.length) {
			throw new IllegalArgumentException("Index out of crystal array bounds.");
		}
		
		return this.crystals[i];
	}
	
	// Setters :
	
	/**
	 * Updates the crystal slot.
	 * @param crystal - the new item stack to insert in the crystal slot.
	 */
	protected void setCrystal(int i, ItemStack crystal) {
		if(i < 0 || i >= this.crystals.length) {
			throw new IllegalArgumentException("Index out of crystal array bounds.");
		}
		
		if(!ItemStack.areItemStacksEqual(this.crystals[i], crystal)) {
			this.crystals[i] = crystal;
			this.tileEntity.onConsoleDataChanged();
		}
	}
	
	// Methods :
	
	/**
	 * Returns the list of the crystals inserted in this console.
	 * @return the list of the crystals inserted in this console.
	 */
	public ArrayList<ItemCrystal> getCrystals() {
		ArrayList<ItemCrystal> crystals = new ArrayList<ItemCrystal>();
		
		for(ItemStack itemStack : this.crystals) {
			if(itemStack != null) {
				Item item = itemStack.getItem();
				
				if(item instanceof ItemCrystal) {
					crystals.add((ItemCrystal) item);
				}
				else {
					crystals.clear();
					break;
				}
			}
		}
		
		return crystals;
	}
	
	@Override
	public String getInvName() {
		return INV_NAME;
	}
	
	@Override
	public int getSizeInventory() {
		return this.crystals.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.getCrystal(index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		this.setCrystal(index, itemStack);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemCrystal;
	}
	
}
