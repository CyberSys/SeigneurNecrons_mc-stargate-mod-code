package seigneurnecron.minecraftmods.stargate.tileentity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateOrientation;

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
		this.setChanged();
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
		this.update();
	}
	
	/**
	 * Informs the block it no longer belongs to a gate.
	 */
	public void breakGate() {
		this.setPartOfGate(false);
		this.update();
	}
	
	/**
	 * Returns the gate metadata.
	 * @return the gate metadata.
	 */
	public int getGateMetadata() {
		if(this.isPartOfGate()) {
			return this.worldObj.getBlockMetadata(this.xGate, this.yGate, this.zGate);
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
		if(this.isPartOfGate()) {
			TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
			
			if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
				return (TileEntityStargateControl) tileEntity;
			}
		}
		
		return null;
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
	protected void getEntityData(DataOutputStream output) throws IOException {
		super.getEntityData(output);
		
		output.writeInt(this.xGate);
		output.writeInt(this.yGate);
		output.writeInt(this.zGate);
		output.writeBoolean(this.partOfGate);
	}
	
	@Override
	protected void loadEntityData(DataInputStream input) throws IOException {
		super.loadEntityData(input);
		
		this.xGate = input.readInt();
		this.yGate = input.readInt();
		this.zGate = input.readInt();
		this.partOfGate = input.readBoolean();
	}
	
}
