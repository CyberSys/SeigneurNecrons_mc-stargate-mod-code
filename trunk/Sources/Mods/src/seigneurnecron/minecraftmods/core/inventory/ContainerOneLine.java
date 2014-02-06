package seigneurnecron.minecraftmods.core.inventory;

import net.minecraft.entity.player.EntityPlayer;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public abstract class ContainerOneLine<T extends InventoryOneLine<?>> extends ContainerBasic<T> {
	
	// Constants :
	
	public static final int OUT_OF_VIEW = -1000;
	
	// Constructors :
	
	protected ContainerOneLine(EntityPlayer player, T inventory) {
		super(player, inventory);
	}
	
	// Methods :
	
	@Override
	protected void init() {
		int inventorySize = this.inventory.getSafeSizeInventory();
		int nbNormalSlots = this.inventory.getSafeNbNormalSlots();
		int slotSize = this.slotSizePlusMargin();
		int firstSlotXPos = this.firstSlotXPos() + this.borderSize();
		int firstSlotYPos = this.firstSlotYPos() + this.borderSize();
		
		if(nbNormalSlots == inventorySize) {
			firstSlotXPos += ((9 - inventorySize) * slotSize) / 2;
			
			for(int i = 0; i < inventorySize; i++) {
				this.addSlotToContainer(this.getNewSlot(i, firstSlotXPos + (i * slotSize), firstSlotYPos));
			}
		}
		else {
			for(int i = 0; i < nbNormalSlots; i++) {
				this.addSlotToContainer(this.getNewSlot(i, firstSlotXPos + (i * slotSize), firstSlotYPos));
			}
			
			firstSlotXPos += (9 - inventorySize) * slotSize;
			
			for(int i = nbNormalSlots; i < inventorySize; i++) {
				this.addSlotToContainer(this.getNewSlot(i, firstSlotXPos + (i * slotSize), firstSlotYPos));
			}
		}
	}
	
	@Override
	public int containerHeight() {
		return this.toolBarHeight();
	}
	
}
