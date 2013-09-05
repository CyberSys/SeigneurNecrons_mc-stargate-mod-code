package seigneurnecron.minecraftmods.stargate.tileentity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.enums.GateState;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityBaseStargateConsole extends TileEntityBase {
	
	/**
	 * The maximum range in which a DHD can connect to a gate.
	 */
	public static final int MAX_RANGE = 20;
	
	/**
	 * Indicates whether the DHD is connected to a gate.
	 */
	private boolean linkedToGate = false;
	
	/**
	 * The X coordinate of the gate connected to the DHD.
	 */
	private int xGate = 0;
	
	/**
	 * The Y coordinate of the gate connected to the DHD.
	 */
	private int yGate = 0;
	
	/**
	 * The Z coordinate of the gate connected to the DHD.
	 */
	private int zGate = 0;
	
	/**
	 * Indicates whether the DHD is connected to a gate.
	 * @return true if the DHD is connected to a gate, else false.
	 */
	public boolean isLinkedToGate() {
		return this.linkedToGate;
	}
	
	/**
	 * Returns the X coordinate of the gate connected to the DHD.
	 * @return the X coordinate of the gate connected to the DHD.
	 */
	public int getXGate() {
		return this.xGate;
	}
	
	/**
	 * Returns the Y coordinate of the gate connected to the DHD.
	 * @return the Y coordinate of the gate connected to the DHD.
	 */
	public int getYGate() {
		return this.yGate;
	}
	
	/**
	 * Returns the Z coordinate of the gate connected to the DHD.
	 * @return the Z coordinate of the gate connected to the DHD.
	 */
	public int getZGate() {
		return this.zGate;
	}
	
	/**
	 * Updates the the DHD state (inform clients).
	 * @param linkedToGate - true if the DHD is connected to a gate, else false.
	 */
	private void setLinkedToGate(boolean linkedToGate) {
		this.linkedToGate = linkedToGate;
		this.setChanged();
		this.update();
	}
	
	/**
	 * Updates the gate position.
	 * @param x - the gate X coordinate.
	 * @param y - the gate Y coordinate.
	 * @param z - the gate Z coordinate.
	 */
	private void setGatePos(int x, int y, int z) {
		this.xGate = x;
		this.yGate = y;
		this.zGate = z;
	}
	
	/**
	 * Searches for stargates in the area and registers the coordinates of the nearest stargate.
	 */
	private void searchGate() {
		// No need to search if the DHD is already connected to a stargate.
		if(!this.linkedToGate && this.isIntact()) {
			// Searches all the control units within range.
			LinkedList<ChunkPosition> controlUnitsList = new LinkedList<ChunkPosition>();
			
			// Searches in a cube with a side length of MAX_RANGE.
			for(int i = -MAX_RANGE; i <= MAX_RANGE; ++i) {
				for(int j = -MAX_RANGE; j <= MAX_RANGE; ++j) {
					for(int k = -MAX_RANGE; k <= MAX_RANGE; ++k) {
						int x = this.xCoord + k;
						int y = this.yCoord + i;
						int z = this.zCoord + j;
						
						// If the block is a control unit, adds it to the list of stargates that can be linked to the DHD.
						if(this.worldObj.getBlockId(x, y, z) == StargateMod.block_stargateControl.blockID) {
							TileEntity tileEntity = this.worldObj.getBlockTileEntity(x, y, z);
							
							if(tileEntity instanceof TileEntityStargateControl && ((TileEntityStargateControl) tileEntity).getState() != GateState.BROKEN) {
								controlUnitsList.add(new ChunkPosition(x, y, z));
							}
						}
					}
				}
			}
			
			// If at least one control unit was found.
			if(controlUnitsList.size() > 0) {
				// Takes the first control unit.
				ChunkPosition pos = controlUnitsList.get(0);
				this.setGatePos(pos.x, pos.y, pos.z);
				
				// Goes through the list, looking for the nearest control unit.
				for(int i = 1; i < controlUnitsList.size(); i++) {
					pos = controlUnitsList.get(i);
					
					// If the new control unit is closer than the previous one, links the DHD with the new control unit.
					if(this.squaredDistance(pos.x, pos.y, pos.z) < this.squaredDistance(this.xGate, this.yGate, this.zGate)) {
						this.setGatePos(pos.x, pos.y, pos.z);
					}
				}
				
				// Updates the DHD state.
				this.setLinkedToGate(true);
			}
		}
	}
	
	/**
	 * Returns the squared distance between this DHD and the block at the given coordinates.
	 * @param x - the X coordinate of the block that we want to know the distance.
	 * @param y - the Y coordinate of the block that we want to know the distance.
	 * @param z - the Z coordinate of the block that we want to know the distance.
	 * @return the squared distance between the DHD and the block.
	 */
	private double squaredDistance(int x, int y, int z) {
		return Math.pow(this.xCoord - x, 2) + Math.pow(this.yCoord - y, 2) + Math.pow(this.zCoord - z, 2);
	}
	
	/**
	 * Return the tile entity of the stargate linked to this console.
	 * @return the tile entity of the stargate linked to this console if it exists, else null.
	 */
	public TileEntityStargateControl getStargateControl() {
		this.searchGate();
		
		if(this.linkedToGate) {
			TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
			if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
				return (TileEntityStargateControl) tileEntity;
			}
			else {
				this.setLinkedToGate(false);
				return this.getStargateControl();
			}
		}
		
		return null;
	}
	
	/**
	 * Called when the stargate linked to this console is destroyed.
	 */
	public void onStargateDestroyed(TileEntityStargateControl stargate) {
		this.setLinkedToGate(false);
	}
	
	/**
	 * Informs the stargate that the console has been destroyed.
	 * @param tileEntity - the stargate tile entity.
	 */
	protected abstract void onStargateConsoleDestroyed(TileEntityStargateControl stargate);
	
	@Override
	public final void onConsoleDestroyed() {
		TileEntityStargateControl stargate = this.getStargateControl();
		
		if(stargate != null) {
			onStargateConsoleDestroyed(stargate);
			this.setLinkedToGate(false);
		}
	}
	
	@Override
	public boolean isActive() {
		return this.isLinkedToGate();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.linkedToGate = par1NBTTagCompound.getBoolean("linkedToGate");
		this.xGate = par1NBTTagCompound.getInteger("xGate");
		this.yGate = par1NBTTagCompound.getInteger("yGate");
		this.zGate = par1NBTTagCompound.getInteger("zGate");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("linkedToGate", this.linkedToGate);
		par1NBTTagCompound.setInteger("xGate", this.xGate);
		par1NBTTagCompound.setInteger("yGate", this.yGate);
		par1NBTTagCompound.setInteger("zGate", this.zGate);
	}
	
	@Override
	protected void getEntityData(DataOutputStream output) throws IOException {
		super.getEntityData(output);
		
		output.writeBoolean(this.linkedToGate);
		output.writeInt(this.xGate);
		output.writeInt(this.yGate);
		output.writeInt(this.zGate);
	}
	
	@Override
	protected void loadEntityData(DataInputStream input) throws IOException {
		super.loadEntityData(input);
		
		this.linkedToGate = input.readBoolean();
		this.xGate = input.readInt();
		this.yGate = input.readInt();
		this.zGate = input.readInt();
	}
	
}
