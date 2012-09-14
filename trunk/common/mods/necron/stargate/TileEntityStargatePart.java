package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public abstract class TileEntityStargatePart extends TileEntityStargate {
	
	protected int xGate = 0;
	protected int yGate = 0;
	protected int zGate = 0;
	protected boolean partOfGate = false;
	
	/**
	 * Retourne la coordonnée en X de la porte à laquelle est lié ce bloc.
	 * @return la coordonnée en X de la porte à laquelle est lié ce bloc.
	 */
	public int getXGate() {
		return xGate;
	}
	
	/**
	 * Retourne la coordonnée en Y de la porte à laquelle est lié ce bloc.
	 * @return la coordonnée en Y de la porte à laquelle est lié ce bloc.
	 */
	public int getYGate() {
		return yGate;
	}
	
	/**
	 * Retourne la coordonnée en Z de la porte à laquelle est lié ce bloc.
	 * @return la coordonnée en Z de la porte à laquelle est lié ce bloc.
	 */
	public int getZGate() {
		return zGate;
	}
	
	/**
	 * Indique si le block appartient à une porte.
	 * @return true si le block appartient à une porte, false sinon.
	 */
	public boolean isPartOfGate() {
		return this.partOfGate;
	}
	
	/**
	 * Signal à ce block s'il appartient à une porte et previent les clients du changement.
	 * @param partOfGate - true si le block appartient à une porte, false sinon.
	 */
	protected void setPartOfGate(boolean partOfGate) {
		this.partOfGate = partOfGate;
		this.onInventoryChanged();
		this.updateClients();
	}
	
	/**
	 * Lie ce bloc à une porte.
	 * @param x - la coordonnée en X du chevron maitre de la porte.
	 * @param y - la coordonnée en Y du chevron maitre de la porte.
	 * @param z - la coordonnée en Z du chevron maitre de la porte.
	 */
	public void setGate(int x, int y, int z) {
		this.xGate = x;
		this.yGate = y;
		this.zGate = z;
		this.setPartOfGate(true);
	}
	
	/**
	 * Signal à ce block qu'il n'appartient plus à une porte.
	 */
	public void breakGate() {
		this.setPartOfGate(false);
	}
	
	public int getGateOrientation() {
		TileEntityMasterChevron gate = this.getMasterChevron();
		
		if(gate != null) {
			return gate.getBlockMetadata();
		}
		
		return 0;
	}
	
	/**
	 * Recupère la tile entity du chevron maitre de la porte à laquelle apartient ce block.
	 * @return la tile entity du chevron maitre de la porte à laquelle apartient ce block.
	 */
	public TileEntityMasterChevron getMasterChevron() {
		if(!this.isPartOfGate()) {
			return null;
		}
		
		TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
		
		if(tileEntity == null || !(tileEntity instanceof TileEntityMasterChevron)) {
			return null;
		}
		
		return (TileEntityMasterChevron) tileEntity;
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.xGate = par1NBTTagCompound.getInteger("xGate");
		this.yGate = par1NBTTagCompound.getInteger("yGate");
		this.zGate = par1NBTTagCompound.getInteger("zGate");
		this.partOfGate = par1NBTTagCompound.getBoolean("partOfGate");
	}
	
	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("xGate", this.xGate);
		par1NBTTagCompound.setInteger("yGate", this.yGate);
		par1NBTTagCompound.setInteger("zGate", this.zGate);
		par1NBTTagCompound.setBoolean("partOfGate", this.partOfGate);
	}
	
	/**
	 * Enregistre les données de la tileEntity dans une List de Byte, dans le but de créer un packet.
	 * @return les données de la tileEntity sous la forme d'une List de Byte.
	 */
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.xGate);
		writeInt(list, this.yGate);
		writeInt(list, this.zGate);
		writeBoolean(list, this.partOfGate);
		
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
			this.xGate = readInt(list);
			this.yGate = readInt(list);
			this.zGate = readInt(list);
			this.partOfGate = readBoolean(list);
			return true;
		}
		return false;
	}
	
	/**
	 * Retourne une représentation textuelle de cette tile entity.
	 */
	@Override
	public String toString() {
		return ("[] TileEntityStargate[xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",partOfGate" + this.partOfGate + "]");
	}
	
}
