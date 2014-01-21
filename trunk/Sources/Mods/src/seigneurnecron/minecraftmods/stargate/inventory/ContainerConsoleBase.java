package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * @author Seigneur Necron
 */
public class ContainerConsoleBase extends ContainerOneLine<InventoryConsoleBase> {
	
	// Constructors :
	
	public ContainerConsoleBase(EntityPlayer player, InventoryConsoleBase inventory) {
		super(player, inventory);
	}
	
}
