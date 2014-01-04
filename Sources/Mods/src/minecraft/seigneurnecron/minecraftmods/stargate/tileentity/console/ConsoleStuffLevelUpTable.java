package seigneurnecron.minecraftmods.stargate.tileentity.console;

import seigneurnecron.minecraftmods.stargate.inventory.InventoryStuffLevelUpTable;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public class ConsoleStuffLevelUpTable extends ConsoleConatainer<InventoryStuffLevelUpTable> {
	
	// Constructors :
	
	public ConsoleStuffLevelUpTable(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	protected InventoryStuffLevelUpTable getNewInventory() {
		return new InventoryStuffLevelUpTable(this.tileEntity);
	}
	
}
