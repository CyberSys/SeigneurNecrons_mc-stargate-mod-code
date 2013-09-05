package seigneurnecron.minecraftmods.stargate.tileentity;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.getCommandPacketIdFromName;
import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.getTileEntityPacketIdFromClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.stargate.StargateMod;

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
		output.writeInt(this.worldObj.provider.dimensionId);
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
		StargateMod.sendPacketToAllPlayersInDimension(this.getDescriptionPacket(), this.worldObj.provider.dimensionId);
	}
	
	/**
	 * Informs the renderer that the block associated with this tile entity needs to be updated.
	 */
	private void updateBlockTexture() {
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
}
