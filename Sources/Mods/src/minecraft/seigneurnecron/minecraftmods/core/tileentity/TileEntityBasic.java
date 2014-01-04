package seigneurnecron.minecraftmods.core.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.core.sound.Sound;

/**
 * @author Seigneur Necron
 */
public abstract class TileEntityBasic extends TileEntity {
	
	// Update part :
	
	private boolean changed = false;
	
	/**
	 * Mark this tileEntity as needing an update.
	 */
	protected final void setChanged() {
		this.changed = true;
	}
	
	/**
	 * Transmits changes to server/clients.
	 */
	public final void update() {
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
		ModBase.sendPacketToServer(this.getDescriptionPacket());
	}
	
	/**
	 * Transmits changes to clients.
	 */
	private void updateClients() {
		ModBase.sendPacketToAllPlayersInDimension(this.getDescriptionPacket(), this.getDimension());
	}
	
	/**
	 * Informs the renderer that the block associated with this tile entity needs to be updated.
	 */
	private void updateBlockTexture() {
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	// Packet read/write part :
	
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
	private void loadEntityData(DataInputStream input) throws IOException {
		NBTTagCompound compound = CompressedStreamTools.read(input);
		this.readFromNBT(compound);
	}
	
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
			this.getTileEntityData(output);
			output.close();
			data = outputStream.toByteArray();
		}
		catch(IOException argh) {
			data = new byte[] {};
			SeigneurNecronMod.instance.log("Couldn't create a tile entity packet.", Level.SEVERE);
			argh.printStackTrace();
		}
		
		return new Packet250CustomPayload(this.getTileEntityChanel(), data);
	}
	
	/**
	 * Writes the tile entity data in a DataOutputStream, in order to create a tile entity packet.
	 * @param output - the DataOutputStream in which the data must be written.
	 * @throws IOException - if an IOException occurs while writting in the DataOutputStream.
	 */
	private void getTileEntityData(DataOutputStream output) throws IOException {
		this.getTileEntityBasicData(output, this.getTileEntityPacketId());
		
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		CompressedStreamTools.write(compound, output);
	}
	
	/**
	 * Writes the tile entity basic data in a DataOutputStream, in order to create a packet with the given id.
	 * @param output - the DataOutputStream in which the data must be written.
	 * @param id - the packet id.
	 * @throws IOException - if an IOException occurs while writting in the DataOutputStream.
	 */
	protected final void getTileEntityBasicData(DataOutputStream output, int id) throws IOException {
		output.writeInt(id);
		output.writeInt(this.getDimension());
		output.writeInt(this.xCoord);
		output.writeInt(this.yCoord);
		output.writeInt(this.zCoord);
	}
	
	/**
	 * Returns the packet id for this tileEntity class. <br />
	 * You will find it in your tileEntityPacketHandler map.
	 * @return the packet id for this tileEntity class.
	 */
	protected abstract int getTileEntityPacketId();
	
	/**
	 * Returns the chanel used to send/receive tile entity packets.
	 * @return the chanel used to send/receive tile entity packets.
	 */
	protected abstract String getTileEntityChanel();
	
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
		int xd = this.xCoord - x;
		int yd = this.yCoord - y;
		int zd = this.zCoord - z;
		return (xd * xd) + (yd * yd) + (zd * zd);
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
