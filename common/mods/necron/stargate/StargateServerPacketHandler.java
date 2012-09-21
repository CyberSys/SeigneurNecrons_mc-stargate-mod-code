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
		// On verifie que le packet est bien un packet de ce mode.
		if(packet != null && packet.channel.equals(StargateMod.chanel)) {
			this.handlePacket(manager, packet, player);
		}
	}
	
	private void handlePacket(NetworkManager manager, Packet250CustomPayload packet, Player player) {
		// La longueur du packet doit etre au moins de 4 (int id).
		if(packet.data != null && packet.length >= 4) {
			LinkedList<Byte> list = arrayToList(packet.data);
			int id = readInt(list);
			
			if(id == packetId_CloseGuiTeleporter || id == packetId_CloseGuiDhd || id == packetId_CloseGuiDetector) {
				// Cas ou le packet proviens de la fermeture d'une interface.
				this.updateTileEntityGui(manager, packet);
			}
		}
	}
	
	private void updateTileEntityGui(NetworkManager manager, Packet250CustomPayload packet) {
		LinkedList<Byte> list = arrayToList(packet.data);
		int id = readInt(list);
		int dim = readInt(list);
		int x = readInt(list);
		int y = readInt(list);
		int z = readInt(list);
		
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
