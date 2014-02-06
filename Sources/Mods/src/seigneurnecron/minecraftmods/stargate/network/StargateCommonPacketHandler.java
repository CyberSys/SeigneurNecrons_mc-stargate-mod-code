package seigneurnecron.minecraftmods.stargate.network;

import seigneurnecron.minecraftmods.core.network.PacketHandler;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargatePlayerDataPacketMapping;
import seigneurnecron.minecraftmods.stargate.network.packetmapping.StargateTileEntityPacketMapping;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class StargateCommonPacketHandler extends PacketHandler {
	
	// Constructors :
	
	public StargateCommonPacketHandler() {
		this.packetsMaps.add(new StargateTileEntityPacketMapping());
		this.packetsMaps.add(new StargatePlayerDataPacketMapping());
	}
	
}
