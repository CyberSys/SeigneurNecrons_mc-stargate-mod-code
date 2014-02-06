package seigneurnecron.minecraftmods.stargate.tileentity.console;

import seigneurnecron.minecraftmods.stargate.inventory.InventorySoulCrystalFactory;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ConsoleSoulCrystalFactory extends ConsoleContainer<InventorySoulCrystalFactory> {
	
	// Constructors :
	
	public ConsoleSoulCrystalFactory(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	protected InventorySoulCrystalFactory getNewInventory() {
		return new InventorySoulCrystalFactory(this.tileEntity, this);
	}
	
}
