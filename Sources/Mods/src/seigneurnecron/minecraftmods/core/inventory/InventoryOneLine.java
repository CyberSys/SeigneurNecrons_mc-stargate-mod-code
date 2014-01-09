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
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
}
