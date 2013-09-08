package seigneurnecron.minecraftmods.stargate.tools.playerData;

import static seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler.getPlayerDataPacketIdFromClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Loadable;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Seigneur Necron
 */
public abstract class PlayerDataList<T extends Loadable> implements IExtendedEntityProperties {
	
	// NBTTags names :
	
	protected static final String LIST = "dataList";
	
	// Fields :
	
	/**
	 * The player to which the properties belong.
	 */
	protected final EntityPlayer player;
	
	/**
	 * A list of registered data.
	 */
	protected List<T> dataList;
	
	// Constructors :
	
	protected PlayerDataList(EntityPlayer player) {
		this.initList();
		this.player = player;
	}
	
	// Getters :
	
	public List<T> getDataList() {
		return this.dataList;
	}
	
	// Interface :
	
	public void addElementAndSync(T element) {
		this.addElement(element);
		this.syncProperties();
	}
	
	public void removeElementAndSync(T element) {
		this.removeElement(element);
		this.syncProperties();
	}
	
	public void overwriteElementAndSync(T oldElement, T newElement) {
		this.removeElement(oldElement);
		this.addElement(newElement);
		this.syncProperties();
	}
	
	// Methods :
	
	private void initList() {
		this.dataList = new LinkedList<T>();
	}
	
	private void addElement(T element) {
		this.dataList.remove(element);
		
		int i = 0;
		while(i < this.dataList.size() && element.compareTo(this.dataList.get(i)) >= 0) {
			i++;
		}
		
		this.dataList.add(i, element);
	}
	
	private void removeElement(T element) {
		this.dataList.remove(element);
	}
	
	// Packet system :
	
	public void syncProperties() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		
		try {
			output.writeInt(getPlayerDataPacketIdFromClass(this.getClass()));
			output.writeInt(this.dataList.size());
			
			for(T element : this.dataList) {
				element.writeData(output);
			}
			
			output.close();
		}
		catch(IOException argh) {
			StargateMod.debug("Error while writing in a DataOutputStream. Couldn't send a player data packet.", Level.SEVERE, true);
			argh.printStackTrace();
			return;
		}
		
		Packet250CustomPayload packet;
		
		try {
			packet = new Packet250CustomPayload(StargateMod.CHANEL_PLAYER_DATA, outputStream.toByteArray());
		}
		catch(IllegalArgumentException argh) {
			StargateMod.debug("Exceeded maximum player data size. Couldn't send a player data packet.", Level.WARNING, true);
			argh.printStackTrace();
			return;
		}
		
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		if(side == Side.SERVER) {
			EntityPlayerMP player = (EntityPlayerMP) this.player;
			StargateMod.sendPacketToPlayer(packet, player);
		}
		else {
			StargateMod.sendPacketToServer(packet);
		}
	}
	
	public void loadProperties(DataInputStream input) throws IOException {
		int nbElements = input.readInt();
		List<T> list = new LinkedList<T>();
		
		for(int i = 0; i < nbElements; i++) {
			list.add(this.getElement(input));
		}
		
		this.dataList = list;
	}
	
	protected abstract T getElement(DataInputStream input) throws IOException;
	
	// NBTTags system :
	
	@Override
	public void init(Entity entity, World world) {
		// Nothing here.
	}
	
	@Override
	public void saveNBTData(NBTTagCompound entityTag) {
		// The tag you get here is not specific to your property, it is the entity global tagCompound !
		// To avoid problems with other property setting a tag with the same name, be sure to create a subtag with a unique name, for exemple the identifier of your property.
		NBTTagCompound thisTag = new NBTTagCompound();
		entityTag.setTag(this.getIdentifier(), thisTag);
		
		NBTTagList list = new NBTTagList();
		thisTag.setTag(LIST, list);
		
		for(T element : this.dataList) {
			NBTTagCompound tag = new NBTTagCompound();
			element.saveNBTData(tag);
			list.appendTag(tag);
		}
	}
	
	@Override
	public void loadNBTData(NBTTagCompound entityTag) {
		// The tag you get here is not specific to your property, it is the entity global tagCompound !
		// To avoid problems with other property setting a tag with the same name, be sure to create a subtag with a unique name, for exemple the identifier of your property.
		NBTTagCompound thisTag = entityTag.getCompoundTag(this.getIdentifier());
		
		NBTTagList list = thisTag.getTagList(LIST);
		this.initList();
		
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			T element = this.getElement(tag);
			this.dataList.add(element);
		}
	}
	
	protected abstract T getElement(NBTTagCompound tag);
	
	protected abstract String getIdentifier();
	
}
