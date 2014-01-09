package seigneurnecron.minecraftmods.stargate.tileentity.console;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.gui.GuiTeleporter;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateCommandPacketMapping;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityConsoleBase;

/**
 * @author Seigneur Necron
 */
public class ConsoleTeleporter extends Console {
	
	// Constants :
	
	public static final int MAX_RANGE = 256;
	
	// Constructors :
	
	public ConsoleTeleporter(TileEntityConsoleBase tileEntity) {
		super(tileEntity);
	}
	
	// Methods :
	
	@Override
	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		if(world.isRemote) {
			ModLoader.openGUI(player, new GuiTeleporter(this.tileEntity, player, this));
		}
		
		return true;
	}
	
	/**
	 * Checks that the teleportation coordinates are valid.
	 * @param x - the x coordinate.
	 * @param y - the y coordinate.
	 * @param z - the z coordinate.
	 * @return true if the teleportation coordinates are valid, else false.
	 */
	public boolean isValidDestination(int x, int y, int z) {
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
		int xCoord = this.tileEntity.xCoord;
		int yCoord = this.tileEntity.yCoord;
		int zCoord = this.tileEntity.zCoord;
		
		return (x != xCoord || y != yCoord || z != zCoord) && (Math.abs(xCoord - x) <= MAX_RANGE) && (Math.abs(yCoord - y) <= MAX_RANGE) && (Math.abs(zCoord - z) <= MAX_RANGE);
	}
	
	/**
	 * Checks that there is a teleporter at the given coordinates.
	 * @param x - the x coordinate.
	 * @param y - the y coordinate.
	 * @param z - the z coordinate.
	 * @return true if there is a teleporter at the given coordinates, else false.
	 */
	public boolean isTeleporter(int x, int y, int z) {
		TileEntity tileEntity = this.tileEntity.worldObj.getBlockTileEntity(x, y, z);
		
		if(tileEntity instanceof TileEntityConsoleBase) {
			TileEntityConsoleBase tileEntityConsole = (TileEntityConsoleBase) tileEntity;
			
			return tileEntityConsole.isIntact() && tileEntityConsole.getConsole() instanceof ConsoleTeleporter;
		}
		
		return false;
	}
	
	/**
	 * Teleports the player at the given coordinates, if they are valid.
	 * @param player - the player.
	 * @param x - the x coordinate.
	 * @param y - the y coordinate.
	 * @param z - the z coordinate.
	 */
	public void teleportPlayer(EntityPlayerMP player, int x, int y, int z) {
		if(!this.tileEntity.worldObj.isRemote) {
			// Checks that the teleportation coordinates are not to far away and there is a teleporter in this position.
			if(this.isValidDestination(x, y, z) && this.isInRange(x, y, z) && this.isTeleporter(x, y, z)) {
				// Gets the orientation of the arrival teleporter and calculates the teleportation coordinates.
				int metadata = this.tileEntity.worldObj.getBlockMetadata(x, y + 1, z);
				
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
				if(!(this.tileEntity.worldObj.isBlockNormalCube(xTp, yTp, zTp) || this.tileEntity.worldObj.isBlockNormalCube(xTp, yTp + 1, zTp))) {
					this.tileEntity.playSoundEffect(StargateMod.getSounds().teleportation, player);
					player.playerNetServerHandler.setPlayerLocation(xTp + 0.5, yTp, zTp + 0.5, rotationYaw, rotationPitch);
					this.tileEntity.playSoundEffect(StargateMod.getSounds().teleportation, player);
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
		return this.tileEntity.getCommandPacket(StargateCommandPacketMapping.getInstance().TELEPORT, x, y, z);
	}
	
}
