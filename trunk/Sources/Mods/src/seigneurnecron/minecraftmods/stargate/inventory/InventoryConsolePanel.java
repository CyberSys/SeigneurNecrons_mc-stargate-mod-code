package seigneurnecron.minecraftmods.stargate.inventory;

import seigneurnecron.minecraftmods.core.inventory.InventoryOneLine;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.console.Console;

/**
 * @author Seigneur Necron
 */
public abstract class InventoryConsolePanel<T extends Console> extends InventoryOneLine<TileEntityConsoleBase> {
	
	// Fields :
	
	public final T console;
	
	// Constructors :
	
	protected InventoryConsolePanel(TileEntityConsoleBase tileEntity, T console) {
		super(tileEntity);
		this.console = console;
	}
	
}
