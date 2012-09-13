package mods.stargate;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;

public class ContainerGui extends Container {
	
	private TileEntityGui tileEntity;
	
	public ContainerGui(InventoryPlayer inventoryPlayer, TileEntityGui tileEntity) {
		this.tileEntity = tileEntity;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
}
