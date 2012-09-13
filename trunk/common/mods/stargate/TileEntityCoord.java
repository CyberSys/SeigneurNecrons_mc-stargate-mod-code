package mods.stargate;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;

public abstract class TileEntityCoord extends TileEntityGui {
	
	/**
	 * La coordonnée en X de la destination.
	 */
	protected int xDest = 0;
	
	/**
	 * La coordonnée en Y de la destination.
	 */
	protected int yDest = 0;
	
	/**
	 * La coordonnée en Z de la destination.
	 */
	protected int zDest = 0;
	
	/**
	 * Retourne la coordonnée en X de la destination.
	 * @return la coordonnée en X de la destination.
	 */
	public int getXDest() {
		return xDest;
	}
	
	/**
	 * Retourne la coordonnée en Y de la destination.
	 * @return la coordonnée en Y de la destination.
	 */
	public int getYDest() {
		return yDest;
	}
	
	/**
	 * Retourne la coordonnée en Z de la destination.
	 * @return la coordonnée en Z de la destination.
	 */
	public int getZDest() {
		return zDest;
	}
	
	/**
	 * Positionne la coordonnée en X de la destination.
	 * @param xDest - la nouvelle coordonnée en X de la destination.
	 */
	public void setXDest(int xDest) {
		this.xDest = xDest;
	}
	
	/**
	 * Positionne la coordonnée en Y de la destination.
	 * @param yDest - la nouvelle coordonnée en Y de la destination.
	 */
	public void setYDest(int yDest) {
		this.yDest = yDest;
	}
	
	/**
	 * Positionne la coordonnée en Z de la destination.
	 * @param zDest - la nouvelle coordonnée en Z de la destination.
	 */
	public void setZDest(int zDest) {
		this.zDest = zDest;
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.xDest = par1NBTTagCompound.getInteger("xDest");
		this.yDest = par1NBTTagCompound.getInteger("yDest");
		this.zDest = par1NBTTagCompound.getInteger("zDest");
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("xDest", this.xDest);
		par1NBTTagCompound.setInteger("yDest", this.yDest);
		par1NBTTagCompound.setInteger("zDest", this.zDest);
	}
	
	/**
	 * Enregistre les données de la tileEntity dans une List de Byte, dans le but de créer un packet.
	 * @return les données de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.xDest);
		writeInt(list, this.yDest);
		writeInt(list, this.zDest);
		
		return list;
	}
	
	/**
	 * Charge les données de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les données à charger.
	 * @return true si le chargement est un succes, false sinon.
	 */
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
