package seigneurnecron.minecraftmods.stargate.playerData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class StargatePlayerProperties implements IExtendedEntityProperties {
	
	// NBTTags names :
	
	private static final String TELEPORTER_DATA = "teleporterData";
	private static final String STARGATE_DATA = "stargateData";
	private static final String NAME = "name";
	private static final String ADDRESS = "address";
	private static final String CODE = "code";
	private static final String X = "x";
	private static final String Y = "y";
	private static final String Z = "z";
	private static final String DIM = "dim";
	
	// Property indentifier :
	
	public static final String IDENTIFIER = "stargatePlayerProperties";
	
	public static StargatePlayerProperties get(EntityPlayer entity) {
		return (StargatePlayerProperties) entity.getExtendedProperties(IDENTIFIER);
	}
	
	// Fields :
	
	/**
	 * A map of registered teleporter coordinates and names.
	 */
	private Map<Coordinates, String> teleporterData;
	
	/**
	 * A map of registered stargate address, names and codes.
	 */
	private Map<String, GateInformation> stargateData;
	
	// Getters :
	
	public Map<Coordinates, String> getTeleporterData() {
		return this.teleporterData;
	}

	
	public Map<String, GateInformation> getStargateData() {
		return this.stargateData;
	}

	// Setters :
	
	protected void setTeleporterData(Map<Coordinates, String> teleporterData) {
		this.teleporterData = teleporterData;
	}

	
	protected void setStargateData(Map<String, GateInformation> stargateData) {
		this.stargateData = stargateData;
	}
	
	// Methods :
	
	// NBTTags system :

	@Override
	public void init(Entity entity, World world) {
		this.teleporterData = new HashMap<Coordinates, String>();
		this.stargateData = new HashMap<String, GateInformation>();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagList teleporters = new NBTTagList();
		compound.setTag(TELEPORTER_DATA, teleporters);
		
		for(Entry<Coordinates, String> entry : this.teleporterData.entrySet()) {
			NBTTagCompound teleporter = new NBTTagCompound();
			
			teleporter.setInteger(X, entry.getKey().x);
			teleporter.setInteger(Y, entry.getKey().y);
			teleporter.setInteger(Z, entry.getKey().z);
			teleporter.setInteger(DIM, entry.getKey().dim);
			teleporter.setString(NAME, entry.getValue());
			
			teleporters.appendTag(teleporter);
		}
		
		NBTTagList stargates = new NBTTagList();
		compound.setTag(STARGATE_DATA, stargates);
		
		for(Entry<String, GateInformation> entry : this.stargateData.entrySet()) {
			NBTTagCompound stargate = new NBTTagCompound();
			
			stargate.setString(ADDRESS, entry.getKey());
			stargate.setString(NAME, entry.getValue().name);
			stargate.setInteger(CODE, entry.getValue().code);
			
			teleporters.appendTag(stargate);
		}
	}
	
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
}
