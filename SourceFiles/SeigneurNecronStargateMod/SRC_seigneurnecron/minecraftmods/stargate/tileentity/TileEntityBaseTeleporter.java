package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.TELEPORT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.sound.StargateSounds;

/**
 * @author Seigneur Necron
 */
public class TileEntityBaseTeleporter extends TileEntityBase {
	
	public static final String INV_NAME = "container.teleporter";
	public static final int MAX_RANGE = 256;
	
	/**
	 * Checks that the teleportation coordinates are valid.
	 * @param x - the x coordinate.
	 * @param y - the y coordinate.
	 * @param z - the z coordinate.
	 * @return true if the teleportation coordinates are valid, else false.
	 */
	public boolean isValid(int x, int y, int z) {
		return (y > 0) && (y < 255);
	}
	
	/**
	 * Checks that the teleportation coordinates are not to far away.
	 * @param x - the x coordinate.
	 * @param y - the y coordinate.
	 * @param z - the z coordinate.
	 * @return true if the teleportation coordinates are in range, else false.
	 */
	public boolean isInRange(int x, int y, int z) {
		return (x != this.xCoord || y != this.yCoord || z != this.zCoord) && (Math.abs(this.xCoord - x) <= MAX_RANGE) && (Math.abs(this.yCoord - y) <= MAX_RANGE) && (Math.abs(this.zCoord - z) <= MAX_RANGE);
	}
	
	/**
	 * Checks that there is a teleporter at the given coordinates.
	 * @param x - the x coordinate.
	 * @param y - the y coordinate.
	 * @param z - the z coordinate.
	 * @return true if there is a teleporter at the given coordinates, else false.
	 */
	public boolean isTeleporter(int x, int y, int z) {
		return this.worldObj.getBlockId(x, y, z) == this.getBaseBlock().blockID && this.worldObj.getBlockId(x, y + 1, z) == this.getPanelBlock().blockID;
	}
	
	/**
	 * Teleports the player at the given coordinates, if they are valid.
	 * @param player - the player.
	 * @param x - the x coordinate.
	 * @param y - the y coordinate.
	 * @param z - the z coordinate.
	 */
	public void teleportPlayer(EntityPlayerMP player, int x, int y, int z) {
		if(!this.worldObj.isRemote) {
			// Checks that the teleportation coordinates are not to far away and there is a teleporter in this position.
			if(this.isValid(x, y, z) && this.isInRange(x, y, z) && this.isTeleporter(x, y, z)) {
				// Gets the orientation of the arrival teleporter and calculates the teleportation coordinates.
				int metadata = this.worldObj.getBlockMetadata(x, y, z);
				
				int xTp = x;
				int yTp = y;
				int zTp = z;
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
				if(!(this.worldObj.isBlockNormalCube(xTp, yTp, zTp) || this.worldObj.isBlockNormalCube(xTp, yTp + 1, zTp))) {
					this.playSoundEffect(StargateSounds.teleportation, player);
					player.playerNetServerHandler.setPlayerLocation(xTp + 0.5, yTp, zTp + 0.5, rotationYaw, rotationPitch);
					this.playSoundEffect(StargateSounds.teleportation, player);
				}
			}
		}
	}
	
	/**
	 * Returns a teleport command packet. This is used to send the destination coordinates to the server.
	 * @param x - the destination X coordinate.
	 * @param y - the destination Y coordinate.
	 * @param z - the destination Z coordinate.
	 * @return a teleport command packet.
	 */
	public Packet getTeleportPacket(int x, int y, int z) {
		return this.getCommandPacket(TELEPORT, x, y, z);
	}
	
	@Override
	public void onConsoleDestroyed() {
		// Nothing here.
	}
	
	@Override
	public boolean isActive() {
		return this.isIntact();
	}
	
	@Override
	public Block getBaseBlock() {
		return StargateMod.block_teleporterBase;
	}
	
	@Override
	public Block getPanelBlock() {
		return StargateMod.block_teleporterPanel;
	}
	
}
