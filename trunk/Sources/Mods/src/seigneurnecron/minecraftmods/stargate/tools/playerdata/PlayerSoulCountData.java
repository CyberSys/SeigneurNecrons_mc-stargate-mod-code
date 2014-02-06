package seigneurnecron.minecraftmods.stargate.tools.playerdata;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import seigneurnecron.minecraftmods.stargate.proxy.StargateCommonProxy;
import seigneurnecron.minecraftmods.stargate.tools.loadable.SoulCount;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public final class PlayerSoulCountData extends StargatePlayerDataList<SoulCount> {
	
	// Constants :
	
	/**
	 * Property indentifier.
	 */
	public static final String IDENTIFIER = "playerSoulCountData";
	
	// Static part :
	
	private static String getSaveKey(EntityPlayer player) {
		return player.username + ":" + IDENTIFIER;
	}
	
	public static PlayerSoulCountData get(EntityPlayer player) {
		return (PlayerSoulCountData) player.getExtendedProperties(IDENTIFIER);
	}
	
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(IDENTIFIER, new PlayerSoulCountData(player));
	}
	
	public static void saveProxyData(EntityPlayer player) {
		PlayerSoulCountData playerData = PlayerSoulCountData.get(player);
		NBTTagCompound savedData = new NBTTagCompound();
		
		playerData.saveNBTData(savedData);
		StargateCommonProxy.storeEntityData(getSaveKey(player), savedData);
	}
	
	public static void loadProxyData(EntityPlayer player) {
		PlayerSoulCountData playerData = PlayerSoulCountData.get(player);
		NBTTagCompound savedData = StargateCommonProxy.getEntityData(getSaveKey(player));
		
		if(savedData != null) {
			playerData.loadNBTData(savedData);
		}
		
		playerData.syncProperties();
	}
	
	// Constructors :
	
	public PlayerSoulCountData(EntityPlayer player) {
		super(player);
	}
	
	// Methods :
	
	@Override
	protected String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	protected SoulCount getElement(NBTTagCompound tag) {
		return new SoulCount(tag);
	}
	
	@Override
	protected SoulCount getElement(DataInputStream input) throws IOException {
		return new SoulCount(input);
	}
	
}
