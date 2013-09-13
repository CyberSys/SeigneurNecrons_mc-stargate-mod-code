package seigneurnecron.minecraftmods.core.entitydata;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import seigneurnecron.minecraftmods.core.SeigneurNecronMod;
import seigneurnecron.minecraftmods.core.mod.ModBase;
import seigneurnecron.minecraftmods.core.network.packetmapping.PlayerDataPacketMapping;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Seigneur Necron
 */
public abstract class EntityData<T extends Entity> implements IExtendedEntityProperties {
	
	// Fields :
	
	/**
	 * The entity to which the properties belong.
	 */
	protected final T entity;
	
	// Constructors :
	
	protected EntityData(T entity) {
		this.entity = entity;
	}
	
	// Packet system :
	
	/**
	 * Sends a packet containing this property data to the server/client(s).
	 */
	public final void syncProperties() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		
		PlayerDataPacketMapping packetMapping = this.getPacketMapping();
		
		if(packetMapping == null) {
			SeigneurNecronMod.instance.log("Error : The class " + this.getClass().getSimpleName() + " is linked to a null a packet mapping. Couldn't send a custom entity data packet.", Level.SEVERE);
			return;
		}
		
		int packetId = packetMapping.getId(this.getIdentifier());
		
		if(packetId < 0) {
			SeigneurNecronMod.instance.log("Error : The class " + this.getClass().getSimpleName() + " isn't mapped in the packet mapping. Couldn't send a custom entity data packet.", Level.SEVERE);
			return;
		}
		
		try {
			output.writeInt(packetId);
			this.saveProperties(output);
			output.close();
		}
		catch(IOException argh) {
			SeigneurNecronMod.instance.log("Error while writing in a DataOutputStream. Couldn't send a custom entity data packet.", Level.SEVERE);
			argh.printStackTrace();
			return;
		}
		
		Packet packet;
		
		try {
			packet = new Packet250CustomPayload(StargateMod.CHANEL_PLAYER_DATA, outputStream.toByteArray());
		}
		catch(IllegalArgumentException argh) {
			SeigneurNecronMod.instance.log("Exceeded maximum packet data size. Couldn't send a custom entity data packet.", Level.WARNING);
			argh.printStackTrace();
			return;
		}
		
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		if(side == Side.SERVER) {
			this.sendPacketToClients(packet);
		}
		else {
			ModBase.sendPacketToServer(packet);
		}
	}
	
	/**
	 * Sends a packet containing this property data to the client(s).
	 * @param packet - the packet.
	 */
	protected void sendPacketToClients(Packet packet) {
		ModBase.sendPacketToAllPlayers(packet);
	}
	
	/**
	 * Returns the packet mapping in which this class is registered.
	 * @return the packet mapping in which this class is registered.
	 */
	protected abstract PlayerDataPacketMapping getPacketMapping();
	
	/**
	 * Writes the property data to a DataOutputStream.
	 */
	protected abstract void saveProperties(DataOutputStream output) throws IOException;
	
	/**
	 * Reads the property data from a DataInputStream.
	 */
	public abstract void loadProperties(DataInputStream input) throws IOException;
	
	// NBTTags system :
	
	@Override
	public void init(Entity entity, World world) {
		// Nothing here.
	}
	
	/**
	 * Saves the property data to the NBT compound.
	 * The tag you get here is not specific to your property, it is the entity global tagCompound !
	 * To avoid problems with other property setting a tag with the same name, this method create a subtag with the name of your property.
	 */
	@Override
	public final void saveNBTData(NBTTagCompound entityTag) {
		NBTTagCompound propertyTag = new NBTTagCompound();
		entityTag.setTag(this.getIdentifier(), propertyTag);
		this.saveToNBT(propertyTag);
	}
	
	/**
	 * Loads the property data from the NBT compound.
	 * The tag you get here is not specific to your property, it is the entity global tagCompound !
	 * To avoid problems with other property setting a tag with the same name, this method create a subtag with the name of your property.
	 */
	@Override
	public final void loadNBTData(NBTTagCompound entityTag) {
		NBTTagCompound propertyTag = entityTag.getCompoundTag(this.getIdentifier());
		this.loadFromNBT(propertyTag);
	}
	
	/**
	 * Saves the property data to the NBT compound.
	 * The tag you get here is specific to your property, you can add your tags inside without worrying about other properties.
	 */
	protected abstract void saveToNBT(NBTTagCompound propertyTag);
	
	/**
	 * Loads the property data from the NBT compound.
	 * The tag you get here is specific to your property, you can add your tags inside without worrying about other properties.
	 */
	protected abstract void loadFromNBT(NBTTagCompound propertyTag);
	
	/**
	 * Returns the unique identifier of your property.
	 * @return the unique identifier of your property.
	 */
	protected abstract String getIdentifier();
	
}
