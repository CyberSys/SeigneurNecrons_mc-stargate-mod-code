package seigneurnecron.minecraftmods.stargate.client.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import seigneurnecron.minecraftmods.stargate.StargateMod;
import seigneurnecron.minecraftmods.stargate.network.StargateConnectionHandler;
import seigneurnecron.minecraftmods.stargate.playerData.PlayerData;

public class StargateClientConnectionHandler extends StargateConnectionHandler {
	
	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
		StargateMod.debug("############################## Client loged in. ##############################", true);
		PlayerData.loadPlayerData();
	}
	
}
