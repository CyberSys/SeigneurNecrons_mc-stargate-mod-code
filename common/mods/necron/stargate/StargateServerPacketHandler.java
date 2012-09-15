package mods.necron.stargate;

import java.util.LinkedList;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.WorldServer;
import cpw.mods.fml.common.network.Player;

public class StargateServerPacketHandler extends StargatePacketHandler {
	
	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		// La longueur du packet doit etre au moins de 20 (id + dim + x + y + z).
		if(packet != null && packet.data != null && packet.length >= 20) {
			LinkedList<Byte> list = TileEntityStargate.arrayToList(packet.data);
			int id = TileEntityStargate.readInt(list);
			
			if(id == TileEntityStargate.packetId_CloseGuiTeleporter || id == TileEntityStargate.packetId_CloseGuiDhd || id == TileEntityStargate.packetId_CloseGuiDetector) {
				// Cas ou le packet proviens de la fermeture d'une interface :
				/*DEBUG*///StargateMod.debug("Server: packet recu - fermeture d'une interface", true);
				int dim = TileEntityStargate.readInt(list);
				int x = TileEntityStargate.readInt(list);
				int y = TileEntityStargate.readInt(list);
				int z = TileEntityStargate.readInt(list);
				
				this.updateTileEntityGui(manager, packet, dim, x, y, z);
			}
		}
	}
	
	private void updateTileEntityGui(NetworkManager manager, Packet250CustomPayload packet, int dim, int x, int y, int z) {
		WorldServer world = ModLoader.getMinecraftServerInstance().worldServerForDimension(dim);
		if(world != null) {
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof TileEntityGui) {
				TileEntityGui tileEntityGui = (TileEntityGui) tileEntity;
				tileEntityGui.onDataPacket(manager, packet);
				tileEntityGui.setEditable(true);
			}
		}
	}
	
}
