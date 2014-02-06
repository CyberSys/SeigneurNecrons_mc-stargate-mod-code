package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ContainerConsoleBase extends ContainerOneLine<InventoryConsoleBase> {
	
	// Constructors :
	
	public ContainerConsoleBase(EntityPlayer player, InventoryConsoleBase inventory) {
		super(player, inventory);
	}
	
}
