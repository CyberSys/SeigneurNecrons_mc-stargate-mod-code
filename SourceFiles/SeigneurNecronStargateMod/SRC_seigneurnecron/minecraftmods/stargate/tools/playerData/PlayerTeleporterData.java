package seigneurnecron.minecraftmods.stargate.tools.playerData;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.network.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Teleporter;

/**
 * @author Seigneur Necron
 */
public final class PlayerTeleporterData extends PlayerDataList<Teleporter> {
	
	/**
	 * Property indentifier.
	 */
	private static final String IDENTIFIER = "playerTeleporterData";
	
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
	
	// Builders :
	
	public PlayerTeleporterData(EntityPlayer player) {
		super(player);
	}
	
	// Methods :
	
	@Override
	protected Teleporter getElement(NBTTagCompound tag) {
		return new Teleporter(tag);
	}
	
	@Override
	protected Teleporter getElement(DataInputStream input) throws IOException {
		return new Teleporter(input);
	}
	
}
