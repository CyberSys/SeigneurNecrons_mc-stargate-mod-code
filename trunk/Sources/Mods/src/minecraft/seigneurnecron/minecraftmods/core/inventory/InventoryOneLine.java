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
	 * Indicates how many slots must be displayed in the gui. The default value is the size of the inventory.
	 * @return the number of slots that must be displayed in the gui.
	 */
	public int nbSlotToDisplay() {
		return this.getSizeInventory();
	}
	
}
