package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import seigneurnecron.minecraftmods.core.inventory.ContainerOneLine;

/**
 * @author Seigneur Necron
 */
public class ContainerConsoleBase extends ContainerOneLine<InventoryConsoleBase> {
	
	// Constructors :
	
	public ContainerConsoleBase(EntityPlayer player, InventoryConsoleBase inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	protected Slot getNewSlot(int index, int xPos, int yPos) {
		return new SlotCrystal(this.inventory, index, xPos, yPos);
	}
	
}
