package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.readInt;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeBoolean;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.writeInt;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.StargateMod;

/**
 * @author Seigneur Necron
 */
public class TileEntityBaseTeleporter extends TileEntityBase {
	
	public static final String INV_NAME = "container.teleporter";
	
	/**
	 * The destination X coordinate.
	 */
	private int xDest = 0;
	
	/**
	 * The destination Y coordinate.
	 */
	private int yDest = 0;
	
	/**
	 * The destination Z coordinate.
	 */
	private int zDest = 0;
	
	/**
	 * Indicates whether the teleporter is connected to another teleporter.
	 */
	private boolean connected = false;
	
	/**
	 * Returns the destination X coordinate.
	 * @return the destination X coordinate.
	 */
	public int getXDest() {
		return this.xDest;
	}
	
	/**
	 * Returns the destination Y coordinate.
	 * @return the destination Y coordinate.
	 */
	public int getYDest() {
		return this.yDest;
	}
	
	/**
	 * Returns the destination Z coordinate.
	 * @return the destination Z coordinate.
	 */
	public int getZDest() {
		return this.zDest;
	}
	
	/**
	 * Indicates whether the teleporter is connected to another teleporter.
	 * @return true if the teleporter is connected to another teleporter, else false.
	 */
	public boolean isConnected() {
		return this.connected;
	}
	
	/**
	 * Sets the destination X coordinate.
	 * @param xDest - the new destination X coordinate.
	 */
	public void setXDest(int xDest) {
		this.xDest = xDest;
	}
	
	/**
	 * Sets the destination Y coordinate.
	 * @param yDest - the new destination Y coordinate.
	 */
	public void setYDest(int yDest) {
		this.yDest = yDest;
	}
	
	/**
	 * Sets the destination Z coordinate.
	 * @param zDest - the new destination Z coordinate.
	 */
	public void setZDest(int zDest) {
		this.zDest = zDest;
	}
	
	/**
	 * Sets the state of the teleporter.
	 * @param connected - true if the teleporter is connected to another teleporter, else false.
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	/**
	 * Checks that the teleportation coordinates are not to far away and there is a teleporter in this position.
	 */
	private boolean isDestinationValid() {
		boolean connected = Math.abs(this.xDest - this.xCoord) <= 256 && Math.abs(this.yDest - this.yCoord) <= 256 && Math.abs(this.zDest - this.zCoord) <= 256 && this.worldObj.getBlockId(this.xDest, this.yDest, this.zDest) == StargateMod.block_teleporterBase.blockID && this.worldObj.getBlockId(this.xDest, this.yDest + 1, this.zDest) == StargateMod.block_teleporterPanel.blockID;
		this.setConnected(connected);
		return connected;
	}
	
	/**
	 * Teleports the player at the recorded coordinates, if they are valid.
	 */
	private void teleportPlayer(EntityPlayerMP player) {
		// Checks that the teleportation coordinates are not to far away and there is a teleporter in this position.
		if(!this.isDestinationValid()) {
			return;
		}
		
		// Gets the orientation of the arrival teleporter and calculates the teleportation coordinates.
		int metadata = this.worldObj.getBlockMetadata(this.xDest, this.yDest, this.zDest);
		
		int xTp = this.xDest;
		int yTp = this.yDest;
		int zTp = this.zDest;
		float rotationYaw = player.rotationYaw;
		float rotationPitch = player.rotationPitch;
		
		switch(metadata) {
			case 2:
				zTp -= 1;
				rotationYaw = 0;
				break;
			case 3:
				zTp += 1;
				rotationYaw = 180;
				break;
			case 4:
				xTp -= 1;
				rotationYaw = 270;
				break;
			case 5:
				xTp += 1;
				rotationYaw = 90;
				break;
		}
		
		// Checks that there is enough space at the destination.
		if(this.worldObj.isBlockNormalCube(xTp, yTp, zTp) || this.worldObj.isBlockNormalCube(xTp, yTp + 1, zTp)) {
			return;
		}
		
		player.playerNetServerHandler.setPlayerLocation(xTp + 0.5, yTp, zTp + 0.5, rotationYaw, rotationPitch);
	}
	
	@Override
	public final void activate(EntityPlayer player, int side, float xOffset, float yOffset, float zOffset) {
		this.teleportPlayer((EntityPlayerMP) player);
	}
	
	@Override
	public boolean isActive() {
		return this.isConnected();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.xDest = par1NBTTagCompound.getInteger("xDest");
		this.yDest = par1NBTTagCompound.getInteger("yDest");
		this.zDest = par1NBTTagCompound.getInteger("zDest");
		this.connected = par1NBTTagCompound.getBoolean("connected");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("xDest", this.xDest);
		par1NBTTagCompound.setInteger("yDest", this.yDest);
		par1NBTTagCompound.setInteger("zDest", this.zDest);
		par1NBTTagCompound.setBoolean("connected", this.connected);
	}
	
	@Override
	protected LinkedList<Byte> getEntityData() {
		LinkedList<Byte> list = super.getEntityData();
		
		writeInt(list, this.xDest);
		writeInt(list, this.yDest);
		writeInt(list, this.zDest);
		writeBoolean(list, this.connected);
		
		return list;
	}
	
	@Override
	protected boolean loadEntityData(LinkedList<Byte> list) {
		if(super.loadEntityData(list)) {
			this.xDest = readInt(list);
			this.yDest = readInt(list);
			this.zDest = readInt(list);
			this.connected = readBoolean(list);
			
			this.updateClients();
			this.updateBlockTexture();
			return true;
		}
		return false;
	}
	
	/**
	 * Transmits changes to clients.
	 */
	@Override
	protected void updateClients() {
		if(this.worldObj != null && !this.worldObj.isRemote) {
			this.isDestinationValid();
			StargateMod.sendPacketToAllPlayersInDimension(this.getDescriptionPacket(), this.worldObj.provider.dimensionId);
		}
	}
	
}
