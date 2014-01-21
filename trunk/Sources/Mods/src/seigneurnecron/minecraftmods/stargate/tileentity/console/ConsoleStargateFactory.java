package seigneurnecron.minecraftmods.stargate.tileentity.console;

import seigneurnecron.minecraftmods.stargate.inventory.InventoryStargateFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public class ConsoleStargateFactory extends ConsoleContainer<InventoryStargateFactory> {
	
	// Constructors :
	
	public ConsoleStargateFactory(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	protected InventoryStargateFactory getNewInventory() {
		return new InventoryStargateFactory(this.tileEntity, this);
	}
	
}
