package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityMobGenerator;

public class ContainerMobGenerator extends ContainerStargate<TileEntityMobGenerator> {
	
	public ContainerMobGenerator(InventoryPlayer inventoryPlayer, TileEntityMobGenerator tileEntity) {
		super(inventoryPlayer, tileEntity);
	}
	
	@Override
	protected void init() {
		this.addSlotToContainer(new Slot(this.tileEntity, 0, 80, 35));
	}
	
}
