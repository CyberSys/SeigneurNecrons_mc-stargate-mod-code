package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityGuiContainer extends TileEntityStargate implements IInventory {
	
	/**
	 * Checks that the given number is a valid index for this inventory.
	 * @param index - the number to check.
	 * @return true if the number is a valid index, else false.
	 */
	protected boolean isCorrectIndex(int index) {
		return index >= 0 && index < this.getSizeInventory();
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int nb) {
		ItemStack itemStack = this.getStackInSlot(index);
		if(itemStack != null) {
			if(nb >= itemStack.stackSize) {
				this.setInventorySlotContents(index, null);
				return itemStack;
			}
			else {
				itemStack.stackSize -= nb;
				return new ItemStack(itemStack.itemID, nb, itemStack.getItemDamage());
			}
		}
		
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if(this.isCorrectIndex(index)) {
			ItemStack itemStack = this.getStackInSlot(index);
			this.setInventorySlotContents(index, null);
			return itemStack;
		}
		
		return null;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (entityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D);
	}
	
	@Override
	public void openChest() {
		// Nothing to do.
	}
	
	@Override
	public void closeChest() {
		// Nothing to do.
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		
		NBTTagList itemListTag = par1NBTTagCompound.getTagList("Items");
		
		for(int i = 0; i < itemListTag.tagCount(); ++i) {
			NBTTagCompound itemTag = (NBTTagCompound) itemListTag.tagAt(i);
			int index = itemTag.getByte("Slot") & 255;
			this.setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(itemTag));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		
		NBTTagList itemListTag = new NBTTagList();
		
		for(int i = 0; i < this.getSizeInventory(); ++i) {
			ItemStack itemStack = this.getStackInSlot(i);
			if(itemStack != null) {
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("Slot", (byte) i);
				itemStack.writeToNBT(itemTag);
				itemListTag.appendTag(itemTag);
			}
		}
		
		par1NBTTagCompound.setTag("Items", itemListTag);
	}
	
}
