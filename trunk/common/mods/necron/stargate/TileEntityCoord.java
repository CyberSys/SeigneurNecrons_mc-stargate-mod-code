package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;

public abstract class TileEntityCoord extends TileEntityGui {
	
	/**
	 * La coordonn�e en X de la destination.
	 */
	protected int xDest = 0;
	
	/**
	 * La coordonn�e en Y de la destination.
	 */
	protected int yDest = 0;
	
	/**
	 * La coordonn�e en Z de la destination.
	 */
	protected int zDest = 0;
	
	/**
	 * Retourne la coordonn�e en X de la destination.
	 * @return la coordonn�e en X de la destination.
	 */
	public int getXDest() {
		return xDest;
	}
	
	/**
	 * Retourne la coordonn�e en Y de la destination.
	 * @return la coordonn�e en Y de la destination.
	 */
	public int getYDest() {
		return yDest;
	}
	
	/**
	 * Retourne la coordonn�e en Z de la destination.
	 * @return la coordonn�e en Z de la destination.
	 */
	public int getZDest() {
		return zDest;
	}
	
	/**
	 * Positionne la coordonn�e en X de la destination.
	 * @param xDest - la nouvelle coordonn�e en X de la destination.
	 */
	public void setXDest(int xDest) {
		this.xDest = xDest;
	}
	
	/**
	 * Positionne la coordonn�e en Y de la destination.
	 * @param yDest - la nouvelle coordonn�e en Y de la destination.
	 */
	public void setYDest(int yDest) {
		this.yDest = yDest;
	}
	
	/**
	 * Positionne la coordonn�e en Z de la destination.
	 * @param zDest - la nouvelle coordonn�e en Z de la destination.
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
	 * Enregistre les donn�es de la tileEntity dans une List de Byte, dans le but de cr�er un packet.
	 * @return les donn�es de la tileEntity sous la forme d'une List de Byte.
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
	 * Charge les donn�es de la tileEntity depuis une LinkedList de Byte.
	 * @param list - la LinkedList de Byte contenant les donn�es � charger.
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
