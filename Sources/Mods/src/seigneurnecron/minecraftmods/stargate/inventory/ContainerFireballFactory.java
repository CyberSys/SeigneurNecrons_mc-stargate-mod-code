package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class ContainerFireballFactory extends ContainerConsolePanel<InventoryFireballFactory> {
	
	// Constructors :
	
	public ContainerFireballFactory(EntityPlayer player, InventoryFireballFactory inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	public boolean enchantItem(EntityPlayer player, int index) {
		return this.inventory.reconfigureFireball(index);
	}
	
}
