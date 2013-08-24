package seigneurnecron.minecraftmods.stargate.network;

import java.util.LinkedList;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import seigneurnecron.minecraftmods.stargate.tileentity.TileEntityGuiScreen;
import cpw.mods.fml.common.network.Player;

/**
 * @author Seigneur Necron
 */
public class StargateServerPacketHandler extends StargatePacketHandler {
	
	@Override
	protected void handlePacket(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		LinkedList<Byte> list = arrayToList(packet.data);
		int id = readInt(list);
		
		// Case of a gui closed packet.
		if(isGuiClosedPaketId(id)) {
			updateTileEntityGui(manager, packet);
		}
	}
	
	private static void updateTileEntityGui(INetworkManager manager, Packet250CustomPayload packet) {
		LinkedList<Byte> list = arrayToList(packet.data);
		readInt(list); // id
		int dim = readInt(list);
		int x = readInt(list);
		int y = readInt(list);
		int z = readInt(list);
		
		WorldServer world = ModLoader.getMinecraftServerInstance().worldServerForDimension(dim);
		if(world != null) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityGuiScreen) {
				TileEntityGuiScreen tileEntityGui = (TileEntityGuiScreen) tileEntity;
				tileEntityGui.onDataPacket(manager, packet);
				tileEntityGui.setEditable(true);
			}
		}
	}
	
}
