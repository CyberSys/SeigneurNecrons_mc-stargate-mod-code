package seigneurnecron.minecraftmods.stargate.client.network;

import java.util.LinkedList;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import seigneurnecron.minecraftmods.stargate.network.StargatePacketHandler;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityStargate;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Seigneur Necron
 */
@SideOnly(Side.CLIENT)
public class StargateClientPacketHandler extends StargatePacketHandler {
	
	@Override
	protected void handlePacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		LinkedList<Byte> list = arrayToList(packet.data);
		int id = readInt(list);
		
		// Case of a tile entity update packet.
		if(isTileEntityUpdatePaketId(id)) {
			handleTileEntityUpdate(manager, packet);
		}
	}
	
	private static void handleTileEntityUpdate(INetworkManager manager, Packet250CustomPayload packet) {
		LinkedList<Byte> list = arrayToList(packet.data);
		readInt(list); // id
		int dim = readInt(list);
		int x = readInt(list);
		int y = readInt(list);
		int z = readInt(list);
		
		WorldClient world = ModLoader.getMinecraftInstance().theWorld;
		if(world != null && world.provider.dimensionId == dim) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityStargate) {
				((TileEntityStargate) tileEntity).onDataPacket(manager, packet);
			}
		}
	}
	
}
