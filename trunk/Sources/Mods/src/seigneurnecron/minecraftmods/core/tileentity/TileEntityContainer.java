package seigneurnecron.minecraftmods.core.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.core.inventory.InventoryBasic;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityContainer<T extends InventoryBasic> extends TileEntityCommand {
	
	// Fields :
	
	protected T inventory;
	
	// Constructors :
	
	public TileEntityContainer() {
		super();
		this.inventory = this.getNewInventory();
	}
	
	// Getters :
	
	public T getInventory() {
		return this.inventory;
	}
	
	// Methods :
	
	/**
	 * Returns a new inventory used to initialize this tile entity.
	 * @return a new inventory used to initialize this tile entity.
	 */
	protected abstract T getNewInventory();
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory.readFromNBT(compound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.inventory.writeToNBT(compound);
	}
	
}
