package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * @author Seigneur Necron
 */
public class ContainerMobGenerator extends ContainerOneLine<InventoryMobGenerator> {
	
	// Constructors :
	
	public ContainerMobGenerator(EntityPlayer player, InventoryMobGenerator inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	protected Slot getNewSlot(int index, int xPos, int yPos) {
		return new SlotSoulCrystalFull(this.inventory, index, xPos, yPos);
	}
	
}
