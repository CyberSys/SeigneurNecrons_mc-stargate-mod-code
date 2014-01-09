package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

/**
 * @author Seigneur Necron
 */
public class ContainerSoulCrystalFactory extends ContainerConsolePanel<InventorySoulCrystalFactory> {
	
	// Constructors :
	
	public ContainerSoulCrystalFactory(EntityPlayer player, InventorySoulCrystalFactory inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	protected Slot getNewSlot(int index, int xPos, int yPos) {
		return new SlotSoulCrystal(this.inventory, index, xPos, yPos);
	}
	
}
