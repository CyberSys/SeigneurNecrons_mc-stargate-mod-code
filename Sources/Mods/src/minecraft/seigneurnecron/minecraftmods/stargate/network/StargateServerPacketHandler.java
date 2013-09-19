package seigneurnecron.minecraftmods.stargate.network;

import seigneurnecron.minecraftmods.core.network.PacketHandler;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateCommandPacketMapping;

/**
 * @author Seigneur Necron
 */
public class StargateServerPacketHandler extends PacketHandler {
	
	// Constructors :
	
	public StargateServerPacketHandler() {
		this.packetsMaps.add(new StargateCommandPacketMapping());
	}
	
}
