package seigneurnecron.minecraftmods.stargate.inventory;

import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * @author Seigneur Necron
 */
public class ContainerMobGenerator extends ContainerOneLine<InventoryMobGenerator> {
	
	public ContainerMobGenerator(InventoryPlayer inventoryPlayer, InventoryMobGenerator inventory) {
		super(inventoryPlayer, inventory);
	}
	
}
