package seigneurnecron.minecraftmods.stargate.tileentity.console;

import seigneurnecron.minecraftmods.stargate.inventory.InventoryCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public class ConsoleCrystalFactory extends ConsoleConatainer<InventoryCrystalFactory> {
	
	// Constructors :
	
	public ConsoleCrystalFactory(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	protected InventoryCrystalFactory getNewInventory() {
		return new InventoryCrystalFactory(this.tileEntity);
	}
	
}
