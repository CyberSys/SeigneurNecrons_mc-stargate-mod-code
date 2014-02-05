package seigneurnecron.minecraftmods.stargate.tileentity.console;

import seigneurnecron.minecraftmods.stargate.inventory.InventoryFireballFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public class ConsoleFireballFactory extends ConsoleContainer<InventoryFireballFactory> {
	
	// Constructors :
	
	public ConsoleFireballFactory(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	protected InventoryFireballFactory getNewInventory() {
		return new InventoryFireballFactory(this.tileEntity, this);
	}
	
}
