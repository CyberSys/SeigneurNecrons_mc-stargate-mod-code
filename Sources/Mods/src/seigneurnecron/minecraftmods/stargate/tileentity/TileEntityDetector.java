package seigneurnecron.minecraftmods.stargate.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class TileEntityDetector extends TileEntityStargate {
	
	// NBTTags names :
	
	private static final String RANGE = "range";
	private static final String INVERTED = "inverted";
	private static final String PROVIDING_POWER = "providingPower";
	
	// Constants :
	
	/**
	 * Minimal detection range.
	 */
	private static final int MIN_RANGE = 1;
	
	/**
	 * Maximal detection range.
	 */
	private static final int MAX_RANGE = 10;
	
	// Fields :
	
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
	
	// Getters :
	
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
	
	// Setters :
	
	/**
	 * Sets the detection range of the detector.
	 * @param range - the new detection range.
	 */
	public void setRange(int range) {
		if(range < MIN_RANGE) {
			range = MIN_RANGE;
		}
		else if(range > MAX_RANGE) {
			range = MAX_RANGE;
		}
		
		if(range != this.range) {
			this.range = range;
			this.setChanged();
		}
	}
	
	/**
	 * Sets the state of the detector.
	 * @param inverted - true if the output must be inverted, else false.
	 */
	public void setInverted(boolean inverted) {
		if(inverted != this.inverted) {
			this.inverted = inverted;
			this.setChanged();
		}
	}
	
	/**
	 * Sets the output of the detector.
	 * @param providingPower - true if the detector must provide a redstone signal, else false.
	 */
	private void setProvidingPower(boolean providingPower) {
		if(this.providingPower != providingPower) {
			this.providingPower = providingPower;
			this.updateNeighborBlocks();
			this.setChanged();
			this.update();
		}
	}
	
	// Methods :
	
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
		super.updateEntity();
		
		if(!this.worldObj.isRemote) {
			this.setProvidingPower(this.inverted != this.anyPlayerInRange());
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.range = compound.getInteger(RANGE);
		this.inverted = compound.getBoolean(INVERTED);
		this.providingPower = compound.getBoolean(PROVIDING_POWER);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(RANGE, this.range);
		compound.setBoolean(INVERTED, this.inverted);
		compound.setBoolean(PROVIDING_POWER, this.providingPower);
	}
	
}
