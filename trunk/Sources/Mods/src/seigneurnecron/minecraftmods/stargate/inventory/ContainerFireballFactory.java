package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;

/**
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
