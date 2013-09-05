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
	
	// Builders :
	
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
	
	public void deleteElementAndSync(T element) {
		this.deleteElement(element);
		this.syncProperties();
	}
	
	public void overwriteElementAndSync(T oldElement, T newElement) {
		this.deleteElement(oldElement);
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
	
	private void deleteElement(T element) {
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
		
		StargateMod.debug((FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? "Server" : "Client") + " - " + this.getClass().getSimpleName() + " - Player data packet sent.", Level.WARNING, true); // FIXME - delete.
		
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
		
		StargateMod.debug((FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? "Server" : "Client") + " - " + this.getClass().getSimpleName() + " - Player data packet received.", Level.WARNING, true); // FIXME - delete.
	}
	
	protected abstract T getElement(DataInputStream input) throws IOException;
	
	// NBTTags system :
	
	@Override
	public void init(Entity entity, World world) {
		// Nothing here.
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		compound.setTag(LIST, list);
		
		for(T element : this.dataList) {
			NBTTagCompound tag = new NBTTagCompound();
			element.saveNBTData(tag);
			list.appendTag(tag);
		}
		
		StargateMod.debug((FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? "Server" : "Client") + " - " + this.getClass().getSimpleName() + " - Player data saved to NBT.", Level.WARNING, true); // FIXME - delete.
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.initList();
		NBTTagList list = compound.getTagList(LIST);
		
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			T element = this.getElement(tag);
			this.dataList.add(element);
		}
		
		StargateMod.debug((FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? "Server" : "Client") + " - " + this.getClass().getSimpleName() + " - Player data loaded from NBT.", Level.WARNING, true); // FIXME - delete.
	}
	
	protected abstract T getElement(NBTTagCompound tag);
	
}
