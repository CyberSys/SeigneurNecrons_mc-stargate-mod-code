package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import seigneurnecron.minecraftmods.stargate.enums.GateOrientation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Seigneur Necron
 */
public class TileEntityStargatePart extends TileEntityStargate {
	
	protected int xGate = 0;
	protected int yGate = 0;
	protected int zGate = 0;
	protected boolean partOfGate = false;
	
	/**
	 * Returns the X coordinate of the gate which this block belongs.
	 * @return the X coordinate of the gate which this block belongs.
	 */
	public int getXGate() {
		return this.xGate;
	}
	
	/**
	 * Returns the Y coordinate of the gate which this block belongs.
	 * @return the Y coordinate of the gate which this block belongs.
	 */
	public int getYGate() {
		return this.yGate;
	}
	
	/**
	 * Returns the Z coordinate of the gate which this block belongs.
	 * @return the Z coordinate of the gate which this block belongs.
	 */
	public int getZGate() {
		return this.zGate;
	}
	
	/**
	 * Indicates whether the block belongs to a gate.
	 * @return true if the block belongs to a gate, else false.
	 */
	public boolean isPartOfGate() {
		return this.partOfGate;
	}
	
	/**
	 * Updates the state of this block and informs the clients.
	 * @param partOfGate - true the block belongs to a gate, else false.
	 */
	protected void setPartOfGate(boolean partOfGate) {
		this.partOfGate = partOfGate;
		this.onInventoryChanged();
		this.updateClients();
	}
	
	/**
	 * Binds this bloc to a gate.
	 * @param x - the X coordinate of control unit of the gate.
	 * @param y - the Y coordinate of control unit of the gate.
	 * @param z - the Z coordinate of control unit of the gate.
	 */
	public void setGate(int x, int y, int z) {
		this.xGate = x;
		this.yGate = y;
		this.zGate = z;
		this.setPartOfGate(true);
	}
	
	/**
	 * Informs the block it no longer belongs to a gate.
	 */
	public void breakGate() {
		this.setPartOfGate(false);
	}
	
	/**
	 * Returns the gate metadata.
	 * @return the gate metadata.
	 */
	public int getGateMetadata() {
		TileEntityStargateControl gate = this.getGateControlUnit();
		
		if(gate != null) {
			return gate.getBlockMetadata();
		}
		
		return 0;
	}
	
	/**
	 * Returns the axis corresponding to the gate orientation.
	 * @return an axis (X_AXIS/Z_AXIS/ERROR).
	 */
	public GateOrientation getGateOrientation() {
		TileEntityStargateControl gate = this.getGateControlUnit();
		
		if(gate != null) {
			return gate.getGateOrientation();
		}
		
		return GateOrientation.ERROR;
	}
	
	/**
	 * Returns the tile entity of the control unit of the gate which this block belongs.
	 * @return the tile entity of the control unit of the gate which this block belongs.
	 */
	public TileEntityStargateControl getGateControlUnit() {
		if(!this.isPartOfGate()) {
			return null;
		}
		
		TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
		
		if(tileEntity == null || !(tileEntity instanceof TileEntityStargateControl)) {
			return null;
		}
		
		return (TileEntityStargateControl) tileEntity;
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
	
}
