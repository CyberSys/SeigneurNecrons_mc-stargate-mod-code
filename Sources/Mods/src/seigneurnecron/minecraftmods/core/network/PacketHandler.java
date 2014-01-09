package seigneurnecron.minecraftmods.core.network;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import seigneurnecron.minecraftmods.core.network.packetmapping.PacketMapping;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Seigneur Necron
 */
public abstract class PacketHandler implements IPacketHandler {
	
	// Fields :
	
	protected final List<PacketMapping> packetsMaps = new LinkedList<PacketMapping>();
	
	// Methods :
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if(packet != null && packet.channel != null && packet.data != null) {
			for(PacketMapping map : this.packetsMaps) {
				if(packet.channel.equals(map.getChanel()) && packet.length >= map.minPacketLenght()) {
					map.handlePacket(manager, packet, (EntityPlayer) player);
				}
			}
		}
	}
	
}
