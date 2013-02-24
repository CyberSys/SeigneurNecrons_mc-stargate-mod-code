package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntityCoord extends TileEntityGuiScreen {
	
	/**
	 * La coordonnee en X de la destination.
	 */
	protected int xDest = 0;
	
	/**
	 * La coordonnee en Y de la destination.
	 */
	protected int yDest = 0;
	
	/**
	 * La coordonnee en Z de la destination.
	 */
	protected int zDest = 0;
	
	/**
	 * Retourne la coordonnee en X de la destination.
	 * @return la coordonnee en X de la destination.
	 */
	public int getXDest() {
		return this.xDest;
	}
	
	/**
	 * Retourne la coordonnee en Y de la destination.
	 * @return la coordonnee en Y de la destination.
	 */
	public int getYDest() {
		return this.yDest;
	}
	
	/**
	 * Retourne la coordonnee en Z de la destination.
	 * @return la coordonnee en Z de la destination.
	 */
	public int getZDest() {
		return this.zDest;
	}
	
	/**
	 * Positionne la coordonnee en X de la destination.
	 * @param xDest - la nouvelle coordonnee en X de la destination.
	 */
	public void setXDest(int xDest) {
		this.xDest = xDest;
	}
	
	/**
	 * Positionne la coordonnee en Y de la destination.
	 * @param yDest - la nouvelle coordonnee en Y de la destination.
	 */
	public void setYDest(int yDest) {
		this.yDest = yDest;
	}
	
	/**
	 * Positionne la coordonnee en Z de la destination.
	 * @param zDest - la nouvelle coordonnee en Z de la destination.
	 */
	public void setZDest(int zDest) {
		this.zDest = zDest;
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
