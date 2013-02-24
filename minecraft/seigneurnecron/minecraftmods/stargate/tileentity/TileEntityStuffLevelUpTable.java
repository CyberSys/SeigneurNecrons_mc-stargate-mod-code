package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;

public class TileEntityStuffLevelUpTable extends TileEntityGuiContainer {
	
	/**
	 * L'unique emplacement de l'inventaire, ou on peut poser un item a enchanter.
	 */
	private ItemStack stuff;
	
	/**
	 * Met a jour l'emplacement d'enchantement (previens les clients).
	 * @param stuff - le nouvel objet a mettre dans l'emplacement.
	 */
	private void setStuff(ItemStack stuff) {
		if(!ItemStack.areItemStacksEqual(this.stuff, stuff)) {
			this.stuff = stuff;
			this.updateClients();
		}
	}
	
	@Override
	public String getInvName() {
		return "container.stuffLevelUpTable";
	}
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0 ? this.stuff : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stuff) {
		if(index == 0) {
			this.setStuff(stuff);
		}
	}
	
	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote) {
			// TODO - a faire.
		}
		
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		// TODO - virer la methode si rien d'autre a mettre dedans.
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		// TODO - virer la methode si rien d'autre a mettre dedans.
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, (this.stuff != null) ? this.stuff.itemID : -1);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			
			int itemId = readInt(list);
			Item item = (itemId > 0 && itemId < Item.itemsList.length) ? Item.itemsList[itemId] : null;
			this.setStuff((item != null) ? new ItemStack(item) : null);
			
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean isCorrectId(int id) {
		return(super.isCorrectId(id) || id == StargatePacketHandler.packetId_CloseGuiStuffLevelUpTable);
	}
	
	@Override
	public String toString() {
		return("TileEntityStuffLevelUpTable[]");
	}
	
}
