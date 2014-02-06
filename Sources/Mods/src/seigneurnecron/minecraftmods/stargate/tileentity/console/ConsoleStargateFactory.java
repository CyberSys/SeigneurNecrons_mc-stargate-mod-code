package seigneurnecron.minecraftmods.stargate.tileentity.console;

import seigneurnecron.minecraftmods.stargate.inventory.InventoryStargateFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
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
