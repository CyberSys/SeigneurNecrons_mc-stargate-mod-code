package seigneurnecron.minecraftmods.stargate.network;

import seigneurnecron.minecraftmods.core.network.PacketHandler;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateCommandPacketMapping;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class StargateServerPacketHandler extends PacketHandler {
	
	// Constructors :
	
	public StargateServerPacketHandler() {
		this.packetsMaps.add(new StargateCommandPacketMapping());
	}
	
}
