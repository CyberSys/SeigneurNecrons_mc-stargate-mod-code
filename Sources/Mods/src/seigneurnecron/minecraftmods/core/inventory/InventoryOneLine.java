package seigneurnecron.minecraftmods.core.inventory;

import net.minecraft.tileentity.TileEntity;

/**
 * @author Seigneur Necron
 */
public abstract class InventoryOneLine<T extends TileEntity> extends InventoryBasic<T> {
	
	// Constructors :
	
	protected InventoryOneLine(T tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	/**
	 * Returns the number of slots in this inventory. Can't be outside of [0, 9].
	 * @return the number of slots in this inventory.
	 */
	public final int getSafeSizeInventory() {
		int inventorySize = this.getSizeInventory();
		
		if(inventorySize < 0) {
			inventorySize = 0;
		}
		else if(inventorySize > 9) {
			inventorySize = 9;
		}
		
		return inventorySize;
	}
	
	/**
	 * Returns the number of normal slots. Can't be outside of [0, inventorySize].
	 * @return the number of normal slots.
	 */
	public final int getSafeNbNormalSlots() {
		int inventorySize = this.getSafeSizeInventory();
		int nbNormalSlots = this.getNbNormalSlots();
		
		if(nbNormalSlots < 0) {
			nbNormalSlots = 0;
		}
		else if(nbNormalSlots > inventorySize) {
			nbNormalSlots = inventorySize;
		}
		
		return nbNormalSlots;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
}
