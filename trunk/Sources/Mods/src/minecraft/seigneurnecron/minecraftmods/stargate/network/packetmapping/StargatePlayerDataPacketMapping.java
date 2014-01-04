package seigneurnecron.minecraftmods.stargate.network.packetmapping;

import seigneurnecron.minecraftmods.core.network.packetmapping.PlayerDataPacketMapping;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerSoulCountData;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerStargateData;
import seigneurnecron.minecraftmods.stargate.tools.playerdata.PlayerTeleporterData;

/**
 * @author Seigneur Necron
 */
public class StargatePlayerDataPacketMapping extends PlayerDataPacketMapping {
	
	// Static instance :
	
	private static StargatePlayerDataPacketMapping instance;
	
	public static StargatePlayerDataPacketMapping getInstance() {
		return instance;
	}
	
	// Constructors :
	
	public StargatePlayerDataPacketMapping() {
		super(StargateMod.CHANEL_PLAYER_DATA);
		instance = this;
		
		this.register(PlayerTeleporterData.IDENTIFIER);
		this.register(PlayerStargateData.IDENTIFIER);
		this.register(PlayerSoulCountData.IDENTIFIER);
	}
	
}
