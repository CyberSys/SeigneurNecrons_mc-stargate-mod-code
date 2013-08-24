package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Seigneur Necron
 */
public class TileEntityBaseDhd extends TileEntityBaseStargateConsole {
	
	public static final String INV_NAME = "container.dhd";
	
	/**
	 * The destination X coordinate.
	 */
	private int xDest = 0;
	
	/**
	 * The destination Y coordinate.
	 */
	private int yDest = 0;
	
	/**
	 * The destination Z coordinate.
	 */
	private int zDest = 0;
	
	/**
	 * Returns the destination X coordinate.
	 * @return the destination X coordinate.
	 */
	public int getXDest() {
		return this.xDest;
	}
	
	/**
	 * Returns the destination Y coordinate.
	 * @return the destination Y coordinate.
	 */
	public int getYDest() {
		return this.yDest;
	}
	
	/**
	 * Returns the destination Z coordinate.
	 * @return the destination Z coordinate.
	 */
	public int getZDest() {
		return this.zDest;
	}
	
	/**
	 * Sets the destination X coordinate.
	 * @param xDest - the new destination X coordinate.
	 */
	public void setXDest(int xDest) {
		this.xDest = xDest;
	}
	
	/**
	 * Sets the destination Y coordinate.
	 * @param yDest - the new destination Y coordinate.
	 */
	public void setYDest(int yDest) {
		this.yDest = yDest;
	}
	
	/**
	 * Sets the destination Z coordinate.
	 * @param zDest - the new destination Z coordinate.
	 */
	public void setZDest(int zDest) {
		this.zDest = zDest;
	}
	
	/**
	 * Activates the stargate connected to this DHD.
	 */
	@Override
	protected void sendCustomCommand(TileEntityStargateControl tileEntity) {
		tileEntity.onDhdActivation(this.xDest, this.yDest, this.zDest);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.xDest = par1NBTTagCompound.getInteger("xDest");
		this.yDest = par1NBTTagCompound.getInteger("yDest");
		this.zDest = par1NBTTagCompound.getInteger("zDest");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("xDest", this.xDest);
		par1NBTTagCompound.setInteger("yDest", this.yDest);
		par1NBTTagCompound.setInteger("zDest", this.zDest);
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.xDest);
		writeInt(list, this.yDest);
		writeInt(list, this.zDest);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.xDest = readInt(list);
			this.yDest = readInt(list);
			this.zDest = readInt(list);
			return true;
		}
		return false;
	}
	
}
