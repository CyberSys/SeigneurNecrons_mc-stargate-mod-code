package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seigneurnecron.minecraftmods.stargate.inventory.ContainerStuffLevelUpTable;

/**
 * @author Seigneur Necron
 */
public class TileEntityStuffLevelUpTable extends TileEntityStargateGuiContainer {
	
	public static final String INV_NAME = "container.stuffLevelUpTable";
	
	/**
	 * The unique inventory slot, where you can put an item to enchant.
	 */
	private ItemStack stuff;
	
	/**
	 * A hidden slot which contains an enchanted book with all available enchantments.
	 */
	private ItemStack tmp = new ItemStack(Item.enchantedBook);
	
	/**
	 * The container binded with that tile entity.
	 */
	public ContainerStuffLevelUpTable container;
	
	/**
	 * Updates the stuff slot.
	 * @param stuff - the new item to put in the slot.
	 */
	private void setStuff(ItemStack stuff) {
		if(!ItemStack.areItemStacksEqual(this.stuff, stuff)) {
			this.stuff = stuff;
			this.onInventoryChanged();
		}
	}
	
	/**
	 * Updates the hidden slot.
	 * @param tmp - the new item to put in the slot.
	 */
	private void setTmp(ItemStack tmp) {
		if(!ItemStack.areItemStacksEqual(this.tmp, tmp)) {
			this.tmp = tmp;
			if(this.worldObj.isRemote) {
				this.container.updateEnchantments();
			}
		}
	}
	
	@Override
	public String getInvName() {
		return INV_NAME;
	}
	
	@Override
	public int getSizeInventory() {
		return 2;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		if(index == 0) {
			return this.stuff;
		}
		if(index == 1) {
			return this.tmp;
		}
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if(index == 0) {
			this.setStuff(itemStack);
		}
		else if(index == 1) {
			this.setTmp(itemStack);
		}
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		if(this.container != null) {
			this.container.onCraftMatrixChanged(this);
		}
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack) {
		return true;
	}
	
}
