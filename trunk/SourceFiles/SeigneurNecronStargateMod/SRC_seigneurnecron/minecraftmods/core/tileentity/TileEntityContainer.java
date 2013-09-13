package seigneurnecron.minecraftmods.core.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityContainer extends TileEntityCommand implements IInventory {
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
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
		if(index >= 0 && index < this.getSizeInventory()) {
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
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList itemListTag = compound.getTagList("Items");
		
		for(int i = 0; i < itemListTag.tagCount(); ++i) {
			NBTTagCompound itemTag = (NBTTagCompound) itemListTag.tagAt(i);
			int index = itemTag.getByte("Slot") & 255;
			this.setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(itemTag));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
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
		
		compound.setTag("Items", itemListTag);
	}
	
}
