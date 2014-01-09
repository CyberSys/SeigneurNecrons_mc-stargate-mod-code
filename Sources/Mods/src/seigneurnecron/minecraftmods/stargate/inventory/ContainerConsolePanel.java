package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * @author Seigneur Necron
 */
public abstract class ContainerConsolePanel<T extends InventoryConsolePanel<?>> extends ContainerOneLine<T> {
	
	// Constructors :
	
	protected ContainerConsolePanel(EntityPlayer player, T inventory) {
		super(player, inventory);
	}
	
}
