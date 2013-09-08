package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.getCommandPacketIdFromName;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.getTileEntityPacketIdFromClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.client.sound.Sound;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityStargate extends TileEntity {
	
	private boolean changed = false;
	
	// Packet read/write part :
	
	/**
	 * Returns a packet containing data about this tile entity.
	 * @return a packet containing data about this tile entity.
	 */
	@Override
	public final Packet getDescriptionPacket() {
		byte[] data;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		
		try {
			this.getEntityData(output);
			output.close();
			data = outputStream.toByteArray();
		}
		catch(IOException argh) {
			data = new byte[] {};
			StargateMod.debug("Couldn't create a tile entity packet.", Level.SEVERE, true);
			argh.printStackTrace();
		}
		
		return new Packet250CustomPayload(StargateMod.CHANEL_TILE_ENTITY, data);
	}
	
	/**
	 * Writes the tile entity basic data in a DataOutputStream, in order to create a packet with the given id.
	 * @param output - the DataOutputStream in which the data must be written.
	 * @param id - the packet id.
	 * @throws IOException - if an IOException occurs while writting in the DataOutputStream.
	 */
	protected final void getEntityBasicData(DataOutputStream output, int id) throws IOException {
		output.writeInt(id);
		output.writeInt(this.getDimension());
		output.writeInt(this.xCoord);
		output.writeInt(this.yCoord);
		output.writeInt(this.zCoord);
	}
	
	/**
	 * Writes the tile entity data in a DataOutputStream, in order to create a tile entity packet.
	 * @param output - the DataOutputStream in which the data must be written.
	 * @throws IOException - if an IOException occurs while writting in the DataOutputStream.
	 */
	protected void getEntityData(DataOutputStream output) throws IOException {
		this.getEntityBasicData(output, getTileEntityPacketIdFromClass(this.getClass()));
	}
	
	/**
	 * Reads the tile entity data from a DataInputStream, and update clients.
	 * @param input - the DataInputStream from which the data must be read.
	 * @throws IOException - if an IOException occurs while reading the DataInputStream.
	 */
	public final void onDataPacket(DataInputStream input) throws IOException {
		this.loadEntityData(input);
		
		if(this.worldObj != null) {
			if(this.worldObj.isRemote) {
				this.updateBlockTexture();
			}
			else {
				this.updateClients();
				this.changed = false;
			}
		}
	}
	
	/**
	 * Reads the tile entity data from a DataInputStream.
	 * @param input - the DataInputStream from which the data must be read.
	 * @throws IOException - if an IOException occurs while reading the DataInputStream.
	 */
	protected void loadEntityData(DataInputStream input) throws IOException {
		// Nothing here.
	}
	
	/**
	 * Returns a command packet.
	 * @param commandName - the command name.
	 * @param parameters - an array containing the parameters of the command.
	 * @return a command packet.
	 */
	public Packet getCommandPacket(String commandName, Object... parameters) {
		byte[] data;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		
		try {
			this.getEntityBasicData(output, getCommandPacketIdFromName(commandName));
			
			for(Object object : parameters) {
				if(object instanceof Boolean) {
					output.writeBoolean((Boolean) object);
				}
				else if(object instanceof Integer) {
					output.writeInt((Integer) object);
				}
				else if(object instanceof Float) {
					output.writeFloat((Float) object);
				}
				else if(object instanceof Double) {
					output.writeDouble((Double) object);
				}
				else if(object instanceof String) {
					output.writeUTF((String) object);
				}
				else {
					throw new IOException("The type of this command parameter isn't compatible : " + object.getClass().getSimpleName());
				}
			}
			
			output.close();
			data = outputStream.toByteArray();
		}
		catch(IOException argh) {
			data = new byte[] {};
			StargateMod.debug("Error while writing in a DataOutputStream. Couldn't create a " + commandName + " command packet.", Level.SEVERE, true);
			argh.printStackTrace();
		}
		
		return new Packet250CustomPayload(StargateMod.CHANEL_COMMANDS, data);
	}
	
	// Update part :
	
	/**
	 * Mark this tileEntity as needing an update.
	 */
	protected final void setChanged() {
		this.changed = true;
	}
	
	/**
	 * Transmits changes to server/clients.
	 */
	protected final void update() {
		if(this.worldObj != null && this.changed) {
			if(this.worldObj.isRemote) {
				this.updateServer();
			}
			else {
				this.updateClients();
			}
			
			this.changed = false;
		}
	}
	
	/**
	 * Transmits changes to server.
	 */
	private void updateServer() {
		StargateMod.sendPacketToServer(this.getDescriptionPacket());
	}
	
	/**
	 * Transmits changes to clients.
	 */
	private void updateClients() {
		StargateMod.sendPacketToAllPlayersInDimension(this.getDescriptionPacket(), this.getDimension());
	}
	
	/**
	 * Informs the renderer that the block associated with this tile entity needs to be updated.
	 */
	private void updateBlockTexture() {
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	// Utility :
	
	/**
	 * Returns the dimension of the world containing this tileEntity.
	 * @return the dimension of the world containing this tileEntity.
	 */
	public int getDimension() {
		return this.worldObj.provider.dimensionId;
	}
	
	/**
	 * Returns the squared distance between this and the block at the given coordinates.
	 * @param x - the X coordinate of the block that we want to know the distance.
	 * @param y - the Y coordinate of the block that we want to know the distance.
	 * @param z - the Z coordinate of the block that we want to know the distance.
	 * @return the squared distance between the DHD and the block.
	 */
	public double squaredDistance(int x, int y, int z) {
		return Math.pow(this.xCoord - x, 2) + Math.pow(this.yCoord - y, 2) + Math.pow(this.zCoord - z, 2);
	}
	
	/**
	 * Plays a sound at the given coordinates, volume and pitch.
	 * @param sound - the sound.
	 * @param x - the X coordinate.
	 * @param y - the Y coordinate.
	 * @param z - the Z coordinate.
	 * @param volume - the volume.
	 * @param pitch - the pitch/frequency.
	 */
	public void playSoundEffect(Sound sound, double x, double y, double z, float volume, float pitch) {
		this.worldObj.playSoundEffect(x, y, z, sound.toString(), volume, pitch);
	}
	
	/**
	 * Plays a sound at the given coordinates, at the default volume and pitch.
	 * @param sound - the sound.
	 * @param x - the X coordinate.
	 * @param y - the Y coordinate.
	 * @param z - the Z coordinate.
	 */
	public void playSoundEffect(Sound sound, double x, double y, double z) {
		this.playSoundEffect(sound, x, y, z, 1.0F, 1.0F);
	}
	
	/**
	 * Plays a sound at the coordinates of the block, at the given volume and pitch.
	 * @param sound - the sound.
	 * @param volume - the volume.
	 * @param pitch - the pitch/frequency.
	 */
	public void playSoundEffect(Sound sound, float volume, float pitch) {
		this.playSoundEffect(sound, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, volume, pitch);
	}
	
	/**
	 * Plays a sound at coordinates of the block.
	 * @param sound - the sound.
	 */
	public void playSoundEffect(Sound sound) {
		this.playSoundEffect(sound, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, 1.0F, 1.0F);
	}
	
	/**
	 * Plays a sound at the coordinates of the given entity, volume and pitch.
	 * @param sound - the sound.
	 * @param entity - the entity where the sound must be played.
	 * @param volume - the volume.
	 * @param pitch - the pitch/frequency.
	 */
	public void playSoundEffect(Sound sound, Entity entity, float volume, float pitch) {
		this.worldObj.playSoundAtEntity(entity, sound.toString(), volume, pitch);
	}
	
	/**
	 * Plays a sound at the coordinates of the given entity, at default volume and pitch.
	 * @param sound - the sound.
	 * @param entity - the entity where the sound must be played.
	 */
	public void playSoundEffect(Sound sound, Entity entity) {
		this.playSoundEffect(sound, entity, 1.0F, 1.0F);
	}
	
}
