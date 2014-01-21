package seigneurnecron.minecraftmods.stargate.tileentity;

import seigneurnecron.minecraftmods.stargate.inventory.InventoryCrystalFactory;

/**
 * @author Seigneur Necron
 */
public class TileEntityCrystalFactory extends TileEntityContainerStargate<InventoryCrystalFactory> {
	
	// Methods :
	
	@Override
	protected InventoryCrystalFactory getNewInventory() {
		return new InventoryCrystalFactory(this);
	}
	
	public void onCrystalChanged() {
		if(this.worldObj != null && !this.worldObj.isRemote) {
			this.setChanged();
			this.update();
		}
	}
	
}
