package seigneurnecron.minecraftmods.stargate.tools.playerdata;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Teleporter;

/**
 * @author Seigneur Necron
 */
public final class PlayerTeleporterData extends StargatePlayerDataList<Teleporter> {
	
	// Constants :
	
	/**
	 * Property indentifier.
	 */
	public static final String IDENTIFIER = "playerTeleporterData";
	
	// Static part :
	
	private static String getSaveKey(EntityPlayer player) {
		return player.username + ":" + IDENTIFIER;
	}
	
	public static PlayerTeleporterData get(EntityPlayer player) {
		return (PlayerTeleporterData) player.getExtendedProperties(IDENTIFIER);
	}
	
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(IDENTIFIER, new PlayerTeleporterData(player));
	}
	
	public static void saveProxyData(EntityPlayer player) {
		PlayerTeleporterData playerData = PlayerTeleporterData.get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		
		playerData.saveNBTData(savedData);
		StargateCommonProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	public static void loadProxyData(EntityPlayer player) {
		PlayerTeleporterData playerData = PlayerTeleporterData.get(player);
		NBTTagCompound savedData = StargateCommonProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null) {
			playerData.loadNBTData(savedData);
		}
		
		playerData.syncProperties();
	}
	
	// Constructors :
	
	public PlayerTeleporterData(EntityPlayer player) {
		super(player);
	}
	
	// Methods :
	
	@Override
	protected String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	protected Teleporter getElement(NBTTagCompound tag) {
		return new Teleporter(tag);
	}
	
	@Override
	protected Teleporter getElement(DataInputStream input) throws IOException {
		return new Teleporter(input);
	}
	
}
