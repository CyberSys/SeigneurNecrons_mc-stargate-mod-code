package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class TileEntityDetector extends TileEntityGuiScreen {
	
	public static final String INV_NAME = "container.detector";
	
	/**
	 * Minimal detection range.
	 */
	private static final int MIN_RANGE = 1;
	
	/**
	 * Maximal detection range.
	 */
	private static final int MAX_RANGE = 10;
	
	/**
	 * Current detection range.
	 */
	private int range = 2;
	
	/**
	 * Indicates whether the detector provides a redstone signal when it detects a player or when it doesn't detects any player.
	 */
	private boolean inverted = false;
	
	/**
	 * Indicates whether the detector is currently providing a redstone signal.
	 */
	private boolean providingPower = false;
	
	/**
	 * Returns the range in which the detector can detect a player.
	 * @return the detection range.
	 */
	public int getRange() {
		return this.range;
	}
	
	/**
	 * Indicates whether the detector provides a redstone signal when it detects a player or when it doesn't detects any player.
	 * @return true if the detector provides a redstone signal when it doesn't detects any player, false if it provides a redstone signal when it detects a player.
	 */
	public boolean isInverted() {
		return this.inverted;
	}
	
	/**
	 * Indicates whether the detector is currently providing a redstone signal.
	 * @return true if the detector is currently providing a redstone signal, else false.
	 */
	public boolean isProvidingPower() {
		return this.providingPower;
	}
	
	/**
	 * Sets the detection range of the detector.
	 * @param range - the new detection range.
	 */
	public void setRange(int range) {
		if(range < MIN_RANGE) {
			this.range = MIN_RANGE;
		}
		else if(range > MAX_RANGE) {
			this.range = MAX_RANGE;
		}
		else {
			this.range = range;
		}
	}
	
	/**
	 * Sets the state of the detector.
	 * @param inverted - true if the output must be inverted, else false.
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	/**
	 * Sets the output of the detector.
	 * @param providingPower - true if the detector must provide a redstone signal, else false.
	 */
	private void setProvidingPower(boolean providingPower) {
		if(this.providingPower != providingPower) {
			this.providingPower = providingPower;
			this.updateClients();
			this.updateNeighborBlocks();
		}
	}
	
	/**
	 * Checks if there is a player in detection range.
	 * @return true if there is a player in detection range, else false.
	 */
	public boolean anyPlayerInRange() {
		return(this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.range) != null);
	}
	
	/**
	 * Updates neighboring blocks.
	 */
	private void updateNeighborBlocks() {
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, StargateMod.block_detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord + 1, this.yCoord, this.zCoord, StargateMod.block_detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord - 1, this.yCoord, this.zCoord, StargateMod.block_detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord + 1, StargateMod.block_detector.blockID);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord - 1, StargateMod.block_detector.blockID);
	}
	
	/**
	 * This method is called at each tick, it scans the area looking for players.
	 */
	@Override
	public void updateEntity() {
		// If server side.
		if(!this.worldObj.isRemote) {
			this.setProvidingPower(this.inverted != this.anyPlayerInRange());
		}
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.range = par1NBTTagCompound.getInteger("range");
		this.inverted = par1NBTTagCompound.getBoolean("inverted");
		this.providingPower = par1NBTTagCompound.getBoolean("providingPower");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("range", this.range);
		par1NBTTagCompound.setBoolean("inverted", this.inverted);
		par1NBTTagCompound.setBoolean("providingPower", this.providingPower);
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.range);
		writeBoolean(list, this.inverted);
		writeBoolean(list, this.providingPower);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.range = readInt(list);
			this.inverted = readBoolean(list);
			this.providingPower = readBoolean(list);
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
}
