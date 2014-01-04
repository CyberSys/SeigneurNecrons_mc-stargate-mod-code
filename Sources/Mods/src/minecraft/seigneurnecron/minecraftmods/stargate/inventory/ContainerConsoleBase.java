package seigneurnecron.minecraftmods.stargate.inventory;

import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * @author Seigneur Necron
 */
public class ContainerConsoleBase extends ContainerOneLine<InventoryConsoleBase> {
	
	public ContainerConsoleBase(InventoryPlayer inventoryPlayer, InventoryConsoleBase inventory) {
		super(inventoryPlayer, inventory);
	}
	
}
