package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityStargatePart extends TileEntityStargate {
	
	protected int xGate = 0;
	protected int yGate = 0;
	protected int zGate = 0;
	protected boolean partOfGate = false;
	
	/**
	 * Retourne la coordonnee en X de la porte a laquelle est lie ce bloc.
	 * @return la coordonnee en X de la porte a laquelle est lie ce bloc.
	 */
	public int getXGate() {
		return this.xGate;
	}
	
	/**
	 * Retourne la coordonnee en Y de la porte a laquelle est lie ce bloc.
	 * @return la coordonnee en Y de la porte a laquelle est lie ce bloc.
	 */
	public int getYGate() {
		return this.yGate;
	}
	
	/**
	 * Retourne la coordonnee en Z de la porte a laquelle est lie ce bloc.
	 * @return la coordonnee en Z de la porte a laquelle est lie ce bloc.
	 */
	public int getZGate() {
		return this.zGate;
	}
	
	/**
	 * Indique si le block appartient a une porte.
	 * @return true si le block appartient a une porte, false sinon.
	 */
	public boolean isPartOfGate() {
		return this.partOfGate;
	}
	
	/**
	 * Signal a ce block s'il appartient a une porte et previent les clients du changement.
	 * @param partOfGate - true si le block appartient a une porte, false sinon.
	 */
	protected void setPartOfGate(boolean partOfGate) {
		this.partOfGate = partOfGate;
		this.onInventoryChanged();
		this.updateClients();
	}
	
	/**
	 * Lie ce bloc a une porte.
	 * @param x - la coordonnee en X du chevron maitre de la porte.
	 * @param y - la coordonnee en Y du chevron maitre de la porte.
	 * @param z - la coordonnee en Z du chevron maitre de la porte.
	 */
	public void setGate(int x, int y, int z) {
		this.xGate = x;
		this.yGate = y;
		this.zGate = z;
		this.setPartOfGate(true);
	}
	
	/**
	 * Signal a ce block qu'il n'appartient plus a une porte.
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
	 * Recupere la tile entity du chevron maitre de la porte a laquelle apartient ce block.
	 * @return la tile entity du chevron maitre de la porte a laquelle apartient ce block.
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
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.xGate = par1NBTTagCompound.getInteger("xGate");
		this.yGate = par1NBTTagCompound.getInteger("yGate");
		this.zGate = par1NBTTagCompound.getInteger("zGate");
		this.partOfGate = par1NBTTagCompound.getBoolean("partOfGate");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("xGate", this.xGate);
		par1NBTTagCompound.setInteger("yGate", this.yGate);
		par1NBTTagCompound.setInteger("zGate", this.zGate);
		par1NBTTagCompound.setBoolean("partOfGate", this.partOfGate);
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.xGate);
		writeInt(list, this.yGate);
		writeInt(list, this.zGate);
		writeBoolean(list, this.partOfGate);
		
		return list;
	}
	
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
	
	@Override
	public String toString() {
		return ("TileEntityStargate[xGate: " + this.xGate + ",yGate " + this.yGate + ",zGate: " + this.zGate + ",partOfGate" + this.partOfGate + "]");
	}
	
}
