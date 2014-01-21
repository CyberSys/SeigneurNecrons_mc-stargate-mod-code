package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * @author Seigneur Necron
 */
public class ContainerMobGenerator extends ContainerOneLine<InventoryMobGenerator> {
	
	// Constructors :
	
	public ContainerMobGenerator(EntityPlayer player, InventoryMobGenerator inventory) {
		super(player, inventory);
	}
	
}
