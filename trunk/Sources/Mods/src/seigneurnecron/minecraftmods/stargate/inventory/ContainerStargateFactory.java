package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Seigneur Necron
 */
public class ContainerStargateFactory extends ContainerConsolePanel<InventoryStargateFactory> {
	
	// Constructors :
	
	public ContainerStargateFactory(EntityPlayer player, InventoryStargateFactory inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	public boolean enchantItem(EntityPlayer player, int index) {
		return this.inventory.craft();
	}
	
}
