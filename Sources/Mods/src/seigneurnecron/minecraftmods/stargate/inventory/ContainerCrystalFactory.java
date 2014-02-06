package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ContainerCrystalFactory extends ContainerOneLine<InventoryCrystalFactory> {
	
	// Constructors :
	
	public ContainerCrystalFactory(EntityPlayer player, InventoryCrystalFactory inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	public boolean enchantItem(EntityPlayer player, int index) {
		return this.inventory.reconfigureCrystal(index);
	}
	
}
