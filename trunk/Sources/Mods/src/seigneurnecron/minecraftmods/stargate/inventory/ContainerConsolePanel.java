package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class ContainerConsolePanel<T extends InventoryConsolePanel<?>> extends ContainerOneLine<T> {
	
	// Constructors :
	
	protected ContainerConsolePanel(EntityPlayer player, T inventory) {
		super(player, inventory);
	}
	
}
