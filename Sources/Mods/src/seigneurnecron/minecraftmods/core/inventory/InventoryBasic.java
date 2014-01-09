package seigneurnecron.minecraftmods.core.inventory;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Seigneur Necron
 */
public abstract class InventoryBasic<T extends TileEntity> implements IInventory {
	
	// Fields :
	
	public final T tileEntity;
	
	// Constructors :
	
	protected InventoryBasic(T tileEntity) {
		this.tileEntity = tileEntity;
	}
	
	// Methods :
	
	/**
	 * Indicates how many slots must be displayed in the gui, and droped when the container is destroyed. The default value is the size of the inventory.
	 * @return the number of slots that must be displayed in the gui.
	 */
	public int nbSlotToDisplay() {
		return this.getSizeInventory();
	}
	
	/**
	 * Drop all the items on the ground.
	 */
	public void dropContent() {
		Random rand = new Random();
		
		for(int i = 0; i < this.nbSlotToDisplay(); ++i) {
			ItemStack itemstack = this.getStackInSlot(i);
			
			if(itemstack != null) {
				float x1 = rand.nextFloat() * 0.8F + 0.1F;
				float y1 = rand.nextFloat() * 0.8F + 0.1F;
				float z1 = rand.nextFloat() * 0.8F + 0.1F;
				
				while(itemstack.stackSize > 0) {
					int k1 = rand.nextInt(21) + 10;
					
					if(k1 > itemstack.stackSize) {
						k1 = itemstack.stackSize;
					}
					
					itemstack.stackSize -= k1;
					EntityItem entityitem = new EntityItem(this.tileEntity.worldObj, this.tileEntity.xCoord + x1, this.tileEntity.yCoord + y1, this.tileEntity.zCoord + z1, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
					float f3 = 0.05F;
					entityitem.motionX = (float) rand.nextGaussian() * f3;
					entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float) rand.nextGaussian() * f3;
					this.tileEntity.worldObj.spawnEntityInWorld(entityitem);
				}
			}
		}
	}
	
	@Override
	public void onInventoryChanged() {
		this.tileEntity.onInventoryChanged();
	}
	
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
		return (this.tileEntity.worldObj.getBlockTileEntity(this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord) == this.tileEntity) && (entityPlayer.getDistanceSq(this.tileEntity.xCoord + 0.5D, this.tileEntity.yCoord + 0.5D, this.tileEntity.zCoord + 0.5D) <= 64.0D);
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
	public boolean isInvNameLocalized() {
		return false;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList itemListTag = compound.getTagList("Items");
		
		for(int i = 0; i < itemListTag.tagCount(); ++i) {
			NBTTagCompound itemTag = (NBTTagCompound) itemListTag.tagAt(i);
			int index = itemTag.getByte("Slot") & 255;
			this.setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(itemTag));
		}
	}
	
	public void writeToNBT(NBTTagCompound compound) {
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
