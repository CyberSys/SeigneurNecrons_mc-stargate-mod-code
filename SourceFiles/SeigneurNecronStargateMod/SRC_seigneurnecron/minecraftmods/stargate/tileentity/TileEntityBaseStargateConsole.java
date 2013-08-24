package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityBaseStargateConsole extends TileEntityBase {
	
	/**
	 * The maximum range in which a DHD can connect to a gate.
	 */
	private static final int MAX_RANGE = 20;
	
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
	 * Sets the state of the DHD (inform clients).
	 * @param linkedToGate - true if the DHD is connected to a gate, else false.
	 */
	private void setLinkedToGate(boolean linkedToGate) {
		this.linkedToGate = linkedToGate;
		this.updateClients();
	}
	
	/**
	 * Searches for stargates in the area and registers the coordinates of the nearest stargate.
	 */
	private void searchGate() {
		// No need to search if the DHD is already connected to a stargate.
		if(!this.linkedToGate) {
			// Searches all the control units within range.
			LinkedList<ChunkPosition> controlUnitsList = new LinkedList<ChunkPosition>();
			
			// Searches in a cube with a side length of MAX_RANGE.
			for(int i = -MAX_RANGE; i <= MAX_RANGE; ++i) {
				for(int j = -MAX_RANGE; j <= MAX_RANGE; ++j) {
					for(int k = -MAX_RANGE; k <= MAX_RANGE; ++k) {
						int x = this.xCoord + k;
						int y = this.yCoord + i;
						int z = this.zCoord + j;
						
						// If the block is a control unit, adds it to the list of the stargates that can be linked to the DHD.
						if(this.worldObj.getBlockId(x, y, z) == StargateMod.block_stargateControl.blockID) {
							controlUnitsList.add(new ChunkPosition(x, y, z));
						}
					}
				}
			}
			
			// If at least one control unit was found.
			if(controlUnitsList.size() > 0) {
				// Goes through the list, looking for the nearest chevron.
				for(ChunkPosition pos : controlUnitsList) {
					// If the DHD wasn't linked yet or if the new control unit is closer than the previous one.
					if(!this.linkedToGate || this.squaredDistance(pos.x, pos.y, pos.z) < this.squaredDistance(this.xGate, this.yGate, this.zGate)) {
						// Links the DHD with the new control unit.
						this.xGate = pos.x;
						this.yGate = pos.y;
						this.zGate = pos.z;
						this.setLinkedToGate(true);
					}
				}
			}
		}
	}
	
	/**
	 * Sends a command to the stargate connected to this console, if there is one.
	 */
	private final void sendCommandeToGate() {
		this.searchGate();
		
		if(this.linkedToGate) {
			TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
			if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
				sendCustomCommand((TileEntityStargateControl) tileEntity);
			}
			else {
				this.setLinkedToGate(false);
				this.sendCommandeToGate();
			}
		}
	}
	
	/**
	 * Sends a customisable command the stargate.
	 * @param tileEntity - the tile entity of the stargate.
	 */
	protected abstract void sendCustomCommand(TileEntityStargateControl tileEntity);
	
	/**
	 * Defines what happen when the console is destroyed.
	 */
	protected final void onStargateConsoleDestroyed() {
		if(this.linkedToGate) {
			TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xGate, this.yGate, this.zGate);
			if(tileEntity != null && tileEntity instanceof TileEntityStargateControl) {
				informStargateOfConsoleDestruction((TileEntityStargateControl) tileEntity);
			}
		}
	}
	
	/**
	 * Informs the stargate that the console has been destroyed.
	 * @param tileEntity - the tile entity of the stargate.
	 */
	protected void informStargateOfConsoleDestruction(TileEntityStargateControl tileEntity) {
		// Nothing to do here.
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
	
	@Override
	public final void activate(EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		this.sendCommandeToGate();
	}
	
	@Override
	public final void onConsoleDestroyed() {
		this.onStargateConsoleDestroyed();
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
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeBoolean(list, this.linkedToGate);
		writeInt(list, this.xGate);
		writeInt(list, this.yGate);
		writeInt(list, this.zGate);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.linkedToGate = readBoolean(list);
			this.xGate = readInt(list);
			this.yGate = readInt(list);
			this.zGate = readInt(list);
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
}
