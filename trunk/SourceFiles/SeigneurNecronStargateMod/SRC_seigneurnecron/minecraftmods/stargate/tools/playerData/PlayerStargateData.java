package seigneurnecron.minecraftmods.stargate.tools.playerdata;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tools.loadable.Stargate;

/**
 * @author Seigneur Necron
 */
public final class PlayerStargateData extends StargatePlayerDataList<Stargate> {
	
	/**
	 * Property indentifier.
	 */
	public static final String IDENTIFIER = "playerStargateData";
	
	private static String getSaveKey(EntityPlayer player) {
		return player.username + ":" + IDENTIFIER;
	}
	
	public static PlayerStargateData get(EntityPlayer player) {
		return (PlayerStargateData) player.getExtendedProperties(IDENTIFIER);
	}
	
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(IDENTIFIER, new PlayerStargateData(player));
	}
	
	public static void saveProxyData(EntityPlayer player) {
		PlayerStargateData playerData = PlayerStargateData.get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		
		playerData.saveNBTData(savedData);
		StargateCommonProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	public static void loadProxyData(EntityPlayer player) {
		PlayerStargateData playerData = PlayerStargateData.get(player);
		NBTTagCompound savedData = StargateCommonProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null) {
			playerData.loadNBTData(savedData);
		}
		
		playerData.syncProperties();
	}
	
	// Constructors :
	
	public PlayerStargateData(EntityPlayer player) {
		super(player);
	}
	
	// Methods :
	
	@Override
	protected String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	protected Stargate getElement(NBTTagCompound tag) {
		return new Stargate(tag);
	}
	
	@Override
	protected Stargate getElement(DataInputStream input) throws IOException {
		return new Stargate(input);
	}
	
}
