package seigneurnecron.minecraftmods.stargate.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;

public class StargateConnectionHandler implements IConnectionHandler {
	
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		// Server side.
	}
	
	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		// Server side.
		return null;
	}
	
	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
		// Client side.
	}
	
	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
		// Client side.
	}
	
	@Override
	public void connectionClosed(INetworkManager manager) {
		// All sides.
	}
	
	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
		// Client side.
	}
	
}
