package seigneurnecron.minecraftmods.stargate.inventory;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargateControl;
import seigneurnecron.minecraftmods.stargate.tileentity.console.ConsoleStargateFactory;

/**
 * @author Seigneur Necron
 */
public class InventoryStargateFactory extends InventoryConsolePanel<ConsoleStargateFactory> {
	
	// Constants :
	
	public static final String INV_NAME = "container.stargateFactory";
	public static final int NAQUADAH_ALLOY = 0;
	public static final int CHEVRON = 1;
	public static final int STARGATE_CONTROL = 2;
	public static final int FAST_STARGATE = 3;
	
	// Fields :
	
	/**
	 * The slots of this inventory.
	 */
	private final ItemStack[] itemStacks;
	
	private final int[] blockIds;
	
	private final int[] maxStackSizes;
	
	// Constructors :
	
	public InventoryStargateFactory(TileEntityConsoleBase tileEntity, ConsoleStargateFactory console) {
		super(tileEntity, console);
		this.itemStacks = new ItemStack[this.getSizeInventory()];
		this.blockIds = new int[] {StargateMod.block_naquadahAlloy.blockID, StargateMod.block_chevronOff.blockID, StargateMod.block_stargateControl.blockID, StargateMod.block_fastStargate.blockID};
		this.maxStackSizes = new int[] {TileEntityStargateControl.NB_NAQUADAH_ALLOY_BLOCKS, TileEntityStargateControl.NB_CHEVRON, 1, 64};
	}
	
	// Methods :
	
	/**
	 * Indicates whether the materials required to craft the fast stargate block are in the slots.
	 * @return true if the materials are in the slots, else false.
	 */
	public boolean isReadyToCraft() {
		int lastIndex = this.getSizeInventory() - 1;
		
		for(int i = 0; i <= lastIndex; i++) {
			ItemStack itemStack = this.itemStacks[i];
			boolean isItemValid = (itemStack == null) || this.isItemValidForSlot(i, itemStack);
			boolean isStackSizeValid = (i == lastIndex) == ((itemStack == null) || (itemStack.stackSize < this.maxStackSizes[i]));
			
			if(!(isItemValid && isStackSizeValid)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Craft a fast stargate block using the materials in the slots.
	 * @return true if the fast stargate block was succesfully crafted, false if some materials are missing.
	 */
	public boolean craft() {
		if(this.isReadyToCraft()) {
			int lastIndex = this.getSizeInventory() - 1;
			
			for(int i = 0; i < lastIndex; i++) {
				ItemStack itemStack = this.itemStacks[i];
				int required = this.maxStackSizes[i];
				
				if(itemStack.stackSize > required) {
					itemStack.stackSize -= required;
				}
				else {
					this.itemStacks[i] = null;
				}
			}
			
			if(this.itemStacks[lastIndex] == null) {
				this.itemStacks[lastIndex] = new ItemStack(StargateMod.block_fastStargate);
			}
			else {
				this.itemStacks[lastIndex].stackSize++;
			}
			
			this.onInventoryChanged();
			return true;
		}
		
		return false;
	}
	
	@Override
	public int getNbNormalSlots() {
		return 3;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public int getInventoryStackLimit(int index) {
		if(index >= 0 && index < this.itemStacks.length) {
			return this.maxStackSizes[index];
		}
		
		return this.getInventoryStackLimit();
	}
	
	@Override
	public String getInvName() {
		return INV_NAME;
	}
	
	@Override
	public int getSizeInventory() {
		return 4;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		if(index >= 0 && index < this.itemStacks.length) {
			return this.itemStacks[index];
		}
		
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index >= 0 && index < this.itemStacks.length && !ItemStack.areItemStacksEqual(itemStack, this.itemStacks[index])) {
			this.itemStacks[index] = itemStack;
			this.onInventoryChanged();
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemStack) {
		if(index >= 0 && index < this.itemStacks.length) {
			return (itemStack.getItem() instanceof ItemBlock) && (((ItemBlock) itemStack.getItem()).getBlockID() == this.blockIds[index]);
		}
		
		return false;
	}
	
}
